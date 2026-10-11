package com.it.dorm.controller;


import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.common.pojo.TableDataInfo;
import com.it.dorm.domain.SysNotice;
import com.it.dorm.service.ISysNoticeService;
import com.it.dorm.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通知公告Controller
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
    @Resource
    private ISysNoticeService sysNoticeService;

    /**
     * 查询通知公告列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('message:notice:list')")
    public TableDataInfo list(SysNotice sysNotice) {
        startPage();
        List<SysNotice> list = sysNoticeService.selectSysNoticeList(sysNotice);
        return getDataTable(list);
    }


    /**
     * 获取通知公告详细信息
     */
    @GetMapping(value = "/{noticeId}")
    @PreAuthorize("hasAuthority('message:notice:query')")
    public AjaxResult getInfo(@PathVariable("noticeId") Long noticeId) {
        SysNotice info = sysNoticeService.selectSysNoticeByNoticeId(noticeId);
        if (StringUtils.isNull(info)) {
            return error("信息不存在");
        }
        return success(info);
    }

    /**
     * 新增通知公告
     */
    @PostMapping
    @PreAuthorize("hasAuthority('message:notice:add')")
    public AjaxResult add(@RequestBody SysNotice sysNotice) {
        return toAjax(sysNoticeService.insertSysNotice(sysNotice));
    }

    /**
     * 修改通知公告
     */
    @PutMapping
    @PreAuthorize("hasAuthority('message:notice:edit')")
    public AjaxResult edit(@RequestBody SysNotice sysNotice) {
        SysNotice info = sysNoticeService.selectSysNoticeByNoticeId(sysNotice.getNoticeId());
        if (StringUtils.isNull(info)) {
            return error("信息不存在");
        }
        return toAjax(sysNoticeService.updateSysNotice(sysNotice));
    }

    /**
     * 删除通知公告
     */
    @DeleteMapping("/{noticeIds}")
    @PreAuthorize("hasAuthority('message:notice:remove')")
    public AjaxResult remove(@PathVariable Long[] noticeIds) {
        return toAjax(sysNoticeService.deleteSysNoticeByNoticeIds(noticeIds));
    }
}
