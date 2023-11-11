<template>
	<view>
		<view class="collect-title">
			{{ collectStatus }}

			<!-- 底部的边框线 -->
			<image src="/static/cart_border@2x.png" class="address-border"></image>
		</view>
		<view class="shop-list" v-if="state == 1">
			<view v-for="(item, index) in shopList" :key="index" @click="gotoShop(item)">
				<my-shop :shop="item"></my-shop>
			</view>
		</view>
		<view class="goods-list" v-else>
			<view v-for="(item, index) in goodsList" :key="index" @click="gotoGoodsDetail(item)">
				<my-collect :goods="item"></my-collect>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 存放商品列表数据
				goodsList: [],
				// 存放店铺列表数据
				shopList: [{

				}, {

				}],
				// 状态
				state: 0,
			};
		},

		computed: {
			// 收藏状态
			collectStatus() {
				switch (this.state) {
					case "0":
						return '收藏商品'
						break;
					case "1":
						return '收藏店铺'
						break
					case "2":
						return '浏览足迹'
						break;
					default:
						return ''
				}
			}
		},

		methods: {
			// 获取收藏商品列表
			async getGoodsList(state) {
				const {
					data: res
				} = await uni.$http.get('/goods/collectionApi/getCollectWithMark?mark=' + state)
				console.log(res);
				if (res.code !== 200) return uni.$showMsg()
				this.goodsList = res.data
			},

			// 获取店铺

			// 点击跳转到店铺详情页面
			gotoShop(item) {
				uni.navigateTo({
					url: '/subpkg/seller/seller?seller_id=' + item[0]
				})
			},
			// 点击跳转到商品详情页面
			gotoGoodsDetail(item) {
				uni.navigateTo({
					url: '/subpkg/goods_detail/goods_detail?goods_id=' + item[0]
				})
			},
		},

		onLoad(loading) {
			this.state = loading.state
			if (loading.state != 1) {
				this.getGoodsList(loading.state)
			}

			// if (loading.state === 0) {

			// }
		},

		onShow() {
			if (this.state != 1) {
				this.getGoodsList(this.state)
			}
		}
	}
</script>

<style lang="scss">
	.collect-title {
		// 设置定位效果为“吸顶”
		position: sticky;
		// 吸顶的“位置”
		top: 0;
		// 提高层级，防止被轮播图覆盖
		z-index: 999;

		// margin: 10px 10px 5px 10px;
		padding: 10px 10px 5px 10px;
		font-size: 18px;
		background-color: #f5f5f5;

		.address-border {
			display: block;
			margin-top: 5px;
			width: 100%;
			height: 5px;
		}
	}

	.goods-list {
		padding: 10rpx 10rpx 0 10rpx;
	}
</style>
