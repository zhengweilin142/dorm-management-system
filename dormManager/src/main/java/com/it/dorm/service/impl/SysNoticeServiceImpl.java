package com.it.dorm.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dorm.domain.SysNotice;
import com.it.dorm.mapper.SysNoticeMapper;
import com.it.dorm.service.ISysNoticeService;
import com.it.dorm.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 通知公告Service业务层处理
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements ISysNoticeService {
    @Resource
    private SysNoticeMapper sysNoticeMapper;

    /**
     * 查询通知公告
     *
     * @param noticeId 通知公告主键
     * @return 通知公告
     */
    @Override
    public SysNotice selectSysNoticeByNoticeId(Long noticeId) {
        return sysNoticeMapper.selectSysNoticeByNoticeId(noticeId);
    }

    /**
     * 查询通知公告列表
     *
     * @param sysNotice 通知公告
     * @return 通知公告
     */
    @Override
    public List<SysNotice> selectSysNoticeList(SysNotice sysNotice) {
        return sysNoticeMapper.selectSysNoticeList(sysNotice);
    }

    /**
     * 新增通知公告
     *
     * @param sysNotice 通知公告
     * @return 结果
     */
    @Override
    public int insertSysNotice(SysNotice sysNotice) {
        sysNotice.setCreateTime(DateUtils.getNowDate());
        return this.baseMapper.insert(sysNotice);
    }

    /**
     * 修改通知公告
     *
     * @param sysNotice 通知公告
     * @return 结果
     */
    @Override
    public int updateSysNotice(SysNotice sysNotice) {
        sysNotice.setUpdateTime(DateUtils.getNowDate());
        return sysNoticeMapper.updateById(sysNotice);
    }

    /**
     * 批量删除通知公告
     *
     * @param noticeIds 需要删除的通知公告主键
     * @return 结果
     */
    @Override
    public int deleteSysNoticeByNoticeIds(Long[] noticeIds) {
        return sysNoticeMapper.deleteBatchIds(Arrays.asList(noticeIds));
    }

    /**
     * 删除通知公告信息
     *
     * @param noticeId 通知公告主键
     * @return 结果
     */
    @Override
    public int deleteSysNoticeByNoticeId(Long noticeId) {
        return sysNoticeMapper.deleteSysNoticeByNoticeId(noticeId);
    }

    @Override
    public List<SysNotice> homePageNotice() {
        return this.baseMapper.selectSysNoticeList(null);
    }
}
