package com.xyw.product.order.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.common.core.constant.ProductConstants;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.common.security.annotation.Inner;
import com.xyw.product.common.security.util.SecurityUtils;
import com.xyw.product.order.dto.OrderDTO;
import com.xyw.product.order.entity.Order;
import com.xyw.product.order.service.OrdersService;
import com.xyw.product.order.vo.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderApi")
@Tag(name = "订单表管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class OrdersController {

	private final OrdersService ordersService;

	/**
	 * 分页查询
	 * @param page
	 * @param orders
	 * @return
	 */
	@Inner(false)
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/ordersPage")
	public R getOrdersPage(Page page, Order orders) {
		return R.ok(ordersService.page(page, Wrappers.query(orders)));
	}

	/**
	 * 用户下单
	 * @return
	 */
	@Operation(summary = "用户下单", description = "用户下单")
	@SysLog("用户下单")
	@PostMapping("/createOrder")
	public R createOrder(@RequestBody OrderDTO dto) {
		// TODO 下单详细操作
		// 不能直接插入数据库，而是插入到redis中，同时设置缓存过期时间，付款的时候从redis中拿，拿不到就是订单过期，拿到了并且付款成功了，则再把订单改变状态，加入到数据库中
		return R.ok(ordersService.createOrder(dto));
	}

	/**
	 * 通过order_id删除订单
	 * @param orderId
	 * @return
	 */
	@Operation(summary = "通过order_id删除订单", description = "通过order_id删除订单")
	@SysLog("通过order_id删除订单")
	@DeleteMapping("/deleteOrder/{orderId:\\d+}")
	@PreAuthorize("@pms.hasPermission('orders_orders_del')")
	public R deleteByOrderId(@PathVariable Long orderId) {
		return R.ok(ordersService.removeById(orderId));
	}

	/**
	 * 修改订单
	 * @param order
	 * @return
	 */
	@Operation(summary = "修改订单", description = "修改订单")
	@SysLog("修改订单")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('orders_orders_edit')")
	public R updateByOrderId(@RequestBody Order order) {
		return R.ok(ordersService.updateById(order));
	}

	/**
	 * 根据order_id获取用户的订单
	 * @param orderId
	 * @return
	 */
	@Operation(summary = "通过order_id查询订单", description = "通过order_id查询订单")
	@SysLog("通过order_id查询订单")
	@GetMapping("/{orderId:\\d+}")
	public R getOrderByOrderId(@PathVariable Long orderId) {
		return R.ok(ordersService.getOrderByOrderId(orderId));
	}

	/**
	 * 获取所有订单
	 * @return
	 */
	@Operation(summary = "获取所有订单", description = "获取所有订单")
	@SysLog("获取所有订单")
	@GetMapping("/list")
	public R<Map<String, Object>> getAllOrder() {
		return R.ok(ordersService.getAllOrder());
	}

	/**
	 * 用户通过status查询订单明细
	 * @param status
	 * @return
	 */
	@Operation(summary = "用户通过status查询订单明细", description = "用户通过status查询订单明细")
	@SysLog("用户通过status查询订单明细")
	@GetMapping("/getOrderByStatus/{status:\\d+}")
	public R getOrderByStatus(@PathVariable Long status) {
		return R.ok(ordersService.getOrderByStatus(status, true));
	}

	/**
	 * 商户通过status查询订单明细
	 * @param status
	 * @return
	 */
	@Operation(summary = "商户通过status查询订单明细", description = "商户通过status查询订单明细")
	@SysLog("商户通过status查询订单明细")
	@GetMapping("/getOrderByStatusToBusiness/{status:\\d+}")
	public R getOrderByStatusToBusiness(@PathVariable Long status) {
		return R.ok(ordersService.getOrderByStatus(status, false));
	}

	/**
	 * 查询订单总数
	 * @return
	 */
	@Operation(summary = "查询订单总数", description = "查询订单总数")
	@SysLog("查询订单总数")
	@GetMapping("/getCount")
	public R getCount() {
		return R.ok(ordersService
				.count(Wrappers.<Order>lambdaQuery().eq(Order::getUserId, SecurityUtils.getUser().getId())));
	}

	/**
	 * 用户获取自己的待发货数量
	 * @return
	 */
	@GetMapping("/getWaitSend")
	public R getWaitSend() {
		return R.ok(ordersService.getWaitSend(SecurityUtils.getUser().getId()));
	}

	/**
	 * 商户获取店铺的待退款/退货数量
	 * @return
	 */
	@GetMapping("/getWaitRefundORReturnToBusiness")
	public R getWaitRefundORReturnToBusiness() {
		return R.ok(ordersService.getWaitRefundORReturnToBusiness(SecurityUtils.getUser().getId()));
	}

	/**
	 * 商户获取店铺的待发货数量
	 * @return
	 */
	@GetMapping("/getWaitSendToBusiness")
	public R getWaitSendToBusiness() {
		return R.ok(ordersService.getWaitSendToBusiness(SecurityUtils.getUser().getId()));
	}

	/**
	 * 用户获取待退款/退货数量
	 * @return
	 */
	@GetMapping("/getWaitRefundORReturn")
	public R getWaitRefundORReturn() {
		return R.ok(ordersService.getWaitRefundORReturn(SecurityUtils.getUser().getId()));
	}

	// @GetMapping("/autoInsert")
	// @Inner(false)
	// public R autoInsert() {
	// Random rd = new Random();
	// ArrayList<Byte> bytes = new ArrayList<Byte>() {{
	// add(ProductConstants.WAIT_PAY);
	// add(ProductConstants.WAIT_SEND);
	// add(ProductConstants.WAIT_OBTAIN);
	// add(ProductConstants.REFUND_OR_RETURN);
	// add(ProductConstants.WAIT_REFUND_OR_RETURN);
	// add(ProductConstants.FINISH_OBTAIN);
	// }};
	// ordersService.getAllOrder().stream().forEach(vo -> {
	// Order order = new Order();
	// order.setOrderId(vo.getOrderId());
	// order.setStatus("" + bytes.get(rd.nextInt(bytes.size())));
	// ordersService.updateById(order);
	// });
	// return R.ok();
	// }

}
