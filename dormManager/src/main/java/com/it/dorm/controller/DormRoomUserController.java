package com.it.dorm.controller;


import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.common.pojo.TableDataInfo;
import com.it.dorm.domain.DormRoomUser;
import com.it.dorm.service.IDormRoomUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 宿舍人员Controller
 */
@RestController
@RequestMapping("/dormRoomUser")
public class DormRoomUserController extends BaseController {
    @Resource
    private IDormRoomUserService dormRoomUserService;

    /**
     * 查询宿舍人员列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('dorm:user:list')")
    public TableDataInfo list(DormRoomUser dormRoomUser) {
        startPage();
        List<DormRoomUser> list = dormRoomUserService.selectDormRoomUserList(dormRoomUser);
        return getDataTable(list);
    }

    /**
     * 新增宿舍人员
     */
    @PostMapping
    @PreAuthorize("hasAuthority('dorm:user:add')")
    public AjaxResult add(@RequestBody DormRoomUser dormRoomUser) {
        return toAjax(dormRoomUserService.insertDormRoomUser(dormRoomUser));
    }

    /**
     * 删除宿舍人员
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('dorm:user:remove')")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(dormRoomUserService.deleteDormRoomUserByIds(ids));
    }


    /**
     * 删除宿舍人员
     */
    @PostMapping("/remove")
    @PreAuthorize("hasAuthority('dorm:user:remove')")
    public AjaxResult removeUser(@RequestBody DormRoomUser dormRoomUser) {
        return toAjax(dormRoomUserService.removeUser(dormRoomUser));
    }
}
