<template>
	<view>
		<!-- 面板的主体 -->
		<view class="panel-body">
			<!-- 面板主体中的 item 项 -->
			<view class="panel-item" @click="getPanelsList(1)"
				:style="'background-color:' + (color == 1 ? '#f5f5f5' : '#ffffff')">
				<image src="/static/my-icons/icon1.png" class="icon"></image>
				<text>待发货</text>
			</view>
			<view class="panel-item" @click="getPanelsList(2)"
				:style="'background-color:' + (color == 2 ? '#f5f5f5' : '#ffffff')">
				<image src="/static/my-icons/icon2.png" class="icon"></image>
				<text>待收货</text>
			</view>
			<view class="panel-item" @click="getPanelsList(3)"
				:style="'background-color:' + (color == 3 ? '#f5f5f5' : '#ffffff')">
				<image src="/static/my-icons/icon3.png" class="icon"></image>
				<text>退款/退货</text>
			</view>
			<view class="panel-item" @click="getAllPanelsList(6)"
				:style="'background-color:' + (color == 6 ? '#f5f5f5' : '#ffffff')">
				<image src="/static/my-icons/icon4.png" class="icon"></image>
				<text>全部订单</text>
			</view>
		</view>

		<view class="panel-list">
			<view v-for="(item,index) in panelsList" :key="index">
				<my-sellerIndent @indentClick="gotoIndent(item.orderId)" :indentItem="item"></my-sellerIndent>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				state: null,
				color: 1,
				panelsList: [],

			};
		},

		methods: {
			gotoOrderDetails() {
				uni.navigateTo({
					url: '/subpkg/order_details/order_details?orderId=' + n
				});
			},

			// 前往商户订单页面
			gotoIndent(e) {
				uni.navigateTo({
					url: '/subpkg/order_details/order_details?orderId=' + e
				});
			},

			// 获取订单信息
			async getPanelsList(n, e) {
				if (e == 'all') {
					this.color = 6
				} else {
					this.color = n
				}
				n = Number(n)
				const {
					data: res
				} = await uni.$http.get('/orders/orderApi/getOrderByStatus/' + n)
				console.log('获取订单信息', res);
				if (res.code !== 200) return uni.$showMsg()
				if (e != 'all') {
					this.panelsList = res.data
				} else {
					this.panelsList = [...this.panelsList, ...res.data]
				}

			},
			// 获取全部订单信息
			// async getAllPanelsList(n) {
			// 	this.color = n
			// 	// n = Number(n)
			// 	const { data: res } = await uni.$http.get('/orders/orderApi/list')
			// 	console.log(res);
			// 	if( res.code !== 200) return uni.$showMsg()
			// 	this.panelsList = res.data
			// },
			getAllPanelsList(n) {
				this.color = n
				this.panelsList = []
				this.getPanelsList(1, 'all')
				this.getPanelsList(2, 'all')
				this.getPanelsList(3, 'all')
				this.getPanelsList(5, 'all')
			}
		},

		onLoad(loading) {
			this.state = loading.state
			this.color = loading.state
			if (loading.state == 6) {
				this.getAllPanelsList(6)
			} else {
				this.getPanelsList(loading.state)
			}

			console.log(this.state);
		},

		onShow(loading) {}
	}
</script>

<style lang="scss">
	.panel-body {
		// 设置定位效果为“吸顶”
		position: sticky;
		// 吸顶的“位置”
		top: 0;
		// 提高层级，防止被轮播图覆盖
		z-index: 999;
		display: flex;
		justify-content: space-around;
		background-color: #fff;

		.panel-item {
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

	.panel-list {
		padding: 20px 10px 0px 10px;
		background-color: #F5F5F5;
	}
</style>
