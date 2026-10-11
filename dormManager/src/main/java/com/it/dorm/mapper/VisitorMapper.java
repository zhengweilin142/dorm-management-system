package com.it.dorm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.dorm.domain.Visitor;

import java.util.List;

/**
 * 到访Mapper接口
 */
public interface VisitorMapper extends BaseMapper<Visitor> {
    /**
     * 查询到访
     *
     * @param id 到访主键
     * @return 到访
     */
    public Visitor selectVisitorById(Long id);

    /**
     * 查询到访列表
     *
     * @param visitor 到访
     * @return 到访集合
     */
    public List<Visitor> selectVisitorList(Visitor visitor);

    /**
     * 新增到访
     *
     * @param visitor 到访
     * @return 结果
     */
    public int insertVisitor(Visitor visitor);

    /**
     * 修改到访
     *
     * @param visitor 到访
     * @return 结果
     */
    public int updateVisitor(Visitor visitor);

    /**
     * 删除到访
     *
     * @param id 到访主键
     * @return 结果
     */
    public int deleteVisitorById(Long id);

    /**
     * 批量删除到访
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVisitorByIds(Long[] ids);
}
