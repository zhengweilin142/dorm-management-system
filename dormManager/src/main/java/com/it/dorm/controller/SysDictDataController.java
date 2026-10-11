package com.it.dorm.controller;


import com.it.dorm.domain.SysDictData;
import com.it.dorm.service.ISysDictDataService;
import com.it.dorm.service.ISysDictTypeService;
import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.common.pojo.TableDataInfo;
import com.it.dorm.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 
 * @date 2024-11-03
 */
@RestController
@RequestMapping("/system/dict/data")
@Api(value = "数据字典管理", tags = "数据字典管理")
public class SysDictDataController extends BaseController {
    @Resource
    private ISysDictDataService dictDataService;

    @Resource
    private ISysDictTypeService dictTypeService;

    //@PreAuthorize("hasAnyAuthority('system:dict:list')")
    @GetMapping("/list")
    @ApiOperation("列表")
    public TableDataInfo list(SysDictData dictData) {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    /**
     * 查询字典数据详细
     */
    //@PreAuthorize("hasAnyAuthority('system:dict:query')")
    @GetMapping(value = "/{dictCode}")
    @ApiOperation("查询字典数据详细")
    public AjaxResult getInfo(@PathVariable Long dictCode) {
        return success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    @ApiOperation("根据字典类型查询字典数据信息")
    public AjaxResult dictType(@PathVariable String dictType) {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data)) {
            data = new ArrayList<>();
        }
        return success(data);
    }

    /**
     * 新增字典类型
     */
    //@PreAuthorize("hasAnyAuthority('system:dict:add')")
    @PostMapping
    @ApiOperation("新增字典类型")
    public AjaxResult add(@RequestBody SysDictData dict) {
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    //@PreAuthorize("hasAnyAuthority('system:dict:edit')")
    @PutMapping
    @ApiOperation("修改保存字典类型")
    public AjaxResult edit(@RequestBody SysDictData dict) {

        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    //@PreAuthorize("hasAnyAuthority('system:dict:remove')")
    @DeleteMapping("/{dictCodes}")
    @ApiOperation("删除字典类型")
    public AjaxResult remove(@PathVariable Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return success();
    }
}
