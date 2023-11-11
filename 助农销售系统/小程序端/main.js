// #ifndef VUE3
import Vue from 'vue'
import App from './App'

// 按需导入 $http 对象
import {
	$http
} from '@escook/request-miniprogram'

// 导入 json-bigint
import JSONbig from 'json-bigint'

// 1. 导入 store 的实例对象
import store from './store/store.js'

// 全局定义七牛云
// import $mUploadImage from '@/config/uploadImage'
// Vue.prototype.$mUploadImage = $mUploadImage;

// import uViex from 'uview-ui'
// Vue.use(uViex)



// 将导入的 $http 挂载到 wx 顶级对象之上，方便全局调用
// wx.$http = $http
// 但是我们现在是 uni-app 项目，应该挂载到 uni 顶级对象上
uni.$http = $http

// 配置请求根路径
// $http.baseUrl = 'https://f396010651.goho.co'
// $http.baseUrl = 'http://8.134.170.84:9999'
$http.baseUrl = 'http://localhost:9999'

$http.beforeRequest = function(options) { // 在发起请求之前做的事情
	uni.showLoading({
		title: '数据加载中...',
		mask: true
	})

	// 判断请求的是否为有权限的 API 接口
	if (options.url.indexOf('/my/') !== -1) {
		// 为请求头添加身份认证字段
		options.header = {
			// 字段的值可以直接从 vuex 中进行获取
			Authorization: 'Bearer ' + store.state.m_user.token,
		}
	}
	console.log(options.url);

	// 判断请求的是否为有权限的 API 接口
	if (options.url.indexOf('/getBusinessGetInfo') !== -1) {
		// 为请求头添加身份认证字段
		options.header = {
			// 字段的值可以直接从 vuex 中进行获取
			Authorization: 'Bearer ' + store.state.m_user.token,
		}
	}

	// 判断请求的是否为有权限的 API 接口
	if (options.url.indexOf('/admin/') !== -1) {
		// 为请求头添加身份认证字段
		options.header = {
			// 字段的值可以直接从 vuex 中进行获取
			Authorization: 'Bearer ' + store.state.m_user.token,
		}
	}

	// 判断请求的是否为有权限的 API 接口
	if (options.url.indexOf('/goods/') !== -1) {
		// 为请求头添加身份认证字段
		options.header = {
			// 字段的值可以直接从 vuex 中进行获取
			Authorization: 'Bearer ' + store.state.m_user.token,
		}
	}

	// 判断请求的是否为有权限的 API 接口
	if (options.url.indexOf('/orders/') !== -1) {
		// 为请求头添加身份认证字段
		options.header = {
			// 字段的值可以直接从 vuex 中进行获取
			Authorization: 'Bearer ' + store.state.m_user.token,
		}
	}


	if (options.url.indexOf('/auth/oauth2/token') !== -1 && options.data.wx_code) {
		options.header = {
			Authorization: 'Basic cHJvZHVjdDpwcm9kdWN0',
			'content-type': 'application/x-www-form-urlencoded;charset=utf-8'
		}
	}

	if (options.url.indexOf('/auth/oauth2/token') !== -1 && options.data.refresh_token) {
		options.header = {
			Authorization: 'Basic cHJvZHVjdDpwcm9kdWN0',
			'content-type': 'application/x-www-form-urlencoded;charset=utf-8'
		}
	}
}
$http.afterRequest = function(options) { // 在结束请求之后做的事情
	uni.hideLoading()
}



// 封装弹框的方法，当返回错误信息时，调用此方法
uni.$showMsg = function(title = '数据加载失败！！！', duration =
	2000) { // 这里为什么要定义 title与duration，是作为默认值，如果没有传参则使用默认值，有传参则使用传过来的参数
	uni.showToast({
		title,
		duration,
		icon: 'none'
	})
}

Vue.config.productionTip = false

// ? 不知道什么意思
Vue.prototype.$store = store

App.mpType = 'app'

const app = new Vue({
	...App,
	// 2. 将 store 挂载到 Vue 实例上
	store,
})
app.$mount()
// #endif

// #ifdef VUE3
import {
	createSSRApp
} from 'vue'
import App from './App.vue'
export function createApp() {
	const app = createSSRApp(App)
	return {
		app
	}
}
// #endif
