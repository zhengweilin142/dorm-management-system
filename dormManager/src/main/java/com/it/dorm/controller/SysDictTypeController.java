package com.it.dorm.controller;


import com.it.dorm.service.ISysDictTypeService;
import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.common.pojo.TableDataInfo;
import com.it.dorm.domain.SysDictType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典信息
 *
 
 * @date 2024-11-03
 */
@RestController
@RequestMapping("/system/dict/type")
@Api(value = "数据字典类型管理", tags = "数据字典类型管理")
public class SysDictTypeController extends BaseController {
    @Resource
    private ISysDictTypeService dictTypeService;

    @GetMapping("/list")
    @ApiOperation("列表")
    public TableDataInfo list(SysDictType dictType) {
        startPage();
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }


    /**
     * 查询字典类型详细
     */
    @GetMapping(value = "/{dictId}")
    @ApiOperation("查询字典类型详细")
    public AjaxResult getInfo(@PathVariable Long dictId) {
        return success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    //@PreAuthorize("hasAnyAuthority('system:dict:add')")
    @PostMapping
    @ApiOperation("新增字典类型")
    public AjaxResult add(@Validated @RequestBody SysDictType dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    //@PreAuthorize("hasAnyAuthority('system:dict:edit')")
    @PutMapping
    @ApiOperation("修改字典类型")
    public AjaxResult edit(@Validated @RequestBody SysDictType dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        return toAjax(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    //@PreAuthorize("hasAnyAuthority('system:dict:remove')")
    @DeleteMapping("/{dictIds}")
    @ApiOperation("删除字典类型")
    public AjaxResult remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return success();
    }

    /**
     * 刷新字典缓存
     */
    //@PreAuthorize("hasAnyAuthority('system:dict:remove')")
    @DeleteMapping("/refreshCache")
    @ApiOperation("刷新字典缓存")
    public AjaxResult refreshCache() {
        dictTypeService.resetDictCache();
        return success();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    @ApiOperation("获取字典选择框列表")
    public AjaxResult optionSelect() {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return success(dictTypes);
    }
}
