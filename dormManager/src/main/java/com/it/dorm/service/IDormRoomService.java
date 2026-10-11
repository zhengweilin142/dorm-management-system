package com.it.dorm.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.it.dorm.domain.DormRoom;

import java.util.List;
import java.util.Map;

/**
 * 宿舍Service接口
 */
public interface IDormRoomService extends IService<DormRoom> {
    /**
     * 查询宿舍
     *
     * @param id 宿舍主键
     * @return 宿舍
     */
    public DormRoom selectDormRoomById(Long id);

    /**
     * 查询宿舍列表
     *
     * @param dormRoom 宿舍
     * @return 宿舍集合
     */
    public List<DormRoom> selectDormRoomList(DormRoom dormRoom);

    /**
     * 新增宿舍
     *
     * @param dormRoom 宿舍
     * @return 结果
     */
    public int insertDormRoom(DormRoom dormRoom);

    /**
     * 修改宿舍
     *
     * @param dormRoom 宿舍
     * @return 结果
     */
    public int updateDormRoom(DormRoom dormRoom);

    /**
     * 批量删除宿舍
     *
     * @param ids 需要删除的宿舍主键集合
     * @return 结果
     */
    public int deleteDormRoomByIds(Long[] ids);

    /**
     * 删除宿舍信息
     *
     * @param id 宿舍主键
     * @return 结果
     */
    public int deleteDormRoomById(Long id);

    /**
     * 统计没有住满的宿舍数量
     * @return
     */
    public  int notFullRoom();

    /**
     * 住宿人数
     * @return
     */
    Long selectHaveRoomStuNum();

    /**
     * 获取每栋宿舍学生总人数
     * @return
     */
    Map<String, Long> getEachBuildingStuNum();
}
