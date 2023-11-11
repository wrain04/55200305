<template>
	<view class="message-container">
		<view class="messageHead">
			<view class="messageTitle">
				{{dataList.informationTitle}}
			</view>
			<view class="messagebox1">
				<view class="messageAuthor">
					{{dataList.informationAuthor}}
				</view>
				<view class="messageLocation">
					点击量：{{dataList.viewNumber}}
				</view>
			</view>

			<view class="messageTime">
				发布时间：{{dataList.createTime}}
			</view>
		</view>
		<view class="messageBody">
			{{dataList.info}}
		</view>
		<image :src="dataList.cover" class="bg"></image>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				dataList: [],
				loading: true // 加载状态
			};
		},

		methods: {
			async getInformationDetail() {
				this.loading = true
				console.log(this.informationId);
				const res = await uni.$http.get('/admin/informationApi/' + this.informationId)
				if (res.data.code !== 200) {
					return uni.$showMsg()
				}

				this.dataList = res.data.data
				console.log("获取", res);
			}
		},

		onLoad(loading) {
			this.informationId = loading.messageId
			console.log("获取到了吗",
				this.informationId);
		},
		mounted() {
			this.getInformationDetail()
		}
	}
</script>

<style lang="scss">
	.message-container {
		padding: 0 10px;

		.messageHead {
			margin-top: 20px;

			.messageTitle {
				margin-bottom: 10px;
				font-size: 20px;
			}

			.messagebox1 {
				display: flex;
				justify-content: space-between;

				.messageAuthor {
					color: #0F40F5;
				}

				.messageTime {
					margin: 0 10px;
				}
			}

			.messageLocation {}
		}

		.messageBody {}

		.bg {
			height: 150px;
			width: 100%;
		}
	}
</style>
