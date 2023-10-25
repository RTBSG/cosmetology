package com.cosmetology.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cosmetology.entity.Appointments;
import com.cosmetology.service.AppointmentsService;
import com.cosmetology.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Appointments)表控制层
 *
 * @author makejava
 * @since 2023-10-25 12:01:47
 */
@RestController
@RequestMapping("appointments")
@Api(tags = "美容接口")
public class AppointmentsController extends ApiController {
	/**
	 * 服务对象
	 */
	@Resource
	private AppointmentsService appointmentsService;

	/**
	 * 分页查询所有数据
	 *
	 * @param page         分页对象
	 * @param appointments 查询实体
	 * @return 所有数据
	 */
	@ApiOperation("分页查询用户预约信息")
	@GetMapping
	public R selectAll(Page<Appointments> page, Appointments appointments) {
		return R.ok().setData(this.appointmentsService.page(page, new QueryWrapper<>(appointments)));
	}

	/**
	 * 通过主键查询单条数据
	 *
	 * @param id 主键
	 * @return 单条数据
	 */
	@GetMapping("{id}")
	@ApiOperation("查询单一用户预约信息")
	public R selectOne(@PathVariable Serializable id) {
		return R.ok().setData(this.appointmentsService.getById(id));
	}

	/**
	 * 新增数据
	 *
	 * @param appointments 实体对象
	 * @return 新增结果
	 */
	@PostMapping
	@ApiOperation("新增用户预约信息")
	public R insert(@RequestBody Appointments appointments) {
		return R.ok().setData(this.appointmentsService.save(appointments));
	}

	/**
	 * 修改数据
	 *
	 * @param appointments 实体对象
	 * @return 修改结果
	 */
	@PutMapping
	@ApiOperation("更新用户预约信息")
	public R update(@RequestBody Appointments appointments) {
		return R.ok().setData(this.appointmentsService.updateById(appointments));
	}

	/**
	 * 删除数据
	 *
	 * @param idList 主键结合
	 * @return 删除结果
	 */
	@DeleteMapping
	@ApiOperation("删除用户预约信息")
	public R delete(@RequestParam("idList") List<Long> idList) {
		return R.ok().setData(this.appointmentsService.removeByIds(idList));
	}
}

