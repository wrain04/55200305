<template>
	<view class="personalInformation-container" :style="'height:' + wh + 'px'">
		<button class="avatar-wrapper" open-type="chooseAvatar" @chooseavatar="onChooseAvatar">
			<image class="avatar" :src="avatarUrl || userinfo.avatarUrl || defaultAvatarUrl"></image>
		</button>
		<view class="nickname">
			<text>昵称</text>
			<input id="nickname-input" type="nickname" class="weui-input" v-model="nickname" @blur="getnickname"
				placeholder="请输入昵称" />
		</view>
		<button class="submit" @click="submit">提交</button>
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
				wh: null,
				avatarUrl: '',
				defaultAvatarUrl: 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0',
				// defaultAvatarUrl: 'https://wx3.sinaimg.cn/mw2000/007eYxgSgy1hastp303ywj31401e0dlo.jpg',
				nickname: '',
			};
		},

		computed: {
			...mapState('m_user', ['userinfo']),
		},

		methods: {
			...mapMutations('m_user', ['updateUserInfo']),

			onChooseAvatar(e) {
				this.avatarUrl = e.detail.avatarUrl
				console.log("头像", this.avatarUrl);

			},

			getnickname(e) {
				uni.createSelectorQuery().in(this) // 注意这里要加上 in(this)  
					.select("#nickname-input")
					.fields({
						properties: ["value"],
					})
					.exec((res) => {
						const nickName = res?. [0]?.value
						console.log('昵称', nickName)
						this.nickname = nickName
					})
			},

			async submit() {
				const userInfo = {
					avatarUrl: this.avatarUrl,
					city: "",
					country: "",
					gender: 0,
					is_demote: true,
					language: "",
					nickName: this.nickname,
					province: "",
				}

				// const query = {
				// 	username: this.nickname,
				// 	avater: this.avatarUrl
				// }

				const {
					data: res
				} = await uni.$http.put('/admin/wechat/updateInfo?username=' + this.nickname + '&avatar=' + this
					.avatarUrl)
				console.log(res);

				if (res.code !== 200) return uni.$showMsg()


				this.updateUserInfo(userInfo)

				uni.navigateBack({
					delta: 1
				});
			}
		},

		onLoad(loading) {
			this.avatarUrl = this.userinfo.avatarUrl
			this.nickname = this.userinfo.nickName

			// 获取当前系统的信息
			const sysInfo = uni.getSystemInfoSync()
			// 为 wh 窗口可用高度动态赋值 - 自定义的search组件高度
			this.wh = sysInfo.windowHeight
		}
	}
</script>

<style lang="scss">
	.personalInformation-container {
		display: flex;
		flex-direction: column;
		align-items: center;
		background-color: #ffffff;

		.avatar-wrapper {
			margin: 100rpx 0;
			padding: 0;
			width: 100rpx;
			height: 100rpx;
			border-radius: 10rpx;

			.avatar {
				width: 100rpx;
				height: 100rpx;
			}
		}

		.nickname {
			display: flex;
			align-items: center;
			width: 100%;
			height: 100rpx;
			border: 1px solid #f5f5f5;
			border: 1px 0px;

			text {
				margin: 0 150rpx 0 40rpx;
				font-weight: 700;
				color: #000;
			}

			.weui-input {}
		}

		.submit {
			position: fixed;
			bottom: 80rpx;

			width: 400rpx;
			bordr: 0;
			background-color: #f9f9f9;
		}
	}
</style>
