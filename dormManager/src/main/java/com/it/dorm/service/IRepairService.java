package com.it.dorm.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.it.dorm.domain.Repair;

import java.util.List;

/**
 * 保修Service接口
 */
public interface IRepairService extends IService<Repair> {
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
     * 批量删除保修
     *
     * @param ids 需要删除的保修主键集合
     * @return 结果
     */
    public int deleteRepairByIds(Long[] ids);

    /**
     * 删除保修信息
     *
     * @param id 保修主键
     * @return 结果
     */
    public int deleteRepairById(Long id);

    /**
     * 显示保修数量
     * @return
     */
    public int showOrderNum();

}
