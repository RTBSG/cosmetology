package com.cosmetology.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AppointmentsVO {
	@ApiModelProperty(value = "预约ID")
	private Integer appointmentId;
	//用户ID
	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	//美容师ID
	@ApiModelProperty(value = "美容师ID")
	private Integer beauticianId;
	//预约时间
	@ApiModelProperty(value = "预约日期")
	private String appointmentData;
	@ApiModelProperty(value = "预约时间")
	private String appointmentTime;
	@ApiModelProperty(value = "联系方式")
	private String appointmentPhone;
	//预约状态 0->待处理；1->已确认；2->已取消
	@ApiModelProperty(value = "预约状态 0->待处理；1->已确认；2->已取消")
	private Integer status;
	@ApiModelProperty(value = "美容项目")
	private Integer beautyItemId;

	@ApiModelProperty(value = "美容项目名称")
	private String itemName;
	@ApiModelProperty(value = "美容项目类型")
	private String itemType;

	@ApiModelProperty(value = "美容项目时长（分钟）")
	private Integer itemDuration;

	@ApiModelProperty(value = "美容项目状态")
	private Integer itemStatus;

	@ApiModelProperty(value = "美容项目具体描述")
	private String itemDescribe;

	@ApiModelProperty(value = "默认美容项目图片地址")
	private String itemDefaultImageUrl;


	@ApiModelProperty(value = "是否推荐")
	private Integer isHot;

	@ApiModelProperty(value = "售价")
	private BigDecimal salePrice;

}
