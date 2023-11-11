<template>
	<view class="reviews-container">
		<view class="reviews-top">
			<view :class="!isOnlyImageState ? 'chooseReview reviewBtn' : 'reviewBtn'"
				@click="changeIsOnlyImageState(0)">
				全部
			</view>
			<view :class="isOnlyImageState ? 'chooseReview reviewBtn' : 'reviewBtn'" @click="changeIsOnlyImageState(1)">
				图片
			</view>
		</view>
		<view class="reviews-bottom">
			<view class="reviews-bottom-title">
				<view :class="isOrderState ? 'chooseReview reviewBtn' : 'reviewBtn'" @click="changeIsOrderState(1)">
					综合
				</view>
				<view class="division"></view>
				<view :class="!isOrderState ? 'chooseReview reviewBtn' : 'reviewBtn'" @click="changeIsOrderState(0)">
					最新
				</view>
			</view>
			<view class="reviews-bottom-body">
				<view v-for="(item,index) in reviewsList" :key="index">
					<my-review :review="item" :state="1"></my-review>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				goodsId: null,
				current: 0,
				size: 10,
				reviewsList: [],
				isOrderState: 0,
				isOnlyImageState: 0,
			};
		},

		methods: {
			async getReviewsList(newState) {
				if (newState) {
					this.reviewsList = []
					this.current = 0
				}
				const {
					data: res
				} = await uni.$http.get('/goods/commentApi/getCommentByGoodsId/' + this.goodsId + '?current=' + this
					.current++ + '&size=' + this.size + '&isOrder=' + this.isOrderState + '&isOnlyImage=' + this
					.isOnlyImageState)
				console.log(res);
				if (res.code !== 200) return uni.$showMsg()
				this.reviewsList = this.reviewsList.concat(res.data.comments)
			},

			// 更改 全部/图片 事件
			changeIsOnlyImageState(i) {
				if (this.isOnlyImageState == i) return
				this.isOnlyImageState = i;
				this.getReviewsList(1)
			},

			// 更改 综合/最新 事件
			changeIsOrderState(i) {
				if (this.isOrderState == i) return
				this.isOrderState = i;
				this.getReviewsList(1)
			},

		},

		onLoad(loading) {
			this.goodsId = loading.goodsId
			this.getReviewsList(1)
		}
	}
</script>

<style lang="scss">
	.reviews-container {
		.reviews-top {
			position: sticky;
			top: 0;
			z-index: 999;
			display: flex;
			align-items: center;
			height: 100rpx;
			padding: 0 10rpx;
			background-color: #f5f5f5;

			.reviewBtn {
				padding: 0 10rpx;
				font-size: 18px;
			}

			.chooseReview {
				color: orangered;
			}

		}

		.reviews-bottom {
			min-height: 80vh;
			border-radius: 50rpx;
			background-color: #ffffff;

			.reviews-bottom-title {
				display: flex;
				justify-content: flex-end;
				align-items: center;
				padding: 20rpx 0rpx;
				margin: 0 20rpx;

				.reviewBtn {
					padding: 0 10rpx;
					font-size: 16px;
				}

				.division {
					border-left: 1rpx solid #999999;
					height: 40rpx;
					margin: 0 10rpx;
				}

				.chooseReview {
					color: orangered;
				}
			}

			.reviews-bottom-body {}
		}
	}
</style>
