package com.it.dorm.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dorm.domain.DormRoom;
import com.it.dorm.domain.Repair;
import com.it.dorm.exception.ServiceException;
import com.it.dorm.mapper.DormRoomMapper;
import com.it.dorm.mapper.RepairMapper;
import com.it.dorm.service.IRepairService;
import com.it.dorm.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 保修Service业务层处理
 */
@Service
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair> implements IRepairService {
    @Resource
    private RepairMapper repairMapper;
    @Resource
    private DormRoomMapper dormRoomMapper;

    /**
     * 查询保修
     *
     * @param id 保修主键
     * @return 保修
     */
    @Override
    public Repair selectRepairById(Long id) {
        return repairMapper.selectRepairById(id);
    }

    /**
     * 查询保修列表
     *
     * @param repair 保修
     * @return 保修
     */
    @Override
    public List<Repair> selectRepairList(Repair repair) {
        return repairMapper.selectRepairList(repair);
    }

    /**
     * 新增保修
     *
     * @param repair 保修
     * @return 结果
     */
    @Override
    public int insertRepair(Repair repair) {
        Long roomId = repair.getRoomId();
        DormRoom dormRoom = dormRoomMapper.selectDormRoomById(roomId);
        if (StringUtils.isNull(dormRoom)) {
            throw new ServiceException("宿舍不存在");
        }
        repair.setBuildId(dormRoom.getBuildId());
        return repairMapper.insert(repair);
    }

    /**
     * 修改保修
     *
     * @param repair 保修
     * @return 结果
     */
    @Override
    public int updateRepair(Repair repair) {
        return repairMapper.updateRepair(repair);
    }

    /**
     * 批量删除保修
     *
     * @param ids 需要删除的保修主键
     * @return 结果
     */
    @Override
    public int deleteRepairByIds(Long[] ids) {
        return repairMapper.deleteRepairByIds(ids);
    }

    /**
     * 删除保修信息
     *
     * @param id 保修主键
     * @return 结果
     */
    @Override
    public int deleteRepairById(Long id) {
        return repairMapper.deleteRepairById(id);
    }

    @Override
    public int showOrderNum() {
        QueryWrapper<Repair> qw = new QueryWrapper<>();
        return Math.toIntExact(repairMapper.selectCount(qw));
    }
}
