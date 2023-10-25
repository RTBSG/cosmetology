package com.cosmetology.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cosmetology.dao.AppointmentsDao;
import com.cosmetology.entity.Appointments;
import com.cosmetology.entity.Users;
import com.cosmetology.enums.StatusEnums;
import com.cosmetology.filter.AuthenticationTokenFilter;
import com.cosmetology.service.AppointmentsService;
import com.cosmetology.vo.AppointmentsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * (Appointments)表服务实现类
 *
 * @author makejava
 * @since 2023-10-25 12:01:48
 */
@Service("appointmentsService")
public class AppointmentsServiceImpl extends ServiceImpl<AppointmentsDao, Appointments> implements AppointmentsService {
	@Resource
	AppointmentsDao appointmentsDao;

	@Override
	public void saveAppointments(Appointments appointments) {
		Users users = AuthenticationTokenFilter.usersThreadLocal.get();
		appointments.setStatus(StatusEnums.STATUS_ENUMS_0.getCode());
		appointments.setAppointmentData(appointments.getAppointmentData());
		appointments.setAppointmentTime(appointments.getAppointmentTime());
		appointments.setAppointmentPhone(appointments.getAppointmentPhone());
		appointments.setUserId(users.getUserId());
		appointments.setBeautyItemId(appointments.getBeautyItemId());
		appointments.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		appointments.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		List<Appointments> appointmentsList = this.list(new QueryWrapper<Appointments>().eq("appointment_data", appointments.getAppointmentData()).eq("appointment_time", appointments.getAppointmentTime()));
		if (appointmentsList != null &&appointmentsList.size() > 0) {
			throw new RuntimeException("您已经预定过了请去个人中心查看");
		}
		this.save(appointments);
	}

	@Override
	public List<AppointmentsVO> selectAppointments(Integer userId) {
		return  appointmentsDao.selectAppointments(userId);


	}
}

