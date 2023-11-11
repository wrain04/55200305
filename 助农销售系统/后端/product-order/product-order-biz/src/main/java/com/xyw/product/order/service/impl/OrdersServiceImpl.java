package com.xyw.product.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyw.product.admin.api.feign.RemoteUserService;
import com.xyw.product.common.core.constant.ProductConstants;
import com.xyw.product.common.core.constant.SecurityConstants;
import com.xyw.product.common.security.util.SecurityUtils;
import com.xyw.product.goods.feign.RemoteGoodsService;
import com.xyw.product.goods.feign.RemoteStatisticsService;
import com.xyw.product.order.dto.OrderDTO;
import com.xyw.product.order.entity.Order;
import com.xyw.product.order.mapper.OrdersMapper;
import com.xyw.product.order.service.OrdersService;
import com.xyw.product.order.util.RedisCreateOrderUtil;
import com.xyw.product.order.vo.OrderVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Order> implements OrdersService {

	private final RemoteUserService userService;

	private final RemoteStatisticsService statisticsService;

	private final RemoteGoodsService goodsService;

	@Resource
	OrdersMapper ordersMapper;

	@Resource
	RedisCreateOrderUtil redisCreateOrderUtil;

	/**
	 * 用户下单
	 *
	 * @param dto
	 * @return
	 */
	@Override
	public Long createOrder(OrderDTO dto) {

		Order order = BeanUtil.copyProperties(dto, Order.class);
		order.setTimeExpire(DateUtil.date().offset(DateField.MINUTE, 30).toLocalDateTime());
		order.setOrderId(redisCreateOrderUtil.getOrderNoByRedis());
		// 订单状态：状态：1、未付款，2、已付款，3、未发货，4、已发货
		ordersMapper.insert(order);
		// 用户下单成功后，增加商户的订单统计数量
		statisticsService.updateOrderSumByBusinessId(order.getBusinessId(), SecurityConstants.FROM_IN);
		return order.getOrderId();
	}

	@Override
	public List<OrderVO> getOrderByStatus(Long status, boolean isUser) {
		return getBaseMapper()
				.selectList(Wrappers.<Order>lambdaQuery().eq(Order::getStatus, status)
						.eq(isUser ? Order::getUserId : Order::getBusinessId, SecurityUtils.getUser().getId()))
				.stream()
				.map(o -> BeanUtil.copyProperties(o, OrderVO.class, "goodDetail")
					.setGoodDetail(goodsService.getById(Long.toString(o.getGoodId())).getData()))
				.collect(Collectors.toList());
	}

	/**
	 * 通过订单id获取订单
	 *
	 * @param orderId
	 * @return
	 */
	@Override
	public OrderVO getOrderByOrderId(Long orderId) {
		Order order = ordersMapper.selectById(orderId);
		OrderVO vo = BeanUtil.copyProperties(order, OrderVO.class, "goodDetail");
		vo.setGoodDetail(goodsService.getById(Long.toString(order.getGoodId())).getData());
		return vo;
	}

	/**
	 * 获取所有订单
	 *
	 * @return
	 */
	@Override
	public Map<String, Object> getAllOrder() {
		List<OrderVO> goodDetail = ordersMapper.selectList(null).stream()
				.map(o -> BeanUtil.copyProperties(o, OrderVO.class, "goodDetail")
						.setBusinessName(userService.user(o.getBusinessId()).getData().getUsername())
						.setGoodDetail(goodsService.getById(Long.toString(o.getGoodId())).getData()))
				.collect(Collectors.toList());
		return new HashMap<String, Object>() {
			{
				put("list", goodDetail);
				put("total", goodDetail.size());
			}
		};
	}

	/**
	 * userId获取待发货数量
	 *
	 * @return
	 */
	@Override
	public Long getWaitSend(Long id) {
		return getBaseMapper().selectCount(Wrappers.<Order>lambdaQuery()
				.eq(Order::getStatus, ProductConstants.WAIT_SEND).eq(Order::getUserId, id));
	}

	/**
	 * userId获取待退款/退货数量
	 *
	 * @return
	 */
	@Override
	public Long getWaitRefundORReturn(Long id) {
		return getBaseMapper().selectCount(Wrappers.<Order>lambdaQuery()
				.eq(Order::getStatus, ProductConstants.WAIT_REFUND_OR_RETURN).eq(Order::getUserId, id));
	}

	@Override
	public Long getWaitRefundORReturnToBusiness(Long id) {
		return getBaseMapper().selectCount(Wrappers.<Order>lambdaQuery()
				.eq(Order::getStatus, ProductConstants.WAIT_REFUND_OR_RETURN).eq(Order::getBusinessId, id));
	}

	@Override
	public Long getWaitSendToBusiness(Long id) {
		return getBaseMapper().selectCount(Wrappers.<Order>lambdaQuery()
				.eq(Order::getStatus, ProductConstants.WAIT_SEND).eq(Order::getBusinessId, id));
	}

}
