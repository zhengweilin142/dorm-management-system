package com.it.dorm.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dorm.domain.DormRoom;
import com.it.dorm.domain.DormRoomUser;
import com.it.dorm.exception.ServiceException;
import com.it.dorm.mapper.DormRoomMapper;
import com.it.dorm.mapper.DormRoomUserMapper;
import com.it.dorm.service.IDormRoomUserService;
import com.it.dorm.utils.DateUtils;
import com.it.dorm.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 宿舍人员Service业务层处理
 */
@Service
public class DormRoomUserServiceImpl extends ServiceImpl<DormRoomUserMapper, DormRoomUser> implements IDormRoomUserService {
    @Resource
    private DormRoomUserMapper dormRoomUserMapper;
    @Resource
    private DormRoomMapper dormRoomMapper;

    /**
     * 查询宿舍人员
     *
     * @param id 宿舍人员主键
     * @return 宿舍人员
     */
    @Override
    public DormRoomUser selectDormRoomUserById(Long id) {
        return dormRoomUserMapper.selectDormRoomUserById(id);
    }

    /**
     * 查询宿舍人员列表
     *
     * @param dormRoomUser 宿舍人员
     * @return 宿舍人员
     */
    @Override
    public List<DormRoomUser> selectDormRoomUserList(DormRoomUser dormRoomUser) {
        return dormRoomUserMapper.selectDormRoomUserList(dormRoomUser);
    }

    /**
     * 新增宿舍人员
     *
     * @param dormRoomUser 宿舍人员
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDormRoomUser(DormRoomUser dormRoomUser) {
        Long roomId = dormRoomUser.getRoomId();
        DormRoom dormRoom = dormRoomMapper.selectDormRoomById(roomId);
        ArrayList<DormRoomUser> users = new ArrayList<>();
        Long[] userIds = dormRoomUser.getUserIds();

        if (userIds.length > 0) {
            for (int i = 0; i < userIds.length; i++) {
                Long userId = userIds[i];
                DormRoomUser roomUser = new DormRoomUser();
                roomUser.setRoomId(dormRoom.getId());
                roomUser.setUserId(userId);
                roomUser.setCreateTime(DateUtils.getNowDate());
                users.add(roomUser);
                if (StringUtils.isNull(dormRoomUser.getType())) {
                    Long count = this.baseMapper.selectCount(new LambdaQueryWrapper<DormRoomUser>().eq(DormRoomUser::getUserId, userId));

                    if (count >= 1) {
                        throw new ServiceException("改学生在其他宿舍无法添加");
                    }
                }

            }
        }
        this.saveBatch(users);
        Long count = this.baseMapper.selectCount(new LambdaQueryWrapper<DormRoomUser>().eq(DormRoomUser::getRoomId, roomId));
        dormRoom.setCurrentCapacity(Math.toIntExact(count));

        if (count > dormRoom.getMaxCapacity()) {
            throw new ServiceException("总人数超过最大人数,无法添加");
        }
        dormRoomMapper.updateById(dormRoom);
        return 1;
    }

    /**
     * 修改宿舍人员
     *
     * @param dormRoomUser 宿舍人员
     * @return 结果
     */
    @Override
    public int updateDormRoomUser(DormRoomUser dormRoomUser) {
        dormRoomUser.setUpdateTime(DateUtils.getNowDate());
        return dormRoomUserMapper.updateDormRoomUser(dormRoomUser);
    }

    /**
     * 批量删除宿舍人员
     *
     * @param ids 需要删除的宿舍人员主键
     * @return 结果
     */
    @Override
    public int deleteDormRoomUserByIds(Long[] ids) {
        return dormRoomUserMapper.deleteDormRoomUserByIds(ids);
    }

    /**
     * 删除宿舍人员信息
     *
     * @param id 宿舍人员主键
     * @return 结果
     */
    @Override
    public int deleteDormRoomUserById(Long id) {
        return dormRoomUserMapper.deleteDormRoomUserById(id);
    }

    @Override
    public int removeUser(DormRoomUser dormRoomUser) {
        LambdaQueryWrapper<DormRoomUser> dormRoomUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dormRoomUserLambdaQueryWrapper.eq(DormRoomUser::getRoomId, dormRoomUser.getRoomId());
        dormRoomUserLambdaQueryWrapper.eq(DormRoomUser::getUserId, dormRoomUser.getUserId());
        int row = this.baseMapper.delete(dormRoomUserLambdaQueryWrapper);
        DormRoom dormRoom = new DormRoom();
        Long count = this.baseMapper.selectCount(new LambdaQueryWrapper<DormRoomUser>().eq(DormRoomUser::getRoomId, dormRoomUser.getRoomId()));
        dormRoom.setCurrentCapacity(Math.toIntExact(count));
        dormRoom.setId(dormRoomUser.getRoomId());
        dormRoomMapper.updateById(dormRoom);
        return row;
    }
}
