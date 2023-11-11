<template>
	<view class="shop-item">
		<!-- 商户左侧图片区域 -->
		<view class="shop-item-left">
			<image :src="shop.images[0] || defaultPic" class="shop-pic"></image>
		</view>
		<!-- 商户右侧信息区域 -->
		<view class="shop-item-right">
			<!-- 商户信息 -->
			<view class="shop-name">
				{{ shop.shopName }}
				<view class="description">
					{{ shop.type }}
				</view>
			</view>
			<view class="shop-info-box">
				<!-- 主要产品 -->
				<view class="shop-stapleProducts">
					{{ shop.stapleProducts }}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {	
		//自定义组件的名称
		name: "my-shop",
		
		props: {
			// 商户的信息对象
			shop: {
				type: Object,
				default: {}
			},
		},
		
		data() {
			return {
			    // 默认的空图片
			    defaultPic: 'https://img3.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png',
			}
		},
		
		methods: {
			
		},
		
		
		filters: {
			
		}
		
	}
</script>

<style lang="scss">
	.shop-item {
		display: flex;
		width: 750rpx;
		box-sizing: border-box;
		padding: 10px 5px;
		border-bottom: 1px solid #f0f0f0;
		
		.shop-item-left {
			margin-right: 5px;
			display: flex;
			justify-content: center;
			align-items: center;
			
			.shop-pic {
				width: 200rpx;
				height: 200rpx;
				display: block;
			}
		}
		.shop-item-right {
			display: flex;
			flex: 1;
			flex-direction: column;
			justify-content: space-between;
			
			.shop-name {
				font-size: 16px;
				.description {
					width: 460rpx;
					font-size: 12px;
					// 在同一行显示并给省略号
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
				}
			}
			
			
			.shop-info-box {
				display: flex;
				justify-content: space-between;
				align-items: center;
				
				.shop-price {
					font-size: 16px;
					color: #c00000;
				}
			}
		}
	}
</style>
