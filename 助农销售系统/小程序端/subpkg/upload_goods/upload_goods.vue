<template>
	<view class="uploadGoods-container">
		<view class="toggle-statusBar">
			<!-- 商品简介 -->
			<view class="product-profile-status text" @click="toggleState(0)"
				:style="'background-color: ' + (state == 0 ? '#f5f5f5' : '#ffffff')">
				<text>商品简介</text>
			</view>
			<!-- 生产记录 -->
			<view class="production-records-status text" @click="toggleState(1)"
				:style="'background-color: ' + (state != 0 ? '#f5f5f5' : '#ffffff')">
				<text>生产记录</text>
			</view>
		</view>

		<view class="informationBody" v-if="!state">
			<text>商品名称</text>
			<view class="goods-name text">
				<input type="text" placeholder="输入商品名称" v-model="goods.goodsName">
			</view>
			<text>商品图片</text>
			<view class="goods-img">
				<!-- 图片 -->
				<view class="goods-image" v-for="(item, index) in goods.imageList" :key="index">
					<image :src="item"></image>
					<view class="delete-img" @click="deletImg(index)">
						<uni-icons type="closeempty" size="14" color="#fff"></uni-icons>
					</view>
				</view>
				<!-- 选择图片 -->
				<view class="select-image" @click="selectImage" v-if="goods.imageList.length < 6">
					<uni-icons type="plusempty" size="20"></uni-icons>
					<view>选择图片</view>
				</view>
			</view>
			<view class="goods-data">
				<text>商品状态</text>
				<view class="goods-state text">
					<uni-data-select v-model="goods.status" :localdata="range1"></uni-data-select>
				</view>
				<text>商品分类</text>
				<view class="goods-state text">
					<uni-data-select v-model="goods.categoryId" :localdata="range2"></uni-data-select>
				</view>
				<text>商品品级</text>
				<view class="goods-state text">
					<uni-data-select v-model="goods.gradeId" :localdata="range3"></uni-data-select>
				</view>
				<text>商品库存</text>
				<view class="goods-number text">
					<input type="digit" placeholder="输入商品库存(单位: 斤)" v-model="goods.stock">
				</view>
				<text>商品销量</text>
				<view class="goods-sales-volume text">
					<input type="digit" placeholder="输入商品销量(单位: 斤)" disabled="true" v-model="goods.sales">
				</view>
				<text>商品简介</text>
				<view class="goods-introduce">
					<textarea v-model="goods.description" id="" cols="30" rows="8" placeholder="输入商品简介"></textarea>
				</view>
			</view>
		</view>

		<view class="informationBody" v-else>
			<text>记录信息</text>
			<view class="goods-introduce">
				<textarea v-model="productionInformation.recordText" id="" cols="30" rows="8"
					placeholder="输入商品简介"></textarea>
			</view>
			<text>生产图片</text>
			<view class="goods-img">
				<!-- 图片 -->
				<view class="goods-image" v-for="(item, index) in productionInformation.imageList" :key="index">
					<image :src="item"></image>
					<view class="delete-img" @click="deletImg(index)">
						<uni-icons type="closeempty" size="14" color="#fff"></uni-icons>
					</view>
				</view>
				<!-- 选择图片 -->
				<view class="select-image" @click="selectImage" v-if="productionInformation.imageList.length < 6">
					<uni-icons type="plusempty" size="20"></uni-icons>
					<view>选择图片</view>
				</view>
			</view>
		</view>

		<!-- 提交 -->
		<view class="submit" @click="submit">
			<text class="submit-text" v-if="!state">{{ merchantGoods == 0 ? '提 交 审 核' : '修 改 信 息' }}</text>
			<text class="submit-text" v-else>提 交 审 核</text>
		</view>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations
	} from 'vuex'
	export default {
		data() {
			return {
				goods: {
					// 商品名称
					goodsName: '',
					// 商品图片列表
					imageList: [],
					// 商品状态
					status: null,
					// 商品分类
					categoryId: '',
					// 商品品级
					gradeId: '',
					// 商品库存
					stock: 0,
					// 商品销量
					sales: 0,
					// 商品简介
					description: ''
				},
				merchantGoods: 0,
				// 存储处理之后的图片
				changeImg: [],
				// 记录上传了多少张照片
				count: 0,
				productionInformation: {
					// 生产记录信息
					recordText: '',
					// 生产图片列表
					imageList: [],
				},
				// 下拉选择框内容
				range1: [{
						value: 0,
						text: "上架商品"
					},
					{
						value: 8,
						text: "待审核商品"
					},
					{
						value: 2,
						text: "下架商品"
					},
				],
				range2: [{
						value: '1586999251224342529',
						text: "红富士"
					},
					{
						value: '1586993984378105858',
						text: "木丹坑特色白菜"
					},
					{
						value: '1622103853774766081',
						text: "油柑"
					},
				],
				range3: [{
						value: '1586993961175216130',
						text: "一等品苹果"
					},
					{
						value: '1586993961175216136',
						text: "二等品苹果"
					},
					{
						value: '1586993961175216325',
						text: "三等品苹果"
					},
					{
						value: '1622126048693526530',
						text: "一等品白菜"
					},
					{
						value: '1622126136669052930',
						text: "二等品白菜"
					},
					{
						value: '1622126237978271745',
						text: "三等品白菜"
					},
					{
						value: '1622126938838081537',
						text: "一等品油柑"
					},
					{
						value: '1622126967548092418',
						text: "二等品油柑"
					},
					{
						value: '1622126994815262722',
						text: "三等品油柑"
					}
				],
				state: 0
			}
		},
		computed: {
			...mapState('m_user', ['token']),
		},
		methods: {
			// 切换状态
			toggleState(n) {
				this.state = n
			},

			selectImage() {
				// 将 this 重新赋值一下，因为 success 中的 this 与这里不是同一个
				const that = this

				uni.chooseImage({
					success: function(res) {
						console.log(JSON.stringify(res.tempFilePaths));
						if (that.state == 0) {
							console.log('hello', res.tempFilePaths);
							that.goods.imageList = res.tempFilePaths
							console.log('处理之前的数组', that.goods.imageList);
							that.count = that.goods.imageList.length
							console.log('count', that.count);
							that.uploadImg()
						} else {
							that.productionInformation.imageList.push('' + res.tempFilePaths)
						}

					}
				})
			},

			uploadImg() {
				if (this.goods.imageList.length > 0) {
					for (var i = 0; i < this.goods.imageList.length; i++) {
						uni.uploadFile({
							url: 'http://localhost:9999/admin/sys-file/upload',
							filePath: this.goods.imageList[i],
							name: 'file',
							header: {
								'content-type': 'multipart/form-data',
								'Authorization': 'Bearer ' + this.token
							},
							success: (uploadFileRes) => {
								let ress = JSON.parse(uploadFileRes.data)
								this.changeImg.push(ress.data.url)
							},
						})
					}

				}
				const that = this
				var timer = setInterval(function() {
					that.goods.imageList = that.changeImg
					console.log('处理之后', that.goods.imageList);
					clearInterval(timer)
				}, 5000)

			},

			// 删除图片
			deletImg(i) {
				// TODO...
				// 确认事件
				if (this.state == 0) {
					this.goods.imageList.splice(i, 1)
				} else {
					this.productionInformation.imageList.splice(i, 1)
				}
			},

			// 提交审核
			async postCheck() {
				const that = this
				that.goods.stock = Number(that.goods.stock)
				that.goods.sales = Number(that.goods.sales)
				const {
					data: res
				} = await uni.$http.post('/goods/goodApi/register', that.goods)
				if (res.code === 200)
					console.log('提交结果', res);
				return uni.showToast({
					title: '添加成功!',
					icon: 'success',
					duration: 3000
				});
			},

			// 修改信息
			async postChoose() {
				const {
					data: res
				} = await uni.$http.post('/goods/goodApi/register')
				console.log(123456);
				console.log(res);
				if (res.code === 200)
					return uni.showToast({
						title: '修改成功!',
						icon: 'success',
						duration: 3000
					});
			},

			// 提交审核
			submit() {
				const that = this
				uni.showModal({
					title: '提交审核',
					content: '您确认提交您的审核信息吗？',
					success: function(res) {
						if (res.confirm) {
							console.log(that);
							if (that.state == 0) {
								if (that.merchantGoods == 0) {
									// 添加商品
									that.postCheck()
								} else {
									// 修改商品
									that.postChoose()
								}
							} else {

							}
							uni.switchTab({
								url: '/pages/cate/cate'
							})
						} else if (res.cancel) {

						}
					}
				});
			}
		},
		onLoad(loading) {
			if (loading.merchantGoods != 0) {
				console.log(111111);
				console.log(JSON.parse(loading.merchantGoods));
				this.merchantGoods = JSON.parse(loading.merchantGoods)
				this.goods.goodsName = this.merchantGoods.goodsName
				this.goods.imageList = this.merchantGoods.images
				this.goods.goodsPrice = this.merchantGoods.price
				this.goods.goodsNumber = this.merchantGoods.stock
				this.goods.goodsSalesVolume = this.merchantGoods.sales
				this.goods.goodsIntroduce = this.merchantGoods.description
			}

		}
	}
</script>

<style lang="scss">
	.uploadGoods-container {
		position: relative;

		.toggle-statusBar {
			position: sticky;
			top: 0;
			z-index: 999;

			display: flex;
			justify-content: space-between;
			width: 100%;

			.text {
				display: flex;
				justify-content: center;
				align-items: center;
				width: 50%;
				height: 100rpx;
			}
		}

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

			.goods-data {
				.goods-state {
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
