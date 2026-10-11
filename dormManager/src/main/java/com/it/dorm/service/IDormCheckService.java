package com.it.dorm.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.it.dorm.domain.DormCheck;

import java.util.List;

/**
 * 宿舍卫生检查，记录每次宿舍的卫生检查情况Service接口
 */
public interface IDormCheckService extends IService<DormCheck> {
    /**
     * 查询宿舍卫生检查，记录每次宿舍的卫生检查情况
     *
     * @param checkId 宿舍卫生检查，记录每次宿舍的卫生检查情况主键
     * @return 宿舍卫生检查，记录每次宿舍的卫生检查情况
     */
    public DormCheck selectDormCheckByCheckId(Long checkId);

    /**
     * 查询宿舍卫生检查，记录每次宿舍的卫生检查情况列表
     *
     * @param dormCheck 宿舍卫生检查，记录每次宿舍的卫生检查情况
     * @return 宿舍卫生检查，记录每次宿舍的卫生检查情况集合
     */
    public List<DormCheck> selectDormCheckList(DormCheck dormCheck);

    /**
     * 新增宿舍卫生检查，记录每次宿舍的卫生检查情况
     *
     * @param dormCheck 宿舍卫生检查，记录每次宿舍的卫生检查情况
     * @return 结果
     */
    public int insertDormCheck(DormCheck dormCheck);

    /**
     * 修改宿舍卫生检查，记录每次宿舍的卫生检查情况
     *
     * @param dormCheck 宿舍卫生检查，记录每次宿舍的卫生检查情况
     * @return 结果
     */
    public int updateDormCheck(DormCheck dormCheck);

    /**
     * 批量删除宿舍卫生检查，记录每次宿舍的卫生检查情况
     *
     * @param checkIds 需要删除的宿舍卫生检查，记录每次宿舍的卫生检查情况主键集合
     * @return 结果
     */
    public int deleteDormCheckByCheckIds(Long[] checkIds);

    /**
     * 删除宿舍卫生检查，记录每次宿舍的卫生检查情况信息
     *
     * @param checkId 宿舍卫生检查，记录每次宿舍的卫生检查情况主键
     * @return 结果
     */
    public int deleteDormCheckByCheckId(Long checkId);
}
