package com.it.dorm.controller;


import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.common.pojo.TableDataInfo;
import com.it.dorm.domain.Repair;
import com.it.dorm.service.IRepairService;
import com.it.dorm.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 保修Controller
 */
@RestController
@RequestMapping("/repair")
public class RepairController extends BaseController {
    @Resource
    private IRepairService repairService;

    /**
     * 查询保修列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('message:repair:list')")
    public TableDataInfo list(Repair repair) {
        startPage();
        List<Repair> list = repairService.selectRepairList(repair);
        return getDataTable(list);
    }

    /**
     * 获取保修详细信息
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('message:repair:query')")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        Repair info = repairService.selectRepairById(id);
        if (StringUtils.isNull(info)) {
            return error("信息不存在");
        }
        return success(info);
    }

    /**
     * 新增保修
     */
    @PostMapping
    @PreAuthorize("hasAuthority('message:repair:add')")
    public AjaxResult add(@RequestBody Repair repair) {
        return toAjax(repairService.insertRepair(repair));
    }

    /**
     * 修改保修
     */
    @PutMapping
    @PreAuthorize("hasAuthority('message:repair:edit')")
    public AjaxResult edit(@RequestBody Repair repair) {
        Repair info = repairService.selectRepairById(repair.getId());
        if (StringUtils.isNull(info)) {
            return error("信息不存在");
        }
        return toAjax(repairService.updateRepair(repair));
    }

    /**
     * 删除保修
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('message:repair:remove')")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(repairService.deleteRepairByIds(ids));
    }
}
