package com.cosmetology.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cosmetology.dao.BeautyItemsDao;
import com.cosmetology.entity.BeautyItems;
import com.cosmetology.service.BeautyItemsService;
import org.springframework.stereotype.Service;

/**
 * (BeautyItems)表服务实现类
 *
 * @author makejava
 * @since 2023-10-30 16:26:15
 */
@Service("beautyItemsService")
public class BeautyItemsServiceImpl extends ServiceImpl<BeautyItemsDao, BeautyItems> implements BeautyItemsService {

}

