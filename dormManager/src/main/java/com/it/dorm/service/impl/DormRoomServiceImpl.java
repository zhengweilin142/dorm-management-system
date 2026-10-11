package com.it.dorm.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dorm.domain.DormBuild;
import com.it.dorm.domain.DormRoom;
import com.it.dorm.domain.DormRoomUser;
import com.it.dorm.exception.ServiceException;
import com.it.dorm.mapper.DormBuildMapper;
import com.it.dorm.mapper.DormRoomMapper;
import com.it.dorm.mapper.DormRoomUserMapper;
import com.it.dorm.service.IDormRoomService;
import com.it.dorm.utils.DateUtils;
import com.it.dorm.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 宿舍Service业务层处理
 */
@Service
public class DormRoomServiceImpl extends ServiceImpl<DormRoomMapper, DormRoom> implements IDormRoomService {
    @Resource
    private DormRoomMapper dormRoomMapper;
    @Resource
    private DormBuildMapper dormBuildMapper;
    @Resource
    private DormRoomUserMapper dormRoomUserMapper;

    /**
     * 查询宿舍
     *
     * @param id 宿舍主键
     * @return 宿舍
     */
    @Override
    public DormRoom selectDormRoomById(Long id) {
        return dormRoomMapper.selectDormRoomById(id);
    }

    /**
     * 查询宿舍列表
     *
     * @param dormRoom 宿舍
     * @return 宿舍
     */
    @Override
    public List<DormRoom> selectDormRoomList(DormRoom dormRoom) {
        return dormRoomMapper.selectDormRoomList(dormRoom);
    }

    /**
     * 新增宿舍
     *
     * @param dormRoom 宿舍
     * @return 结果
     */
    @Override
    public int insertDormRoom(DormRoom dormRoom) {
        Long buildId = dormRoom.getBuildId();
        DormBuild dormBuild = dormBuildMapper.selectDormBuildById(buildId);
        if (StringUtils.isNull(dormBuild)) {
            throw new ServiceException("楼宇不存在");
        }
        dormRoom.setCreateTime(DateUtils.getNowDate());
        return dormRoomMapper.insert(dormRoom);
    }

    /**
     * 修改宿舍
     *
     * @param dormRoom 宿舍
     * @return 结果
     */
    @Override
    public int updateDormRoom(DormRoom dormRoom) {
        dormRoom.setUpdateTime(DateUtils.getNowDate());
        return dormRoomMapper.updateById(dormRoom);
    }

    /**
     * 批量删除宿舍
     *
     * @param ids 需要删除的宿舍主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDormRoomByIds(Long[] ids) {
        int row = ids.length;
        if (row > 0) {
            for (int i = 0; i < row; i++) {
                Long count = dormRoomUserMapper.selectCount(new LambdaQueryWrapper<DormRoomUser>().eq(DormRoomUser::getRoomId, ids[i]));
                if (count > 0) {
                    throw new ServiceException("无法删除");
                }
            }
        }
        return dormRoomMapper.deleteDormRoomByIds(ids);
    }

    /**
     * 删除宿舍信息
     *
     * @param id 宿舍主键
     * @return 结果
     */
    @Override
    public int deleteDormRoomById(Long id) {
        Long count = dormRoomUserMapper.selectCount(new LambdaQueryWrapper<DormRoomUser>().eq(DormRoomUser::getRoomId, id));
        if (count > 0) {
            throw new ServiceException("无法删除");
        }
        return dormRoomMapper.deleteById(id);
    }

    @Override
    public int notFullRoom() {
        return dormRoomMapper.notFullRoom();
    }

    @Override
    public Long selectHaveRoomStuNum() {
        return dormRoomUserMapper.selectCount(null);
    }

    @Override
    public Map<String, Long> getEachBuildingStuNum() {
        Map<String, Long> map = new HashMap<>();
        List<DormBuild> dormBuildList = dormBuildMapper.selectDormBuildList(null);
        dormBuildList.forEach(item -> {
            Long id = item.getId();
            DormRoom dormRoom = new DormRoom();
            dormRoom.setBuildId(id);
            List<DormRoom> dormRoomList = dormRoomMapper.selectDormRoomList(dormRoom);
            Set<Long> collect = dormRoomList.stream().map(DormRoom::getId).collect(Collectors.toSet());
            Long count = 0L;
            if (collect.size() > 0) {
                count = dormRoomUserMapper.selectCount(new LambdaQueryWrapper<DormRoomUser>().in(DormRoomUser::getRoomId, collect));
            }
            map.put(item.getBuildName(), count);

        });
        return map;
    }
}
