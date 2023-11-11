<template>
	<view class="my-search-container" :style="{'background-color': bgcolor}">
		<view class="my-search-box" :style="{'border-radius': radius + 'px'}" @click="searchBoxHandler">
			<!-- 使用 uni-ui 提供的图标组件 -->
			<uni-icons type="search" size="17"></uni-icons>
			<text class="placeholder">搜索</text>
		</view>
	</view>
</template>


<script>
export default {
	//自定义组件的名称
	name: "my-search",
	
	props: {
		// 背景颜色
		bgcolor: { 
			type: String,
			default: '#C00000'
		},
		// 圆角尺寸
		radius: {
			type: Number,
			default: 18
		}
	},
	
	data() {
		return {
		
		};
	},
	
	methods: {
		searchBoxHandler() {
			this.$emit('myclick')
		}
	}
}
</script>


<style lang="scss">
	.my-search-container {
		height: 48px;
		// background-color: #C00000;
		display: flex;
		align-items: center;
		
		.my-search-box {
			display: flex;
			justify-content: center;
			align-items: center;
			width: 100%;
			height: 36px;
			margin: 0px 12px;
			// border-radius: 18px;
			background-color: #fff;
			
			
			.placeholder {
				font-size: 15px;
				margin-left: 5px;
			}
		}
	}
</style>