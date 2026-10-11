package com.it.dorm.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dorm.domain.DormBuild;
import com.it.dorm.mapper.DormBuildMapper;
import com.it.dorm.service.IDormBuildService;
import com.it.dorm.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 宿舍楼Service业务层处理
 */
@Service
public class DormBuildServiceImpl extends ServiceImpl<DormBuildMapper, DormBuild> implements IDormBuildService {
    @Resource
    private DormBuildMapper dormBuildMapper;

    /**
     * 查询宿舍楼
     *
     * @param id 宿舍楼主键
     * @return 宿舍楼
     */
    @Override
    public DormBuild selectDormBuildById(Long id) {
        return this.baseMapper.selectDormBuildById(id);
    }

    /**
     * 查询宿舍楼列表
     *
     * @param dormBuild 宿舍楼
     * @return 宿舍楼
     */
    @Override
    public List<DormBuild> selectDormBuildList(DormBuild dormBuild) {
        return this.baseMapper.selectDormBuildList(dormBuild);
    }

    /**
     * 新增宿舍楼
     *
     * @param dormBuild 宿舍楼
     * @return 结果
     */
    @Override
    public int insertDormBuild(DormBuild dormBuild) {
        String buildName = dormBuild.getBuildName();
        LambdaQueryWrapper<DormBuild> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DormBuild::getBuildName, buildName);
        Long count = this.baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new SecurityException("宿舍楼已经存在");
        }
        dormBuild.setCreateTime(DateUtils.getNowDate());
        return this.baseMapper.insert(dormBuild);
    }

    /**
     * 修改宿舍楼
     *
     * @param dormBuild 宿舍楼
     * @return 结果
     */
    @Override
    public int updateDormBuild(DormBuild dormBuild) {
        String buildName = dormBuild.getBuildName();
        LambdaQueryWrapper<DormBuild> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DormBuild::getBuildName, buildName);
        queryWrapper.ne(DormBuild::getId, dormBuild.getId());
        Long count = this.baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new SecurityException("宿舍楼已经存在");
        }
        dormBuild.setUpdateTime(DateUtils.getNowDate());
        return this.baseMapper.updateById(dormBuild);
    }

    /**
     * 批量删除宿舍楼
     *
     * @param ids 需要删除的宿舍楼主键
     * @return 结果
     */
    @Override
    public int deleteDormBuildByIds(Long[] ids) {
        return this.baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 删除宿舍楼信息
     *
     * @param id 宿舍楼主键
     * @return 结果
     */
    @Override
    public int deleteDormBuildById(Long id) {
        return this.baseMapper.deleteById(id);
    }
}
