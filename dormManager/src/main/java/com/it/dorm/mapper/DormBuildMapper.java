package com.it.dorm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.dorm.domain.DormBuild;

import java.util.List;

/**
 * 宿舍楼Mapper接口
 */
public interface DormBuildMapper extends BaseMapper<DormBuild> {
    /**
     * 查询宿舍楼
     *
     * @param id 宿舍楼主键
     * @return 宿舍楼
     */
    public DormBuild selectDormBuildById(Long id);

    /**
     * 查询宿舍楼列表
     *
     * @param dormBuild 宿舍楼
     * @return 宿舍楼集合
     */
    public List<DormBuild> selectDormBuildList(DormBuild dormBuild);

    /**
     * 新增宿舍楼
     *
     * @param dormBuild 宿舍楼
     * @return 结果
     */
    public int insertDormBuild(DormBuild dormBuild);

    /**
     * 修改宿舍楼
     *
     * @param dormBuild 宿舍楼
     * @return 结果
     */
    public int updateDormBuild(DormBuild dormBuild);

    /**
     * 删除宿舍楼
     *
     * @param id 宿舍楼主键
     * @return 结果
     */
    public int deleteDormBuildById(Long id);

    /**
     * 批量删除宿舍楼
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDormBuildByIds(Long[] ids);
}
