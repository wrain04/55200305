<template>
	<view class="evaluation-container">
		<view class="evaluation-top">
			<view class="evaluation-title">
				商品评价
			</view>
			<view class="evaluation-starLevel">
				<view v-for="(item,index) in evaluation.starNum" :key="index">
					<uni-icons type="star-filled"size="34" color="orangered" @click="starNumTrue(index)"></uni-icons>
				</view>
				<view v-for="(item,index) in (5 - evaluation.starNum)" :key="index">
					<uni-icons type="star"size="34" color="#5e6d82" @click="starNumFalse(index)"></uni-icons>
				</view>
			</view>
		</view>
		
		<view class="evaluation-bottom">
			<view class="brief-introduction">
				<view class="brief-introduction-title">
					追加一下你的购买体验吧 ~
				</view>
				<view class="brief-introduction-message">
					<textarea placeholder="对评价进行补充 ~"></textarea>
				</view>
			</view>
			<view class="evaluation-img">
				<!-- 图片 -->
				<view class="evaluation-image" v-for="(item, index) in evaluation.evaluationImageList" :key="index">
					<image :src="item"></image>
					<view class="delete-img" @click="deletImg(index, 0)">
						<uni-icons type="closeempty" size="14" color="#fff"></uni-icons>
					</view>
				</view>
				<!-- 选择图片 -->
				<view class="select-image" @click="selectImage(0)" v-if="evaluation.evaluationImageList.length < 6">
					<uni-icons type="plusempty" size="20"></uni-icons>
					<view>选择图片</view>
				</view>
			</view>
		</view>
			
		<!-- 提交 -->
		<view class="submit" @click="submit">
			<text class="submit-text">{{ '提 交 评 价' }}</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				orderId: null,
				evaluation: {
					starNum: 5,
					evaluationImageList: []
				},
			};
		},
		
		methods: {
			starNumTrue(n) {
				this.evaluation.starNum = n + 1
			},
			
			starNumFalse(n) {
				this.evaluation.starNum += n + 1
			},
			
			// 提交评价
			submit() {
				const that = this
				
				uni.showModal({
					title: '提交评价',
					content: '您确认提交您的评价信息吗？',
					success: function (res) {
						if (res.confirm) {
							console.log(that);
							
							uni.navigateBack({
								delta: 1
							});
						} else if (res.cancel) {
							
						}
					}
				});
			},
			
			// 删除证书栏的图片
			deletImg(i, n) {
				this.evaluation.evaluationImageList.splice(i, 1)
			},
			
			// 选择图片
			selectImage(n) {
				const that = this
				
				uni.chooseImage({
					success: function (res) {
						that.evaluation.evaluationImageList.push('' + res.tempFilePaths)
					}
				})
			}
		},
		
		onLoad(loading) {
			this.orderId = loading.orderId
		}
	}
</script>

<style lang="scss">
	.evaluation-container {
		padding-bottom: 180rpx;
		
		.evaluation-top {
			margin: 20rpx 0;
			padding: 20rpx;
			border-radius: 30rpx;
			background-color: #ffffff;
			
			.evaluation-title {
				margin-bottom: 10rpx;
				font-size: 20px;
			}
			.evaluation-starLevel {
				display: flex;
			}
		}
		
		.evaluation-bottom {
			border-radius: 30rpx;
			background-color: #ffffff;
			
			.brief-introduction {
				
				.brief-introduction-title {
					padding: 20rpx;
					background-color: #fafafa;
					font-size: 17px
				}
				
				.brief-introduction-message {
					padding: 30rpx 20rpx;
					background-color: #ffffff;
					
					textarea {
						width: 710rpx;
						height: 300rpx;
					}
				}
			}
			
			.evaluation-img {
				display: flex;
				flex-wrap: wrap;
				box-sizing: border-box;
				
				width: 750rpx;
				margin: 10rpx 0 10rpx 0;
				padding: 30rpx 0 0 30rpx;
				border-radius: 10rpx;
				
				background-color: #ffffff;
				
				.evaluation-image {
					position: relative;
					width: 210rpx;
					height: 210rpx;
					margin: 0rpx 30rpx 30rpx 0;
					border-radius: 5px;
					
					background-color: #f5f5f5;
					
					image {
						width: 210rpx;
						height: 210rpx;
					}
					
					.delete-img {
						position: absolute;
						top: 10rpx;
						right: 10rpx;
						
						width: 40rpx;
						height: 40rpx;
						border-radius: 100%;
						
						background-color: #9d9d9d;
						text-align: center;
						line-height: 40rpx;
					}
				}
				
				.select-image {
					display: flex;
					flex-direction: column;
					justify-content: center;
					align-items: center;
					
					width: 210rpx;
					height: 210rpx;
					margin: 0 30rpx 30rpx 0;
					border-radius: 5px;
					
					background-color: #f5f5f5;
					
				}
			}
		}
		
		// 提交
		.submit {
			position: fixed;
			bottom: 0;
			left: 0;
			
			width: 100%;
			height: 140rpx;
			background-color: #c00000;
			z-index: 999;
			text-align: center;
			
			&::after {
				content: '';
				position: absolute;
				top: -70rpx;
				left: 0;
				width: 100%;
				height: 140rpx;
				border-radius: 100%;
				z-index: -1;
				
				background-color: #c00000;
			}
			
			.submit-text {
				font-size: 60rpx;
				color: #fff;
			}
		}
	}
</style>
