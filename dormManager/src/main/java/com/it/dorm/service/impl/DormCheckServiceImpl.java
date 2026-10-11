package com.it.dorm.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dorm.domain.DormCheck;
import com.it.dorm.mapper.DormCheckMapper;
import com.it.dorm.service.IDormCheckService;
import com.it.dorm.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 宿舍卫生检查，记录每次宿舍的卫生检查情况Service业务层处理
 */
@Service
public class DormCheckServiceImpl extends ServiceImpl<DormCheckMapper, DormCheck> implements IDormCheckService {
    @Resource
    private DormCheckMapper dormCheckMapper;

    /**
     * 查询宿舍卫生检查，记录每次宿舍的卫生检查情况
     *
     * @param checkId 宿舍卫生检查，记录每次宿舍的卫生检查情况主键
     * @return 宿舍卫生检查，记录每次宿舍的卫生检查情况
     */
    @Override
    public DormCheck selectDormCheckByCheckId(Long checkId) {
        return dormCheckMapper.selectDormCheckByCheckId(checkId);
    }

    /**
     * 查询宿舍卫生检查，记录每次宿舍的卫生检查情况列表
     *
     * @param dormCheck 宿舍卫生检查，记录每次宿舍的卫生检查情况
     * @return 宿舍卫生检查，记录每次宿舍的卫生检查情况
     */
    @Override
    public List<DormCheck> selectDormCheckList(DormCheck dormCheck) {
        return dormCheckMapper.selectDormCheckList(dormCheck);
    }

    /**
     * 新增宿舍卫生检查，记录每次宿舍的卫生检查情况
     *
     * @param dormCheck 宿舍卫生检查，记录每次宿舍的卫生检查情况
     * @return 结果
     */
    @Override
    public int insertDormCheck(DormCheck dormCheck) {
        dormCheck.setCreateTime(DateUtils.getNowDate());
        return dormCheckMapper.insert(dormCheck);
    }

    /**
     * 修改宿舍卫生检查，记录每次宿舍的卫生检查情况
     *
     * @param dormCheck 宿舍卫生检查，记录每次宿舍的卫生检查情况
     * @return 结果
     */
    @Override
    public int updateDormCheck(DormCheck dormCheck) {
        dormCheck.setUpdateTime(DateUtils.getNowDate());
        return dormCheckMapper.updateDormCheck(dormCheck);
    }

    /**
     * 批量删除宿舍卫生检查，记录每次宿舍的卫生检查情况
     *
     * @param checkIds 需要删除的宿舍卫生检查，记录每次宿舍的卫生检查情况主键
     * @return 结果
     */
    @Override
    public int deleteDormCheckByCheckIds(Long[] checkIds) {
        return dormCheckMapper.deleteDormCheckByCheckIds(checkIds);
    }

    /**
     * 删除宿舍卫生检查，记录每次宿舍的卫生检查情况信息
     *
     * @param checkId 宿舍卫生检查，记录每次宿舍的卫生检查情况主键
     * @return 结果
     */
    @Override
    public int deleteDormCheckByCheckId(Long checkId) {
        return dormCheckMapper.deleteDormCheckByCheckId(checkId);
    }
}
