package com.it.dorm.controller;


import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.common.pojo.TableDataInfo;
import com.it.dorm.domain.DormCheck;
import com.it.dorm.service.IDormCheckService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 宿舍卫生检查，记录每次宿舍的卫生检查情况Controller
 */
@RestController
@RequestMapping("/dormCheck")
public class DormCheckController extends BaseController {
    @Resource
    private IDormCheckService dormCheckService;

    /**
     * 查询宿舍卫生检查，记录每次宿舍的卫生检查情况列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('check:check:list')")
    public TableDataInfo list(DormCheck dormCheck) {
        startPage();
        List<DormCheck> list = dormCheckService.selectDormCheckList(dormCheck);
        return getDataTable(list);
    }


    /**
     * 获取宿舍卫生检查，记录每次宿舍的卫生检查情况详细信息
     */
    @GetMapping(value = "/{checkId}")
    @PreAuthorize("hasAuthority('check:check:query')")
    public AjaxResult getInfo(@PathVariable("checkId") Long checkId) {
        return success(dormCheckService.selectDormCheckByCheckId(checkId));
    }

    /**
     * 新增宿舍卫生检查，记录每次宿舍的卫生检查情况
     */
    @PostMapping
    @PreAuthorize("hasAuthority('check:check:add')")
    public AjaxResult add(@RequestBody DormCheck dormCheck) {
        return toAjax(dormCheckService.insertDormCheck(dormCheck));
    }

    /**
     * 修改宿舍卫生检查，记录每次宿舍的卫生检查情况
     */
    @PutMapping
    @PreAuthorize("hasAuthority('check:check:edit')")
    public AjaxResult edit(@RequestBody DormCheck dormCheck) {
        return toAjax(dormCheckService.updateDormCheck(dormCheck));
    }

    /**
     * 删除宿舍卫生检查，记录每次宿舍的卫生检查情况
     */
    @DeleteMapping("/{checkIds}")
    @PreAuthorize("hasAuthority('check:check:remove')")
    public AjaxResult remove(@PathVariable Long[] checkIds) {
        return toAjax(dormCheckService.deleteDormCheckByCheckIds(checkIds));
    }
}
