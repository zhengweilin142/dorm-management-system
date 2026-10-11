package com.it.dorm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.dorm.domain.DormRoom;

import java.util.List;

/**
 * 宿舍Mapper接口
 */
public interface DormRoomMapper extends BaseMapper<DormRoom> {
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
     * 批量删除宿舍
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDormRoomByIds(Long[] ids);

    /**
     * 统计没有住满人数的宿舍
     * @return
     */
    public int notFullRoom();

}
