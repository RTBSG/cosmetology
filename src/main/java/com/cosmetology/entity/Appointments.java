package com.cosmetology.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (Appointments)表实体类
 *
 * @author makejava
 * @since 2023-10-25 12:01:48
 */
@SuppressWarnings("serial")
@Data
@ApiModel(value="预约信息表", description="用户预约信息 预约的美容项目")
public class Appointments extends Model<Appointments> {
	//预约ID
	@ApiModelProperty(value = "预约ID")
	private Integer appointmentId;
	//用户ID
	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	//美容师ID
	@ApiModelProperty(value = "美容师ID")
	private Integer beauticianId;
	//预约时间
	@ApiModelProperty(value = "预约时间")
	private Date dateAndTime;
	//预约状态 0->待处理；1->已确认；2->已取消
	@ApiModelProperty(value = "预约状态 0->待处理；1->已确认；2->已取消")
	private String status;
	@ApiModelProperty(value = "美容项目")
	private Integer beautyItemId;

	/**
	 * 获取主键值
	 *
	 * @return 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.appointmentId;
	}
}

