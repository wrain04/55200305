<template>
	<view class="goods-item">
		<!-- 商品左侧图片区域 -->
		<view class="goods-item-left">
			<radio :checked="goods.goods_state" color="#C00000" v-if="showRadio" @click="radioClickHandler"></radio>
			<image :src="goods.images[0] || defaultPic" class="goods-pic"></image>
		</view>
		<!-- 商品右侧信息区域 -->
		<view class="goods-item-right">
			<!-- 商品标题 -->
			<view class="goods-name">
				{{ goods.goodsName }}
				<view class="description">
					{{ goods.description }}
				</view>
				<view class="sellerposition">
					当前商户距离您：{{goods.distance}}
				</view>
			</view>
			<view class="goods-info-box">
				<!-- 商品价格 -->
				<view class="goods-price">
					￥{{ gradePrice || goods.price || goods.gradePrice | tofixed}}/斤
				</view>
				<!-- 商品数量 -->
				<uni-number-box :min="1" :value="goods.goods_count" @change="numChangeHandler" v-if="showNum">
				</uni-number-box>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		//自定义组件的名称
		name: "my-goods",

		props: {
			// 商品的信息对象
			goods: {
				type: Object,
				default: {}
			},
			// 是否展示图片左侧的 radio
			showRadio: {
				type: Boolean,
				default: false
			},
			// 是否展示价格右侧的 NumberBox 组件
			showNum: {
				type: Boolean,
				defalut: false
			},
			// 等级价格
			gradePrice: {
				type: Number,
				default: 0
			}
		},

		data() {
			return {
				// 默认的空图片
				defaultPic: 'https://img3.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png',
			}
		},

		methods: {
			// radio 组件的点击事件处理函数
			radioClickHandler() {
				// 通过 this.$emit() 触发外界通过 @ 绑定的 radio-change 事件，
				// 同时把商品的 Id 和 勾选状态 作为参数传递给 radio-change 事件处理函数
				this.$emit('radio-change', {
					// 商品的 Id
					goodsId: this.goods.goodsId,
					// 商品最新的勾选状态
					goods_state: !this.goods.goods_state
				})
			},

			numChangeHandler(val) {
				this.$emit('num-change', {
					// 商品的 Id
					goodsId: this.goods.goodsId,
					// 商品的最新数量（为了保证这个 val 是个数值，所以在它前面加个 + 号）
					goods_count: +val
				})
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
	.goods-item {
		display: flex;
		width: 750rpx;
		box-sizing: border-box;
		padding: 10px 5px;
		border-bottom: 1px solid #f0f0f0;

		.goods-item-left {
			margin-right: 5px;
			display: flex;
			justify-content: center;
			align-items: center;

			.goods-pic {
				width: 200rpx;
				height: 200rpx;
				display: block;
			}
		}

		.goods-item-right {
			display: flex;
			flex: 1;
			flex-direction: column;
			justify-content: space-between;

			.goods-name {
				font-size: 16px;

				.description {
					width: 460rpx;
					font-size: 12px;
					// 在同一行显示并给省略号
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
				}

				.sellerposition {
					width: 460rpx;
					font-size: 12px;
					// 在同一行显示并给省略号
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
				}
			}


			.goods-info-box {
				display: flex;
				justify-content: space-between;
				align-items: center;

				.goods-price {
					font-size: 16px;
					color: #c00000;
				}
			}
		}
	}
</style>
