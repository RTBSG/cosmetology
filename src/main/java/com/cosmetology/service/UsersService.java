package com.cosmetology.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cosmetology.entity.Users;
import org.apache.catalina.User;

/**
 * (Users)表服务接口
 *
 * @author makejava
 * @since 2023-10-25 12:03:08
 */
public interface UsersService extends IService<Users> {

	Users login(Users userInfo);

	void regist(Users registerVo);

	Users getUserinfoByNickName(String nickName);
}

