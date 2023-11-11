<template>
	<view class="">
		<!-- 选择收货地址的盒子 -->
		<!-- 这里为什么要使用 JSON 转换为字符串再比较，是因为 {} 与 {} 对比的是内存地址，是不会相等的，所以使用字符串对比 -->
		<view class="address-choose-box" v-if="JSON.stringify(address) === '{}'">
			<button type="primary" size="mini" class="btnChooseAddress" @click="chooseAddress">请选择收货地址+</button>
		</view>

		<!-- 渲染收货信息的盒子 -->
		<view class="address-info-box" v-else @click="chooseAddress">
			<view class="row1">
				<view class="row1-left">
					<view class="username">
						收货人：<text>{{ address.userName }}</text>
					</view>
				</view>
				<view class="row1-right">
					<view class="phone">
						电话：<text>{{ address.telNumber }}</text>
					</view>
					<uni-icons type="right" size="16"></uni-icons>
				</view>
			</view>
			<view class="row2">
				<view class="row2-left">
					收货地址：
				</view>
				<view class="row2-right">
					{{ addstr }}
				</view>
			</view>
		</view>

		<!-- 底部的边框线 -->
		<image src="/static/cart_border@2x.png" class="address-border"></image>
	</view>
</template>

<script>
	// 1. 按需导入 mapState 和 mapMutations 这两个辅助函数
	import {
		mapState,
		mapMutations,
		mapGetters
	} from 'vuex'

	export default {
		data() {
			return {
				// 收货地址
				// 2.1 注释掉下面的 address 对象，使用 2.2 中的代码替代之
				// address: {},
			}
		},

		computed: {
			// 2.2 把 m_user 模块中的 address 对象映射当前组件中使用，代替 data 中 address 对象
			...mapState('m_user', ['address']),
			// 将 m_user 模块中的 addstr 映射到当前组件中使用
			...mapGetters('m_user', ['addstr']),

			// 收货详细地址的计算属性
			// addstr() {
			// 	if (!this.address.provinceName) return ''
			// 	// 拼接 省，市，区，详细地址 的字符串并返回给用户
			// 	return this.address.provinceName + this.address.cityName + this.address.countyName + this.address.detailInfo
			// }
		},

		methods: {
			// 3.1 把 m_user 模块中的 updateAddress 函数映射到当前组件
			...mapMutations('m_user', ['updateAddress']),

			// 选择收货地址
			async chooseAddress() {
				// 1. 调用小程序提供的 chooseAddress() 方法，即可使用选择收货地址的功能
				//    返回值是一个数组：第 1 项为错误对象；第 2 项为成功之后的收货地址对象
				//    chooseAddress() 方法返回的是一个 Promise 对象，所以用 async await 来接收
				//    catch(err => err) 是防止失败，进行捕获错误的，并将 err return出去
				const [err, succ] = await uni.chooseAddress().catch(err => err)

				// 2. 用户成功的选择了收货地址
				if (err === null && succ.errMsg === 'chooseAddress:ok') {
					// 为 data 里面的收货地址对象赋值
					// this.address = succ

					// 3.3 调用 Store 中提供的 updateAddress 方法，将 address 保存到 Store 里面
					this.updateAddress(succ)
				}
			}
		}
	}
</script>

<style lang="scss">
	.address-choose-box {
		height: 90px;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.address-info-box {
		display: flex;
		height: 90px;
		padding: 0 5px;
		flex-direction: column;
		justify-content: center;
		font-size: 12px;

		.row1 {
			display: flex;
			justify-content: space-between;

			.row1-right {
				display: flex;
				justify-content: space-between;

				.phone {
					margin-right: 5px;
				}
			}
		}

		.row2 {
			display: flex;
			align-items: center;
			margin-top: 10px;

			.row2-left {
				white-space: nowrap;
			}
		}
	}

	.address-border {
		display: block;
		width: 100%;
		height: 5px;
	}
</style>
