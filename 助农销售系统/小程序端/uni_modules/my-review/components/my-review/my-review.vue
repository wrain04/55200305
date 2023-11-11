<template>
	<view class="myReview-container">
		<view class="myReview-top">
			<view class="myReview-information">
				<view class="myReview-information-left">
					<view class="headPortrait">
						<image :src="userInfo.avatar"></image>
					</view>
					<view class="myReview-message">
						<view class="myReview-userName">
							{{ userInfo.username }}
						</view>
						<view class="myReview-time">
							{{ review.createTime }}
						</view>
					</view>
				</view>
				<view class="myReview-information-right">
					<view v-for="(item,index) in review.star" :key="index">
						<uni-icons type="star-filled" size="18" color="orangered"></uni-icons>
					</view>
					<view v-for="(item,index) in (5 - review.star)" :key="index">
						<uni-icons type="star" size="18" color="#5e6d82"></uni-icons>
					</view>
				</view>

			</view>
		</view>
		<view class="myReview-bottom">
			<view class="myReview-introduce">
				{{ reviewContent.info }}
			</view>
			<view class="myReview-images" v-if="state">
				<!-- 图片 -->
				<view class="merchant-image" v-for="(item, index) in reviewContent.images" :key="index">
					<image :src="item"></image>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		//自定义组件的名称
		name: "my-review",

		props: {
			// 评论内容
			review: {
				type: Object,
				default: {}
			},
			state: {
				type: Number
			}
		},

		data() {
			return {
				abc: [{}, {}, {}],
				userInfo: {},
				reviewContent: {},
			};
		},


		methods: {
			// 获取用户头像与昵称
			async getUserInfo() {
				const {
					data: res
				} = await uni.$http.get('/admin/user/avatar/' + this.review.userId)
				console.log(res);
				if (res.code !== 200) return uni.$showMsg();
				this.userInfo = res.data
			}
		},

		created() {
			this.getUserInfo()
			console.log('评论', this.review.content);
			this.reviewContent = JSON.parse(this.review.content)
		}
	}
</script>

<style lang="scss" scoped>
	.myReview-container {
		border-top: 1px solid #d4d4d4;
		margin: 0 20rpx;
		padding: 20rpx 0;

		.myReview-top {
			display: flex;
			align-items: center;
			height: 100rpx;

			.myReview-information {
				display: flex;
				justify-content: space-between;
				align-items: center;
				width: 100%;
				margin-bottom: 10rpx;

				.myReview-information-left {
					display: flex;

					.headPortrait {
						width: 80rpx;
						height: 80rpx;
						margin-right: 8rpx;
						border-radius: 100%;
						background-color: orangered;

						image {
							width: 100%;
							height: 100%;
							border-radius: 100%;
						}
					}

					.myReview-message {
						.myReview-userName {
							font-size: 18px;
						}

						.myReview-time {
							font-size: 14px;
							color: #b8b8b8;
						}
					}
				}

				.myReview-information-right {
					display: flex;
				}


			}
		}

		.myReview-bottom {
			.myReview-introduce {}

			.myReview-images {
				display: flex;
				flex-wrap: wrap;
				margin: 30rpx 0 0 14rpx;

				.merchant-image {
					width: 210rpx;
					height: 210rpx;
					margin: 0rpx 22rpx 20rpx 0;
					border-radius: 5px;

					background-color: #f5f5f5;

					image {
						width: 210rpx;
						height: 210rpx;
					}
				}
			}
		}
	}
</style>
