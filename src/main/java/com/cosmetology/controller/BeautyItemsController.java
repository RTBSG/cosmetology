package com.cosmetology.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cosmetology.entity.BeautyItems;
import com.cosmetology.entity.Users;
import com.cosmetology.service.BeautyItemsService;
import com.cosmetology.utils.R;
import io.swagger.models.auth.In;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.cosmetology.constant.AuthServerConstant.LOGIN_USER;

/**
 * (BeautyItems)表控制层
 *
 * @author makejava
 * @since 2023-10-30 16:26:14
 */
@RestController
@RequestMapping("beautyItems")
public class BeautyItemsController  {
	/**
	 * 服务对象
	 */
	@Resource
	private BeautyItemsService beautyItemsService;
	@Resource
	StringRedisTemplate stringRedisTemplate;
	Integer count = 1;

	/**
	 * 分页查询所有数据
	 *
	 * @param page        分页对象
	 * @param beautyItems 查询实体
	 * @return 所有数据
	 */
	@GetMapping("/page/selectAll")
	public R selectAll(Page<BeautyItems> page, BeautyItems beautyItems) {
		return R.ok().setData(this.beautyItemsService.page(page, new QueryWrapper<>(beautyItems)));
	}
	@GetMapping("/selectAll")
	public R selectAll(HttpServletResponse response ) {
		count = count++;
		if (count ==1) {
			login(response);

		}
		return R.ok().setData(this.beautyItemsService.list());
	}

	/**
	 * 通过主键查询单条数据
	 *
	 * @param id 主键
	 * @return 单条数据
	 */
	@GetMapping("/select/{id}")
	public R selectOne(@PathVariable Integer id) {


		return R.ok().setData(this.beautyItemsService.getById(id));
	}

	public void login(HttpServletResponse response){
		String token = UUID.randomUUID().toString().replace("-", "");
		stringRedisTemplate.opsForValue().set(LOGIN_USER+token,"1",24,TimeUnit.HOURS);
		response.addHeader("Authorization", token);
	}
	/**
	 * 新增数据
	 *
	 * @param beautyItems 实体对象
	 * @return 新增结果
	 */
	@PostMapping("/insert")
	public R insert(@RequestBody BeautyItems beautyItems) {
		return R.ok().setData(this.beautyItemsService.save(beautyItems));
	}

	/**
	 * 修改数据
	 *
	 * @param beautyItems 实体对象
	 * @return 修改结果
	 */
	@PutMapping("/update")
	public R update(@RequestBody BeautyItems beautyItems) {
		return R.ok().setData(this.beautyItemsService.updateById(beautyItems));
	}

	/**
	 * 删除数据
	 *
	 * @param idList 主键结合
	 * @return 删除结果
	 */
	@DeleteMapping("/delete")
	public R delete(@RequestParam("idList") List<Long> idList) {
		return R.ok().setData(this.beautyItemsService.removeByIds(idList));
	}
}

