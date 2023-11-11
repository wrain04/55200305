<template>
	<view>
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				merchantGoodsId: null,
			};
		},
		
		methods: {
			
		},
		
		onLoad(loading) {
			this.merchantGoodsId = loading.merchantGoodsId
			console.log(this.merchantGoodsId);
		}
	}
</script>

<style lang="scss">
	
</style>
