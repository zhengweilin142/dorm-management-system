package com.it.dorm.controller;

import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.common.pojo.TableDataInfo;
import com.it.dorm.domain.Visitor;
import com.it.dorm.service.IVisitorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 到访Controller
 */
@RestController
@RequestMapping("/visitor")
public class VisitorController extends BaseController {
    @Resource
    private IVisitorService visitorService;

    /**
     * 查询到访列表
     */
    @PreAuthorize("hasAuthority('visitor:visitor:list')")
    @GetMapping("/list")
    public TableDataInfo list(Visitor visitor) {
        startPage();
        List<Visitor> list = visitorService.selectVisitorList(visitor);
        return getDataTable(list);
    }


    /**
     * 获取到访详细信息
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('visitor:visitor:query')")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(visitorService.selectVisitorById(id));
    }

    /**
     * 新增到访
     */
    @PostMapping
    @PreAuthorize("hasAuthority('visitor:visitor:add')")
    public AjaxResult add(@RequestBody Visitor visitor) {
        return toAjax(visitorService.insertVisitor(visitor));
    }

    /**
     * 修改到访
     */
    @PutMapping
    @PreAuthorize("hasAuthority('visitor:visitor:edit')")
    public AjaxResult edit(@RequestBody Visitor visitor) {
        return toAjax(visitorService.updateVisitor(visitor));
    }

    /**
     * 删除到访
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('visitor:visitor:remove')")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(visitorService.deleteVisitorByIds(ids));
    }
}
