package com.it.dorm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.dorm.domain.SysNotice;

import java.util.List;

/**
 * 通知公告Mapper接口
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice> {
    /**
     * 查询通知公告
     *
     * @param noticeId 通知公告主键
     * @return 通知公告
     */
    public SysNotice selectSysNoticeByNoticeId(Long noticeId);

    /**
     * 查询通知公告列表
     *
     * @param sysNotice 通知公告
     * @return 通知公告集合
     */
    public List<SysNotice> selectSysNoticeList(SysNotice sysNotice);

    /**
     * 新增通知公告
     *
     * @param sysNotice 通知公告
     * @return 结果
     */
    public int insertSysNotice(SysNotice sysNotice);

    /**
     * 修改通知公告
     *
     * @param sysNotice 通知公告
     * @return 结果
     */
    public int updateSysNotice(SysNotice sysNotice);

    /**
     * 删除通知公告
     *
     * @param noticeId 通知公告主键
     * @return 结果
     */
    public int deleteSysNoticeByNoticeId(Long noticeId);

    /**
     * 批量删除通知公告
     *
     * @param noticeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysNoticeByNoticeIds(Long[] noticeIds);
}
