package com.cosmetology.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cosmetology.entity.BeautyItems;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (BeautyItems)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-30 16:26:14
 */
@Mapper
public interface BeautyItemsDao extends BaseMapper<BeautyItems> {

}

