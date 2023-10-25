package com.cosmetology.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cosmetology.dao.WechatDao;
import com.cosmetology.entity.Users;
import com.cosmetology.exception.PhoneException;
import com.cosmetology.exception.UsernameException;
import com.cosmetology.service.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * (Users)表服务实现类
 *
 * @author makejava
 * @since 2023-10-25 12:03:08
 */
@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<WechatDao, Users> implements UsersService {


	@Override
	public void regist(Users registerVo) throws PhoneException, UsernameException{

		Users users = new Users();
		//        需要验证用户名和手机的唯一 并且需要感知到到底是哪一个出问题，因此使用异常处理
		checkPhoneAndUserName(registerVo);
		users.setUserName(registerVo.getUserName());
		users.setAge(registerVo.getAge());
		users.setAddress(registerVo.getAddress());
		users.setStatus(true);
		users.setEmail(registerVo.getEmail());
		users.setPhoneNumber(registerVo.getPhoneNumber());
		//        密码加密  使用md5加盐，防止密码泄漏后彩虹表解密
		//        BCryptPasswordEncoder 每次编码 盐值是不同的 解密直接对比明文和密文即可
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String password  = bCryptPasswordEncoder.encode(registerVo.getPasswordHash());
		users.setPasswordHash(password);
		this.baseMapper.insert(users);


	}

	@Override
	public Users getUserinfoByNickName(String nickName) {
		return baseMapper.selectOne(new QueryWrapper<Users>().eq("user_name", nickName));
	}

	/**
	 * 手机号和用户名验证
	 * @param registerVo
	 * @return
	 */
	private void checkPhoneAndUserName(Users registerVo) throws PhoneException, UsernameException {
		//    验证手机号
		Integer phoneCount = this.baseMapper.selectCount(new QueryWrapper<Users>().eq("phone_number", registerVo.getPhoneNumber()));

		if (!phoneCount.equals(0)) {
			throw new PhoneException();
		}

	}

	@Override
	public Users login(Users userInfo) {
		String phoneNumber = userInfo.getPhoneNumber();
		String password = userInfo.getPasswordHash();
		//
		Users users = this.baseMapper.selectOne(new QueryWrapper<Users>().eq("phone_number", phoneNumber));
		//        对数据库查询到的加密md5 密码和用户输入的密码进行比较
		if (users == null) {
			return null;
		}
		String dataPassword = userInfo.getPasswordHash();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		boolean matches = bCryptPasswordEncoder.matches(password, dataPassword);
		if (!matches) {
			return null;
		}
		return users;
	}
}

