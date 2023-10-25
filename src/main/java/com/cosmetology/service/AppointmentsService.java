package com.cosmetology.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cosmetology.entity.Appointments;
import com.cosmetology.vo.AppointmentsVO;

import java.util.List;

/**
 * (Appointments)表服务接口
 *
 * @author makejava
 * @since 2023-10-25 12:01:48
 */
public interface AppointmentsService extends IService<Appointments> {

	void saveAppointments(Appointments appointments);

	List<AppointmentsVO> selectAppointments(Integer userId);
}

