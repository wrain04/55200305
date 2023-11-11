export default {
  // 开启命名空间
  namespaced: true,

  // state 数据
  state: () => ({
	  // 3. 读取本地的收货地址数据，初始化 address 对象
	  address: JSON.parse(uni.getStorageSync('address') || '{}') ,
	  
	  // 登录成功之后的 token 字符串
	  token: uni.getStorageSync('token') || '',
	  
	  // 登录成功之后的 refreshToken 字符串
	  refreshToken: uni.getStorageSync('refreshToken') || '',
	  
	  // 登录成功之后的用户id
	  userLoginInfo: uni.getStorageSync('userLoginInfo') || '',
	  
	  // 用户的基本信息
	  userinfo: JSON.parse(uni.getStorageSync('userinfo') || '{}'),
	  
	  // 重定向的 Object 对象 { openType, from }
	  redirectInfo: null
  }),

  // 方法
  mutations: {
	  updateAddress(state, address) {
		  state.address = address
		  
		  // 2. 通过 this.commit() 方法，调用 m_user 模块下的 saveAddressToStorage 方法将 address 对象持久化存储到本地
		  this.commit('m_user/saveAddressToStorage')
	  },
	  
	  // 1. 定义将 address 持久化存储到本地 mutations 方法
	  saveAddressToStorage(state) {
		  uni.setStorageSync('address', JSON.stringify(state.address))
	  },
	  
	  // 更新用户的基本信息
	  updateUserInfo(state, userinfo) {
		  state.userinfo = userinfo
		  
		  this.commit('m_user/saveUserInfoToStorage')
	  },
	  
	  // 将 userinfo 持久化存储到本地
	  saveUserInfoToStorage(state) {
		  uni.setStorageSync('userinfo', JSON.stringify(state.userinfo))
	  },
	  
	  // 更新 token 字符串
	  updateToken(state, token) {
		  state.token = token
		  
		  this.commit('m_user/saveTokenToStorage')
	  },
	  
	  // 将 token 字符串持久化存储到本地
	  saveTokenToStorage(state) {
		  uni.setStorageSync('token', state.token)
	  },
	  
	  // 更新 refreshToken 字符串
	  updataRefreshToken(state, refreshToken) {
	  		  state.refreshToken = refreshToken
	  		  
	  		  this.commit('m_user/saveRefreshTokenToStorage')
	  },
	  
	  // 将 refreshToken 字符串持久化存储到本地
	  saveRefreshTokenToStorage(state) {
	  		  uni.setStorageSync('refreshToken', state.refreshToken)
	  },
	  
	  // 更新用户登录后的信息
	  updateUserLoginInfo(state, userLoginInfo) {
		  state.userLoginInfo = userLoginInfo
		  this.commit('m_user/saveUserLoginInfoToStorage')
	  },
	  
	  // 将用户id持久化存储到本地
	  saveUserLoginInfoToStorage(state) {
		  uni.setStorageSync('userLoginInfo', state.userLoginInfo)
	  },
	  
	  // 更新重定向的信息对象
	  updateRedirectInfo(state, info) {
	    state.redirectInfo = info
	  }
  },

  // 数据包装器
  getters: {
	  // 收货详细地址的计算属性
	  addstr(state) {
		  if(!state.address.provinceName) return ''
		  
		  // 拼接 省，市，区，详细地址 的字符串并返回给用户
		  return state.address.provinceName + state.address.cityName + state.address.countyName + state.address.detailInfo
	  },
	  
	  
  }
}