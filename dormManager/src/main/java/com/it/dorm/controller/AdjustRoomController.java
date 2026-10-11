package com.it.dorm.controller;


import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.common.pojo.TableDataInfo;
import com.it.dorm.domain.AdjustRoom;
import com.it.dorm.service.IAdjustRoomService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 申请换宿Controller
 */
@RestController
@RequestMapping("/adjustRoom")
public class AdjustRoomController extends BaseController {
    @Resource
    private IAdjustRoomService adjustRoomService;

    /**
     * 查询申请换宿列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('adjust:adjust:list')")
    public TableDataInfo list(AdjustRoom adjustRoom) {
        startPage();
        List<AdjustRoom> list = adjustRoomService.selectAdjustRoomList(adjustRoom);
        return getDataTable(list);
    }

    /**
     * 获取申请换宿详细信息
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('adjust:adjust:query')")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(adjustRoomService.selectAdjustRoomById(id));
    }

    /**
     * 新增申请换宿
     */
    @PostMapping
    @PreAuthorize("hasAuthority('adjust:adjust:add')")
    public AjaxResult add(@RequestBody AdjustRoom adjustRoom) {
        return toAjax(adjustRoomService.insertAdjustRoom(adjustRoom));
    }

    /**
     * 修改申请换宿
     */
    @PutMapping
    @PreAuthorize("hasAuthority('adjust:adjust:edit')")
    public AjaxResult edit(@RequestBody AdjustRoom adjustRoom) {
        return toAjax(adjustRoomService.updateAdjustRoom(adjustRoom));
    }

    /**
     * 删除申请换宿
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('adjust:adjust:remove')")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(adjustRoomService.deleteAdjustRoomByIds(ids));
    }

    /**
     * 新增申请换宿
     */
    @PostMapping("/change")
    @PreAuthorize("hasAuthority('adjust:adjust:edit')")
    public AjaxResult change(@RequestBody AdjustRoom adjustRoom) {
        return toAjax(adjustRoomService.change(adjustRoom));
    }
}
