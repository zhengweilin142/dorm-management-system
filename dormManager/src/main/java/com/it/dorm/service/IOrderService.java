package com.it.dorm.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.it.dorm.domain.Order;

import java.util.List;

/**
 * 订单Service接口
 */
public interface IOrderService extends IService<Order> {
    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    public Order selectOrderByOrderId(Long orderId);

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    public int insertOrder(Order order);


}
