package com.cosmetology.controller;

import com.alibaba.fastjson.JSON;
import com.cosmetology.entity.Users;
import com.cosmetology.service.UsersService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.cosmetology.constant.AuthServerConstant.LOGIN_USER;

@Controller
public class WeiXinLoginController {
	@Value("${wechat.appid}")
	private String appId;
	@Value("${wechat.secret}")
	private String secret;
	@Value("${wechat.redirecturi}")
	private String redirectUrl;

	@Resource
	StringRedisTemplate stringRedisTemplate;
	@Resource
	UsersService usersService;

	@GetMapping("weChat/login/getAccessToken")
	public String getAccessToken(@RequestParam("code") String code) {
		try {
			redirectUrl = URLEncoder.encode((redirectUrl), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		String url = "https://open.weixin.qq.com/connect/qrconnect?appid=" + appId + "&redirect_uri=" + redirectUrl
				+ "&response_type="+code+"&scope=snsapi_login&state=STATE#wechat_redirect";
		return "redirect:" + url;
	}
	@GetMapping("weChat/login")
	public String wechatLogin(@RequestParam("code") String code,HttpServletResponse res) throws IOException {

		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();

		Response response = client.newCall(request).execute();


		if (response.isSuccessful()) {
			// 解析响应，获取用户信息
			String  responseBody = response.body().string();
			Map accessTokenMap = JSON.parseObject(responseBody,HashMap.class);
			String accessToken = (String) accessTokenMap.get("access_token");
			String openId = (String) accessTokenMap.get("openid"); // 微信用户唯一标识

			// 拼接获取用户基本信息的url
			String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId;

			Request requestUserInfo = new Request.Builder().url(userInfoUrl).build();
			Response responseUserInfo =  client.newCall(requestUserInfo).execute();
			String  userInfo = responseUserInfo.body().string();
			Map userInfoMap = JSON.parseObject(userInfo,HashMap.class);
			// 将微信登录用户进行注册
			String headImgUrl = (String) userInfoMap.get("headimgurl"); // 用户头像
			String nickName = (String) userInfoMap.get("nickname"); // 用户昵称
			Integer sex = (Integer) userInfoMap.get("sex");
			String province = (String) userInfoMap.get("province");
			String city = (String) userInfoMap.get("city");
			Users users = new Users();
			users.setAddress(province + city);
			users.setSex(sex == 1);
			users.setUsername(nickName);
			users.setHeadImgUrl(headImgUrl);
			stringRedisTemplate.opsForValue().set(LOGIN_USER+":"+nickName, accessToken);
			stringRedisTemplate.expire(LOGIN_USER + ":" + nickName, 24, TimeUnit.HOURS);
			usersService.save(users);
			res.addHeader("Authorization", accessToken);
			return "redirect:http://localhost:8080/";
		}else{
			  return "redirect:http://localhost:8080/users/login";
		}

	}
}

