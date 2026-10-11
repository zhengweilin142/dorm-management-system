package com.it.dorm.controller;


import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.common.pojo.TableDataInfo;
import com.it.dorm.domain.DormRoom;
import com.it.dorm.service.IDormRoomService;
import com.it.dorm.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 宿舍Controller
 */
@RestController
@RequestMapping("/dormRoom")
public class DormRoomController extends BaseController {
    @Resource
    private IDormRoomService dormRoomService;

    /**
     * 查询宿舍列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('dorm:room:list')")
    public TableDataInfo list(DormRoom dormRoom) {
        startPage();
        List<DormRoom> list = dormRoomService.selectDormRoomList(dormRoom);
        return getDataTable(list);
    }

    /**
     * 查询宿舍列表
     */
    @GetMapping("/listAll")
    @PreAuthorize("hasAuthority('dorm:room:list')")
    public AjaxResult listAll(DormRoom dormRoom) {
        List<DormRoom> list = dormRoomService.selectDormRoomList(dormRoom);
        return success(list);
    }


    /**
     * 获取宿舍详细信息
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('dorm:room:query')")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        DormRoom info = dormRoomService.selectDormRoomById(id);
        if (StringUtils.isNull(info)) {
            return error("信息不存在");
        }
        return success(dormRoomService.selectDormRoomById(id));
    }

    /**
     * 新增宿舍
     */
    @PostMapping
    @PreAuthorize("hasAuthority('dorm:room:add')")
    public AjaxResult add(@RequestBody DormRoom dormRoom) {

        return toAjax(dormRoomService.insertDormRoom(dormRoom));
    }

    /**
     * 修改宿舍
     */
    @PutMapping
    @PreAuthorize("hasAuthority('dorm:room:edit')")
    public AjaxResult edit(@RequestBody DormRoom dormRoom) {
        DormRoom info = dormRoomService.selectDormRoomById(dormRoom.getId());
        if (StringUtils.isNull(info)) {
            return error("信息不存在");
        }
        return toAjax(dormRoomService.updateDormRoom(dormRoom));
    }

    /**
     * 删除宿舍
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAuthority('dorm:room:remove')")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(dormRoomService.deleteDormRoomByIds(ids));
    }
}
