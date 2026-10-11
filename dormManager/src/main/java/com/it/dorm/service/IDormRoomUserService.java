package com.it.dorm.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.it.dorm.domain.DormRoomUser;

import java.util.List;

/**
 * 宿舍人员Service接口
 */
public interface IDormRoomUserService extends IService<DormRoomUser> {
    /**
     * 查询宿舍人员
     *
     * @param id 宿舍人员主键
     * @return 宿舍人员
     */
    public DormRoomUser selectDormRoomUserById(Long id);

    /**
     * 查询宿舍人员列表
     *
     * @param dormRoomUser 宿舍人员
     * @return 宿舍人员集合
     */
    public List<DormRoomUser> selectDormRoomUserList(DormRoomUser dormRoomUser);

    /**
     * 新增宿舍人员
     *
     * @param dormRoomUser 宿舍人员
     * @return 结果
     */
    public int insertDormRoomUser(DormRoomUser dormRoomUser);

    /**
     * 修改宿舍人员
     *
     * @param dormRoomUser 宿舍人员
     * @return 结果
     */
    public int updateDormRoomUser(DormRoomUser dormRoomUser);

    /**
     * 批量删除宿舍人员
     *
     * @param ids 需要删除的宿舍人员主键集合
     * @return 结果
     */
    public int deleteDormRoomUserByIds(Long[] ids);

    /**
     * 删除宿舍人员信息
     *
     * @param id 宿舍人员主键
     * @return 结果
     */
    public int deleteDormRoomUserById(Long id);

    /**
     * 删除宿舍人员信息
     *
     * @param dormRoomUser 宿舍人员主键
     * @return 结果
     */
    public int removeUser(DormRoomUser dormRoomUser);

}
