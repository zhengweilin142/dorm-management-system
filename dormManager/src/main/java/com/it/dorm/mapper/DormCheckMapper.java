package com.it.dorm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.dorm.domain.DormCheck;

import java.util.List;

/**
 * 宿舍卫生检查，记录每次宿舍的卫生检查情况Mapper接口
 */
public interface DormCheckMapper extends BaseMapper<DormCheck> {
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
     * 删除宿舍卫生检查，记录每次宿舍的卫生检查情况
     *
     * @param checkId 宿舍卫生检查，记录每次宿舍的卫生检查情况主键
     * @return 结果
     */
    public int deleteDormCheckByCheckId(Long checkId);

    /**
     * 批量删除宿舍卫生检查，记录每次宿舍的卫生检查情况
     *
     * @param checkIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDormCheckByCheckIds(Long[] checkIds);
}
