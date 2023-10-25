package com.cosmetology.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * (BeautyItems)实体类
 *
 * @author makejava
 * @since 2023-10-25 12:47:28
 */
@Data
@ApiModel(value="美容项目表", description="美容项目的种类信息")
public class BeautyItems implements Serializable {
    private static final long serialVersionUID = 746448643748763790L;
    @ApiModelProperty(value = "项目ID")
    private Integer itemId;
    /**
     * 美容项目名称
     */
    @ApiModelProperty(value = "预约ID")
    private String itemName;
    /**
     * 美容项目类型
     */
    @ApiModelProperty(value = "美容项目类型")
    private String itemType;
    /**
     * 美容项目时长（分钟）
     */
    @ApiModelProperty(value = "美容项目时长（分钟）")
    private Integer itemTime;
    /**
     * 美容项目状态
     */
    @ApiModelProperty(value = "美容项目状态0->正常;1->已下线;")
    private Integer itemStatus;
    /**
     * 美容项目具体描述
     */
    @ApiModelProperty(value = "美容项目具体描述")
    private String itemDescribe;
    /**
     * 默认美容项目图片地址
     */
    @ApiModelProperty(value = "默认美容项目图片地址")
    private String itemDefaultImageUrl;

    @ApiModelProperty(value = "美容新项目子节点")
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<BeautyItems> children;

    @ApiModelProperty(value = "父项目ID")
    private Integer parentCid;


}

