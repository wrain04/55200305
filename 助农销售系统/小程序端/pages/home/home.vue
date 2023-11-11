<template>
	<view>
		<view class="user-home" v-if="pattern">
			<!-- 自定义搜索组件 -->
			<view class="search-box">
				<my-search @myclick="gotoSearch"></my-search>
			</view>

			<!-- 轮播图区域 -->
			<swiper indicator-dots autoplay :interval="3000" circular>
				<swiper-item v-for="(item, index) in swiperList" :key="index">
					<navigator class="swiper-item" :url="'/subpkg/goods_detail/goods_detail?goods_id=' + item.goodsId">
						<image :src="item.images[0]"></image>
					</navigator>
				</swiper-item>
			</swiper>
			<!-- 分类导航结构 -->
			<!-- <view class="nav-list">
				<view class="nav-item" v-for="(item, index) in navList" :key="index" @click="navClickHandler(item)">
					<image :src="item.image_src" class="nav-img"></image>
					<view class="itemName">&nbsp;&nbsp;{{item.name}}</view>
					<!-- <view class="message-num" v-if="">
						{{''}}
					</view> -->
			<!-- </view>
			</view> -->
			<view class="spilt">
				最新资讯
			</view>
			<!-- 资讯区域 -->
			<view class="message-list">
				<view v-for="(item,index) in informationList" :key="index">
					<my-information @informationClick="gotoInformation" :informationsId="item.informationId"
						:cover="item.cover" :informationAuthor="item.informationAuthor" :createTime="item.createTime"
						:informationTitle="item.informationTitle" :viewNumber="item.viewNumber"></my-information>
				</view>
			</view>

		</view>

		<view class="seller-home" v-else>
			<!-- 个人信息 -->
			<view class="personal-details">
				<view class="details-top">
					<view class="details-top-left">
						<image :src="userinfo.avatarUrl" class="head-portrait"></image>
					</view>
					<view class="details-top-right">
						<view class="details-name">
							{{ merchantInfo.storeName }}
						</view>
						<view class="details-type">
							商户类型：{{ merchantStatus }}
						</view>
						<view class="details-location">
							{{ merchantAddress }}
						</view>
						<view class="details-stapleProducts">
							{{ '主要产品：' + merchantInfo.mainProducts }}
						</view>
						<view class="details-acreage">
							{{ '地块/生产面积：' + 100 + ' ㎡' }}
						</view>
					</view>
				</view>
				<view class="details-bottom">
					<!-- <uni-data-select v-model="landType" :localdata="range" :clear="false"
						@change="landChange(landType+1)">
					</uni-data-select> -->
					<view class="land-information">
						<view class="land-information-top">
							<view class="acreage">
								{{ '地块面积：' + 64 + ' ㎡' }}
							</view>
							<view class="staple-crop">
								{{ '主要作物：' + merchantInfo.mainProducts }}
							</view>
						</view>
						<view class="land-information-bottom">
							<qiun-data-charts type="column" :opts="opts1" :chartData="chartData1" />
						</view>
					</view>
				</view>
			</view>

			<!-- 销售信息 -->
			<view class="salesVolume">
				<view class="salesVolume-message">
					<text>访客数</text>
					<text>{{ serverData.viewSum[0] || 234 }}</text>
					<text>昨日 {{ serverData.viewSum[1] || 142 }}</text>
				</view>
				<view class="salesVolume-message">
					<text>订单数</text>
					<text>{{ serverData.orderNum[0] || 23 }}</text>
					<text>昨日 {{ serverData.orderNum[1] || 18 }}</text>
				</view>
				<view class="salesVolume-message">
					<text>成交额</text>
					<text>{{ 539 ||serverData.deal[0] || 539 }}</text>
					<text>昨日 {{ 382 || serverData.deal[1] || 382 }}</text>
				</view>
			</view>

			<view class="indent-state">
				<view class="drop-shipping indentState">
					<view class="indentState-top">
						<uni-icons type="mail-open-filled" size="34" color="#fdb84b"></uni-icons>
						{{ waitSendingNum }}
					</view>
					<view class="indentState-bottom">
						待发货
					</view>
				</view>
				<view class="indent-return indentState">
					<view class="indentState-top">
						<uni-icons type="refresh-filled" size="34" color="#fdb84b"></uni-icons>
						{{ waitSalesReturnNum }}
					</view>
					<view class="indentState-bottom">
						待退货退款
					</view>
				</view>
			</view>


			<!-- 可视化数据 -->
			<view class="charts-box">
				<qiun-data-charts type="line" :opts="opts2" :chartData="chartData2" />
			</view>
		</view>



	</view>

</template>

<script>
	// 导入自己封装的 mixin 模块
	import badgeMix from '@/mixins/tabbar-badge.js'

	import {
		mapState,
		mapMutations
	} from 'vuex'

	export default {
		// 将 badgeMix 混入到当前的页面中进行使用
		mixins: [badgeMix],

		computed: {
			...mapState(['pattern']),
			...mapState('m_user', ['refreshToken']),
			...mapState('m_user', ['userinfo']),

			// 商户状态
			merchantStatus() {
				switch (this.merchantInfo.businessType) {
					case '0':
						return '农户'
						break;
					case '1':
						return '合作社'
						break;
					case '2':
						return '家庭农场'
						break;
					case '3':
						return '涉农企业'
						break;
				}
			}
		},

		data() {
			return {
				// 1. 存放轮播图数据的数组
				swiperList: [],
				// 2. 存放分类导航的数据列表
				navList: [{
					image_src: "/static/nav1.png",
					name: "分类",
					open_type: "switchTab"
				}, {
					image_src: "/static/nav2.png",
					name: "秒杀",
					open_type: "switchTab"
				}, {
					image_src: "/static/nav3.png",
					name: "好物",
					open_type: "switchTab"
				}, {
					image_src: "/static/nav4.png",
					name: "推荐",
					open_type: "switchTab"
				}],
				// 3. 存放资讯导航的数据列表
				informationList: [],

				// 判断是否首次访问商户界面数据
				firstVisit: true,
				// 存放柱状图的数据
				chartData1: {},
				// 存放折线图的数据
				chartData2: {},

				// 存放商户主页数据
				serverData: {},
				// 商户信息
				merchantInfo: {},
				// 商户地址
				merchantAddress: '',

				// 待发货数量
				waitSendingNum: 0,
				// 待退货退款数量
				waitSalesReturnNum: 0,

				// 地块选择
				landType: 0,
				// 下拉选择框内容
				range: [{
						value: 0,
						text: "地块1"
					},
					{
						value: 1,
						text: "地块2"
					},
					{
						value: 2,
						text: "地块3"
					},
				],

				opts1: {
					color: ["#1890FF", "#91CB74", "#FAC858", "#EE6666", "#73C0DE", "#3CA272", "#FC8452", "#9A60B4",
						"#ea7ccc"
					],
					padding: [15, 15, 0, 5],
					enableScroll: false,
					legend: {},
					xAxis: {
						disableGrid: true
					},
					yAxis: {
						data: [{
							min: 0
						}]
					},
					extra: {
						column: {
							type: "group",
							width: 20,
							activeBgColor: "#000000",
							activeBgOpacity: 0.08
						}
					}
				},
				//您可以通过修改 config-ucharts.js 文件中下标为 ['line'] 的节点来配置全局默认参数，如都是默认参数，此处可以不传 opts 。实际应用过程中 opts 只需传入与全局默认参数中不一致的【某一个属性】即可实现同类型的图表显示不同的样式，达到页面简洁的需求。
				opts2: {
					color: ["#1890FF", "#91CB74", "#FAC858", "#EE6666", "#73C0DE", "#3CA272", "#FC8452", "#9A60B4",
						"#ea7ccc"
					],
					padding: [15, 10, 10, 15],
					legend: {},
					xAxis: {
						disableGrid: true
					},
					yAxis: {
						gridType: "dash",
						dashLength: 2
					},
					extra: {
						line: {
							// 折线的类型，默认 straight 尖角折线模式
							type: "straight",
							// 折线的宽度
							width: 2
						}
					},
				}
			};
		},
		methods: {
			...mapMutations('m_user', ['updateToken', 'updataRefreshToken']),

			// 获取轮播图数据的方法
			async getSwiperList() {
				// const {data: res} = await uni.$http.get('https://api-hmugo-web.itheima.net/api/public/v1/categories')
				const res = await uni.$http.get('/goods/goodApi/rotationChart/3')
				console.log(res);
				if (res.data.code !== 200) return uni.$showMsg()

				this.swiperList = res.data.data

				console.log(this.swiperList);
			},

			// 获取资讯列表数据的方法
			async getInformationList() {
				const res = await uni.$http.get('/admin/informationApi/info/all')
				console.log(res);
				if (res.data.code !== 200) return uni.$showMsg()

				this.informationList = res.data.data

				console.log('资讯列表', this.informationList);
			},

			// 分类导航点击事件
			navClickHandler(item) {
				if (item.name === '分类') {
					uni.switchTab({
						url: '/pages/cate/cate'
					})
				} else {
					uni.$showMsg('敬请期待该功能 ！！！')
				}

				// if(item.name === '秒杀') {
				// 	console.log(111);
				// 	uni.navigateTo({
				// 		url: '/subpkg/message/message'
				// 	})
				// }
			},

			// 获取地块信息
			async getServerData1() {
				const {
					data: res
				} = await uni.$http.get('/goods/businessLandApi/getLands')
				console.log(123456);
				console.log('123456', res);
				if (res.code !== 200) return uni.$showMsg()
				this.landList = res.data
				for (let i = 0; i < res.data.length; i++) {
					let obj = {
						value: i,
						text: res.data[i].landName
					}
					this.range.push(obj)
				}
				this.landChange()
			},

			// 获取商户信息
			async getMerchantInfo() {
				const {
					data: res
				} = await uni.$http.get('/goods/businessDetailApi/getSelfDetail')
				console.log('获取商户信息', res);
				if (res.code !== 200) return uni.$showMsg()
				this.merchantInfo = res.data
				this.merchantAddress = res.data.address.split('@')[0]

			},
			// 获取商户首页信息
			async getServerData2() {
				const {
					data: res
				} = await uni.$http.get('/goods/statisticsApi/getInfo')
				console.log(res);
				if (res.code !== 200) return uni.$showMsg()
				this.serverData = res.data

				let resMessage = {
					categories: ['11月', '12月', '1月', '2月', '3月', '4月'],
					series: [{
						name: "收入（元/月）",
						data: [4535, 3689, 3889, 3901, 4932, 5024]
					}]
				};
				this.chartData2 = JSON.parse(JSON.stringify(resMessage));
			},

			async landChange() {
				// const {
				// 	data: res
				// } = await uni.$http.get('/goods/landLogApi/getBySelf?landId=' + langId)
				// console.log(99999);
				// console.log(res);
				// if (res.code !== 200) return uni.$showMsg()

				let res1 = {
					categories: ['11月', '12月', '1月', '2月', '3月', '4月'],
					series: [{
						name: "产值（公斤/月）",
						data: [323, 287, 298, 301, 347, 354]
					}]
				};
				this.chartData1 = JSON.parse(JSON.stringify(res1));

			},

			// 获取商户待发货数量
			async getWaitSendingNum() {
				const {
					data: res
				} = await uni.$http.get('/orders/orderApi/getWaitSendToBusiness')
				console.log(res);
				if (res.code != 200) uni.$showMsg()
				this.waitSendingNum = res.data

			},

			// 获取商户待退货退款数量
			async getWaitSalesReturnNum() {
				const {
					data: res
				} = await uni.$http.get('/orders/orderApi/getWaitRefundORReturnToBusiness')
				console.log(res);
				if (res.code != 200) uni.$showMsg()
				this.waitSalesReturnNum = res.data
			},


			// 跳转到分包中的搜索页面
			gotoSearch() {
				console.log(111);
				uni.navigateTo({
					url: '/subpkg/search/search'
				})
			},

			// 跳转到分包中的资讯页面
			gotoInformation(e) {
				console.log("e是", e);
				uni.navigateTo({
					url: '/subpkg/message/message?messageId=' + e
				})
			},

			// 刷新token
			async getRefreshToken() {
				// 准备参数
				const query = {
					scope: 'server',
					refresh_token: this.refreshToken
				}

				// 换取 新token
				const {
					data: res
				} = await uni.$http.post('/auth/oauth2/token?grant_type=refresh_token', query)
				console.log(999999);
				console.log(res);

				if (res.refresh_token) {
					console.log(111);
					this.updateToken(res.access_token)
					this.updataRefreshToken(res.refresh_token)
					return
				}
				console.log(111);
				this.updateToken('')
				return uni.$showMsg('请登录')
			}

		},
		onLoad() {
			this.getRefreshToken()

			const that = this
			var timer = setInterval(function() { //开启定时器，下面就是执行的方法，在窗体加载1S后执行下面的方法
				// 调用方法获取轮播图数据
				that.getSwiperList()
				// 调用方法获取资讯导航数据
				that.getInformationList()


				clearTimeout(timer); //关闭定时器。               
			}, 1000); //这里的1000代表1秒之后执行方法
		},
		onShow() {
			if (this.pattern) {
				uni.setNavigationBarTitle({
					title: '首页'
				})
			} else {
				uni.setNavigationBarTitle({
					title: '主页'
				})
			}

		},
		onReady() {

		},
		onShow() {
			if (!this.pattern && this.firstVisit) {
				this.getMerchantInfo()
				this.getServerData1();
				this.getServerData2();
				this.getWaitSendingNum()
				this.getWaitSalesReturnNum()

				this.firstVisit = false
			}
		}
	}
</script>

<style lang="scss">
	.user-home {
		swiper {
			height: 330rpx;

			.swiper-item,
			image {
				width: 100%;
				height: 100%;
			}
		}

		.spilt {
			width: 100%;
			height: 100rpx;
			background-color: #c00000;
			color: white;
			font-size: 40rpx;
			text-align: center;
			line-height: 100rpx;
		}

		.nav-list {
			display: flex;
			justify-content: space-around;
			margin: 15px 0;

			.nav-item {
				position: relative;

				.nav-img {
					width: 80rpx;
					height: 80rpx;
				}

				.itemName {
					padding-left: 5px;
				}

				.message-num {
					position: absolute;
					top: -4rpx;
					right: 0;
					width: 30rpx;
					height: 30rpx;
					border-radius: 100%;
					background-color: #dc0000;
					color: #ffffff;
					text-align: center;
				}
			}
		}


		// 实现吸顶的效果
		.search-box {
			// 设置定位效果为“吸顶”
			position: sticky;
			// 吸顶的“位置”
			top: 0;
			// 提高层级，防止被轮播图覆盖
			z-index: 999;
		}

		// 资讯区域
		.message-list {
			padding: 20rpx;


		}
	}

	.seller-home {
		.personal-details {
			margin: 20rpx;
			padding: 20rpx;
			border-radius: 20rpx;
			background-color: #fff;

			.details-top {
				display: flex;
				margin-bottom: 20rpx;

				.details-top-left {
					margin-right: 40rpx;

					.head-portrait {
						width: 200rpx;
						height: 200rpx;
						border-radius: 100%;
					}
				}

				.details-top-right {
					width: 430rpx;
					font-size: 14px;

					.details-name {
						margin-bottom: 5px;
						font-size: 20px;
					}

					.details-location {
						width: 100%;
						// 在同一行显示并给省略号
						white-space: nowrap;
						overflow: hidden;
						text-overflow: ellipsis;
					}

					.details-stapleProducts {
						// 在同一行显示并给省略号
						white-space: nowrap;
						overflow: hidden;
						text-overflow: ellipsis;
					}
				}
			}

			.details-bottom {
				.uni-stat__select {
					width: 200rpx;

					.uni-select {
						width: 200rpx;
						border: 0px;
						background-color: #fafafa;
					}
				}

				.land-information {
					margin-top: 20rpx;
					padding-top: 20rpx;
					border-top: 1px solid #f8f8f8;

					.land-information-top {
						// width: 250rpx;
						font-size: 14px;
					}

					.land-information-bottom {
						width: 100%;
						height: 180px;
						margin-top: 10rpx;
					}
				}
			}
		}

		.salesVolume {
			display: flex;
			justify-content: space-between;
			margin: 10px;
			border-radius: 10px;

			background-color: #fff;

			.salesVolume-message {
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				width: 28%;
				height: 100px;


				& text:nth-of-type(1),
				& text:nth-of-type(3) {
					color: #747474;
					font-size: 14px;
				}

				& text:nth-of-type(2) {
					margin: 5px 0;
					font-size: 18px;
					font-weight: 700;
				}

			}
		}

		.charts-box {
			box-sizing: border-box;
			// width: 100%;
			height: 200px;

			margin: 10px;
			padding-top: 15px;
			border: 1px solid #888888;
			border-radius: 10px;

			background-color: #fff;
		}

		.indent-state {
			display: flex;
			justify-content: space-between;

			height: 100px;
			margin: 10px;

			.indentState {
				display: flex;
				flex-direction: column;
				justify-content: center;

				box-sizing: border-box;
				width: 47%;
				padding: 10px;
				border-radius: 10px;

				background-color: #fff;

				.indentState-top {
					display: flex;
					justify-content: space-between;
					align-items: center;
				}

				.indentState-bottom {
					margin-top: 10px;
					font-size: 14px;
				}
			}
		}
	}
</style>
