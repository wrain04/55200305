<template>
	<view class="my-settle-container">
		<!-- 全选 -->
		<label class="radio" @click="changeAllState">
			<radio color="#C00000" :checked="isFullCheck" /><text>全选</text>
		</label>

		<!-- 合计 -->
		<view class="amount-box">
			合计：<text class="amount">￥{{checkedGoodsAmount}}</text>
		</view>

		<!-- 结算 -->
		<view class="btn-settle" @click="settlement">
			结算({{checkedCount}})
		</view>
	</view>
</template>

<script>
	import {
		mapState,
		mapGetters,
		mapMutations
	} from 'vuex'

	export default {
		computed: {
			// 1. 将 total 映射到当前组件中
			...mapGetters('m_cart', ['checkedCount', 'total', 'checkedGoodsAmount']),

			// addstr 是详细的收货地址
			...mapGetters('m_user', ['addstr']),
			// token 是用户登录成功之后的 token 字符串
			...mapState('m_user', ['token']),

			...mapState('m_cart', ['cart']),

			// 2. 是否全选
			isFullCheck() {
				return this.total === this.checkedCount
			},
		},

		data() {
			return {
				// 倒计时的秒数
				seconds: 3,
				// 定时器的 Id
				timer: null,
				// 购物车商品信息
				goods: {}
			}
		},

		methods: {
			// 2. 使用 mapMutations 辅助函数，把 m_cart 模块提供的 updateAllGoodsState 方法映射到当前组件中使用
			...mapMutations('m_cart', ['updateAllGoodsState', ]),

			// 把 m_user 模块中的 updateRedirectInfo 方法映射到当前页面中使用
			...mapMutations('m_user', ['updateRedirectInfo', 'userinfo']),

			// label 的点击事件处理函数
			changeAllState() {
				// 修改购物车中所有商品的选中状态
				// !this.isFullCheck 表示：当前全选按钮的状态取反之后，就是最新的勾选状态
				this.updateAllGoodsState(!this.isFullCheck)
			},

			// 点击了结算按钮
			settlement() {
				// 1. 先判断是否勾选了要结算的商品
				if (!this.checkedCount) return uni.$showMsg('请选择要结算的商品！')

				// 2. 再判断用户是否选择了收货地址
				if (!this.addstr) return uni.$showMsg('请选择收货地址！')

				// 3. 最后判断用户是否登录了
				// if (!this.token) return uni.$showMsg('请先登录！')
				if (!this.token) return this.delayNavigate()

				// 4. 实现微信支付功能
				this.payOrder()
			},

			// 延迟导航到 my 页面
			delayNavigate() {
				// 当在已经倒计时过一次之后，重新倒计时时 把 data 中的秒数重置成 3 秒
				this.seconds = 3

				// 1. 展示提示消息，此时 seconds 的值等于 3
				this.showTips(this.seconds)

				// 2. 创建定时器，每隔 1 秒执行一次

				// 1. 将定时器的 Id 存储到 timer 中
				this.timer = setInterval(() => {
					// 2.1 先让秒数自减 1
					this.seconds--

					// 2. 判断秒数是否 <= 0
					if (this.seconds <= 0) {
						// 2.1 清除定时器
						clearInterval(this.timer)

						// 2.2 跳转到 my 页面
						uni.switchTab({
							url: '/pages/my/my',
							// 页面跳转成功之后的回调函数
							success: () => {

								// 调用 vuex 的 updateRedirectInfo 方法，把跳转信息存储到 Store 中
								this.updateRedirectInfo({
									// 跳转的方式
									openType: 'switchTab',
									// 从哪个页面跳转过去的
									from: '/pages/cart/cart'
								})
							}
						})

						// 2.3 终止后续代码的运行（当秒数为 0 时，不再展示 toast 提示消息）
						return
					}

					// 2.2 再根据最新的秒数，进行消息提示
					this.showTips(this.seconds)
				}, 1000)
			},

			// 展示倒计时的提示消息
			showTips(n) {
				// 调用 uni.showToast() 方法，展示提示消息
				uni.showToast({
					// 不展示任何图标
					icon: 'none',
					// 提示的消息
					title: '请登录后再结算！' + n + ' 秒后自动跳转到登录页',
					// 为页面添加透明遮罩，防止点击穿透
					mask: true,
					// 1.5 秒后自动消失
					duration: 1500
				})
			},

			// 支付
			async payOrder() {
				// 1. 创建订单
				this.goods = this.cart.filter(x => x.goods_state).map(x => ({
					goods_id: x.goodsId,
					goods_number: x.goods_count,
					goods_price: x.gradePrice
				}));
				console.log('购物车商品信息', this.goods);
				// 1.1 组织订单的信息对象
				const orderInfo = {
					// 商品ID
					// good_id: this.goods[0].goods_id,
					// goodId: '1622128121589796866',
					goodId: this.goods[0].goods_id,
					// 写死订单总价为 1 分钱
					price: this.checkedGoodsAmount,
					detailAddress: this.addstr,
					status: "1",
					provinceName: '广东省',
					userId: '1649796123411992578',
					businessId: "1649796123411992578",
					phone: 13154987345,
					payMethod: '1',
					consignee: 'wwwwwz-',
					countyName: '报现六些低',
					amount: 1,
					orderId: "4",
					cityName: "候导果造克"

					// filter 是进行筛选，而 map，是将 对象的格式转换成服务器想要的，服务器需要的是 goods_number,而不是 goods_count
					// goods: this.cart.filter(x => x.goods_state).map(x => ({
					// 	goods_id: x.goodsId,
					// 	goods_number: x.goods_count,
					// 	goods_price: x.gradePrice
					// }))
				}
				console.log('什么', orderInfo);

				// 1.2 发起请求创建订单
				const {
					data: res
				} = await uni.$http.post('/orders/orderApi/createOrder', orderInfo)
				console.log("创建订单", res);
				if (res.code !== 200) {
					return uni.$showMsg('创建订单失败！')
				} else {
					const orderNumber = res.data
					console.log(orderNumber);
					uni.showToast({
						title: '支付成功',
						duration: 3000,
						icon: 'success'
					})
					const timer = setInterval(function() {
						uni.switchTab({
							url: '/pages/my/my'
						})
						clearTimeout(timer)
					}, 3000)


				}

				// 1.3 得到服务器响应的“订单编号”


				// 2. 订单预支付
				// 2.1 发起请求获取订单的支付信息
				// const {
				// 	data: res2
				// } = await uni.$http.post(
				// 	'https://api-hmugo-web.itheima.net/api/public/v1/my/orders/req_unifiedorder', {
				// 		order_number: orderNumber
				// 	})
				// // 2.2 预付订单生成失败
				// if (res2.meta.status !== 200) return uni.$showError('预付订单生成失败！')
				// // 2.3 得到订单支付相关的必要参数
				// const payInfo = res2.message.pay

				// console.log(payInfo);

				// // 3. 发起微信支付
				// // 3.1 调用 uni.requestPayment() 发起微信支付
				// const [err, succ] = await uni.requestPayment(payInfo)
				// // 3.2 未完成支付
				// if (err) return uni.$showMsg('订单未支付！')
				// // 3.3 完成了支付，进一步查询支付的结果
				// const {
				// 	data: res3
				// } = await uni.$http.post('https://api-hmugo-web.itheima.net/api/public/v1/my/orders/chkOrder', {
				// 	order_number: orderNumber
				// })
				// // 3.4 检测到订单未支付
				// if (res3.meta.status !== 200) return uni.$showMsg('订单未支付！')
				// // 3.5 检测到订单支付完成
				// uni.showToast({
				// 	title: '支付完成！',
				// 	icon: 'success'
				// })

			}
		}
	}
</script>

<style lang="scss">
	.my-settle-container {
		display: flex;
		justify-content: space-between;
		align-items: center;
		box-sizing: border-box;
		position: fixed;
		bottom: 0;
		left: 0;
		width: 100%;
		height: 50px;
		padding: 0 5px;
		background-color: #fff;
		font-size: 14px;

		.radio {
			display: flex;
			align-items: center;
		}

		.amount-box {
			.amount {
				color: #C00000;
			}
		}

		.btn-settle {
			display: flex;
			justify-content: center;
			align-items: center;
			min-width: 100px;
			height: 100%;
			padding: 0 10px;
			background-color: #C00000;
			color: #fff;
		}
	}
</style>
