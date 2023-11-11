<template>
	<view class="my-sellerIndent-container">
		<view class="my-sellerIndent-box" @click="sellerIndentBoxHandler">
			<view class="indent-item">
				<!-- 订单头部 -->
				<view class="indent-head">
					<view class="indent-number">
						{{ indentItem.orderId }}
					</view>
					<view class="indent-state">
						{{ indentStatus }}
					</view>
				</view>
				<!-- 订单身体 -->
				<view class="indent-body">
					<view class="indent-img">
						<image class="bg" :src="indentItem.goodDetail.images[0]"></image>
					</view>
					<view class="indent-message">
						<view class="indent-message-left">
							<view class="indent-message-name">
								{{ indentItem.goodDetail.goodsName }}
							</view>
							<view class="indent-message-time">
								{{ indentItem.timeExpire }}
							</view>
						</view>
						<view class="indent-message-right">
							<view class="indent-message-right-top">
								<view class="indent-message-univalence">
									￥{{ indentItem.goodDetail.price }}
								</view>
								<view class="amount">
									<uni-icons type="closeempty" size="14"></uni-icons>
									{{ indentItem.payMethod }}
								</view>
							</view>
							<view class="indent-message-right-bottom">
								<view class="total-prices">
									￥{{ indentItem.goodDetail.price * payMethod }}
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>


<script>
	export default {
		//自定义组件的名称
		name: "my-search",

		props: {
			// 订单详情
			indentItem: {
				type: Object,
				default: {}
			},
		},


		computed: {
			payMethod() {
				return Number(this.indentItem.payMethod)
			},
			// 订单状态
			indentStatus() {
				switch (this.indentItem.status) {
					case "0":
						return '待发货'
						break;
					case "1":
						return '待发货'
						break
					case "2":
						return '待收货'
						break;
					case "3":
						return '退货/退款'
						break;
					case "4":
						return '待退货/退款'
						break;
					case "5":
						return '已收货'
						break;
					default:
						return ''
				}
			}
		},

		data() {
			return {

			};
		},

		methods: {
			sellerIndentBoxHandler() {
				this.$emit('indentClick', this.indentItem.orderId)
			}
		},
	}
</script>


<style lang="scss">
	.indent-item {
		width: 100%;
		height: 140px;
		margin-bottom: 20px;
		border-radius: 10px;
		font-size: 14px;
		background-color: white;

		.indent-head {
			display: flex;
			justify-content: space-between;
			align-items: flex-end;
			height: 20%;
			padding: 0px 10px;
		}

		.indent-body {
			display: flex;
			align-items: center;
			height: 80%;
			padding: 0 10px 0 10px;
			margin-bottom: 10px;

			.indent-img {
				width: 30%;
				height: 80%;
				margin-right: 10px;

				.bg {
					width: 100%;
					height: 100%;
					border-radius: 10px;
				}
			}

			.indent-message {
				display: flex;
				width: 70%;
				height: 70px;

				.indent-message-left {
					display: flex;
					flex-direction: column;
					justify-content: space-between;
					width: 70%;
					height: 100%;

					.indent-message-name {
						width: 100%;

						// 在同一行显示并给省略号
						white-space: nowrap;
						overflow: hidden;
						text-overflow: ellipsis;
					}

					.indent-message-time {
						font-size: 12px;
					}
				}

				.indent-message-right {
					display: flex;
					flex-direction: column;
					justify-content: space-between;
					align-items: flex-end;

					width: 30%;

					.indent-message-right-top {
						display: flex;
						flex-direction: column;
						align-items: flex-end;
					}
				}
			}
		}
	}
</style>
