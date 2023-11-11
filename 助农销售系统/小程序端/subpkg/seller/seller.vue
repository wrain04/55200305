<template>
	<view class="seller-container">
		<view class="seller-information">
			<view class="seller-information-top">
				<view class="seller-information-top-left">
					<view class="head-portrait">
						<image :src="businessInfo.businessLogo"></image>
					</view>
				</view>
				<view class="seller-information-top-right">
					<view class="merchant-name-type">
						<view class="merchant-name">
							{{ businessInfo.storeName }}
						</view>
						<view class="merchant-type">
							{{ '农户' }}
						</view>
					</view>

					<view class="merchant-location">
						{{ address }}
					</view>
					<view class="merchant-stapleProducts">
						{{ '主要产品: ' + businessInfo.mainProducts }}
					</view>
				</view>
			</view>
			<!-- 收藏 -->
			<view class="favi" @click="chooseCollect(collection)">
				<uni-icons type="star" size="18" color="gray" v-if="collection == false"></uni-icons>
				<uni-icons type="star-filled" size="18" color="yellow" v-else></uni-icons>
				<text>收藏</text>
			</view>
			<view class="seller-information-bottom">
				<view class="merchant-introduce">
					{{ businessInfo.description }}
				</view>
			</view>
		</view>
		<view class="seller-status">
			<view class="seller-statusBar">
				<view :class="!status ? 'statusBarTrue' : 'statusBarFalse'" @click="changeStatusBar(0)">
					商品
				</view>
				<view :class="!status ? 'statusBarFalse' : 'statusBarTrue'" @click="changeStatusBar(1)">
					动态
				</view>
			</view>
			<!-- 商品 -->
			<view class="commodity" v-if="!status">
				<view v-for="(item, index) in goodsList" :key="index" @click="gotoDetail(item)">
					<my-goods :goods="item"></my-goods>
				</view>
			</view>
			<!-- 动态 -->
			<view class="dynamicState" v-else>
				<!-- 商品详情信息 -->
				<view v-for="(item,index) in time_chart" :key="index">
					<my-timeline :timeChart="item"></my-timeline>
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				status: 0,

				// 商户id
				businessId: undefined,
				// 用户id
				userId: undefined,
				// 商户信息
				businessInfo: {},
				// 商户地址
				address: '',

				// 商品列表的数据
				goodsList: [{

				}, {

				}],
				goodsTotal: 0,
				goodsSize: 10,
				goodsCurrent: 0,

				// 存放时间图数组
				time_chart: [{

				}, {

				}],
				timeTotal: 0,
				timeSize: 10,
				timeCurrent: 1,

				// 是否收藏
				collection: 0,
			};
		},

		methods: {
			changeStatusBar(n) {
				if (this.status == n) return
				this.status = n
			},

			// 获取农户商品信息
			async getSellerCommodityList() {
				const {
					data: res
				} = await uni.$http.get('/goods/goodApi/getByBusinessId/' + this.businessId + '?size=' + this
					.goodsSize + '&current=' + this.goodsCurrent)
				console.log(111);
				console.log('农户商品信息', res);
				if (res.code !== 200) return uni.$showMsg()
				this.goodsList = res.data.list
				this.goodsTotal = res.data.total

			},

			// 获取农户动态信息
			async getSellerDynamic() {
				const {
					data: res
				} = await uni.$http.get('/goods/updatesApi/info@' + this.businessId + '?size=' + this.timeSize +
					'&current=' + this.timeCurrent)
				console.log(222);
				console.log("农户动态", res);
				if (res.code !== 200) return uni.$showMsg()
				this.time_chart = res.data.list
			},

			// 获取商户信息
			async getSellerMessage() {
				console.log('当前商户id', this.businessId);
				const {
					data: res
				} = await uni.$http.get('/goods/businessDetailApi/getBusinessDetail/' + this.businessId)
				console.log('商户详情', res);
				if (res.code !== 200) return uni.$showMsg()
				this.businessInfo = res.data
				console.log('商户信息', this.businessInfo);
				this.address = this.businessInfo.address.split('@')[0]
			},

			// 点击跳转到商品详情页面
			gotoDetail(item) {
				uni.navigateTo({
					url: '/subpkg/goods_detail/goods_detail?goods_id=' + item.goodsId + '&goods_price=' + item
						.price
				})
			},

			// 改变收藏状态
			async chooseCollect(n) {
				if (!n) {
					const {
						data: res
					} = await uni.$http.post('/goods/collectionApi/collectWithMark?mark=1&dataId=' + this.businessId)
					console.log(res);
					if (res.code !== 200) return uni.$showMsg()
					this.collection = 1
				} else {
					const {
						data: res
					} = await uni.$http.delete('/goods/collectionApi/removeWithMark?mark=1&dataIds=' + this.businessId)
					console.log(res);
					if (res.code !== 200) return uni.$showMsg()
					this.collection = 0
				}
			},

			// 获取商品是否收藏信息
			async getCollect(goods_id) {
				const {
					data: res
				} = await uni.$http.get('/goods/collectionApi/isCollect?mark=' + 1 + '&dataId=' + this.businessId)
				console.log(res);
				if (res.code !== 200) return uni.$showMsg()
				this.collection = res.data
			},


		},

		onLoad(loading) {
			this.businessId = loading.businessId
			this.userId = loading.userId
			this.getSellerMessage()
			this.getSellerCommodityList()
			this.getSellerDynamic()

			this.getCollect()
		}
	}
</script>

<style lang="scss">
	.seller-container {

		// background-color: #f5f5f5;
		.seller-information {
			background-color: #ffffff;

			.seller-information-top {
				display: flex;
				padding-top: 30rpx;

				.seller-information-top-left {
					display: flex;
					justify-content: center;
					align-items: center;

					width: 250rpx;

					.head-portrait {
						width: 180rpx;
						height: 180rpx;
						border-radius: 100%;
						background-color: #C00000;

						image {
							width: 100%;
							height: 100%;
							border-radius: 100%;
						}
					}
				}

				.seller-information-top-right {
					display: flex;
					flex-direction: column;
					justify-content: flex-end;

					width: 500rpx;
					margin: 0 25rpx 10rpx 0;

					.merchant-name-type {
						display: flex;
						justify-content: space-between;
						align-items: flex-end;

						.merchant-name {
							font-size: 24px;
						}

						.merchant-type {}
					}

					.merchant-location {
						// 在同一行显示并给省略号
						white-space: nowrap;
						overflow: hidden;
						text-overflow: ellipsis;
					}

					.merchant-stapleProducts {
						// 在同一行显示并给省略号
						white-space: nowrap;
						overflow: hidden;
						text-overflow: ellipsis;
					}
				}
			}

			.favi {
				display: flex;
				justify-content: flex-end;
				height: 50rpx;
				padding: 0 25rpx;

				text {
					margin-left: 6rpx;
				}
			}

			.seller-information-bottom {
				padding: 0 25rpx 25rpx 25rpx;

				.merchant-introduce {}
			}
		}

		.seller-status {

			.seller-statusBar {
				display: flex;

				.statusBarTrue {
					width: 325rpx;
					height: 94rpx;
					margin: 0 25rpx;
					border-bottom: 3px solid #c00000;
					font-size: 22px;
					text-align: center;
					line-height: 100rpx;
				}

				.statusBarFalse {
					width: 325rpx;
					height: 100rpx;
					padding: 0 25rpx;
					background-color: #ffffff;
					color: #6a6a6a;
					font-size: 22px;
					text-align: center;
					line-height: 100rpx;
				}
			}

			.commodity {
				padding-top: 20rpx;
			}

			.dynamicState {
				padding-top: 20rpx;
			}
		}
	}
</style>
