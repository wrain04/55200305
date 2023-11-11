<template>
	<view v-if="goods_info.goodsName" class="goods-detail-container">
		<!-- 轮播图区域 -->
		<swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000" circular>
			<swiper-item v-for="(item, index) in goods_info.images" :key="index">
				<image :src="item" @click="preview(index)"></image>
			</swiper-item>
		</swiper>

		<!-- 商品信息区域 -->
		<view class="goods-info-box">
			<!-- 商品价格/名称 -->
			<view class="price">
				<view style="color: #000;">{{ goods_info.goodsName }}</view>
				<!-- <view style="color: #000;">本地大白菜</view> -->
				<!-- ￥{{ (goods_price || goods_info.price) | tofixed}} -->
				￥{{ (goods_price || goods_info.price) }}
			</view>
			<view class="goods-position">
				当前距离商户:{{distance}}
			</view>
			<!-- 信息主体区域 -->
			<view class="goods-info-body">
				<!-- 商品简介 -->
				<view class="goods-name">
					{{goods_info.description}}
				</view>
				<!-- 收藏 -->
				<view class="favi" @click="chooseCollect(collection)">
					<uni-icons type="star" size="18" color="gray" v-if="collection == false"></uni-icons>
					<uni-icons type="star-filled" size="18" color="#ffa200" v-else></uni-icons>
					<text>收藏</text>
				</view>
			</view>
			<!-- 运费 -->
			<view class="yf">
				快递：免运费
			</view>
		</view>

		<!-- 商品评价 -->
		<view class="reviews" @click="gotoReviews">
			<view class="reviews-top">
				<view class="reviews-titles">
					商品评价
				</view>
				<view class="reviews-viewAll">
					查看全部
					<uni-icons type="right" size="18" color="orangered"></uni-icons>
				</view>
			</view>
			<view v-for="(item,index) in reviewsList" :key="index">
				<my-review :review="item" :state="0"></my-review>
			</view>
			<view v-if="IsComment" class="IsComment">
				暂无评论
			</view>
		</view>

		<!-- 商品详情信息 -->
		<rich-text :nodes="goods_info.goods_introduce"></rich-text>
		<!-- <view v-for="(item,index) in time_chart" :key="index">
			<my-timeline :timeChart="item"></my-timeline>
		</view> -->


		<!-- 商品导航区域 -->
		<view class="goods_nav">
			<uni-goods-nav :fill="true" :options="options" :buttonGroup="buttonGroup" @click="onClick"
				@buttonClick="buttonClick" />
		</view>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations,
		mapGetters
	} from 'vuex'
	import qqmapsdk from "@/static/qqmap-wx-jssdk.js"

	const QQMapWX = new qqmapsdk({
		key: 'NA7BZ-37C3I-MOUGX-UVC3V-C3Z47-CMFVP'
	});

	export default {
		computed: {
			...mapState('m_cart', []),
			...mapGetters('m_cart', ['total']),
			...mapState('m_user', ['userLoginInfo'])
		},
		watch: {
			// 1. 监听 total 值的变化，通过第一个形参得到变化后的新值
			total: {
				handler(newVal) {
					// 2. 通过数组的 find() 方法，找到购物车按钮的配置对象
					const findResult = this.options.find(x => x.text === '购物车')
					if (findResult) {
						// 3. 动态为购物车按钮的 info 属性赋值
						findResult.info = newVal
					}
				},
				// 页面首次加载完毕后，不会调用这个侦听器
				// 所以需要使用 对象 ，而不是 方法 ，再利用对象中的 immediate
				// immediate 属性用来声明此侦听器，是否在页面初次加载完毕后立即调用
				immediate: true
			}
		},

		data() {
			return {
				// 商品详情对象
				goods_info: {},
				// 存放时间图数组
				time_chart: [],
				// 商品等级价格
				goods_price: 0,
				// 是否收藏
				collection: 0,
				options: [{
					icon: 'headphones',
					text: '客服'
				}, {
					icon: 'shop',
					text: '商户',
				}, {
					icon: 'cart',
					text: '购物车',
					info: 0
				}],
				buttonGroup: [{
						text: '加入购物车',
						backgroundColor: '#ff0000',
						color: '#fff'
					},
					{
						text: '立即购买',
						backgroundColor: '#ffa200',
						color: '#fff'
					}
				],
				// 商品评价
				reviewsList: [],
				// 获取商品评论数据页数
				reviewsCurrent: 0,
				// 获取商品评论数据每页多少数据
				reviewsSize: 2,
				// 评论用户信息
				userInfo: {},
				// 商家列表
				markersList: [],
				// 用户经纬度
				UserLocation: {},
				// 商户经纬度
				markersLocation: {},
				// 页码值
				page_index: 1,
				// 每页显示多少条数据
				page_total: 10,
				// 距离
				distance: '',
				// 确认是否成功获取商家地址
				addresscommit: false,
				// 判断是否有评论
				IsComment: true
			}
		},
		methods: {
			// 把 m_cart 模块中的 addToCart 方法映射到当前页面使用
			...mapMutations('m_cart', ['addToCart']),

			// 定义请求商品详情数据的方法
			async getGoodsInfo(goods_id) {
				console.log(11111123 + goods_id);
				const {
					data: res
				} = await uni.$http.get('/goods/goodApi/info/' + goods_id)
				if (res.code !== 200) return uni.$showMsg()

				// 使用字符串的 replace() 方法，为 img 标签添加行内的 style 样式，从而解决图片底部空白间隙的问题
				// 使用字符串的 replace() 方法，将 webp 的后缀名替换为 jpg 的后缀名，解决 .webp 格式图片在 ios 设备上无法正常显示的问题
				// res.message.goods_introduce = res.message.goods_introduce.replace(/<img /g, '<img style="display:block;" ').replace(/webp/g, 'jpg')

				this.goods_info = res.data
				console.log('获取商品信息', this.goods_info);
			},

			// 获取评论区数据
			async getReviewsList(goodsId, isOrder, isOnlyImage) {
				const {
					data: res
				} = await uni.$http.get('/goods/commentApi/getCommentByGoodsId/' + goodsId + '?current=' + this
					.reviewsCurrent++ + '&size=' + this.reviewsSize + '&isOrder=' + isOrder + '&isOnlyImage' +
					isOnlyImage)
				console.log('评论区', res);
				if (res.code !== 200) return uni.$showMsg()
				this.reviewsList = res.data.comments
				if (res.data.total != 0) {
					this.IsComment = false
				}
			},

			// 定义请求时间图数据的方法
			async getTimeChart(goods_id) {
				const {
					data: res
				} = await uni.$http.get('/goods/timechartApi/getInfo?goodId=' + goods_id)
				console.log("时间图数据", res);
				if (res.code !== 200) return uni.$showMsg()
				this.time_chart = res.data
			},

			// 实现轮播图的预览效果
			preview(index) {
				// 调用 uni.previewImage() 方法预览图片
				uni.previewImage({
					// 预览时，默认显示图片的索引
					current: index,
					// 所有图片 url 地址的数组
					// 使用 map 方法，将 pics 中的所有对象中的 pics_big 组成一个新数组，并赋值给 urls
					urls: this.goods_info.images
				})
			},

			// 左侧按钮的点击事件处理函数
			onClick(e) {
				if (e.content.text === '客服') {
					uni.navigateTo({
						url: '/subpkg/service/service'
					})
				} else if (e.content.text === '商户') {
					uni.navigateTo({
						url: '/subpkg/seller/seller?businessId=' + this.goods_info.businessId + '&userId=' + this
							.userLoginInfo.user_id
					})
				} else if (e.content.text === '购物车') {
					uni.switchTab({
						url: '/pages/cart/cart'
					})
				}

			},

			buttonClick(e) {
				if (e.content.text === '加入购物车') {
					// 组织商品的信息对象
					// { goods_id, goods_name, goods_price, goods_count, goods_small_logo, goods_state }
					const goods = {
						goodsId: this.goods_info.goodsId, // 商品的Id
						goodsName: this.goods_info.goodsName, // 商品的名称
						description: this.goods_info.description, // 商品简介
						gradePrice: this.goods_price || this.goods_info.price, // 商品的价格
						goods_count: 1, // 商品的数量
						images: [this.goods_info.images[0]], // 商品的图片
						goods_state: true // 商品的勾选状态
					}
					console.log(goods);
					// 调用 addToCart 方法
					this.addToCart(goods)
				}
			},

			// 改变收藏状态
			async chooseCollect(n) {
				if (!n) {
					const {
						data: res
					} = await uni.$http.post('/goods/collectionApi/collectWithMark?mark=0&dataId=' + this.goods_info
						.goodsId)
					console.log(res);
					if (res.code !== 200) return uni.$showMsg()
					this.collection = 1
				} else {
					const {
						data: res
					} = await uni.$http.delete('/goods/collectionApi/removeWithMark?mark=0&dataIds=' + this.goods_info
						.goodsId)
					console.log(res);
					if (res.code !== 200) return uni.$showMsg()
					this.collection = 0
				}
			},

			// 获取商品是否收藏信息
			async getCollect(goods_id) {
				const {
					data: res
				} = await uni.$http.get('/goods/collectionApi/isCollect?mark=' + 0 + '&dataId=' + goods_id)
				console.log(res);
				if (res.code !== 200) return uni.$showMsg()
				this.collection = res.data
			},

			// 向后端传递商品被访问信息
			async getVisitMessage() {
				const {
					data: res
				} = await uni.$http.put('/goods/statisticsApi/updateDateView/' + this.goods_info.businessId +
					'?goodsId=' + this.goods_info.goodsId + '&userId=' + this.userLoginInfo.user_id)
				console.log(232323);
				console.log(res);
				if (res.code !== 200) return uni.$showMsg()
			},

			// 前往评论页面
			gotoReviews() {
				uni.navigateTo({
					url: '/subpkg/reviews/reviews?goodsId=' + this.goods_info.goodsId
				})
			},
			/* 下面的方法是获取当前用户的地址,根据地址获取当前最近的门店 */
			getUserLocation() {
				//获取当前地理位置
				let that = this
				uni.getLocation({
					type: 'wgs84',
					geocode: true,
					success: (res) => {
						that.UserLocation.latitude = res.latitude
						that.UserLocation.longitude = res.longitude
						console.log('用户当前位置', res);
						if (that.addresscommit === true) {
							that.changeWidth(that.markersLocation.lat, that.markersLocation.lng)
						}
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
			// 获取农户商品信息
			async getSellerCommodityList() {
				const {
					data: res
				} = await uni.$http.get('/goods/businessDetailApi/getBusinessDetail/' + this.goods_info.businessId)
				this.markersList = res.data
			},

			getViennaList() {
				//获取商家位置
				let that = this
				QQMapWX.geocoder({
					address: that.markersList.address,
					success: function(res) {
						that.markersLocation.lat = res.result.location.lat;
						that.markersLocation.lng = res.result.location.lng;
						that.addresscommit = true
					},
					fail: (err) => {
						uni.showToast({
							title: '无法获取商店位置!',
							icon: 'error',
							duration: 3000
						});
					}
				})
			},
			rad(d) {
				return d * Math.PI / 180.0;
			},
			//计算距离
			changeWidth(lat, lng) {
				let hwidth = ''
				var radLat1 = this.rad(lat);
				var radLat2 = this.rad(this.UserLocation.latitude);
				var a = radLat1 - radLat2;
				var b = this.rad(lng) - this.rad(this.UserLocation.longitude);
				var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
					Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
				s = s * 6378.137; // EARTH_RADIUS;
				s = Math.round(s * 10000) / 10000; //输出为公里

				var distance = s;
				var distance_str = "";

				if (parseInt(distance) >= 1) {
					distance_str = distance.toFixed(1) + "km";
				} else {
					distance_str = distance * 1000 + "m";
				}
				this.distance = distance_str
				return distance_str
				// QQMapWX.calculateDistance({
				// 	from: _this.UserLocation,
				// 	to: [{
				// 		latitude: _this.markersLocation.lat,
				// 		longitude: _this.markersLocation.lng
				// 	}],
				// 	success: (res) => {
				// 		for (var i = 0; i < res.result.elements.length; i++) {
				// 			let hw = res.result.elements[i].distance //拿到距离(米)
				// 			if (hw && hw > 1 && hw < 1000) { //拿到正确的值
				// 				hw = hw + '米'
				// 			} else if (hw && hw !== -1) {
				// 				hw = (hw / 2 / 500).toFixed(1) + '公里'
				// 			} else {
				// 				hw = "距离太近或请刷新重试"
				// 			}
				// 			// mks[i].distance = hw
				// 			// console.log("距离", hw);
				// 			_this.distance = hw
				// 			console.log('距离', _this.distance);
				// 		}
				// 	},
				// 	fail: (error) => {
				// 		console.log(error);
				// 		uni.showToast({
				// 			title: error,
				// 			icon: 'error',
				// 			duration: 4000
				// 		});
				// 	},
				// });
			},
		},
		onLoad(options) {

			const goods_id = options.goods_id
			// const goods_id = '1587421177964937217'
			this.getGoodsInfo(goods_id)
			this.getTimeChart(goods_id)
			this.getReviewsList(goods_id, 0, 0);
			// this.getSellerCommodityList()
			this.getCollect(goods_id)
			const that = this
			var timer1 = setInterval(function() { //开启定时器，下面就是执行的方法，在窗体加载1S后执行下面的方法
				// 调用访问信息的方法
				that.getSellerCommodityList()
				// that.getViennaList()
				clearTimeout(timer1); //关闭定时器。               
			}, 1000); //这里的1000代表1秒之后执行方法
			var timer2 = setInterval(function() {
				that.getViennaList()
				clearTimeout(timer2)
			}, 3000)
			var timer3 = setInterval(function() {
				that.getUserLocation()

				clearTimeout(timer3)
			}, 5000)

			this.goods_price = options.goods_price


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
	.goods-detail-container {
		padding-bottom: 50px;
	}

	swiper {
		height: 750rpx;

		image {
			width: 100%;
			height: 100%;
		}
	}

	.goods-info-box {
		padding: 20px 10px;
		padding-right: 0;
		position: relative;

		.price {
			color: #C00000;
			font-size: 20px;
		}

		.goods-position {
			color: grey;
			font-size: 16px;
			right: 0;
			top: 20%;
			position: absolute;
		}

		.goods-info-body {
			display: flex;
			justify-content: space-between;
			padding: 10px 0;

			.goods-name {
				font-size: 14px;
				padding-right: 10px;
			}

			.favi {
				width: 120px;
				font-size: 12px;
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				border-left: 1px solid #efefef;
				color: gray;
			}
		}

		.yf {
			font-size: 12px;
			color: gray;
		}
	}

	.reviews {
		padding: 0 10px 10px 10px;

		.IsComment {
			height: 40px;
			width: 100%;
			line-height: 40px;
			text-align: center;
			font-size: 20px;
		}

		.reviews-top {
			display: flex;
			justify-content: space-between;
			align-items: center;

			height: 40px;

			.reviews-titles {
				font-size: 18px;
			}

			.reviews-viewAll {
				font-size: 14px;
				color: orangered;
			}
		}

		.reviews-bottom {
			margin: 20rpx 10rpx;

			.reviews-information {
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin-bottom: 10rpx;

				.reviews-information-left {
					display: flex;
					align-items: center;

					.reviews-headPortrait {
						width: 80rpx;
						height: 80rpx;
						margin-right: 14rpx;
						border-radius: 100%;
						background-color: orangered;
					}

					.reviews-message {
						.reviews-name {
							font-size: 16px;
						}

						.reviews-oldDays {
							font-size: 12px;
							color: gray;
						}
					}
				}

				.reviews-information-right {
					display: flex;
				}


			}

			.reviews-briefIntroduction {
				// 在同一行显示并给省略号
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis;
			}
		}
	}

	.goods_nav {
		position: fixed;
		bottom: 0;
		left: 0%;
		width: 100%;
	}
</style>
