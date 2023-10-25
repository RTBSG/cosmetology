package com.cosmetology.service;

import com.cosmetology.entity.BeautyItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (BeautyItems)表服务接口
 *
 * @author makejava
 * @since 2023-10-25 12:47:29
 */
public interface BeautyItemsService {

    /**
     * 通过ID查询单条数据
     *
     * @param itemId 主键
     * @return 实例对象
     */
    BeautyItems queryById(Integer itemId);

    /**
     * 分页查询
     *
     * @param beautyItems 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<BeautyItems> queryByPage(BeautyItems beautyItems, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param beautyItems 实例对象
     * @return 实例对象
     */
    BeautyItems insert(BeautyItems beautyItems);

    /**
     * 修改数据
     *
     * @param beautyItems 实例对象
     * @return 实例对象
     */
    BeautyItems update(BeautyItems beautyItems);

    /**
     * 通过主键删除数据
     *
     * @param itemId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer itemId);

}
