<template>
	<view class="my-container">

		<!-- 用户未登录时，显示登录组件??? -->
		<my-login v-if="!token"></my-login>

		<!-- 用户登录后，显示用户信息组件abcde -->
		<my-userinfo ref="userinfo" v-else></my-userinfo>

	</view>
</template>

<script>
	// 导入自己封装的 mixin 模块abc
	import badgeMix from '@/mixins/tabbar-badge.js'
	// 1. 从 vuex 中按需导入 mapState 辅助函数
	import {
		mapState,
		mapMutations
	} from 'vuex'

	export default {
		// 将 badgeMix 混入到当前的页面中进行使用
		mixins: [badgeMix],

		data() {
			return {

			};
		},

		computed: {
			// 2. 从 m_user 模块中导入需要的 token 字符串
			...mapState('m_user', ['token']),
			...mapState('m_user', ['refreshToken'])
		},

		methods: {
			...mapMutations('m_user', ['updateToken', 'updataRefreshToken']),

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
				console.log("获取新token", res);

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
			// 需用ref之前，对于的组件必须先渲染成功，否则就不能使用ref，如果一开始页面就在
			setTimeout(() => {
				console.log(this.$refs);
				// this.$refs.userinfo.getCollectHistory()
			})

		}
	}
</script>

<style lang="scss">
	page,
	.my-container {
		height: 100%;
	}
</style>
