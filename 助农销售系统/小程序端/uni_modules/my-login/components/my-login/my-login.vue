<template>
	<view class="login-container">
		<!-- 提示登录的图标 -->
		<uni-icons type="contact-filled" size="100" color="#AFAFAF"></uni-icons>
		<!-- 登录按钮 -->
		<!-- <button type="primary" class="btn-login" @click="getUserProfile">一键登录</button> -->
		<button type="primary" class="btn-login" @click="getToken">一键登录</button>
		<button open-type="getUserInfo"></button>
		<!-- 登录提示 -->
		<view class="tips-text">
			登录后尽享更多权益
		</view>
	</view>
</template>

<script>
	import {
		mapMutations,
		mapState
	} from 'vuex'
	import JSONbig from 'json-bigint'

	export default {
		data() {
			return {
				isFresh: true,
			}
		},

		computed: {
			// 调用 mapState 辅助方法，把 m_user 模块中的数据映射到当前用组件中使用
			...mapState('m_user', ['redirectInfo']),
		},

		methods: {
			...mapMutations('m_user', ['updateUserInfo', 'updateUserLoginInfo', 'updataRefreshToken', 'updateToken',
				'updateRedirectInfo'
			]),

			// 获取用户基本信息，并赋予 用户基本信息
			// async getUserProfile() {
			// 	const res = await uni.getUserProfile({
			// 		desc: '登录授权'
			// 	}).catch(err => err)

			// 	console.log(res);
			// 	console.log(res[1].userInfo);

			// 	// 判断是否成功
			// 	if(res[0] !== null && res[0].errMsg === 'getUserProfile:fail auth deny') return uni.$showMsg('请重新进行授权！！！')

			// 	// 里面包含有 UserInfo 信息
			// 	this.updateUserInfo(res[1].userInfo)

			// 	// 获取登录成功的 Token 字符串
			// 	this.getToken(res[1])
			// },

			// 调用登录接口，换取永久的 token
			// async getToken(info) {
			// 	console.log(info);
			async getToken() {
				console.log();
				// 调用微信登录接口
				const [err, res] = await uni.login().catch(err => err)

				console.log(res);
				// 判断是否 wx.login() 调用失败
				// 如果访问失败，则 res 可能是个 undefined，就需要在前面给个判断条件，看看 err 是否为 true
				if (err || res.errMsg !== 'login:ok') return uni.$showMsg('登录失败!')

				// 准备参数
				const query = {
					wx_code: res.code,
					// wx_raw_data: info.rawData,
				}
				console.log(111111);
				console.log(query);

				// 换取 token
				const {
					data: loginResult
				} = await uni.$http.post('/auth/oauth2/token?grant_type=wx_code&scope=server', query)
				console.log(loginResult);

				if (loginResult.code !== 200) return uni.$showMsg('登录失败！')

				this.updateUserLoginInfo(loginResult.user_info)
				// console.log(JSONbig.parse(loginResult.user_info));

				this.updateToken(loginResult.access_token)
				this.updataRefreshToken(loginResult.refresh_token)

				uni.$showMsg('登录成功')

				// 判断 vuex 中的 redirectInfo 是否为 null
				// 如果不为 null，则登录成功之后，需要重新导航到对应的页面
				// this.navigateBack()

				// 判断是否为新用户
				this.getIsFresh()

				const that = this
				var timer = setInterval(function() { //开启定时器，下面就是执行的方法，在窗体加载1S后执行下面的方法

					console.log(that.isFresh);
					if (that.isFresh) {
						that.gotoPersonalInformation()
					} else {
						that.getUserInfo()
					}

					clearTimeout(timer); //关闭定时器。               
				}, 500); //这里的1000代表1秒之后执行方法

			},

			// 获取是否为新用户
			async getIsFresh() {
				const {
					data: res
				} = await uni.$http.get('/admin/wechat/isFresh')
				console.log(res);
				this.isFresh = res.data

				if (res.code !== 200) return uni.$showMsg()
			},

			// 获取用户信息
			async getUserInfo() {
				const {
					data: res
				} = await uni.$http.get('/admin/user/info')
				console.log(res);
				console.log(res.data);

				const userInfo = {
					avatarUrl: res.data.sysUser.avatar,
					city: "",
					country: "",
					gender: 0,
					is_demote: true,
					language: "",
					nickName: res.data.sysUser.username,
					province: "",
				}

				this.updateUserInfo(userInfo)

				if (res.code !== 200) return uni.$showMsg()
			},

			gotoPersonalInformation() {
				uni.navigateTo({
					url: '/subpkg/personal_information/personal_information'
				})
			},

			navigateBack() {
				console.log(this.redirectInfo);

				// redirectInfo 不为 null，并且导航方式为 switchTab
				if (this.redirectInfo && this.redirectInfo.openType === 'switchTab') {
					// 调用小程序提供的 uni.switchTab() API 进行页面的导航
					uni.switchTab({
						// 要导航到的页面地址
						url: this.redirectInfo.from,
						// 导航成功之后，把 vuex 中的 redirectInfo 对象重置为 null
						complete: () => {
							this.updateRedirectInfo(null)
						}
					})
				}
			},

		}
	}
</script>

<style lang="scss">
	.login-container {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		height: 750rpx;
		background-color: #f8f8f8;

		.btn-login {
			width: 90%;
			border-radius: 100px;
			margin: 15px 0;
			background-color: #c00000;
		}

		.tips-text {
			font-size: 12px;
			color: gray;
		}
	}
</style>
