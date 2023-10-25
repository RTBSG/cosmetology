package com.cosmetology.service.impl;

import com.cosmetology.entity.BeautyItems;
import com.cosmetology.dao.BeautyItemsDao;
import com.cosmetology.service.BeautyItemsService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (BeautyItems)表服务实现类
 *
 * @author makejava
 * @since 2023-10-25 12:47:29
 */
@Service("beautyItemsService")
public class BeautyItemsServiceImpl implements BeautyItemsService {
    @Resource
    private BeautyItemsDao beautyItemsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param itemId 主键
     * @return 实例对象
     */
    @Override
    public BeautyItems queryById(Integer itemId) {
        return this.beautyItemsDao.queryById(itemId);
    }

    /**
     * 分页查询
     *
     * @param beautyItems 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<BeautyItems> queryByPage(BeautyItems beautyItems, PageRequest pageRequest) {
        long total = this.beautyItemsDao.count(beautyItems);
        return new PageImpl<>(this.beautyItemsDao.queryAllByLimit(beautyItems, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param beautyItems 实例对象
     * @return 实例对象
     */
    @Override
    public BeautyItems insert(BeautyItems beautyItems) {
        this.beautyItemsDao.insert(beautyItems);
        return beautyItems;
    }

    /**
     * 修改数据
     *
     * @param beautyItems 实例对象
     * @return 实例对象
     */
    @Override
    public BeautyItems update(BeautyItems beautyItems) {
        this.beautyItemsDao.update(beautyItems);
        return this.queryById(beautyItems.getItemId());
    }

    /**
     * 通过主键删除数据
     *
     * @param itemId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer itemId) {
        return this.beautyItemsDao.deleteById(itemId) > 0;
    }
}
