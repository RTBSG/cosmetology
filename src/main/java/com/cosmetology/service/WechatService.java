package com.cosmetology.service;

import com.cosmetology.dto.UserLoginRequestDto;
import com.cosmetology.entity.Users;
import com.cosmetology.vo.LoginVo;
import org.apache.ibatis.annotations.Param;

public interface WechatService {
	/**
	 * 获取openid
	 * @param code  登录凭证
	 * @return
	 * @throws
	 */
	public String getOpenid(String code) ;

	/**
	 * 获取手机号
	 * @param code  手机号凭证
	 * @return
	 * @throws
	 */
	public String getPhone(String code);

	LoginVo login(UserLoginRequestDto userLoginRequestDto);



}
