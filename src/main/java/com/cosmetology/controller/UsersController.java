package com.cosmetology.controller;

import com.cosmetology.entity.Users;
import com.cosmetology.enums.BizCodeEnums;
import com.cosmetology.exception.PhoneException;
import com.cosmetology.service.UsersService;
import com.cosmetology.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.cosmetology.constant.AuthServerConstant.LOGIN_USER;

/**
 * (Users)表控制层
 *
 * @author makejava
 * @since 2023-10-25 12:03:08
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("users")
public class UsersController  {
	/**
	 * 服务对象
	 */
	@Resource
	private UsersService usersService;
	@Resource
	StringRedisTemplate stringRedisTemplate;

	@PostMapping("/login")
	@ApiOperation("用户登陆")
	public R login(@RequestBody Users userInfo,HttpServletResponse response) {
		Users login =usersService.login(userInfo);
		if (login == null) {
			return R.error(BizCodeEnums.LOGINACCT_PASSWORD_EXCEPTION.getCode(), BizCodeEnums.LOGINACCT_PASSWORD_EXCEPTION.getMsg());
		}
		String token = UUID.randomUUID().toString().replace("-", "");
		stringRedisTemplate.opsForValue().set(LOGIN_USER+token, String.valueOf(login.getUserId()), 24, TimeUnit.HOURS);
		response.addHeader("Authorization", token);
		return R.ok().setData(login);
	}

	@PostMapping("/regist")
	@ApiOperation("用户注册")
	public R regist(@RequestBody Users registerVo) {

		try {
			usersService.regist(registerVo);
		} catch (PhoneException phoneException) {
			return R.error(BizCodeEnums.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnums.PHONE_EXIST_EXCEPTION.getMsg());
		}
		return R.ok();
	}


	/**
	 * 通过主键查询单条数据
	 *
	 * @param id 主键
	 * @return 单条数据
	 */
	@ApiOperation("查询单个用户信息")
	@GetMapping("{id}")
	public R selectOne(@PathVariable Serializable id) {
		Users users = this.usersService.getById(id);
		return  R.ok().setData(users);

	}

	/**
	 * 新增数据
	 *
	 * @param users 实体对象
	 * @return 新增结果
	 */
	@ApiOperation("新增用户信息")
	@PostMapping
	public R insert(@RequestBody Users users) {
		return R.ok().setData(this.usersService.save(users));
	}

	/**
	 * 修改数据
	 *
	 * @param users 实体对象
	 * @return 修改结果
	 */
	@PutMapping
	@ApiOperation("更新用户信息")
	public R update(@RequestBody Users users) {
		return R.ok().setData(this.usersService.updateById(users));
	}

	/**
	 * 删除数据
	 *
	 * @param idList 主键结合
	 * @return 删除结果
	 */
	@ApiOperation("删除用户信息")
	@DeleteMapping
	public R delete(@RequestParam("idList") List<Long> idList) {
		return R.ok().setData(this.usersService.removeByIds(idList));
	}
}

