package com.cosmetology.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (Users)表实体类
 *
 * @author makejava
 * @since 2023-10-25 12:03:08
 */
@SuppressWarnings("serial")
@Data
@ApiModel(value="用户信息表", description="用户的详细信息")
public class Users extends Model<Users> {
	@ApiModelProperty(value = "用户id")
	@TableId(type = IdType.AUTO)
	private Integer userId;
	@ApiModelProperty(value = "用户名")
	private String userName;
	@ApiModelProperty(value = "用户年龄")
	private Integer age;
	@ApiModelProperty(value = "用户性别")
	private Boolean sex;
	@ApiModelProperty(value = "用户密码hash")
	private String passwordHash;
	@ApiModelProperty(value = "用户邮箱")
	private String email;
	@ApiModelProperty(value = "用户手机号码")
	private String phoneNumber;
	@ApiModelProperty(value = "用户地址")
	private String address;
	@ApiModelProperty(value = "用户状态")
	private Boolean status;
	@ApiModelProperty(value = "用户头像")
	private String headImgUrl;
	@ApiModelProperty(value = "微信openId")
	private String openId;




	/**
	 * 获取主键值
	 *
	 * @return 主键值
	 */
	@Override
	protected Serializable pkVal() {
		return this.userId;
	}
}

