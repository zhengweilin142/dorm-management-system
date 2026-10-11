package com.it.dorm.controller;


import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.common.pojo.TableDataInfo;
import com.it.dorm.domain.DormBuild;
import com.it.dorm.service.IDormBuildService;
import com.it.dorm.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 宿舍楼Controller
 */
@RestController
@RequestMapping("/dormBuild")
public class DormBuildController extends BaseController {
    @Resource
    private IDormBuildService dormBuildService;

    /**
     * 查询宿舍楼列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('dorm:build:list')")
    public TableDataInfo list(DormBuild dormBuild) {
        startPage();
        List<DormBuild> list = dormBuildService.selectDormBuildList(dormBuild);
        return getDataTable(list);
    }

    /**
     * 查询宿舍楼列表
     */
    @GetMapping("/listAll")
    @PreAuthorize("hasAuthority('dorm:build:list')")
    public AjaxResult listAll(DormBuild dormBuild) {

        List<DormBuild> list = dormBuildService.selectDormBuildList(dormBuild);
        return success(list);
    }


    /**
     * 获取宿舍楼详细信息
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('dorm:build:query')")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        DormBuild info = dormBuildService.selectDormBuildById(id);
        if (StringUtils.isNull(info)) {
            return error("信息不存在");
        }
        return success(info);
    }

    /**
     * 新增宿舍楼
     */
    @PostMapping
    @PreAuthorize("hasAuthority('dorm:build:add')")
    public AjaxResult add(@RequestBody DormBuild dormBuild) {
        return toAjax(dormBuildService.insertDormBuild(dormBuild));
    }

    /**
     * 修改宿舍楼
     */
    @PutMapping
    @PreAuthorize("hasAuthority('dorm:build:edit')")
    public AjaxResult edit(@RequestBody DormBuild dormBuild) {
        DormBuild info = dormBuildService.selectDormBuildById(dormBuild.getId());
        if (StringUtils.isNull(info)) {
            return error("信息不存在");
        }
        return toAjax(dormBuildService.updateDormBuild(dormBuild));
    }

    /**
     * 删除宿舍楼
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('dorm:build:remove')")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(dormBuildService.deleteDormBuildByIds(ids));
    }
}
