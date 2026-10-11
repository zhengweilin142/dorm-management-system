package com.it.dorm.controller;


import com.it.dorm.common.AjaxResult;
import com.it.dorm.common.controller.BaseController;
import com.it.dorm.common.pojo.TableDataInfo;
import com.it.dorm.domain.Order;
import com.it.dorm.service.IOrderService;
import com.it.dorm.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单Controller
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {
    @Resource
    private IOrderService orderService;

    /**
     * 查询订单列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('order:order:list')")
    public TableDataInfo list(Order order) {
        startPage();
        List<Order> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 获取订单详细信息
     */
    @GetMapping(value = "/{orderId}")
    @PreAuthorize("hasAuthority('order:order:query')")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId) {
        Order info = orderService.selectOrderByOrderId(orderId);
        if (StringUtils.isNull(info)){
            return error("信息不存在");
        }
        return success(info);
    }

    /**
     * 新增订单
     */
    @PostMapping
    @PreAuthorize("hasAuthority('order:order:add')")
    public AjaxResult add(@RequestBody Order order) {
        return toAjax(orderService.insertOrder(order));
    }

}
