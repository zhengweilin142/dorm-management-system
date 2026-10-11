package com.it.dorm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.dorm.domain.AdjustRoom;

import java.util.List;

/**
 * 申请换宿Mapper接口
 */
public interface AdjustRoomMapper extends BaseMapper<AdjustRoom> {
    /**
     * 查询申请换宿
     *
     * @param id 申请换宿主键
     * @return 申请换宿
     */
    public AdjustRoom selectAdjustRoomById(Long id);

    /**
     * 查询申请换宿列表
     *
     * @param adjustRoom 申请换宿
     * @return 申请换宿集合
     */
    public List<AdjustRoom> selectAdjustRoomList(AdjustRoom adjustRoom);

    /**
     * 新增申请换宿
     *
     * @param adjustRoom 申请换宿
     * @return 结果
     */
    public int insertAdjustRoom(AdjustRoom adjustRoom);

    /**
     * 修改申请换宿
     *
     * @param adjustRoom 申请换宿
     * @return 结果
     */
    public int updateAdjustRoom(AdjustRoom adjustRoom);

    /**
     * 删除申请换宿
     *
     * @param id 申请换宿主键
     * @return 结果
     */
    public int deleteAdjustRoomById(Long id);

    /**
     * 批量删除申请换宿
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAdjustRoomByIds(Long[] ids);
}
