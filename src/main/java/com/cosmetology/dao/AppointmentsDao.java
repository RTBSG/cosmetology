package com.cosmetology.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cosmetology.entity.Appointments;
import com.cosmetology.vo.AppointmentsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Appointments)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-25 12:01:48
 */
@Mapper
public interface AppointmentsDao extends BaseMapper<Appointments> {

	List<AppointmentsVO> selectAppointments(@Param("userId") Integer userId);
}

