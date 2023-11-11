<template>
	<view class="">
		<view class="search-box">
			<uni-search-bar v-model="searchValue" @input="input" @confirm="search" placeholder="搜索" :radius="100"
				:cancelButton="none" focus></uni-search-bar>
		</view>

		<!-- 搜索建议列表 -->
		<view class="sugg-list" v-if="searchResults.length !== 0">
			<view class="sugg-item" v-for="(item, index) in searchResults" :key="index"
				@click="gotoDetail(item.categoryId)">
				<view class="goods-name">
					{{ item.name }}
				</view>
				<uni-icons type="forward" size="16 "></uni-icons>
			</view>
		</view>

		<!-- 搜索历史 -->
		<view class="history-box" v-else>
			<!-- 标题区 -->
			<view class="history-title">
				<text>搜索历史</text>
				<uni-icons type="trash" size="17" @click="cleanHistory"></uni-icons>
			</view>
			<!-- 列表区 -->
			<view class="history-list">
				<uni-tag @click="gotoGoodsList(item)" :text="item" v-for="(item, index) in histories" :key="index">
				</uni-tag>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 延时器的 timerId
				timer: null,
				// 搜索关键词
				kw: '',
				// 搜索结果列表
				searchResults: [],
				// 搜索关键词的历史记录
				historyList: [],
				// 搜索内容
				searchValue: '',
			};
		},
		methods: {
			input(e) {
				// e 是最新的搜索内容
				console.log(e);
				// 防抖：当我们 input 的时候就触发 500ms 的事件，只有 500ms 过后才会触发事件，如果在这期间 input，则触发 clearTimeout 重新计时
				// 清除 timer 对应的延时器
				clearTimeout(this.timer)
				// 重新启动一个延时器，并把 timerId 赋值给 this.timer
				this.timer = setTimeout(() => {
					// 如果 500 毫秒内，没有触发新的输入事件，则为搜索关键词赋值
					this.kw = e

					// 触发获取搜索结果列表数据
					this.getSearchResults()
				}, 500)
			},

			// 获取搜索结果列表数据方法
			async getSearchResults() {
				if (this.kw === '') {
					this.searchResults = []
					return
				}

				const {
					data: res
				} = await uni.$http.get('/goods/categoryApi/search?categoryName=' + this.kw)
				console.log(res);

				if (res.code !== 200) return uni.$showMsg()

				this.searchResults = res.data

				this.saveSearchHistory()
			},

			// 保存历史记录
			saveSearchHistory() {
				// this.historyList.push(this.kw) 

				// 1. 将 Array 数组转化为 Set 对象
				const set = new Set(this.historyList)
				// 2. 调用 Set 对象的 delete 方法，移除对应的元素
				set.delete(this.kw)
				// 3. 调用 Set 对象的 add 方法，向 Set 中添加元素
				set.add(this.kw)
				// 4. 将 Set 对象转化为 Array 数组
				this.historyList = Array.from(set)

				// 调用 uni.setStorageSync(key, value) 将搜索历史记录持久化存储到本地
				// JSON.stringify 是将括号里面的 "字符串" 转为 "JSON 对象" 
				uni.setStorageSync('kw', JSON.stringify(this.historyList))
			},

			// 跳转到商品详情页面
			gotoDetail(category_Id) {
				uni.navigateTo({
					url: '/subpkg/goods_list/goods_list?categoryId=' + category_Id
				})
			},

			// 清空历史记录
			cleanHistory() {
				this.historyList = []
				// 清空本地存储中记录的搜索历史（就是将本地的 kw，改为 [] 空数组）
				uni.setStorageSync('kw', '[]')
			},

			// 点击跳转到商品列表页面
			gotoGoodsList(kw) {
				// uni.navigateTo({
				// 	url: '/subpkg/goods_list/goods_list?query=' + kw
				// })
				this.searchValue = kw
			}
		},
		computed: {
			histories() {
				// 注意：由于数组是引用类型，所以不要直接基于原数组调用 reverse 方法，以免修改原数组中元素的顺序
				// 而是应该新建一个内存无关的数组，再进行 reverse 反转
				// 不直接 this.historyList.reverse() 是因为这个方法会直接改变原数组
				return [...this.historyList].reverse()
			}
		},
		onLoad() {
			// 这个 kw 的值可能不存在，所以要或一个空数组，这个 kw 不是 data 里面的那个，而是 setStorageSync 里的
			// JSON.parse 是将括号里面的 "JSON 对象" 转为 "字符串" 
			// 记住渲染的数据与存储的数据是相反的
			this.historyList = JSON.parse(uni.getStorageSync('kw') || '[]')

		}
	}
</script>

<style lang="scss">
	.search-box {
		position: sticky;
		top: 0;
		z-index: 999;
		background-color: #C00000;

		.uni-searchbar__box {
			justify-content: flex-start;
		}
	}

	.sugg-list {
		.sugg-item {
			display: flex;
			justify-content: space-between;
			align-items: center;
			height: 50px;
			margin: 0 10px;
			border-bottom: 2px solid #efefef;
			font-size: 12px;

			.goods-name {
				// 文字不允许换行（单行文本）
				white-space: nowrap;
				// 溢出部分隐藏
				overflow: hidden;
				// 文本溢出后，使用 ... 代替
				text-overflow: ellipsis;
				margin-right: 3px;
			}
		}
	}

	.history-box {
		padding: 0 5px;

		.history-title {
			display: flex;
			justify-content: space-between;
			align-items: center;
			height: 40px;
			font-size: 13px;
			border-bottom: 1px solid #efefef;
		}

		.history-list {
			display: flex;
			flex-wrap: wrap;
			margin-top: 6px;

			.uni-tag {
				margin-top: 5px;
				margin-right: 5px;
				background-color: #c7c7c7;
				border-color: #c7c7c7;
			}
		}
	}
</style>
