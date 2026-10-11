package com.it.dorm.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dorm.domain.AdjustRoom;
import com.it.dorm.domain.DormRoomUser;
import com.it.dorm.exception.ServiceException;
import com.it.dorm.mapper.AdjustRoomMapper;
import com.it.dorm.service.IAdjustRoomService;
import com.it.dorm.service.IDormRoomUserService;
import com.it.dorm.utils.DateUtils;
import com.it.dorm.utils.SecurityUtils;
import com.it.dorm.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 申请换宿Service业务层处理
 */
@Service
public class AdjustRoomServiceImpl extends ServiceImpl<AdjustRoomMapper, AdjustRoom> implements IAdjustRoomService {
    @Resource
    private AdjustRoomMapper adjustRoomMapper;
    @Resource
    private IDormRoomUserService dormRoomUserService;

    /**
     * 查询申请换宿
     *
     * @param id 申请换宿主键
     * @return 申请换宿
     */
    @Override
    public AdjustRoom selectAdjustRoomById(Long id) {
        return adjustRoomMapper.selectAdjustRoomById(id);
    }

    /**
     * 查询申请换宿列表
     *
     * @param adjustRoom 申请换宿
     * @return 申请换宿
     */
    @Override
    public List<AdjustRoom> selectAdjustRoomList(AdjustRoom adjustRoom) {
        return adjustRoomMapper.selectAdjustRoomList(adjustRoom);
    }

    /**
     * 新增申请换宿
     *
     * @param adjustRoom 申请换宿
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertAdjustRoom(AdjustRoom adjustRoom) {
        adjustRoom.setUserId(SecurityUtils.getUserId());
        adjustRoom.setCreateTime(DateUtils.getNowDate());
        LambdaQueryWrapper<DormRoomUser> dormRoomUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dormRoomUserLambdaQueryWrapper.eq(DormRoomUser::getUserId, adjustRoom.getUserId());
        DormRoomUser info = dormRoomUserService.getOne(dormRoomUserLambdaQueryWrapper);

        if (StringUtils.isNull(info)) {
            throw new ServiceException("改学生没有分配宿舍");
        }
        Long currentRoomId = info.getRoomId();
        adjustRoom.setCurrentRoomId(currentRoomId);

        Long towardsRoomId = adjustRoom.getTowardsRoomId();
        if (StringUtils.isNull(towardsRoomId)) {
            throw new ServiceException("目标宿舍不能为空");
        }
        adjustRoom.setApplyTime(String.valueOf(new Date()));
        return adjustRoomMapper.insert(adjustRoom);
    }

    /**
     * 修改申请换宿
     *
     * @param adjustRoom 申请换宿
     * @return 结果
     */
    @Override
    public int updateAdjustRoom(AdjustRoom adjustRoom) {
        adjustRoom.setUpdateTime(DateUtils.getNowDate());
        return adjustRoomMapper.updateAdjustRoom(adjustRoom);
    }

    /**
     * 批量删除申请换宿
     *
     * @param ids 需要删除的申请换宿主键
     * @return 结果
     */
    @Override
    public int deleteAdjustRoomByIds(Long[] ids) {
        return adjustRoomMapper.deleteAdjustRoomByIds(ids);
    }

    /**
     * 删除申请换宿信息
     *
     * @param id 申请换宿主键
     * @return 结果
     */
    @Override
    public int deleteAdjustRoomById(Long id) {
        return adjustRoomMapper.deleteAdjustRoomById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int change(AdjustRoom adjustRoom) {
        Long id = adjustRoom.getId();
        AdjustRoom info = this.baseMapper.selectById(id);
        String state = adjustRoom.getState();
        Long userId = adjustRoom.getUserId();

        Long currentRoomId = info.getCurrentRoomId();

        // 创建 dormRoomUser 对象时先赋值
        DormRoomUser dormRoomUser = new DormRoomUser();
        dormRoomUser.setUserId(userId);
        dormRoomUser.setRoomId(currentRoomId);


        // 如果状态为通过，则处理用户调整房间
        if ("通过".equals(state)) {
            // 移除用户
            dormRoomUser.setType("check");
            dormRoomUserService.removeUser(dormRoomUser);
            // 设置用户ID数组
            dormRoomUser.setUserIds(new Long[]{userId});
            // 设置目标房间ID
            dormRoomUser.setRoomId(info.getTowardsRoomId());
            // 插入新房间信息
            dormRoomUserService.insertDormRoomUser(dormRoomUser);
        }

        // 更新调整记录
        return this.baseMapper.updateById(adjustRoom);
    }

}
