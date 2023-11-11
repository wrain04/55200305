<template>
	<view class="my-indentGoods-container">
		<view class="my-indentGoods-box" @click="indentGoodsBoxHandler">
			<view class="myGoods-item">
				<view class="myGoods-img">
					<image class="bg" :src="indentGood.images[0]"></image>
				</view>
				<view class="myGoods-information">
					<view class="myGoods-head">
						<view class="myGoodsName">
							{{indentGood.goodsName}}
						</view>
						<view class="myGoods-state" :style="'color:' + ('#C00000')">
							已上架商品
						</view>
					</view>
					<view class="myGoods-number">
						<!-- 销量 -->
						<view class="sales-volume">
							销量：{{indentGood.sales | tofixed }}斤
						</view>
						<!-- 单价 -->
						<view class="unit-price">
							{{indentGood.price | tofixed }}/斤
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
			// 商品
			indentGood: {
				type: Object,
				default: {}
			}
		},

		data() {
			return {

			};
		},

		methods: {
			indentGoodsBoxHandler() {
				this.$emit('indentGoodClick', this.indentGood.goodsId)
			}
		},


		filters: {
			// 把数字处理为带两位小数点的数字
			tofixed(num) {
				return Number(num).toFixed(2)
			}
		}
	}
</script>


<style lang="scss">
	.my-indentGoods-box {
		background-color: none;
	}

	.myGoods-item {
		display: flex;
		box-sizing: border-box;
		width: 100%;
		height: 120px;
		padding: 10px;
		margin-bottom: 10px;
		border-radius: 20px;

		background-color: #ffffff;

		.myGoods-img {
			width: 40%;
			height: 100px;

			.bg {
				width: 100%;
				height: 100%;
				border-radius: 10px;
			}

		}

		.myGoods-information {
			display: flex;
			flex-direction: column;
			justify-content: space-between;

			width: 65%;
			height: 100px;
			margin-left: 10px;

			.myGoods-head {
				display: flex;
				justify-content: space-between;
				align-items: center;

				.myGoodsName {
					// 在同一行显示并给省略号
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
					font-size: 15px;
				}

				.myGoods-state {
					font-size: 12px;
				}
			}

			.myGoods-number {
				display: flex;
				justify-content: space-between;
				font-size: 14px;

				.sales-volume {}

				.unit-price {}
			}
		}
	}
</style>
