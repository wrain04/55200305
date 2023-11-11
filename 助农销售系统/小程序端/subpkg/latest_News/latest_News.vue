<template>
	<view class="latestNews-container">
		<view class="informationBody">
			<text>信息</text>
			<view class="goods-introduce">
				<textarea v-model="recordText" id="" cols="30" rows="8" placeholder="输入信息"></textarea>
			</view>
			<view class="positionBody" @click="showAdd()">
				<text style="font-size: 16px;">当前位置：{{address}}</text>
				<view class="addPosition">获取定位</view>
			</view>
			<text>图片</text>
			<view class="goods-img">
				<!-- 图片 -->
				<view class="goods-image" v-for="(item, index) in imageList" :key="index">
					<image :src="item"></image>
					<view class="delete-img" @click="deletImg(index)">
						<uni-icons type="closeempty" size="14" color="#fff"></uni-icons>
					</view>
				</view>
				<!-- 选择图片 -->
				<view class="select-image" @click="selectImage" v-if="imageList.length < 6">
					<uni-icons type="plusempty" size="20"></uni-icons>
					<view>选择图片</view>
				</view>
			</view>
		</view>

		<!-- 提交 -->
		<view class="submit" @click="submit()">
			<text class="submit-text">{{ '提 交 审 核' }}</text>
		</view>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations
	} from 'vuex'
	import qqmapsdk from "@/static/qqmap-wx-jssdk.js"

	const QQMapWX = new qqmapsdk({
		key: 'NA7BZ-37C3I-MOUGX-UVC3V-C3Z47-CMFVP'
	});
	export default {
		data() {
			return {
				latestNews: {
					info: '',
					businessId: '1649796123411992578',
				},
				// 近期动态信息
				recordText: '',
				// 近期动态图片列表
				imageList: [],
				// 处理之后的图片
				changeImg: [],
				// 存放定位信息
				address: '未获取当前定位',
				latitude: '',
				longitude: '',
			};
		},
		computed: {
			...mapState('m_user', ['token']),
		},
		methods: {
			// 删除图片
			deletImg(i) {
				this.imageList.splice(i, 1)
			},

			// 选择图片 
			selectImage() {
				// 将 this 重新赋值一下，因为 success 中的 this 与这里不是同一个
				const that = this
				uni.chooseImage({
					success: function(res) {
						console.log(JSON.stringify(res.tempFilePaths));
						if (that.state == 0) {
							that.imageList = res.tempFilePaths
							console.log('处理之前的数组', that.imageList);

							that.uploadImg()
						} else {
							that.imageList.push('' + res.tempFilePaths)
						}

						that.uploadImg()
					}
				})
			},

			uploadImg() {
				console.log('this.imageList.length', this.imageList.length);
				if (this.imageList.length > 0) {
					for (var i = 0; i < this.imageList.length; i++) {

						uni.uploadFile({
							url: 'http://localhost:9999/admin/sys-file/upload',
							filePath: this.imageList[i],
							name: 'file',
							header: {
								'content-type': 'multipart/form-data',
								'Authorization': 'Bearer ' + this.token
							},
							success: (uploadFileRes) => {

								let ress = JSON.parse(uploadFileRes.data)
								console.log('ress', ress)
								this.changeImg.push(ress.data.url)
							},
							fail() {
								console.log('转换失败');
							}
						})
					}
					const that = this
					var timer = setInterval(function() {
						// that.latestNews.imageList = that.changeImg
						that.imageList = that.changeImg
						clearInterval(timer)
					}, 5000)

				}

			},
			getUserLocation() {
				//获取当前地理位置
				let that = this
				uni.getLocation({
					type: 'wgs84',
					geocode: true,
					success: (res) => {
						that.latitude = res.latitude
						that.longitude = res.longitude
					},
					fail: (err) => {
						uni.showToast({
							title: '无法获取当前位置!',
							icon: 'error',
							duration: 1500
						});
					}
				})
			},
			// 让地址显示在页面上
			showAdd() {
				const that = this
				QQMapWX.reverseGeocoder({
					location: {
						latitude: that.latitude,
						longitude: that.longitude
					},
					success: function(res) {
						console.log("当前位置：", res);
						that.address = res.result.address
						console.log(that.address);
					}
				})
			},
			async submit() {
				const that = this
				uni.showModal({
					title: '提交审核',
					content: '您确认提交您的审核信息吗？',
					success: function(res) {
						that.latestNews.info = JSON.stringify(that.recordText + '@' + that.address + '@' +
							that.imageList)
						that.latestNews = JSON.stringify(that.latestNews)
						console.log('我发送', that.latestNews);
						that.postCheck()
					}
				})
			},
			async postCheck() {
				const that = this
				const {
					data: res
				} = await uni.$http.post('/goods/updatesApi',
					that.latestNews)
				console.log('提交结果', res);
				if (res.code === 200) {
					uni.showToast({
						title: '发布成功!',
						icon: 'success',
						duration: 3000
					});
					uni.switchTab({
						url: '/pages/my/my'
					})
				} else {
					uni.showToast({
						title: '请检查网络',
						icon: 'error',
						duration: 3000
					})
				}
			}
		},

		onLoad(loading) {
			this.getUserLocation()
		}
	}
</script>

<style lang="scss">
	.latestNews-container {
		position: relative;

		.informationBody {
			box-sizing: border-box;
			width: 100%;
			padding: 40rpx;
			padding-bottom: 210rpx;

			font-size: 14px;

			text {
				font-size: 16px;
				color: #333333;

			}

			.positionBody {
				display: flex;
				flex-wrap: wrap;
				box-sizing: border-box;

				width: 670rpx;
				height: 230rpx;
				margin: 10rpx 0 40rpx 0;
				padding: 25rpx 0 0 25rpx;
				border-radius: 10rpx;
				font-size: 14px;
				background-color: #ffffff;

				.addPosition {
					display: flex;
					flex-direction: column;
					justify-content: center;
					align-items: center;
					text-align: center;
					line-height: 100rpx;
					width: 190rpx;
					height: 100rpx;
					margin: 0 50rpx 25rpx 0;
					border-radius: 5px;

					background-color: #f5f5f5;
				}
			}

			.goods-img {
				display: flex;
				flex-wrap: wrap;
				box-sizing: border-box;

				width: 670rpx;
				margin: 10rpx 0 40rpx 0;
				padding: 25rpx 0 0 25rpx;
				border-radius: 10rpx;

				background-color: #ffffff;

				.goods-image {
					position: relative;
					width: 190rpx;
					height: 190rpx;
					margin: 10rpx 25rpx 25rpx 0;
					border-radius: 5px;

					background-color: #f5f5f5;

					image {
						width: 190rpx;
						height: 190rpx;
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

					width: 190rpx;
					height: 190rpx;
					margin: 0 25rpx 25rpx 0;
					border-radius: 5px;

					background-color: #f5f5f5;

				}
			}

			.goods-introduce {
				display: flex;
				align-items: center;
				margin-top: 10rpx;
				padding: 20rpx;
				border-radius: 10rpx;
				margin-bottom: 40rpx;

				background-color: #ffffff;
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
