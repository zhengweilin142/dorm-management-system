package com.it.dorm.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.it.dorm.domain.Visitor;

import java.util.List;

/**
 * 到访Service接口
 */
public interface IVisitorService extends IService<Visitor> {
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
     * 批量删除到访
     *
     * @param ids 需要删除的到访主键集合
     * @return 结果
     */
    public int deleteVisitorByIds(Long[] ids);

    /**
     * 删除到访信息
     *
     * @param id 到访主键
     * @return 结果
     */
    public int deleteVisitorById(Long id);
}
