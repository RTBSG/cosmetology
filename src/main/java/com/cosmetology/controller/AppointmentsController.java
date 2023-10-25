package com.cosmetology.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cosmetology.entity.Appointments;
import com.cosmetology.entity.BeautyItems;
import com.cosmetology.entity.Users;
import com.cosmetology.filter.AuthenticationTokenFilter;
import com.cosmetology.service.AppointmentsService;
import com.cosmetology.service.BeautyItemsService;
import com.cosmetology.utils.R;
import com.cosmetology.vo.AppointmentsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (Appointments)表控制层
 *
 * @author makejava
 * @since 2023-10-25 12:01:47
 */
@RestController
@RequestMapping("appointments")
@Api(tags = "美容接口")
public class AppointmentsController  {
	/**
	 * 服务对象
	 */
	@Resource
	private AppointmentsService appointmentsService;
	@Resource
	BeautyItemsService beautyItemsService;

	/**
	 * 分页查询所有数据
	 *
	 * @param page         分页对象
	 * @param appointments 查询实体
	 * @return 所有数据
	 */
	@ApiOperation("分页查询用户预约信息")
	@GetMapping("/page/selectAll")
	public R selectAll(Page<Appointments> page, Appointments appointments) {
		return R.ok().setData(this.appointmentsService.page(page, new QueryWrapper<>(appointments)));
	}

	@ApiOperation("查询用户预约信息")
	@GetMapping("/selectUserInfo")
	public R selectUserInfo() {
		Users users = AuthenticationTokenFilter.usersThreadLocal.get();
		List<AppointmentsVO> userInfo = appointmentsService.selectAppointments(users.getUserId());


		return R.ok().setData(userInfo);
	}
	/**
	 * 通过主键查询单条数据
	 *
	 * @param id 主键
	 * @return 单条数据
	 */
	@GetMapping("/{id}")
	@ApiOperation("查询单一用户预约信息")
	public R selectOne(@PathVariable Integer id) {

		return R.ok().setData(this.appointmentsService.getById(id));
	}

	/**
	 * 新增数据
	 *
	 * @param appointments 实体对象
	 * @return 新增结果
	 */
	@PostMapping("/insert/appointment")
	@ApiOperation("新增用户预约信息")
	public R insert(@RequestBody Appointments appointments) {
		appointmentsService.saveAppointments(appointments);
		return R.ok();
	}

	/**
	 * 修改数据
	 *
	 * @param appointments 实体对象
	 * @return 修改结果
	 */
	@PutMapping("update/appointment")
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
	@DeleteMapping("delete/appointment")
	@ApiOperation("删除用户预约信息")
	public R delete(@RequestParam("idList") List<Long> idList) {
		return R.ok().setData(this.appointmentsService.removeByIds(idList));
	}
}

