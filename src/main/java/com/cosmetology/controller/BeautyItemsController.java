package com.cosmetology.controller;

import com.cosmetology.entity.BeautyItems;
import com.cosmetology.service.BeautyItemsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (BeautyItems)表控制层
 *
 * @author makejava
 * @since 2023-10-25 12:47:28
 */
@RestController
@RequestMapping("beautyItems")
public class BeautyItemsController {
    /**
     * 服务对象
     */
    @Resource
    private BeautyItemsService beautyItemsService;

    /**
     * 分页查询
     *
     * @param beautyItems 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<BeautyItems>> queryByPage(BeautyItems beautyItems, PageRequest pageRequest) {
        return ResponseEntity.ok(this.beautyItemsService.queryByPage(beautyItems, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<BeautyItems> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.beautyItemsService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param beautyItems 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<BeautyItems> add(BeautyItems beautyItems) {
        return ResponseEntity.ok(this.beautyItemsService.insert(beautyItems));
    }

    /**
     * 编辑数据
     *
     * @param beautyItems 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<BeautyItems> edit(BeautyItems beautyItems) {
        return ResponseEntity.ok(this.beautyItemsService.update(beautyItems));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.beautyItemsService.deleteById(id));
    }

}

