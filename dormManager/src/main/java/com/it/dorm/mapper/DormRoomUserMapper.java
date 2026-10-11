package com.it.dorm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.dorm.domain.DormRoomUser;

import java.util.List;

/**
 * 宿舍人员Mapper接口
 */
public interface DormRoomUserMapper extends BaseMapper<DormRoomUser> {
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
     * 删除宿舍人员
     *
     * @param id 宿舍人员主键
     * @return 结果
     */
    public int deleteDormRoomUserById(Long id);

    /**
     * 批量删除宿舍人员
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDormRoomUserByIds(Long[] ids);
}
