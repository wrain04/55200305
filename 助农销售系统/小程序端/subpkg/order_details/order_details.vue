<template>
	<view class="orderDetails-container" :style="'height:' + wh + 'px'">
		<view class="orderDetails">
			<view class="indent-state">
				{{ indentStatus[0] }}
			</view>
			<view class="indent-message">
				<view class="indent-message-top" @click="gotoGoodsDetail">
					<view class="indent-img">
						<image class="bg" :src="orderItem.goodDetail.images[0]"></image>
					</view>
					<view class="indent-name">
						{{ orderItem.goodDetail.goodsName }}
					</view>
				</view>
				<view class="indent-message-bottom">
					<view class="message ">
						<text>单价</text>
						<text>￥{{ orderItem.goodDetail.price }}</text>
					</view>
					<view class="message ">
						<text>数量</text>
						<view class="">
							<uni-icons type="closeempty" size="14"></uni-icons>
							{{ orderItem.payMethod }}
						</view>
					</view>
					<view class="message ">
						<text>总价</text>
						<text>￥{{ orderItem.payMethod * payMethod }}</text>
					</view>
					<view class="message ">
						<text>订单编号</text>
						<text>{{ orderItem.orderId }}</text>
					</view>
					<view class="message ">
						<text>订单时间</text>
						<text>{{ orderItem.timeExpire }}</text>
					</view>
					<view class="message ">
						<text>收货人</text>
						<text>{{ orderItem.consignee }}</text>
					</view>
					<view class="message ">
						<text>收货地址</text>
						<text>{{ orderItem.detailAddress }}</text>
					</view>

				</view>
			</view>
		</view>

		<view class="orderDetails-bottom">
			<!-- 待付款 -->
			<view class="orderDetails-operate">
				<view class="operateBtn operateBtn2" @click="gotoOperate(2)" v-if="indentOperatebtn[0] == 2">
					{{ indentOperatebtn[1] }}
				</view>
				<view class="operateBtn operateBtn1" @click="gotoOperate(1)" v-if="indentStatus[1]">
					{{ indentStatus[1] }}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	// 按需导入 mapState 这个辅助函数
	import {
		mapState
	} from 'vuex'

	export default {
		data() {
			return {
				orderItem: {},
				orderId: null,
				wh: null,

			};
		},
		computed: {
			...mapState(['pattern']),

			payMethod() {
				return Number(this.orderItem.payMethod)
			},
			// 订单状态
			indentStatus() {
				if (!this.pattern && this.orderItem.status == '5') {
					return ['已收货']
				}
				switch (this.orderItem.status) {
					case "0":
						return ['待发货', '确认']
						break;
					case "1":
						return ['待发货', '已发货']
						break
					case "2":
						return ['待收货', '查看物流']
						break;
					case "3":
						return ['退货/退款', ]
						break;
					case "4":
						return ['待退货/退款', '已退货/退款']
						break;
					case "5":
						return ['已收货', '评价']
						break;
					default:
						return ''
				}
			},
			// 订单操作按钮数
			indentOperatebtn() {
				if (!this.pattern && this.orderItem.status == '5') {
					return ''
				}
				switch (this.orderItem.status) {
					case "5":
						return [2, '退货/退款']
						break;
					default:
						return [1]
				}
			}
		},

		methods: {
			async getOrderItem(orderId) {
				const {
					data: res
				} = await uni.$http.get('/orders/orderApi/' + orderId)
				console.log(res);
				if (res.code !== 200) return uni.$showMsg()
				this.orderItem = res.data
			},

			// 前往商品详情页面
			gotoGoodsDetail() {
				uni.navigateTo({
					url: '/pages/goods_detail/goods_detail?goods_id=' + orderItem.goodDetail.goodsId
				})
			},

			// 点击状态按钮
			gotoOperate(n) {
				const that = this
				switch (this.orderItem.status) {
					case "0":
						return ['待付款', '付款']
						break;
					case "1": {
						uni.showModal({
							title: '确认发货',
							content: '您确认已经发货了吗？',
							success: function(res) {
								if (res.confirm) {

								} else if (res.cancel) {

								}
							}
						});
						return ['待发货', '已发货']
					}
					break;
				case "2":

					return ['待收货', '查看物流']
					break;
				case "3":

					return ['退货/退款', ]
					break;
				case "4": {
					uni.showModal({
						title: '确认退货/退款',
						content: '您确认退货/退款了吗？',
						success: function(res) {
							if (res.confirm) {

							} else if (res.cancel) {

							}
						}
					});
					return ['待退货/退款', '已退货/退款']
				}
				break;
				case "5": {
					if (n == 1) {
						console.log(111);
						uni.navigateTo({
							url: '/subpkg/evaluation/evaluation?orderId=' + this.orderItem.orderId
						})
						return ['已收货', '评价']
					} else if (n == 2) {

					}

				}
				break;
				default:
					return ''
				}
			}
		},

		onLoad(loading) {
			this.orderId = loading.orderId
			console.log(this.orderId);

			this.getOrderItem(loading.orderId)

			// 获取当前系统的信息
			const sysInfo = uni.getSystemInfoSync()
			// 为 wh 窗口可用高度动态赋值
			this.wh = sysInfo.windowHeight
		}
	}
</script>

<style lang="scss">
	.orderDetails-container {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 100%;

		.orderDetails {
			box-sizing: border-box;
			width: 80%;
			height: 85%;
			border-radius: 20px;
			padding: 10px 5px;
			background-color: white;

			.indent-state {
				height: 30px;
				font-size: 20px;
				padding: 0 0 5px 20px;
				border-bottom: 1px solid #f0f0f0;
			}

			.indent-message {
				.indent-message-top {
					box-sizing: border-box;
					float: left;
					width: 100%;
					heignt: 100px;
					padding: 10px 8px 10px 0;
					font-size: 14px;

					.indent-img {
						float: left;
						width: 100px;
						height: 100px;
						margin: 0 10px;

						.bg {
							width: 100%;
							height: 100%;
							border-radius: 10px;
						}
					}

					.indent-name {
						font-size: 14px;
					}
				}

				.indent-message-bottom {
					.message {
						box-sizing: border-box;
						display: flex;
						justify-content: space-between;
						align-items: center;
						width: 100%;
						padding: 10px;

						& text:nth-child(2) {
							display: flex;
							justify-content: flex-end;
							width: 70%;
							font-size: 14px;
						}
					}
				}
			}
		}

		.orderDetails-bottom {
			position: fixed;
			bottom: 0;
			width: 100%;
			height: 9%;
			border-top: 1px solid #c2c2c2;
			background-color: #ffffff;

			.orderDetails-operate {
				display: flex;
				justify-content: flex-end;
				align-items: center;
				height: 100%;

				.operateBtn {
					display: flex;
					justify-content: center;
					align-items: center;
					width: 180rpx;
					height: 70%;
					margin-right: 10px;
					padding: 0 10px;
					border-radius: 30px;
				}

				.operateBtn1 {
					border: 1px solid orangered;
					color: orangered;
				}

				.operateBtn2 {
					border: 1px solid #acacac;
					color: #6a6a6a;
				}
			}
		}
	}
</style>
