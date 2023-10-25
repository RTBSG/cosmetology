package com.cosmetology.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * (BeautyItems)表实体类
 *
 * @author makejava
 * @since 2023-10-30 16:26:14
 */
@SuppressWarnings("serial")
@Data
public class BeautyItems extends Model<BeautyItems> {
	//项目id
	private Integer itemId;
	//美容项目名称
	private String itemName;
	//美容项目类型
	private String itemType;
	//美容项目时长（分钟）
	private Integer itemDuration;
	//美容项目状态
	private Integer itemStatus;
	//美容项目具体描述
	private String itemDescribe;
	//默认美容项目图片地址
	private String itemDefaultImageUrl;
	//父项目id
	private Integer parentId;
	//是否推荐
	private Integer isHot;
	//售价
	private Double salePrice;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;


	/**
	 * 获取主键值
	 *
	 * @return 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.itemId;
	}
}

