package com.it.dorm.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dorm.domain.Visitor;
import com.it.dorm.mapper.VisitorMapper;
import com.it.dorm.service.IVisitorService;
import com.it.dorm.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 到访Service业务层处理
 */
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements IVisitorService {
    @Autowired
    private VisitorMapper visitorMapper;

    /**
     * 查询到访
     *
     * @param id 到访主键
     * @return 到访
     */
    @Override
    public Visitor selectVisitorById(Long id) {
        return visitorMapper.selectVisitorById(id);
    }

    /**
     * 查询到访列表
     *
     * @param visitor 到访
     * @return 到访
     */
    @Override
    public List<Visitor> selectVisitorList(Visitor visitor) {
        return visitorMapper.selectVisitorList(visitor);
    }

    /**
     * 新增到访
     *
     * @param visitor 到访
     * @return 结果
     */
    @Override
    public int insertVisitor(Visitor visitor) {
        visitor.setCreateTime(DateUtils.getNowDate());
        return visitorMapper.insert(visitor);
    }

    /**
     * 修改到访
     *
     * @param visitor 到访
     * @return 结果
     */
    @Override
    public int updateVisitor(Visitor visitor) {
        visitor.setUpdateTime(DateUtils.getNowDate());
        return visitorMapper.updateVisitor(visitor);
    }

    /**
     * 批量删除到访
     *
     * @param ids 需要删除的到访主键
     * @return 结果
     */
    @Override
    public int deleteVisitorByIds(Long[] ids) {
        return visitorMapper.deleteVisitorByIds(ids);
    }

    /**
     * 删除到访信息
     *
     * @param id 到访主键
     * @return 结果
     */
    @Override
    public int deleteVisitorById(Long id) {
        return visitorMapper.deleteVisitorById(id);
    }
}
