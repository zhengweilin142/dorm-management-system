package com.it.dorm.utils;


import com.it.dorm.common.page.PageDomain;
import com.it.dorm.common.page.TableSupport;
import com.github.pagehelper.PageHelper;

/**
 
 * @version 1.0
 *  分页工具类
 * @date 2024/10/25 00:34
 */
public class PageUtils extends PageHelper
{

    /**
     * 设置请求分页数据
     */
    public static void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage()
    {
        PageHelper.clearPage();
    }
}
