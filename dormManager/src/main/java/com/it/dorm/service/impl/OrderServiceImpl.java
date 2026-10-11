package com.it.dorm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.dorm.domain.DormRoom;
import com.it.dorm.domain.Order;
import com.it.dorm.exception.ServiceException;
import com.it.dorm.mapper.DormRoomMapper;
import com.it.dorm.mapper.OrderMapper;
import com.it.dorm.service.IOrderService;
import com.it.dorm.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单Service业务层处理
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private DormRoomMapper dormRoomMapper;

    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    @Override
    public Order selectOrderByOrderId(Long orderId) {
        return orderMapper.selectOrderByOrderId(orderId);
    }

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单
     */
    @Override
    public List<Order> selectOrderList(Order order) {
        return orderMapper.selectOrderList(order);
    }

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertOrder(Order order) {
        // 获取订单的宿舍 ID 和对应的 DormRoom 实体
        Long dormId = order.getDormId();
        DormRoom dormRoom = dormRoomMapper.selectDormRoomById(dormId);

        // 检查宿舍是否存在
        if (dormRoom == null) {
            throw new ServiceException("宿舍不存在");
        }

        // 获取订单金额
        BigDecimal amount = order.getAmount();
        BigDecimal updatedAmount;  // 用于存储更新后的金额
        BigDecimal currentCost;    // 用于存储当前宿舍的水电费用

        // 判断订单类型并进行相应的计算
        if (order.getOrderType().equals("电费")) {
            // 电费计算：将金额除以 0.5，再加上当前的电费
            // 精确到 2 位小数
            updatedAmount = amount.divide(new BigDecimal("0.8"), 2, BigDecimal.ROUND_HALF_UP);
            currentCost = dormRoom.getElectricity();
            // 新的电费
            BigDecimal newElectricity = updatedAmount.add(currentCost);
            // 更新电费
            dormRoom.setElectricity(newElectricity);
        } else {
            // 水费计算：将金额除以 5，再加上当前的水费
            // 精确到 2 位小数
            updatedAmount = amount.divide(new BigDecimal("5"), 2, BigDecimal.ROUND_HALF_UP);
            currentCost = dormRoom.getWater();
            // 新的水费
            BigDecimal newWater = updatedAmount.add(currentCost);
            // 更新水费
            dormRoom.setWater(newWater);
        }

        // 更新宿舍信息
        dormRoomMapper.updateById(dormRoom);

        // 设置订单的创建时间
        order.setCreateTime(DateUtils.getNowDate());

        // 插入订单并返回插入结果
        order.setStatus("已完成");
        return orderMapper.insert(order);
    }


}
