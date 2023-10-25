package com.cosmetology.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cosmetology.dao.WechatDao;
import com.cosmetology.dto.UserLoginRequestDto;
import com.cosmetology.entity.Users;
import com.cosmetology.service.UsersService;
import com.cosmetology.service.WechatService;
import com.cosmetology.vo.LoginVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.cosmetology.constant.AuthServerConstant.LOGIN_USER;

@Service
public class WechatServiceImpl implements WechatService {
	// 登录
	private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";

	// 获取token
	private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

	// 获取手机号
	private static final String PHONE_REQUEST_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";

	@Value("${wechat.appid}")
	private String appId;

	@Value("${wechat.secret}")
	private String secret;
	@Resource
	WechatDao wechatDao;
	@Resource
	UsersService userService;
	@Resource
	StringRedisTemplate stringRedisTemplate;

	@Override
	public String getOpenid(String code) {
		Map<String, Object> appConfig = getAppConfig();
		appConfig.put("js_code",code);
		String result= HttpUtil.get(REQUEST_URL, appConfig);
		HashMap jsonObject = JSON.parseObject(result,HashMap.class);
		Integer errcode = (Integer)jsonObject.get("errcode");
		if (errcode != 0 ) {
			throw new RuntimeException((String) jsonObject.get("errmsg"));
		}
		return ((String) jsonObject.get("openid"));
	}

	@Override
	public String getPhone(String code) {
		Map<String, Object> requestUrlParam = getAppConfig();

		String result = HttpUtil.get(TOKEN_URL, requestUrlParam);
		//解析
		HashMap jsonObject = JSON.parseObject(result,HashMap.class);
		//如果code不正确，则失败
		if (ObjectUtil.isNotEmpty(jsonObject.get("errcode"))) {
			throw new RuntimeException((String) jsonObject.get("errmsg"));
		}
		String access_token = (String) jsonObject.get("access_token");

		String url = PHONE_REQUEST_URL + access_token;
		Map<String,Object> param = new HashMap<>();
		param.put("code",code);

		String results = HttpUtil.post(url,JSON.toJSONString(param));
		HashMap map = JSON.parseObject(results,HashMap.class);
		if ((Integer)map.get("errcode") != 0) {
			//若code不正确，则获取不到phone，响应失败
			throw new RuntimeException((String)map.get("errmsg"));

		}
		return (String)map.get("phone_info");


	}
	/**
	 * 封装公共参数
	 * @return
	 */
	private Map<String, Object> getAppConfig() {

		Map<String, Object> requestUrlParam = new HashMap<>();
		requestUrlParam.put("appid", appId);
		requestUrlParam.put("secret", secret);
		return requestUrlParam;
	}
	@Override
	public LoginVo login(UserLoginRequestDto userLoginRequestDto) {
		String openid = getOpenid(userLoginRequestDto.getCode());
		//		获取用户手机号码
		String phone = getPhone(userLoginRequestDto.getPhoneCode());

		Users user=wechatDao.getUserInfoByOpenId(openid);
//		用户不存在重新注册
		if (user==null||user.getOpenId().isEmpty()) {
			user = new Users();
			user.setPhoneNumber(phone);
			userService.regist(user);
		}
//		为用户生成token
		String token = UUID.randomUUID().toString().replace("-", "");
		stringRedisTemplate.opsForValue().set(LOGIN_USER+token,String.valueOf(user.getUserId()),24, TimeUnit.HOURS);
		LoginVo loginVo = new LoginVo();
		loginVo.setToken(token);
		loginVo.setNickName(user.getUserName());
		return loginVo;

	}


}
