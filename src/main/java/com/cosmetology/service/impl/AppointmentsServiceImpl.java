package com.cosmetology.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cosmetology.dao.AppointmentsDao;
import com.cosmetology.entity.Appointments;
import com.cosmetology.service.AppointmentsService;
import org.springframework.stereotype.Service;

/**
 * (Appointments)表服务实现类
 *
 * @author makejava
 * @since 2023-10-25 12:01:48
 */
@Service("appointmentsService")
public class AppointmentsServiceImpl extends ServiceImpl<AppointmentsDao, Appointments> implements AppointmentsService {

}

