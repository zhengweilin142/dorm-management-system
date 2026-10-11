package com.it.dorm.controller;

import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.domain.DormBuild;
import com.it.dorm.domain.SysNotice;
import com.it.dorm.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class Bicontroller extends BaseController {

    @Resource
    private IDormBuildService dormBuildService;
    @Resource
    private IDormRoomService dormRoomService;
    @Resource
    private IRepairService repairService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysNoticeService noticeService;

    /**
     * 首页Echarts 获取楼宇信息
     */
    @GetMapping("/building/getBuildingName")
    public AjaxResult getBuildingName() {
        List<DormBuild> dormBuildList = dormBuildService.selectDormBuildList(null);
        List<Long> buildingId = dormBuildList.stream().map(dormBuildId -> dormBuildId.getBuildId()).collect(Collectors.toList());
        return success(buildingId);
    }

    /**
     * 首页顶部：空宿舍统计
     */
    @GetMapping("/room/noFullRoom")
    public AjaxResult noFullRoom() {
        int num = dormRoomService.notFullRoom();
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("data", num);
        return ajaxResult;
    }

    /**
     * 首页顶部：报修统计
     */
    @GetMapping("/repair/orderNum")
    public AjaxResult orderNum() {
        int num = repairService.showOrderNum();
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("data", num);
        return ajaxResult;
    }

    /**
     * 主页 住宿人数
     */
    @GetMapping("/room/selectHaveRoomStuNum")
    public AjaxResult selectHaveRoomStuNum() {
        Long count = dormRoomService.selectHaveRoomStuNum();
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("data", count);
        return ajaxResult;
    }

    /**
     * 住宿分布人数
     */
    @GetMapping("/getEachBuildingStuNum")
    public AjaxResult getEachBuildingStuNum() {
        Map<String, Long> map = dormRoomService.getEachBuildingStuNum();
        return success(map);
    }


    /**
     * 主页顶部：学生统计
     */
    @GetMapping("/stu/stuNum")
    public AjaxResult stuNum() {
        int num = sysUserService.stuNum();
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("data", num);
        return ajaxResult;
    }

    /**
     * 首页公告展示
     */
    @GetMapping("/notice/homePageNotice")
    public AjaxResult homePageNotice() {
        List<SysNotice> list = noticeService.homePageNotice();
        return success(list);

    }
}
