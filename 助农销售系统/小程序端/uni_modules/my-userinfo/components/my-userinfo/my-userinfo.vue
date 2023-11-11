<template>
	<view class="my-userinfo-container">
		<!-- 切换按钮 -->
		<button class="switchover" size="mini" @click="switchOverSeller" v-if="pattern">
			切换商户
		</button>
		<button class="switchover" size="mini" @click="switchOverUser" type="primary" v-else>
			切换用户
		</button>

		<!-- 用户短信按钮 -->
		<view class="note" @click="gotoNoteList" v-if="pattern">
			<uni-icons type="email" size="26" color="#fff"></uni-icons>
		</view>


		<!-- 头像昵称区域 -->
		<view class="top-box">
			<image :src="userinfo.avatarUrl" class="avatar"></image>
			<view class="nickname">
				{{userinfo.nickName}}
			</view>
		</view>

		<!-- 用户面板的列表区域 -->
		<view class="panel-list" v-if="pattern">
			<!-- 第一个面板 -->
			<view class="panel">
				<!-- panel 的主体区域 -->
				<view class="panel-body">
					<!-- panel 的 item 项 -->
					<view class="panel-item" @click="gotoCollect(0)">
						<text>{{ collectHistory[2] }}</text>
						<text>收藏商品</text>
					</view>
					<view class="panel-item" @click="gotoCollect(1)">
						<text>{{ collectHistory[1] }}</text>
						<text>收藏店铺</text>
					</view>
					<view class="panel-item" @click="gotoCollect(2)">
						<text>{{ collectHistory[0] }}</text>
						<text>足迹</text>
					</view>
				</view>
			</view>

			<!-- 第二个面板 -->
			<view class="panel">
				<!-- 面板的标题 -->
				<view class="panel-title">我的订单</view>
				<!-- 面板的主体 -->
				<view class="panel-body">
					<!-- 面板主体中的 item 项 -->
					<view class="panel-item" @click="gotoOrderList(1)">
						<image src="/static/my-icons/icon11.png" class="icon"></image>
						<text>待发货</text>
					</view>
					<view class="panel-item" @click="gotoOrderList(2)">
						<image src="/static/my-icons/icon22.png" class="icon"></image>
						<text>待收货</text>
					</view>
					<view class="panel-item" @click="gotoOrderList(3)">
						<image src="/static/my-icons/icon33.png" class="icon"></image>
						<text>退款/退货</text>
					</view>
					<view class="panel-item" @click="gotoOrderList(6)">
						<image src="/static/my-icons/icon44.png" class="icon"></image>
						<text>全部订单</text>
					</view>
				</view>
			</view>

			<!-- 第三个面板 -->
			<view class="panel">
				<view class="panel-list-item" @click="gotoPersonalInformation">
					<text>个人信息</text>
					<uni-icons type="arrowright" size="15"></uni-icons>
				</view>
				<view class="panel-list-item" @click="becomeMerchant">
					<text>成为商户</text>
					<uni-icons type="arrowright" size="15"></uni-icons>
				</view>
			</view>

			<!-- 第四个面板 -->
			<view class="panel">
				<view class="panel-list-item" @click="gotoService">
					<text>联系客服</text>
					<uni-icons type="arrowright" size="15"></uni-icons>
				</view>
				<view class="panel-list-item" @click="logout">
					<text>退出登录</text>
					<uni-icons type="arrowright" size="15"></uni-icons>
				</view>
			</view>
		</view>

		<!-- 商户面板的列表区域 -->
		<view class="panel-list" v-else>
			<!-- 第一个面板 -->
			<view class="panel">
				<view class="panel-list-item" @click="merchantInformation">
					<text>个人信息</text>
					<uni-icons type="arrowright" size="15"></uni-icons>
				</view>
				<!-- <view class="panel-list-item" @click="gotoLandManagement">
					<text>地块管理</text>
					<uni-icons type="arrowright" size="15"></uni-icons>
				</view> -->
				<view class="panel-list-item" @click="gotoLatestNews">
					<text>发布近期动态</text>
					<uni-icons type="arrowright" size="15"></uni-icons>
				</view>
			</view>
			<!-- 第二个面板 -->
			<view class="panel">
				<view class="panel-list-item" @click="publishInformation">
					<text>发布资讯</text>
					<uni-icons type="arrowright" size="15"></uni-icons>
				</view>
				<view class="panel-list-item" @click="gotoService">
					<text>联系客服</text>
					<uni-icons type="arrowright" size="15"></uni-icons>
				</view>
			</view>
			<!-- 第三个面板 -->
			<view class="panel">
				<view class="panel-list-item" @click="logout">
					<text>退出登录</text>
					<uni-icons type="arrowright" size="15"></uni-icons>
				</view>
			</view>
		</view>

		<!-- <button type="default" open-type="getPhoneNumber" @getphonenumber="decryptPhoneNumber">获取手机号</button> -->
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
				judgeMerchantState: 0,
				collectHistory: null,
				AvatarUrl: 'https://wx3.sinaimg.cn/mw2000/007eYxgSgy1hastp303ywj31401e0dlo.jpg'
			}
		},
		computed: {
			...mapState('m_user', ['userinfo']),
			...mapState(['pattern']),
			...mapState('m_user', ['userLoginInfo'])
		},
		methods: {
			...mapMutations('m_user', ['updateUserInfo', 'updateUserLoginInfo', 'updateToken', 'updateAddress']),
			...mapMutations(['updatePattern']),

			// 退出登录
			async logout() {
				// 询问用户是否退出登录
				const [err, succ] = await uni.showModal({
					title: '提示',
					content: '确认退出登录吗？'
				}).catch(err => err)

				if (succ && succ.confirm) {
					// 用户确认了退出登录的操作
					// 需要清空 vuex 中的 userinfo、token 和 address
					this.updateUserInfo({})
					this.updateToken('')
					this.updateUserLoginInfo('')
					this.updateAddress({})
				}
			},

			// 判断是否为商户
			async judgeMerchant() {
				console.log(13214212);
				const {
					data: res
				} = await uni.$http.get('/admin/user/checkBusiness')
				console.log('判断是否为商户', res);
				if (res.code !== 200) return uni.$showMsg('')

				this.judgeMerchantState = res.data
			},

			// 获取收藏与足迹信息
			async getCollectHistory() {
				const {
					data: res
				} = await uni.$http.get('/goods/collectionApi/simple')
				console.log('获取收藏与足迹信息', res);
				if (res.code !== 200) return uni.$showMsg('')

				this.collectHistory = res.data
			},

			// 切换为商户模式
			switchOverSeller() {
				this.judgeMerchant()
				const that = this
				uni.showModal({
					title: '切换商户',
					content: '您确认切换为商户状态吗？',
					success: function(res) {
						if (res.confirm) {
							console.log('商户', that.judgeMerchantState);
							if (that.judgeMerchantState == true) {
								that.updatePattern()

								uni.setTabBarItem({
									index: 0,
									text: '主页',
									iconPath: 'static/tab_icons/home.png',
									selectedIconPath: 'static/tab_icons/home-active.png'
								})
								uni.setTabBarItem({
									index: 1,
									text: '信息',
									iconPath: 'static/tab_icons/message.png',
									selectedIconPath: 'static/tab_icons/message-active.png'
								})
								uni.setTabBarItem({
									index: 2,
									text: '订单',
									iconPath: 'static/tab_icons/indent.png',
									selectedIconPath: 'static/tab_icons/indent-active.png'
								})
								uni.setTabBarItem({
									index: 3,
									text: '我的',
									iconPath: 'static/tab_icons/my2.png',
									selectedIconPath: 'static/tab_icons/my2-active.png'
								})

								// 隐藏 tabbar 右上角徽标
								uni.hideTabBarRedDot({
									index: 2
								})
							} else {
								uni.$showMsg('您还未成为商户')
							}
						} else if (res.cancel) {

						}
					}
				});

			},

			// 获取手机号码（暂时不要）
			// decryptPhoneNumber(e) {
			//     console.log(e)
			// }

			// 切换为用户模式
			switchOverUser() {
				const that = this
				uni.showModal({
					title: '切换用户',
					content: '您确认切换为用户状态吗？',
					success: function(res) {
						if (res.confirm) {
							that.updatePattern()

							uni.setTabBarItem({
								index: 0,
								text: '首页',
								iconPath: 'static/tab_icons/home.png',
								selectedIconPath: 'static/tab_icons/home-active.png'
							})
							uni.setTabBarItem({
								index: 1,
								text: '分类',
								iconPath: 'static/tab_icons/cate.png',
								selectedIconPath: 'static/tab_icons/cate-active.png'
							})
							uni.setTabBarItem({
								index: 2,
								text: '购物车',
								iconPath: 'static/tab_icons/cart.png',
								selectedIconPath: 'static/tab_icons/cart-active.png'
							})
							uni.setTabBarItem({
								index: 3,
								text: '我的',
								iconPath: 'static/tab_icons/my.png',
								selectedIconPath: 'static/tab_icons/my-active.png'
							})

							// // 隐藏 tabbar 右上角徽标
							// uni.showTabBarRedDot({
							// 	index: 2
							// })
						} else if (res.cancel) {

						}
					}
				});

			},

			// 跳转到短信列表页面
			gotoNoteList() {
				uni.navigateTo({
					url: '/subpkg/note_list/note_list'
				});
			},

			// 跳转到订单列表页面
			gotoOrderList(n) {
				uni.navigateTo({
					url: '/subpkg/order_list/order_list?state=' + n
				});
			},

			// 跳转到收藏列表页面
			gotoCollect(n) {

				uni.navigateTo({
					url: '/subpkg/collect/collect?state=' + n
				})
			},

			// 跳转到个人信息页面
			gotoPersonalInformation() {
				uni.navigateTo({
					url: '/subpkg/personal_information/personal_information'
				})
			},

			// 跳转到注册商户页面
			becomeMerchant() {
				uni.navigateTo({
					url: '/subpkg/become_merchant/become_merchant'
				})
			},

			// 跳转到商户详情页面
			merchantInformation() {
				uni.navigateTo({
					url: '/subpkg/become_merchant/become_merchant?merchantId=' + 1649796123411992578
				})
			},

			// 跳转到地块管理页面
			gotoLandManagement() {
				uni.navigateTo({
					url: '/subpkg/land_management/land_management'
				})
			},

			// 跳转到发布近期动态页面
			gotoLatestNews() {
				uni.navigateTo({
					url: '/subpkg/latest_News/latest_News'
				})
			},

			// 跳转到客服界面	
			gotoService() {
				uni.navigateTo({
					url: '/subpkg/service/service'
				})
			},

			// 发布资讯
			publishInformation() {
				uni.$showMsg('请添加客服获取详细信息！！！')
			}
		},
		created() {
			this.getCollectHistory()
		}
	}
</script>

<style lang="scss">
	.my-userinfo-container {
		position: relative;
		height: 100%;
		background-color: #f4f4f4;

		.switchover {
			position: fixed;
			top: 20rpx;
			right: 20rpx;
			background-color: orange;
			color: #fff;
		}

		.note {
			display: flex;
			justify-content: center;
			align-items: center;
			position: fixed;
			top: 260rpx;
			right: 120rpx;
			width: 80rpx;
			height: 80rpx;
			border-radius: 100%;
			background-color: #e50000;
			color: #fff;
		}

		.top-box {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			height: 400rpx;
			background-color: #C00000;

			.avatar {
				display: block;
				width: 180rpx;
				height: 180rpx;
				border-radius: 90rpx;
				border: 2px solid white;
				box-shadow: 0 1px 5px black;
			}

			.nickname {
				color: #fff;
				font-weight: bold;
				font-size: 16px;
				margin-top: 20rpx;
			}
		}

		.panel-list {
			padding: 0 20rpx;
			position: relative;
			top: -20rpx;

			.panel {
				background-color: white;
				border-radius: 6rpx;
				margin-bottom: 16rpx;

				.panel-title {
					line-height: 90rpx;
					padding-left: 20rpx;
					font-size: 30rpx;
					border-bottom: 1px solid #f4f4f4;
				}

				.panel-body {
					display: flex;
					justify-content: space-around;

					.panel-item {
						display: flex;
						flex-direction: column;
						align-items: center;
						justify-content: space-around;
						width: 140rpx;
						font-size: 13px;
						padding: 20rpx 0;

						.icon {
							width: 70rpx;
							height: 70rpx;
						}
					}

					.panel-message {

						& text:nth-of-type(1),
						& text:nth-of-type(3) {
							color: #747474;
						}

						& text:nth-of-type(2) {
							margin: 10rpx 0;
							font-size: 16px;
							font-weight: 700;
						}
					}
				}
			}

			.panel-list-item {
				height: 90rpx;
				display: flex;
				justify-content: space-between;
				align-items: center;
				font-size: 15px;
				padding: 0 20rpx;
			}
		}


	}
</style>
