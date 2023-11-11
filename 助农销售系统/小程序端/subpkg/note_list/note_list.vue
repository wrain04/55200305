<template>
	<view>
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				orderId: null,
			};
		},
		
		methods: {
			
		},
		
		onLoad(loading) {
			this.orderId = loading.orderId
			console.log(this.orderId);
		}
	}
</script>

<style lang="scss">
	
</style>
