<template>
	<view class="goods-item">
		<!-- 商品左侧图片区域 -->
		<view class="goods-item-left">
			<image :src="goods[1] || defaultPic" class="goods-pic"></image>
		</view>
		<!-- 商品右侧信息区域 -->
		<view class="goods-item-right">
			<!-- 商品标题 -->
			<view class="goods-name">
				{{ goods[2] }}
				<view class="description">
					<!-- {{ goods.description }} -->
				</view>
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
				type: Array,
				default: []
			},
		},

		data() {
			return {
				// 默认的空图片
				defaultPic: 'https://img3.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png',
			}
		},

		methods: {

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
		margin: 0 5px;
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
			}

		}
	}
</style>
