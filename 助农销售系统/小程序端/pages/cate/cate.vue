<template>
	<view class="">
		<!-- 分类页面 -->
		<view v-if="pattern">
			<!-- 使用自定义搜索组件 -->
			<my-search :bgcolor="'#C00000'" @myclick="gotoSearch"></my-search>

			<view class="scroll-view-container">
				<!-- 左侧的滑动区域 -->
				<scroll-view class="left-scroll-view" scroll-y :style="{height: wh + 'px'}">
					<block v-for="(item, index) in cateList" :key="index">
						<view :class="['left-scroll-view-item', index === active ? 'active' : '']"
							@click="index !== active ? activeChanged(item, index) : ''">{{ item.name }}</view>
					</block>
				</scroll-view>
				<!-- 右侧的滑动区域 -->
				<scroll-view class="right-scroll-view" scroll-y :style="{height: wh + 'px'}" :scroll-top="scrollTop">
					<view class="cat-lv2" v-for="(item2, index2) in cateLevel2" :key="index2">
						<view class="cat-lv2-title">/ {{item2.name}} /</view>
						<!-- 三级分类 -->
						<view class="cate-lv3-list">
							<view class="cate-lv3-item" v-for="(item3, index3) in item2.children" :key="index3"
								@click="gotoGoodsList(item3)">
								<image :src="item3.logo"></image>
								<text>{{ item3.name }}</text>
							</view>
						</view>
					</view>
				</scroll-view>
			</view>
		</view>


		<!-- 信息页面 -->
		<view class="information-container" v-else>
			<!-- <button type="default" @click="getImage">上传图片</button> -->

			<view class="informatino-top">
				<view class="uploadGoods">
					<navigator class="uploadGoods-button" url="../../subpkg/upload_goods/upload_goods?merchantGoods=0">
						添加商品
					</navigator>
				</view>
				<view class="myGoods-title">
					我的商品
				</view>
			</view>

			<view class="information-bottom">
				<view class="myGoods-body">
					<view v-for="(item,index) in indentGoodsList" :key="index">
						<my-indentGoods @indentGoodClick="gotoIndentGood(item)" :indentGood="item"></my-indentGoods>
					</view>
				</view>
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
			...mapState('m_user', ['merchantinfo']),
		},

		data() {
			return {
				// 窗口可用高度 = 屏幕高度 - navigationBar高度 - tabBar高度 
				wh: 0,
				// 分类数据列表
				cateList: [],
				// 二级分类列表
				cateLevel2: [],
				// 当前选中项的索引
				active: 0,
				scrollTop: 0,
				abc: true,

				// 判断是否首次访问商户界面数据
				firstVisit: true,

				// 获取商户商品列表的信息
				size: 10,
				current: 1,
				// 商品总量
				total: 0,
				itemLogo: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fss2.meipian.me%2Fusers%2F17618077%2Fa9cd66fcb731db3e08fbdde14a06b647.jpg%3Fmeipian-raw%2Fbucket%2Fivwen%2Fkey%2FdXNlcnMvMTc2MTgwNzcvYTljZDY2ZmNiNzMxZGIzZTA4ZmJkZGUxNGEwNmI2NDcuanBn%2Fsign%2F944a706e896e5bf3e4b2820af36dc036.jpg&refer=http%3A%2F%2Fss2.meipian.me&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1682759163&t=0a63d6219b6030f273081cfec313942f',
				imgUrls: [{
					imgurl: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fss2.meipian.me%2Fusers%2F17618077%2Fa9cd66fcb731db3e08fbdde14a06b647.jpg%3Fmeipian-raw%2Fbucket%2Fivwen%2Fkey%2FdXNlcnMvMTc2MTgwNzcvYTljZDY2ZmNiNzMxZGIzZTA4ZmJkZGUxNGEwNmI2NDcuanBn%2Fsign%2F944a706e896e5bf3e4b2820af36dc036.jpg&refer=http%3A%2F%2Fss2.meipian.me&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1682759163&t=0a63d6219b6030f273081cfec313942f'
				}, {
					imgurl: 'https://img2.baidu.com/it/u=1626620125,2686110327&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=423'
				}, {
					imgurl: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2F185c7ff3-f4eb-4f7c-9887-a2ef65b8c2f9%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1682759901&t=927f9a2e57531ef169320b461eb1006a'
				}],


				indentGoodsList: [],
			};
		},
		methods: {
			...mapMutations('m_user', ['updateToken', 'updataRefreshToken']),

			// 获取一级分类数据的方法
			async getCateList1() {
				// const {data: res} = await uni.$http.get('https://api-hmugo-web.itheima.net/api/public/v1/categories')
				const res = await uni.$http.get('/goods/categoryApi/info/parent')
				console.log(res);
				if (res.data.code !== 200) return uni.$showMsg()

				this.cateList = res.data.data

				console.log(this.cateList);

				// 获取二级分类列表数据
				// this.cateLevel2 = res.message[0].children
				this.getCateList2(this.cateList[0].categoryId)
			},
			// 获取二级分类数据的方法
			async getCateList2(parentId) {
				console.log(parentId);
				const res = await uni.$http.get('/goods/categoryApi/info/' + parentId)
				// console.log(res);
				if (res.data.code !== 200) return uni.$showMsg()

				this.cateLevel2 = res.data.data

				console.log("二级分类", this.cateLevel2);
				let that = this;
				for (let i = 0; i < this.cateLevel2.length; i++) {
					this.getCateList3(this.cateLevel2[i])
				}
				console.log(this.cateLevel2[0]);
				console.log(this.cateLevel2[0].children);

			},
			// 获取二级分类数据的方法
			async getCateList3(parent) {
				let parentId = parent.categoryId
				const res = await uni.$http.get('/goods/categoryApi/info/' + parentId)
				// console.log(res);
				if (res.data.code !== 200) return uni.$showMsg()

				// console.log(res.data.data);
				parent.children = []

				res.data.data.forEach(s => {
					parent.children.push(s)
				})

				this.abc = !this.abc

			},
			// 选中项改变的事件处理函数
			activeChanged(item, index) {
				console.log(item);
				this.active = index

				// 为二级分类列表重新赋值
				// this.cateLevel2 = this.cateList[index].children
				this.getCateList2(item.categoryId)

				// 重置滚动条（这里的 1 的作用是因为，我们不能每一个给 scrollTop 赋同样的值）
				// 初始为 0，当我们触发事件时，赋值为 0，这时是不会触发的，我们将它赋值为与底部距离为 1，就可以，0101，不断变化
				this.scrollTop = this.scrollTop === 0 ? 1 : 0
			},

			// 点击三级分类项跳转到商品列表页面
			gotoGoodsList(item3) {
				console.log("为什么出错", item3.categoryId);
				uni.navigateTo({
					url: '/subpkg/goods_list/goods_list?categoryId=' + item3.categoryId
				})
			},

			// 跳转到分包中的搜索页面
			gotoSearch() {
				uni.navigateTo({
					url: '/subpkg/search/search'
				})
			},

			// 跳转到分包中的商户商品详情页面	
			gotoIndentGood(e) {
				uni.navigateTo({
					url: '/subpkg/upload_goods/upload_goods?merchantGoods=' + JSON.stringify(e),
				})
			},

			async getMerchantGoodsList() {
				const that = this
				const {
					data: res
				} = await uni.$http.get('/goods/businessDetailApi/getSelfDetail')
				console.log('this.merchantinfo.businessId', res);
				const bid = res.data.businessId
				console.log('bid', bid);
				if (bid) {
					const {
						data: res1
					} = await uni.$http.get('/goods/goodApi/getByBusinessId/' + bid)
					console.log('当前商户商品', res1);
					if (res1.code === 200) {
						this.indentGoodsList = res1.data.list
						this.total = res1.data.total
						console.log('商户商品列表', this.indentGoodsList);
					}

				}


				// const data = {
				// 	total: "4",
				// 	data: [{
				// 			goodsId: "1587421177964937217",
				// 			gradeId: "1586993961175216130",
				// 			price: 20,
				// 			stock: "1000",
				// 			sales: "500",
				// 			goodsName: "团命后广",
				// 			images: [
				// 				"https://mmbiz.qpic.cn/mmbiz_jpg/CKbYMVUzxGhxJJW1qvmYpOWXl1KadwzRhgojJCu880yWibMZ8sw1uibTfH8jYCsum6A3suR5Dr99HBMPoRWSSzBA/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1",
				// 				"https://mmbiz.qpic.cn/mmbiz_jpg/CKbYMVUzxGiaNrEVvkbg66yUa3MmaHPSlG9vN1ZvfPNYsd995ST5ZUgW9iar2pgbnjtJ0j7icvM4ItRleckz5fXWg/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1"
				// 			],
				// 			description: "生类经科已任例近再就南内还九。离决花信边正术速家江后约先感应最才然。图存走品省众能或许才入界白年开。响立些化单难对算低器形基许。其能三论太再数写风基少回九四好象养。",
				// 			status: "0"
				// 		},
				// 		{
				// 			goodsId: "1587421750588096514",
				// 			gradeId: "1586993961175216130",
				// 			price: 20,
				// 			stock: "1000",
				// 			sales: "500",
				// 			goodsName: "北京绿苹果",
				// 			images: [
				// 				"https://mmbiz.qpic.cn/mmbiz_jpg/CKbYMVUzxGiaNrEVvkbg66yUa3MmaHPSlG9vN1ZvfPNYsd995ST5ZUgW9iar2pgbnjtJ0j7icvM4ItRleckz5fXWg/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1"
				// 			],
				// 			description: "龙具小求交无接适场电料来合思时认号。即文王它力三则研这为听加务器的通。书断权收列府只已去无任产百社第联法。光低能复为书展北边联流党分支连。运习已么易经由政东温或况之又。",
				// 			status: "0"
				// 		},
				// 		{
				// 			goodsId: "1587742117976493712",
				// 			gradeId: "1586993961175216130",
				// 			price: 20,
				// 			stock: "1000",
				// 			sales: "500",
				// 			goodsName: "福建青苹果",
				// 			images: [
				// 				"https://mmbiz.qpic.cn/mmbiz_jpg/CKbYMVUzxGiaNrEVvkbg66yUa3MmaHPSlG9vN1ZvfPNYsd995ST5ZUgW9iar2pgbnjtJ0j7icvM4ItRleckz5fXWg/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1"
				// 			],
				// 			description: "龙具小求交无接适场电料来合思时认号。即文王它力三则研这为听加务器的通。书断权收列府只已去无任产百社第联法。光低能复为书展北边联流党分支连。运习已么易经由政东温或况之又。",
				// 			status: "0"
				// 		},
				// 		{
				// 			goodsId: "1587742117976493721",
				// 			gradeId: "1586993961175216136",
				// 			price: 20,
				// 			stock: "1000",
				// 			sales: "500",
				// 			goodsName: "青苹果",
				// 			images: [
				// 				"https://mmbiz.qpic.cn/mmbiz_jpg/CKbYMVUzxGiaNrEVvkbg66yUa3MmaHPSlG9vN1ZvfPNYsd995ST5ZUgW9iar2pgbnjtJ0j7icvM4ItRleckz5fXWg/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1"
				// 			],
				// 			description: "生类经科已任例近再就南内还九。离决花信边正术速家江后约先感应最才然。图存走品省众能或许才入界白年开。响立些化单难对算低器形基许。其能三论太再数写风基少回九四好象养。",
				// 			status: "0"
				// 		}
				// 	]
				// }

				// this.indentGoodsList = res.data.data
				// this.total = res.data.total



			},

			// 刷新 token
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
				// 调用方法获取分类数据
				that.getCateList1()
				// 获取当前系统的信息
				const sysInfo = uni.getSystemInfoSync()
				// 为 wh 窗口可用高度动态赋值 - 自定义的search组件高度
				that.wh = sysInfo.windowHeight - 48

				clearTimeout(timer); //关闭定时器。               
			}, 1000); //这里的1000代表1秒之后执行方法
		},
		onShow() {
			if (this.pattern) {
				uni.setNavigationBarTitle({
					title: '分类'
				})
			} else {
				uni.setNavigationBarTitle({
					title: '信息'
				})
			}
		},
		onShow() {
			if (!this.pattern && this.firstVisit) {
				// 调用方法获取商户商品列表数据
				this.getMerchantGoodsList()

				this.firstVisit = false
			}
		}
	}
</script>

<style lang="scss">
	.scroll-view-container {
		display: flex;

		.left-scroll-view {
			width: 120px;

			.left-scroll-view-item {
				height: 60px;
				background-color: #f7f7f7;
				line-height: 60px;
				text-align: center;
				font-size: 38rpx;

			}

			& .active {
				position: relative;
				background-color: #ffffff;

				&::before {
					content: '';
					display: block;
					position: absolute;
					top: 50%;
					left: 0;
					width: 3px;
					height: 30px;
					background-color: #c00000;
					transform: translateY(-50%)
				}

			}
		}

		.right-scroll-view {
			background-color: #ffffff;

			.cat-lv2 {
				.cat-lv2-title {
					display: block;
					width: 100%;
					height: 50px;
					line-height: 50px;
					text-align: center;
					font-size: 14px;
					font-weight: 700;
				}

				.cate-lv3-list {
					display: flex;
					flex-wrap: wrap;


					.cate-lv3-item {
						display: flex;
						flex-direction: column;
						align-items: center;
						width: 33.33%;
						margin-bottom: 10px;

						image {
							width: 60px;
							height: 60px;
						}

						text {
							font-size: 12px
						}
					}
				}
			}
		}
	}


	// 商品信息
	.information-container {
		position: relative;

		.informatino-top {
			// 设置定位效果为“吸顶”
			position: sticky;
			// 吸顶的“位置”
			top: 0;
			// 提高层级，防止被轮播图覆盖
			z-index: 999;

			.uploadGoods {
				display: flex;
				justify-content: center;
				align-items: center;

				width: 100%;
				height: 80px;

				background-color: rgb(245, 245, 245);

				.uploadGoods-button {
					width: 120px;
					height: 60px;
					margin-top: 10px;
					border-radius: 50px;
					background-color: #c00000;
					text-align: center;
					line-height: 60px;
					font-size: 16px;
					color: white;
				}

			}

			.myGoods-title {
				padding: 10px;
				font-size: 20px;
				background-color: rgb(245, 245, 245);
			}
		}


		.information-bottom {
			padding: 0 10px;



		}
	}
</style>
