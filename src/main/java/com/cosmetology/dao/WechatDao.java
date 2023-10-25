package com.cosmetology.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cosmetology.entity.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Users)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-25 12:03:08
 */
@Mapper
public interface WechatDao extends BaseMapper<Users> {

	Users getUserInfoByOpenId(String openid);
}

