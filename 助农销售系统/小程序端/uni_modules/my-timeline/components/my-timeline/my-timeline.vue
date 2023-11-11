<template>
	<view class="timeline-container">
		<view class="timeline-top">
			{{ timeChart.createTime }}
		</view>
		<view class="timeline-bottom">
			<text class="timeline-title">
				{{comment}}
			</text>
			<text class="timeline-address">
				发布地点：{{address}}
			</text>
			<view class="timeline-imgs">
				<view class="timeline-image" v-for="(item, index) in images" :key="index">
					<image :src="item"></image>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			timeChart: {
				type: Object,
				default: {}
			}
		},

		data() {
			return {
				timeChartComment: {},
				comment: '',
				address: '',
				images: []
			}
		},

		methods: {
			preview(index) {
				// 调用 uni.previewImage() 方法预览图片
				uni.previewImage({
					// 预览时，默认显示图片的索引
					current: index,
					// 所有图片 url 地址的数组
					// 使用 map 方法，将 pics 中的所有对象中的 pics_big 组成一个新数组，并赋值给 urls
					urls: this.images
				})
			},
			Commentandadd() {
				if (this.timeChart.info != null) {
					this.timeChartComment = JSON.stringify(this.timeChart.info)
					this.comment = this.timeChart.info.split('@')[0]
					this.address = this.timeChart.info.split('@')[1]
					var images = this.timeChart.info.split('@')[2]
					this.images.push(images.substring(0, images.length - 1))
				}
			}

		},
		created() {
			this.Commentandadd()
		}
	}
</script>

<style lang="scss" scoped>
	.timeline-container {
		box-sizing: border-box;
		width: 100%;
		padding: 20rpx 20rpx 0 20rpx;


		.timeline-top {
			display: flex;
			align-items: center;
			font-size: 16px;
			color: #8f8f8f;

			&::before {
				content: '';
				display: block;
				width: 36rpx;
				height: 36rpx;
				border: 4rpx solid #8f8f8f;
				border-radius: 100%;
				margin-right: 40rpx;
			}
		}

		.timeline-bottom {

			margin: 18rpx 18rpx 0 18rpx;
			border-left: 1px dashed #8f8f8f;
			padding: 20rpx 0 20rpx 20rpx;

			.timeline-title {
				display: block;
			}

			.timeline-address {
				display: block;
				font-size: 12px;
			}

			.timeline-imgs {
				display: flex;
				padding: 16rpx 0 0 16rpx;

				.timeline-image {
					margin: 0 16rpx 16rpx 0;
					width: 196rpx;
					height: 196rpx;

					image {
						width: 100%;
						height: 100%;
					}
				}
			}
		}
	}
</style>
