<template>
	<view class="">
		<!-- 购物车页面 -->
		<view class="" v-if="pattern">
			<view class="cart-container" v-if="cart.length !== 0">
				<view class="cart-title-box">
					<!-- 收货地址组件 -->
					<my-address></my-address>

					<!-- 商品列表的标题区域 -->
					<view class="cart-title">
						<!-- 左侧的图标 -->
						<uni-icons type="cart" size="30"></uni-icons>
						<!-- 右侧的文本 -->
						<text class="cart-title-text">购物车</text>
					</view>
				</view>

				<!-- 商品列表区域 -->
				<!-- uni-swipe-action 是最外层包裹性质的容器 -->
				<uni-swipe-action>
					<block v-for="(item, index) in cart" :key="index">
						<uni-swipe-action-item :right-options="options" @click="swipeActionClickHandler(item)">
							<my-goods :goods="item" :showRadio="true" :showNum="true" @radio-change="radioClickHandler"
								@num-change="numberChangeHandler"></my-goods>
						</uni-swipe-action-item>
					</block>
				</uni-swipe-action>

				<!-- 结算组件 -->
				<my-settle></my-settle>
			</view>

			<!-- 空白购物车区域 -->
			<view class="empty-cart" v-else>
				<image src="/static/cart_empty@2x.png" class="empty-img"></image>
				<text class="tip-text">空空如也~</text>
			</view>
		</view>


		<!-- 订单页面 -->
		<view class="indent-container" v-else>
			<view class="indent-body">
				<!-- 面板主体中的 item 项 -->
				<view class="indent-item" @click="indentItemState(5)"
					:style="'background-color:' + (color === 5 ? '#f5f5f5' : '#ffffff')">
					<image src="/static/my-icons/icon1.png" class="icon"></image>
					<text>已收货</text>
				</view>
				<view class="indent-item" @click="indentItemState(1)"
					:style="'background-color:' + (color === 1 ? '#f5f5f5' : '#ffffff')">
					<image src="/static/my-icons/icon2.png" class="icon"></image>
					<text>待发货</text>
				</view>
				<view class="indent-item" @click="indentItemState(4)"
					:style="'background-color:' + (color === 4 ? '#f5f5f5' : '#ffffff')">
					<image src="/static/my-icons/icon3.png" class="icon"></image>
					<text>待退款/退货</text>
				</view>
				<view class="indent-item" @click="allIndentItemState(6)"
					:style="'background-color:' + (color === 6 ? '#f5f5f5' : '#ffffff')">
					<image src="/static/my-icons/icon4.png" class="icon"></image>
					<text>全部订单</text>
				</view>
			</view>
			<view class="indent-list">
				<view v-for="(item,index) in indentsList" :key="index">
					<my-sellerIndent @indentClick="gotoIndent(item.orderId)" :indentItem="item"></my-sellerIndent>
				</view>
			</view>
		</view>
	</view>


</template>

<script>
	// 导入自己封装的 mixin 模块
	import badgeMix from '@/mixins/tabbar-badge.js'
	// 按需导入 mapState 这个辅助函数
	import {
		mapState,
		mapMutations
	} from 'vuex'

	export default {
		// 将 badgeMix 混入到当前的页面中进行使用
		mixins: [badgeMix],
		// 将 m_cart 模块中的 cart 数组映射到当前页面中使用
		computed: {
			...mapState('m_cart', ['cart']),
			...mapState(['pattern']),
			...mapState('m_user', ['refreshToken'])
		},
		data() {
			return {
				options: [{
					text: '删除', // 显示的文本内容
					style: {
						backgroundColor: '#C00000' // 按钮的背景颜色
					}
				}],
				indentsList: [],
				color: 0,

				// 判断是否首次访问商户界面数据
				firstVisit: true,
			};
		},
		methods: {
			...mapMutations('m_cart', ['updateGoodsState', 'updateGoodsCount', 'removeGoodsById']),
			...mapMutations('m_user', ['updateToken', 'updataRefreshToken']),

			// 商品的勾选状态发生了变化
			radioClickHandler(e) {
				this.updateGoodsState(e)
			},

			// 商品的数量发生了变化
			numberChangeHandler(e) {
				this.updateGoodsCount(e)
			},

			// 删除购物车里的商品
			swipeActionClickHandler(goods) {
				this.removeGoodsById(goods.goodsId)
			},

			// 前往商户订单页面
			gotoIndent(e) {
				console.log(111);
				uni.navigateTo({
					url: '/subpkg/order_details/order_details?orderId=' + e
				});
			},

			// 更改订单状态信息
			async indentItemState(n, e) {
				if (e == 'all') {
					this.color = 6
				} else {
					this.color = n
				}
				n = Number(n)
				const {
					data: res
				} = await uni.$http.get('/orders/orderApi/getOrderByStatusToBusiness/' + n)
				console.log(res);
				if (res.code !== 200) return uni.$showMsg()
				if (e != 'all') {
					this.indentsList = res.data
				} else {
					this.indentsList = [...this.indentsList, ...res.data]
				}
			},

			// 全部订单状态信息
			// async allIndentItemState(n) {
			// 	this.color = n;
			// 	const { data: res } = await uni.$http.get('/orders/orderApi/list')
			// 	console.log(res);
			// 	if( res.code !== 200) return uni.$showMsg()
			// 	this.indentsList = res.data
			// },

			allIndentItemState(n) {
				this.color = n
				this.indentsList = []
				// this.indentItemState(0, 'all')
				this.indentItemState(1, 'all')
				this.indentItemState(4, 'all')
				this.indentItemState(5, 'all')
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
		},

		onShow() {
			if (this.pattern) {
				uni.setNavigationBarTitle({
					title: '购物车'
				})
			} else {
				uni.setNavigationBarTitle({
					title: '订单'
				})
			}
		},
		onShow() {
			if (!this.pattern && this.firstVisit) {
				// 获取订单页面数据列表
				this.indentItemState(5)

				this.firstVisit = false
			}
		}
	}
</script>

<style lang="scss" scoped>
	.cart-container {
		padding-bottom: 50px;
	}

	.cart-title-box {
		// 设置定位效果为“吸顶”
		position: sticky;
		// 吸顶的“位置”
		top: 0;
		// 提高层级，防止被轮播图覆盖
		z-index: 999;

		background-color: #F5F5F5;
	}

	.cart-title {
		height: 40px;
		display: flex;
		align-items: center;
		padding-left: 5px;
		border-bottom: 1px solid #efefef;

		.cart-title-text {
			margin-left: 10px;
		}
	}

	.empty-cart {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		padding-top: 150px;

		.empty-img {
			width: 90px;
			height: 90px;
		}

		.tip-text {
			font-size: 12px;
			color: gray;
			margin-top: 15px;
		}
	}

	.indent-container {
		.indent-body {
			// 设置定位效果为“吸顶”
			position: sticky;
			// 吸顶的“位置”
			top: 0;
			// 提高层级，防止被轮播图覆盖
			z-index: 999;
			display: flex;
			justify-content: space-around;
			background-color: #fff;

			.indent-item {
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: space-around;
				width: 187rpx;
				font-size: 13px;
				padding: 10px 0;

				.icon {
					width: 35px;
					height: 35px;
				}
			}
		}

		.indent-list {
			z-index: 0;
			padding: 20px 10px 0px 10px;
			background-color: #F5F5F5;
		}
	}
</style>
