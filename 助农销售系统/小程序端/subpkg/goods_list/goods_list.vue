<template>
	<view class="goodsList-container">
		<view class="goodsList-grade" @click="showChooseGrade">
			{{grade}}等品
		</view>
		<view class="gradeChoose" v-if="showGrade">
			<view v-for="index in gradeNumber" @click="chooseGrade(index + 1)" class="grade-item">
				{{index+1}}等品
			</view>
		</view>
		<view class="goods-list">
			<view v-for="(item, index) in goodsList" :key="index" @click="gotoDetail(item)">
				<my-goods :goods="item" :gradePrice="gradePrice"></my-goods>
			</view>
		</view>
	</view>
</template>

<script>
	import qqmapsdk from "@/static/qqmap-wx-jssdk.js"

	const QQMapWX = new qqmapsdk({
		key: 'NA7BZ-37C3I-MOUGX-UVC3V-C3Z47-CMFVP'
	});
	export default {
		data() {
			return {
				// 请求参数对象，该参数是要发送给服务器的
				// queryObj: {
				// 	// 查询关键词
				// 	// query: '',
				// 	// 商品分类Id
				// 	gradeId: 1586993961175216130,
				// 	// 页码值
				// 	current: 1,
				// 	// 每页显示多少条数据
				// 	size: 10
				// },
				// 页码值
				current: 1,
				// 每页显示多少条数据
				size: 10,
				// 种类id
				categoryId: 0,
				// 存放等级信息数据的列表
				gradeList: [],
				// 商品列表的数据
				goodsList: [],
				// 总数量，用来实现分页
				total: 0,
				// 是否正在请求数据
				isloading: false,
				// 等级
				grade: 1,
				// 等级数量
				gradeNumber: 3,
				// 等级价格
				gradePrice: 0,
				// 等级是否显示
				showGrade: false,
				UserLocation: {},
			};
		},
		methods: {
			// 获取品级数据信息的方法
			async getGradeList() {
				const {
					data: res
				} = await uni.$http.get('/goods/gradeApi/getByCategoryId/' + this.categoryId)
				console.log(1234);
				console.log("获得品级", res);
				this.gradeList = res.data
				this.gradeNumber = res.data.length
				this.gradePrice = res.data[0].price
				if (res.code !== 200) return uni.$showMsg()
			},

			// 获取商品列表数据的方法
			async getGoodsList(cb) {
				// ** 打开节流阀
				this.isloading = true
				// console.log('获得商品列表', this.gradeList[this.grade - 1].gradeId);
				const {
					data: res
				} = await uni.$http.get('/goods/goodApi/category?gradeId=' + this.gradeList[this.grade - 1].gradeId +
					'&current=' + this.current + '&size=' + this.size)

				console.log('商品列表', res);

				for (var i = 0; i < res.data.data.length; i++) {
					if (res.data.data[i].businessAddress.indexOf('@') != -1 &&
						res.data.data[i].businessAddress.indexOf(',') != -1) {
						let distance = this.changeWidth(res.data.data[i].businessAddress.split('@')[1].split(',')[0],
							res.data.data[i].businessAddress.split('@')[1].split(',')[1])
						if (distance === undefined) {
							distance = '暂时无法获取商户位置'
						}
						res.data.data[i].distance = distance;
					}

				}

				// ** 关闭节流阀
				this.isloading = false

				// 只要数据请求完毕，就立即按需调用 cb 回调函数，取消下拉刷新功能
				cb && cb()

				if (res.code !== 200) return uni.$showMsg()
				console.log(res.data.data, "res.data.data");
				// 为数据赋值：通过展开运算符的形式，进行新旧数据的拼接
				this.goodsList = [...this.goodsList, ...res.data.data]


				this.total = res.data.total
			},

			// 点击跳转到商品详情页面
			gotoDetail(item) {
				uni.navigateTo({
					url: '/subpkg/goods_detail/goods_detail?goods_id=' + item.goodsId + '&goods_price=' +
						this.gradePrice
				})
			},

			// 显示选择等级
			showChooseGrade() {
				this.showGrade = !this.showGrade
			},

			// 选择等级
			chooseGrade(index) {
				this.goodsList = []
				this.grade = index
				this.gradePrice = this.gradeList[index - 1].price
				console.log(this.gradeList[index - 1].gradeId);
				this.getGoodsList()
				this.current = 1
				this.showGrade = false
			},

			getUserLocation() {
				//获取当前地理位置
				let that = this
				uni.getLocation({
					type: 'wgs84',
					geocode: true,
					success: (res) => {
						that.UserLocation.latitude = res.latitude
						that.UserLocation.longitude = res.longitude
						console.log('地理位置', that.UserLocation);
					},
					fail: (err) => {
						uni.showToast({
							title: '无法获取当前位置!',
							icon: 'error',
							duration: 1500
						});
					}
				})
			},
			rad(d) {
				return d * Math.PI / 180.0;
			},
			//计算距离
			changeWidth(lat, lng) {
				let _this = this
				let hwidth = ''
				var radLat1 = this.rad(lat);
				var radLat2 = this.rad(this.UserLocation.latitude);
				var a = radLat1 - radLat2;
				var b = this.rad(lng) - this.rad(this.UserLocation.longitude);
				var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
					Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
				s = s * 6378.137; // EARTH_RADIUS;
				s = Math.round(s * 10000) / 10000; //输出为公里

				var distance = s;
				var distance_str = "";

				if (parseInt(distance) >= 1) {
					distance_str = distance.toFixed(1) + "km";
				} else {
					distance_str = distance * 1000 + "m";
				}
				return distance_str

			},

		},

		onLoad(options) {
			// this.queryObj.query = options.query || ''
			this.categoryId = options.categoryId || ''
			console.log(options.categoryId);

			// 调用获取等级列表数据的方法
			this.getGradeList()
			this.getUserLocation()
			const that = this
			var timer = setInterval(function() { //开启定时器，下面就是执行的方法，在窗体加载1S后执行下面的方法
				// 调用获取商品列表数据的方法
				that.getGoodsList()
				clearTimeout(timer); //关闭定时器。               
			}, 1000); //这里的1000代表1秒之后执行方法
		},

		// 上拉触底
		onReachBottom() {
			// 判断是否还有下一页数据
			if (this.current * this.size >= this.total) return uni.$showMsg('已加载所有商品！')

			// 判断是否正在请求其它数据，如果是，则不发起额外的请求
			if (this.isloading) return

			// 让页码值自增 +1
			this.current += 1
			// 重新获取列表数据
			this.getGoodsList()
		},

		// 下拉刷新
		onPullDownRefresh() {
			this.current = 1
			this.total = 0
			this.isloading = false
			this.goodsList = []

			this.getGoodsList(() => uni.stopPullDownRefresh())
		},
	}
</script>

<style lang="scss">
	.goodsList-container {

		.goodsList-grade {
			position: fixed;
			top: 30px;
			right: 10px;

			width: 60px;
			height: 60px;
			border-radius: 100%;

			background-color: rgba(192, 0, 0, 0.6);
			color: #fff;
			text-align: center;
			line-height: 60px;
		}

		.gradeChoose {
			position: fixed;
			top: 50%;
			left: 50%;

			width: 400rpx;
			border-radius: 10px;

			transform: translate(-50%, -50%);
			background-color: #f5f5f5;

			.grade-item {
				width: 100%;
				height: 120rpx;
				text-align: center;
				line-height: 120rpx;
				border-bottom: 1px solid #fff;
			}
		}

		.goods-list {

			background-color: #fff;
		}
	}
</style>
