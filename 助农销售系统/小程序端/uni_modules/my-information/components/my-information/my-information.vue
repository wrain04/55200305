<template>
	<view class="my-information-container">
		<view class="my-information-box" @click="informationBoxHandler">
			<view class="message-item">
				<view class="message-top">
					<view class="message-author">
						{{ informationAuthor }}
					</view>
					<view class="message-time">
						{{ createTime }}
					</view>
				</view>
				<view class="message-bottom">
					<!-- 资讯封面 -->
					<view class="message-cover">
						<image class="bg" :src=cover>
						</image>
					</view>
					<view class="message-details">
						<view class="message-title">
							{{ informationTitle }}
						</view>
						<view class="message-viewsNumber">
							访问人数：{{ viewNumber }}
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>


<script>
	export default {
		//自定义组件的名称
		name: "my-information",
		props: {
			// 资讯ID
			informationsId: {
				type: Number,
				default: 0
			},
			// 资讯封面
			cover: {
				type: String,
				// default: 'https://img2.baidu.com/it/u=3408875335,3729181734&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=333'
			},
			// 资讯发布时间
			createTime: {
				type: String,
				default: ''
			},
			// 资讯标题
			informationTitle: {
				type: String,
				default: ''
			},
			// 资讯作者
			informationAuthor: {
				type: String,
				default: ''
			},
			// 访问人数
			viewNumber: {
				type: Number,
				default: 0
			}
		},

		data() {
			return {
				loading: true,
				dataList: [],
			};
		},

		methods: {
			informationBoxHandler() {
				this.$emit('informationClick', this.informationsId)
			},
			async getInformation() {
				const res = await uni.$http.get('/admin/informationApi/info/all')
				if (res.data.code !== 200)
					return uni.$showMsg()
				this.dataList = res.data.data
				console.log("资料", res.data.data);
			}
		},
		mounted() {
			this.getInformation()
		}
	}
</script>


<style lang="scss">
	.message-item {
		height: 200px;
		margin-bottom: 10px;
		border-radius: 10px;
		padding: 0 10rpx 0 10rpx;
		background-color: #fff;

		.message-top {
			display: flex;
			justify-content: space-between;
			align-items: center;
			height: 35px;
			border-bottom: 2px solid #f5f5f5;

			.message-author {
				font-size: 18px;
			}

			.message-time {
				font-size: 14px;
			}
		}

		.message-bottom {
			position: relative;
			height: 140px;

			.message-cover {
				width: 100%;
				height: 110px;

				.bg {
					width: 100%;
					height: 100%;
				}
			}

			.message-details {
				// display: flex;
				flex-direction: column;
				// justify-content: center;

				position: absolute;
				bottom: -16px;
				left: 0;
				width: 100%;


				.message-title {
					width: 100%;
					// 在同一行显示并给省略号
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
				}

				.message-viewsNumber {
					font-size: 14px;
				}
			}
		}
	}
</style>
