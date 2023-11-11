<template>
	<view class="langManagement-container">
		<view class="land-information">
			<text>选择地块</text>	
			<view class="land-type text">
				<uni-data-select
				    v-model="land.landType"
				    :localdata="range"
					:clear="false"
				></uni-data-select>
			</view>	
			<view class="land-data">
				<text>主要作物</text>	
				<view class="land-stapleProducts text">
					<input type="text" placeholder="输入主要作物" v-model="land.landStapleProducts">
				</view>	
				<text>该地块面积 (单位/平方米)</text>	
				<view class="land-parcelArea text">
					<input type="number" placeholder="输入该地块面积 (单位/平方米)" v-model="land.landParcelArea">
				</view>	
				<text>上月产值</text>	
				<view class="land-parcelNumber text">
					<input type="number" placeholder="输入上月产值 (斤/月)" v-model="land.landParcelNumber">
				</view>	
				<text>地块简介</text>	
				<view class="land-introduce">
					<textarea v-model="land.landIntroduce" id="" cols="30" rows="8" placeholder="输入地块简介"></textarea>
				</view>	
			</view>
		</view>
		
		<!-- 提交 -->
		<view class="submit" @click="submit">
			<text class="submit-text">{{'提 交 审 核'}}</text>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				land: {
					landType: 0,
					landStapleProducts: '',
					landParcelArea: null,
					landParcelNumber: null,
					landIntroduce: '',
				},
				// 下拉选择框内容
				range: [
					{ value: 0, text: "地块1" },
					{ value: 1, text: "地块2" },
					{ value: 2, text: "地块3" },
				],
			};
		},
		
		methods: {
			
		},
		
		onLoad(loading) {
			
		}
	}
</script>

<style lang="scss">
	.langManagement-container {
		position: relative;
		
		.land-information {
			box-sizing: border-box;
			width: 100%;
			padding: 40rpx;
			padding-bottom: 210rpx;
			
			font-size: 14px;
			
			text {
				font-size: 16px;
				color: #333333;
				
			}
			
			.text {
				display: flex;
				align-items: center;
				height: 40rpx;
				margin-top: 10rpx;
				padding: 20rpx;
				border-radius: 10rpx;
				margin-bottom: 40rpx;
				
				background-color: #ffffff;
			}
			
			.land-type {
				
				uni-data-select {
					width: 100%;
					
					.uni-select {
						padding: 0px;
						border: 0px;
						
						.uni-select__input-placeholder {
							font-size: 28rpx;
						}
					}
				}
			}
			
			
			.land-data {
				input {
					width: 100%;
				}
				
				.land-introduce {
					display: flex;
					align-items: center;
					margin-top: 10rpx;
					padding: 20rpx;
					border-radius: 10rpx;
					margin-bottom: 40rpx;
					
					background-color: #ffffff;	
				}
			}
			
		}
		
		// 提交
		.submit {
			position: fixed;
			bottom: 0;
			left: 0;
			
			width: 100%;
			height: 140rpx;
			background-color: #c00000;
			z-index: 999;
			text-align: center;
			
			&::after {
				content: '';
				position: absolute;
				top: -70rpx;
				left: 0;
				width: 100%;
				height: 140rpx;
				border-radius: 100%;
				z-index: -1;
				
				background-color: #c00000;
			}
			
			.submit-text {
				font-size: 60rpx;
				color: #fff;
			}
		}
	}
</style>
