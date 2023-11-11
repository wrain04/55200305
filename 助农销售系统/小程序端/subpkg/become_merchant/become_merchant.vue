<template>
	<view>
		<view class="becomeMerchant-container">
			<view class="merchant-information">

				<text>商户名称</text>
				<view class="merchant-name text">
					<input type="text" placeholder="输入商户名称" v-model="merchant.storeName">
				</view>
				<text>商户类型</text>
				<view class="merchant-type text">
					<uni-data-select v-model="merchant.businessType" :localdata="range"></uni-data-select>
				</view>
				<text>店铺logo</text>
				<view class="merchant-img">
					<!-- 图片 -->
					<view class="merchant-image" v-if="ShowbusinessLogo">
						<image :src="ShowbusinessLogo"></image>
						<view class="delete-img" @click="deletImg(index, 2)">
							<uni-icons type="closeempty" size="14" color="#fff"></uni-icons>
						</view>
					</view>
					<!-- 选择图片 -->
					<view class="select-image" @click="chooseLogo()">
						<uni-icons type="plusempty" size="20"></uni-icons>
						<view>选择图片</view>
					</view>
				</view>
				<text>证书</text>
				<view class="merchant-img">
					<!-- 图片 -->
					<view class="merchant-image" v-for="(item, index) in merchant.licenseList" :key="index">
						<image :src="item"></image>
						<view class="delete-img" @click="deletImg(index, 0)">
							<uni-icons type="closeempty" size="14" color="#fff"></uni-icons>
						</view>
					</view>
					<!-- 选择图片 -->
					<view class="select-image" @click="selectImage(0)" v-if="merchant.licenseList.length < 6">
						<uni-icons type="plusempty" size="20"></uni-icons>
						<view>选择图片</view>
					</view>
				</view>
				<view class="merchant-data">
					<text>身份证姓名</text>
					<view class="merchant-IDName text">
						<input type="text" placeholder="输入身份证姓名" v-model="merchant.realName">
					</view>
					<text>身份证号码</text>
					<view class="merchant-IDNumber text">
						<input type="number" placeholder="输入身份证号码" v-model="merchant.idCard">
					</view>
					<text>商户电话</text>
					<view class="merchant-phone text">
						<input type="number" placeholder="输入商户电话" v-model="merchant.businessPhone">
					</view>
					<text>商户地址</text>
					<view class="merchant-location text">
						<input type="text" placeholder="输入商户地址" v-model="location">
					</view>
					<text>银行账号</text>
					<view class="merchant-bank text">
						<input type="number" placeholder="输入银行账号" v-model="merchant.bankCardNumber">
					</view>
					<text>主要产品</text>
					<view class="merchant-stapleProducts text">
						<input type="text" placeholder="输入主要产品" v-model="merchant.mainProducts">
					</view>
					<!-- <text>地块面积 (单位/平方米)</text>
					<view class="merchant-parcelArea text">
						<input type="number" placeholder="输入地块面积 (单位/平方米)" v-model="merchant.merchantParcelArea">
					</view> -->
					<!-- <text>几个地块</text>
					<view class="merchant-parcelNumber text">
						<input type="number" placeholder="输入你有几个地块" v-model="merchant.merchantParcelNumber">
					</view> -->
					<text>土地图片/基地图片</text>
					<view class="merchant-img">
						<!-- 图片 -->
						<view class="merchant-image" v-for="(item, index) in merchant.landImageList" :key="index">
							<image :src="item"></image>
							<view class="delete-img" @click="deletImg(index, 1)">
								<uni-icons type="closeempty" size="14" color="#fff"></uni-icons>
							</view>
						</view>
						<!-- 选择图片 -->
						<view class="select-image" @click="selectImage(1)" v-if="merchant.landImageList.length < 6">
							<uni-icons type="plusempty" size="20"></uni-icons>
							<view>选择图片</view>
						</view>
					</view>
					<text>商户简介</text>
					<view class="merchant-introduce">
						<textarea v-model="merchant.description" id="" cols="30" rows="8"
							placeholder="输入商户简介"></textarea>
					</view>
				</view>
			</view>

			<!-- 提交 -->
			<view class="submit" @click="submit">
				<text class="submit-text">{{ state == 0 ? '提 交 审 核' : '修 改 信 息' }}</text>
			</view>
		</view>
	</view>
</template>

<script>
	import qqmapsdk from "@/static/qqmap-wx-jssdk.js"
	import qiniuUploader from '@/static/qiniuyunUploader.js'
	import {
		pathToBase64
	} from '../../js_sdk/mmmm-image-tools/index.js'
	import {
		mapState,
		mapMutations
	} from 'vuex'
	const QQMapWX = new qqmapsdk({
		key: 'NA7BZ-37C3I-MOUGX-UVC3V-C3Z47-CMFVP'
	});
	export default {
		data() {
			return {
				merchant: {
					storeName: '',
					businessType: '',
					realName: '',
					idCard: null,
					businessPhone: null,
					address: '',
					bankCardNumber: null,
					mainProducts: '',
					merchantParcelArea: null,
					merchantParcelNumber: null,
					description: '',
					licenseList: [],
					landImageList: [],
					businessLogo: '',

				},
				// 判断是提交审核还是修改信息
				state: 0,
				// 展示的地址
				location: '',
				// 展示的图片
				ShowbusinessLogo: '',
				// 下拉选择框内容
				range: [{
						value: 0,
						text: "农户"
					},
					{
						value: 1,
						text: "合作社"
					},
					{
						value: 2,
						text: "家庭农场"
					},
					{
						value: 3,
						text: "涉农企业"
					},
				],
			};
		},
		computed: {
			...mapState('m_user', ['token']),
			...mapState('m_user', ['userinfo']),
		},

		methods: {
			// 提交审核
			submit() {
				const that = this
				if (this.state == 0) {
					uni.showModal({
						title: '提交审核',
						content: '您确认提交您的审核信息吗？',
						success: function(res) {
							QQMapWX.geocoder({
								address: that.location,
								success: function(response) {
									that.merchant.address = that.location + '@' +
										response.result.location.lat + ',' +
										response.result.location.lng
									console.log('商户的坐标为', that.merchant.address);
									console.log('res.confirm', res.confirm);
									if (that.merchant.address) {

										const {
											data: response
										} = uni.$http.post('/goods/businessDetailApi/uploadDetail',
											that.merchant)
										console.log('提交结果', response);
										uni.switchTab({
											url: '/pages/my/my'
										})
									} else if (res.cancel) {

									}
								},
								fail: (err) => {
									uni.showToast({
										title: '无法获取商店位置!',
										icon: 'error',
										duration: 3000
									});
								}
							})

						}
					});
				} else {
					uni.showModal({
						title: '修改信息',
						content: '您确认修改您的个人信息吗？',
						success: function(res) {
							if (res.confirm) {
								console.log(that);
								uni.switchTab({
									url: '/pages/my/my'
								})
							} else if (res.cancel) {

							}
						}
					});
				}

			},

			// 获取商户信息
			async getMerchantInformation() {
				const {
					data: res
				} = await uni.$http.get('/goods/businessDetailApi/getBusinessDetail/' + '1649796123411992578')
				console.log('商户信息', res);
				if (res.code != 200) uni.$showMsg()
				this.merchant = res.data
				// this.merchant.storeName = '吴蔚佳'
				// this.merchant.merchantPhone = 1234567890
				// this.merchant.address = '广东省广州市仲恺路500号'
				// this.merchant.merchantIntroduce = '从事种植红富士苹果农户，种植面积100亩。'
			},

			// 删除证书栏的图片
			deletImg(i, n) {
				// TODO...
				// 确认事件
				if (n == 0) {
					this.merchant.licenseList.splice(i, 1)
				} else {
					this.merchant.landImageList.splice(i, 1)
				}

			},

			// 选择图片
			selectImage(n) {
				// 将 this 重新赋值一下，因为 success 中的 this 与这里不是同一个
				const that = this

				uni.chooseImage({
					success: function(res) {
						console.log(JSON.stringify(res.tempFilePaths));
						if (n == 0) {
							that.merchant.licenseList.push('' + res.tempFilePaths)
						}
						if (n === 1) {
							that.merchant.landImageList.push('' + res.tempFilePaths)
						}
					}
				})
			},
			chooseLogo() {
				const that = this
				uni.chooseImage({
					count: 1,
					success: function(res) {
						console.log('res', res);
						console.log(JSON.stringify(res.tempFilePaths));
						that.ShowbusinessLogo = res.tempFilePaths
						that.merchant.businessLogo = res.tempFilePaths[0]
						that.uploadImg()
						// uni.getImageInfo({
						// 	src: res.tempFilePaths[0],
						// 	success: (path) => {
						// 		pathToBase64(path.path).then(base64 => {
						// 				console.log("base64=" + base64); // 这就是转为base64格式的图片
						// 				that.merchant.businessLogo = base64
						// 			})
						// 			.catch(error => {
						// 				console.error(error)
						// 			})
						// 	}
						// })
						// const chooseImg = chooseImageRes.tempFilePaths
						// that.axios.request({
						// 	url:,
						// 	method:'GET'
						// }).then(res => {
						// 	if(res.data.data){
						// 		let uploadMsg = res.data.data
						// 		let uniqueCode = generateUUID()
						// 		uni.uploadFile({
						// 			url:''
						// 		})
						// 	}
						// }) 
						// that.merchant.businessLogo = res.tempFilePaths.toString()
					},

				})
			},
			uploadImg() {
				console.log(this.merchant.businessLogo, "this.merchant.businessLogo");
				uni.uploadFile({
					url: 'https://f396010651.goho.co/admin/sys-file/upload',
					filePath: this.merchant.businessLogo,
					name: 'file',
					header: {
						'content-type': 'multipart/form-data',
						// 'Authorization': 'Bearer 7c0668f8-6e3c-432b-9d85-9090eeb312c2' + this.token
						'Authorization': 'Bearer ' + this.token
					},
					success: (uploadFileRes) => {

						let ress = JSON.parse(uploadFileRes.data)
						console.log('ress', ress)
						this.merchant.businessLogo = ress.data.url
					},

				})
			}
		},

		onLoad(loading) {
			if (loading.merchantId) {
				this.getMerchantInformation()
				this.state = 1
			}
		},
	}
</script>

<style lang="scss">
	.becomeMerchant-container {
		position: relative;

		.merchant-information {
			box-sizing: border-box;
			width: 100%;
			padding: 40rpx;
			padding-bottom: 210rpx;

			font-size: 14px;

			text {
				font-size: 16px;
				color: #333333;

			}

			.text {
				display: flex;
				align-items: center;
				height: 40rpx;
				margin-top: 10rpx;
				padding: 20rpx;
				border-radius: 10rpx;
				margin-bottom: 40rpx;

				background-color: #ffffff;
			}

			.merchant-type {

				uni-data-select {
					width: 100%;

					.uni-select {
						padding: 0px;
						border: 0px;

						.uni-select__input-placeholder {
							font-size: 28rpx;
						}
					}
				}
			}


			.merchant-data {
				input {
					width: 100%;
				}

				.merchant-introduce {
					display: flex;
					align-items: center;
					margin-top: 10rpx;
					padding: 20rpx;
					border-radius: 10rpx;
					margin-bottom: 40rpx;

					background-color: #ffffff;
				}
			}

			.merchant-img {
				display: flex;
				flex-wrap: wrap;
				box-sizing: border-box;

				width: 670rpx;
				margin: 10rpx 0 40rpx 0;
				padding: 25rpx 0 0 25rpx;
				border-radius: 10rpx;

				background-color: #ffffff;

				.merchant-image {
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
