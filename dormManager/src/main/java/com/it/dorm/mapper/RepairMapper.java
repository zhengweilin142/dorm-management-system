package com.it.dorm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.dorm.domain.Repair;

import java.util.List;

/**
 * 保修Mapper接口
 */
public interface RepairMapper extends BaseMapper<Repair> {
    /**
     * 查询保修
     *
     * @param id 保修主键
     * @return 保修
     */
    public Repair selectRepairById(Long id);

    /**
     * 查询保修列表
     *
     * @param repair 保修
     * @return 保修集合
     */
    public List<Repair> selectRepairList(Repair repair);

    /**
     * 新增保修
     *
     * @param repair 保修
     * @return 结果
     */
    public int insertRepair(Repair repair);

    /**
     * 修改保修
     *
     * @param repair 保修
     * @return 结果
     */
    public int updateRepair(Repair repair);

    /**
     * 删除保修
     *
     * @param id 保修主键
     * @return 结果
     */
    public int deleteRepairById(Long id);

    /**
     * 批量删除保修
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepairByIds(Long[] ids);
}
