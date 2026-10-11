package com.it.dorm.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.it.dorm.domain.AdjustRoom;

import java.util.List;

/**
 * 申请换宿Service接口
 */
public interface IAdjustRoomService extends IService<AdjustRoom> {
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
     * 批量删除申请换宿
     *
     * @param ids 需要删除的申请换宿主键集合
     * @return 结果
     */
    public int deleteAdjustRoomByIds(Long[] ids);

    /**
     * 删除申请换宿信息
     *
     * @param id 申请换宿主键
     * @return 结果
     */
    public int deleteAdjustRoomById(Long id);

    public int change(AdjustRoom adjustRoom);
}
