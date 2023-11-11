# 日志



## 1、tabBar

修改项目根路径中的 `pages.json` 配置文件，新增 `tabBar` 配置节点

```json
"tabBar": {
    "selectedColor": "#C00000",
    "list": [
      {
        "pagePath": "pages/home/home",
        "text": "首页",
        "iconPath": "static/tab_icons/home.png",
        "selectedIconPath": "static/tab_icons/home-active.png"
      }
    ]
}
```



## 2、首页

#### 2.1 配置网络请求

由于平台的限制，小程序项目**不支持 axios**，而且原生的 `wx.request()` API 功能较为简单，**不支持拦截器**等全局定制的功能。

因此，建议在 uni-app 项目中使用 `@escook/request-miniprogram` 第三方包发起网络数据请求。

参考官方文档，在项目的 `main.js` 入口文件中，通过如下的方式进行配置：

```js
import { $http } from '@escook/request-miniprogram'

uni.$http = $http
// 配置请求根路径
$http.baseUrl = ''

// 请求开始之前做的一些事情
$http.beforeRequest = function (options) {
    uni.showLoading({
        title: '数据加载中...',
        mask: true
    })
}

// 请求开始之后做的一些事情
$http.afterRequest = function () {
    uni.hideLoading()
}
```



#### 2.2 配置小程序分包

> **分包可以减少小程序首次启动时的加载时间**

在项目中，我们把 tabBar 相关的4个页面放在主包中，其他页面放在分包中。

1.在项目根目录中，创建分包根目录，命名为 `subpkg`

2.在 `pages.json` 中，和 `pages` 节点平级的位置声明 `subPackages` 节点，用来定义分包相关的结构：

```json
{
  "pages": [
    {
      "path": "pages/home/home",
      "style": {}
    },
  ],
  "subPackages": [
    {
      "root": "subpkg",
      "pages": [
          {}
      ]
    }
  ]
}
```



#### 2.3 封装 uni.$showMsg() 方法

当数据请求失败之后，经常需要调用 `uni.showToast({ /* 配置对象 */ })` 方法来提示用户。此时，可以在全局封装一个 `uni.$showMsg()` 方法，来简化 `uni.showToast()` 方法的调用。具体的改造步骤如下：

1. 在 `main.js` 中，为 `uni` 对象挂载自定义的 `$showMsg()` 方法：

```js
// 封装的展示消息提示的方法
uni.$showMsg = function (title = '数据加载失败！', duration = 1500) {  // 括号里面的数据代表默认值
    uni.showToast({
        title,
        duration,
        icon: 'none'
    })
}
```

 2. 使用时，直接 uni.showMsg('登录成功'，1000)

    

#### 2.4 轮播图区域

1. 请求轮播图的数据
2. 渲染轮播图的 UI 结构
3. 点击轮播图跳转到商品详情页面

​	将 `<swiper-item></swiper-item>` 节点内的 `view` 组件，改造为 `navigator` 导航组件，并动态绑定 `url 属性` 的值。



#### 2.5 分类导航区域



#### 2.6 资讯区域



## 3、分类

#### 3.1 渲染分类页面的基本结构

左右两边各有一个 `scroll-view`



#### 3.2 动态计算窗口的剩余高度

```vue
data() {
	return {
		// 窗口的可用高度 = 屏幕高度 - navigationBar高度 - tabBar 高度
		wh: 0
	}
},
onLoad() {
	// 获取当前系统的信息
	const sysInfo = uni.getSystemInfoSync()
	// 为 wh 窗口可用高度动态赋值
	this.wh = sysInfo.windowHeight
}
```



#### 3.3 切换一级分类后重置滚动条的位置

1. 在 data 中定义 `滚动条距离顶部的距离`：

```js
data() {
  return {
    // 滚动条距离顶部的距离
    scrollTop: 0
  }
}
```

2. 动态为右侧的 `<scroll-view>` 组件绑定 `scroll-top` 属性的值：

```
<!-- 右侧的滚动视图区域 -->
<scroll-view class="right-scroll-view" scroll-y :style="{height: wh + 'px'}" :scroll-top="scrollTop"></scroll-view>
```

3. 切换一级分类时，动态设置 `scrollTop` 的值：

```
 // 让 scrollTop 的值在 0 与 1 之间切换
 this.scrollTop = this.scrollTop === 0 ? 1 : 0

 // 可以简化为如下的代码：
 // this.scrollTop = this.scrollTop ? 0 : 1
```





## 4、搜索

#### 4.1 自定义搜索组件

(1) 在项目根目录的 `uni_modules ` 目录上，选择新建插件，创建新插件 `my-search`

(2) 定义 UI 结构并美化样式

(3) 由于自定义的 `my-search` 组件高度为 `50px`，因此，需要重新计算分类页面窗口的可用高度：

```
onLoad() {
  const sysInfo = uni.getSystemInfoSync()
  // 可用高度 = 屏幕高度 - navigationBar高度 - tabBar高度 - 自定义的search组件高度
  this.wh = sysInfo.windowHeight - 50
}
```
(4) 通过自定义属性 `props` 增强组件的通用性

通过 `props` 定义 `bgcolor` 和 `radius` 两个属性，并指定值类型和属性默认值

(5) 实现首页搜索组件的吸顶效果

```
.search-box {
  // 设置定位效果为“吸顶”
  position: sticky;
  // 吸顶的“位置”
  top: 0;
  // 提高层级，防止被轮播图覆盖
  z-index: 999;
}
```



#### 4.2 搜索建议

(1) 渲染搜索页面的基本结构

(2) 实现搜索框自动获取焦点

在 uni-search-bar 中添加 focus 即可实现自动获取焦点

```
<uni-search-bar @input="input" @confirm="search" placeholder="搜索" :radius="100" :cancelButton="none" focus ></uni-search-bar>
```

(3) 实现搜索框的防抖处理

1. 在 data 中定义防抖的延时器 timerId 如下：

```
data() {
  return {
    // 延时器的 timerId
    timer: null,
    // 搜索关键词
    kw: ''
  }
}
```

2. 修改 `input` 事件处理函数如下：

``` 
input(e) {
  // 清除 timer 对应的延时器
  clearTimeout(this.timer)
  // 重新启动一个延时器，并把 timerId 赋值给 this.timer
  this.timer = setTimeout(() => {
    // 如果 500 毫秒内，没有触发新的输入事件，则为搜索关键词赋值
    this.kw = e.value
    console.log(this.kw)
  }, 500)
}
```

(4) 根据搜索关键词查询搜索建议列表

注意判断关键词是否为空，为空要将搜索列表清零



#### 4.3 搜索历史

1. 渲染搜索历史记录的基本结构

2. 实现搜索建议的搜索历史的按需展示

​		当搜索结果列表的长度`等于 0`的时候（`searchResults.length === 0`），需要隐藏搜索建议区域，展示搜索历史区域

​		使用 `v-if` 与 `v-else`

3. 将搜索关键词存入 `historyList`

   当我们向服务器提交申请后，查询到搜索建议之后，调用方法保存搜索关键词

   ```
   saveSearchHistory() {
     // 2.1 直接把搜索关键词 push 到 historyList 数组中
     this.historyList.push(this.kw)
   }
   ```

4. 解决关键词前后顺序的问题

   4.1 data 中的 `historyList` 不做任何修改，依然使用 push 进行**末尾追加**

   4.2 定义一个计算属性 `historys`，将 `historyList` 数组 `reverse` 反转之后，就是此计算属性的值：

   ```
   computed: {
     historys() {
       // 注意：由于数组是引用类型，所以不要直接基于原数组调用 reverse 方法，以免修改原数组中元素的顺序
       // 而是应该新建一个内存无关的数组，再进行 reverse 反转
       return [...this.historyList].reverse()
     }
   }
   ```

   4.3 页面中渲染搜索关键词的时候，不再使用 data 中的 `historyList`，而是使用计算属性 `historys`

5. 解决关键词重复的问题

   (1) 修改方法

   ```
   // 保存搜索关键词为历史记录
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
   }
   ```

   

6. 将搜索历史记录持久化存储到本地

   (1) 修改 `saveSearchHistory` 方法如下：

   ```js
   saveSearchHistory() {
   	const set = new Set(this.historyList)
   	set.delete(this.kw)
   	set.add(this.kw)
   	this.historyList = Array.from(set)
   
   	// 调用 uni.setStorageSync(key, value) 将搜索历史记录持久化存储到本地 
    	// 使用 JSON.stringify() 将搜索历史转为 JSON字符串   
       uni.setStorageSync('kw', JSON.stringify(this.historyList))
   }
   ```

   (2) 在 `onLoad` 生命周期函数中，加载本地存储的搜索历史记录：

   ```
   onLoad() {
    	// 使用 JSON.parse() 将搜索历史转为 JS对象   
   	this.historyList = JSON.parse(uni.getStorageSync('kw') || '[]')
   }
   ```

   

7. 清空搜索历史记录

   ```
     // 清空 data 中保存的搜索历史
     this.historyList = []
     // 清空本地存储中记录的搜索历史
     uni.setStorageSync('kw', '[]')
   ```

   

8. 点击搜索历史跳转到商品列表页面





## 5、商品列表

#### 5.1 定义请求体参数对象

1. 事先定义一个请求参数对象

2. 将页面跳转时携带的参数，转存到 `queryObj` 对象中：

   ```
   onLoad(options) {
   	// 将页面参数转存到 this.queryObj 对象中
   	// 参数在 option 中
   }
   ```

#### 5.2 获取商品列表数据

 	1. 在 data 中新增数据节点存放商品列表数据
 	2. 在 `onLoad` 生命周期函数中，调用方法向服务器传递 `queryObj` 对象，并获取数据

#### 5.3 渲染商品列表结构

​	注意：为了防止某些商品的图片不存在，需要在 data 中定义一个默认的图片

#### 5.4 将商品 item 项封装为自定义组件

​	在 `goods_list` 组件中，循环渲染 `my-goods` 组件即可

#### 5.5 使用过滤器处理价格

 1. 在 `my-goods` 组件中，和 `data` 节点平级，声明 `filters` 过滤器节点如下：

    ```js
    filters: {
      // 把数字处理为带两位小数点的数字
      tofixed(num) {
        return Number(num).toFixed(2)
      }
    }
    ```

2. 在渲染商品价格的时候，通过管道符 `|` 调用过滤器：

   ```
   <!-- 商品价格 -->
   <view class="goods-price">￥{{goods.goods_price | tofixed}}</view>
   ```

#### 5.6 上拉加载更多

1. 打开项目根目录中的 `pages.json` 配置文件，为 `subPackages` 分包中的 `goods_list` 页面配置上拉触底的距离：

   ```js
    "subPackages": [
      {
        "root": "subpkg",
        "pages": [
          {
            "path": "goods_list/goods_list",
            "style": {
              "onReachBottomDistance": 150
            }
        ]
      }
    ]
   ```

在 `goods_list` 页面中，和 `methods` 节点平级，声明 `onReachBottom` 事件处理函数，用来监听页面的上拉触底行为：

```js
// 触底的事件
onReachBottom() {
  // 让页码值自增 +1
  this.queryObj.pagenum += 1
  // 重新获取列表数据
  this.getGoodsList()
}
```

改造 `methods` 中的 `getGoodsList` 函数，当列表数据请求成功之后，进行新旧数据的拼接处理：

```js
// 获取商品列表数据的方法
async getGoodsList() {
  // 发起请求
  const { data: res } = await uni.$http.get('/api/public/v1/goods/search', this.queryObj)
  if (res.meta.status !== 200) return uni.$showMsg()

  // 为数据赋值：通过展开运算符的形式，进行新旧数据的拼接
  this.goodsList = [...this.goodsList, ...res.message.goods]
  this.total = res.message.total
}
```

 2. 通过节流阀防止发起额外请求：

    （1）在 data 中定义 `isloading` 节流阀

    （2）修改 `getGoodsList` 方法，在请求数据前后，分别打开和关闭节流阀：

    （3）在 `onReachBottom` 触底事件处理函数中，根据节流阀的状态，来决定是否发起请求

3. 判断数据是否加载完毕

​		（1）如果下面的公式成立，则证明没有下一页数据了：

```text
当前的页码值 * 每页显示多少条数据 >= 总数条数
pagenum * pagesize >= total
```

​		（2）修改 `onReachBottom` 事件处理函数如下：

```js
// 触底的事件
onReachBottom() {
  // 判断是否还有下一页数据
  if (this.queryObj.pagenum * this.queryObj.pagesize >= this.total) return uni.$showMsg('数据加载完毕！')
  ...
}
```

#### 5.7 下拉刷新

1. 在 `pages.json` 配置文件中，为当前的 `goods_list` 页面单独开启下拉刷新效果：

   ```js
   "subPackages": [{
     "root": "subpkg",
     "pages": [{
       "path": "goods_list/goods_list",
       "style": {
         "onReachBottomDistance": 150,
         "enablePullDownRefresh": true,
         "backgroundColor": "#F8F8F8"
       }]
   }]
   ```

2. 监听页面的 `onPullDownRefresh` 事件处理函数：

   ```js
   // 下拉刷新的事件
   onPullDownRefresh() {
     // 1. 重置关键数据
     this.queryObj.pagenum = 1
     this.total = 0
     this.isloading = false
     this.goodsList = []
   
     // 2. 重新发起请求
     this.getGoodsList(() => uni.stopPullDownRefresh())
   }
   ```

3. 修改 `getGoodsList` 函数，接收 `cb` 回调函数并按需进行调用

   ```js
   // 获取商品列表数据的方法
   async getGoodsList(cb) {
     ...
     // 只要数据请求完毕，就立即按需调用 cb 回调函数
     // 前者表示是否有 cb 回调函数，后者为执行 cb
     cb && cb()
     ...
   }
   ```

​		

## 6、商品详情

#### 6.1 获取商品详情数据

通过在 `onLoad` 中获取商品的 Id，并调用请求商品详情的方法

#### 6.2 渲染商品详情页的 UI 结构

1. 渲染轮播图区域

2. 实现轮播图预览效果

   ```vue
   <swiper-item v-for="(item, i) in goods_info.pics" :key="i">
     <!-- 把当前点击的图片的索引，传递到 preview() 处理函数中 -->
     <image :src="item.pics_big" @click="preview(i)"></image>
   </swiper-item>
   ```

   ```js
   // 实现轮播图的预览效果
   preview(i) {
     // 调用 uni.previewImage() 方法预览图片
     uni.previewImage({
       // 预览时，默认显示图片的索引
       current: i,
       // 所有图片 url 地址的数组，将所有图片的 url 重新组成一个数组
       urls: this.goods_info.pics.map(x => x.pics_big)
     })
   }
   ```

3. 渲染商品信息区域

4. 渲染时间轴区域

5. 解决商品价格闪烁问题

   （1）导致问题的原因：在商品详情数据请求回来之前，data 中 `goods_info` 的值为 `{}`，因此初次渲染页面时，会导致 `商品价格、	商品名称` 等闪烁的问题。

   （2）解决方案：判断 `goods_info.goods_name` 属性的值是否存在，从而使用 `v-if` 指令控制页面的显示与隐藏

#### 6.3 渲染详情页底部的商品导航区域

1. 渲染商品导航区域的 UI 结构

   **基于 uni-ui 提供的** [GoodsNav](https://ext.dcloud.net.cn/plugin?id=865) **组件来实现商品导航区域的效果**

   ```js
   data() {
     return {
       // 商品详情对象
       goods_info: {},
       // 左侧按钮组的配置对象
       options: [{
         icon: 'shop',
         text: '店铺'
       }, {
         icon: 'cart',
         text: '购物车',
         info: 2
       }],
       // 右侧按钮组的配置对象
       buttonGroup: [{
           text: '加入购物车',
           backgroundColor: '#ff0000',
           color: '#fff'
         },
         {
           text: '立即购买',
           backgroundColor: '#ffa200',
           color: '#fff'
         }
       ]
     }
   }
   ```

   ```vue
   <!-- 商品导航组件 -->
   <view class="goods_nav">
     <!-- fill 控制右侧按钮的样式 -->
     <!-- options 左侧按钮的配置项 -->
     <!-- buttonGroup 右侧按钮的配置项 -->
     <!-- click 左侧按钮的点击事件处理函数 -->
     <!-- buttonClick 右侧按钮的点击事件处理函数 -->
     <uni-goods-nav :fill="true" :options="options" :buttonGroup="buttonGroup" @click="onClick" @buttonClick="buttonClick" />
   </view>
   ```

​		

2. 点击跳转到购物车页面

   ```js
   // 左侧按钮的点击事件处理函数
   onClick(e) {
     if (e.content.text === '购物车') {
       // 切换到购物车页面
       uni.switchTab({
         url: '/pages/cart/cart'
       })
     }
   }
   ```

   

## 7、加入购物车

#### 7.1 配置 vuex

1. 在项目根目录中创建 `store` 文件夹，专门用来存放 vuex 相关的模块

2. 在 `store.js` 中按照如下 4 个步骤**初始化 Store 的实例对象**：

   ```js
   // 1. 导入 Vue 和 Vuex
   import Vue from 'vue'
   import Vuex from 'vuex'
   
   // 2. 将 Vuex 安装为 Vue 的插件
   Vue.use(Vuex)
   
   // 3. 创建 Store 的实例对象
   const store = new Vuex.Store({
     // TODO：挂载 store 模块
     modules: {},
   })
   
   // 4. 向外共享 Store 的实例对象
   export default store
   ```

3. 在 `main.js` 中导入 `store` 实例对象并挂载到 Vue 的实例上：

   ```js
   // 1. 导入 store 的实例对象
   import store from './store/store.js'
   
   // 省略其它代码...
   
   const app = new Vue({
     ...App,
     // 2. 将 store 挂载到 Vue 实例上
     store,
   })
   app.$mount()
   ```



#### 7.2 创建购物车的 store 模块

1. 在 `store` 目录上鼠标右键，选择 `新建 -> js文件`，创建购物车的 store 模块，命名为 `cart.js`

2. 在 `cart.js` 中，初始化如下的 vuex 模块：

   ```js
   export default {
     // 为当前模块开启命名空间
     namespaced: true,
   
     // 模块的 state 数据
     state: () => ({
       // 购物车的数组，用来存储购物车中每个商品的信息对象
       // 每个商品的信息对象，都包含如下 6 个属性：
       // { goods_id, goods_name, goods_price, goods_count, goods_small_logo, goods_state }
       cart: [],
     }),
   
     // 模块的 mutations 方法
     mutations: {},
   
     // 模块的 getters 属性
     getters: {},
   }
   ```

3. 在 `store/store.js` 模块中，导入并挂载购物车的 vuex 模块，示例代码如下：

   ```js
   import Vue from 'vue'
   import Vuex from 'vuex'
   // 1. 导入购物车的 vuex 模块
   import moduleCart from './cart.js'
   
   Vue.use(Vuex)
   
   const store = new Vuex.Store({
     // TODO：挂载 store 模块
     modules: {
       // 2. 挂载购物车的 vuex 模块，模块内成员的访问路径被调整为 m_cart，例如：
       //    购物车模块中 cart 数组的访问路径是 m_cart/cart
       m_cart: moduleCart,
     },
   })
   
   export default store
   ```



#### 7.3 在商品详情页中使用 Store 中的数据

1. 在 `goods_detail.vue` 页面中，修改 `<script></script>` 标签中的代码如下：

   ```js
   // 从 vuex 中按需导出 mapState 辅助方法
   import { mapState } from 'vuex'
   
   export default {
     computed: {
       // 调用 mapState 方法，把 m_cart 模块中的 cart 数组映射到当前页面中，作为计算属性来使用
       // ...mapState('模块的名称', ['要映射的数据名称1', '要映射的数据名称2'])
       ...mapState('m_cart', ['cart']),
     },
     // 省略其它代码...
   }
   ```

2. 在页面渲染时，可以直接使用映射过来的数据，例如

   ```js
   <!-- 运费 -->
   <view class="yf">快递：免运费 -- {{cart.length}}</view>
   ```


## Git

创建 ` .gitignore` 忽略文件

```
# 忽略 node_modules 目录
/node_modules
/unpackage/dist
```

初始化本地 Git 仓库

```
git init
```

将所有文件都加入暂存区

```
git add .
```

本地提交更新

```
git commit -m "init project"
```

创建分支

```
git checkout -b “分支名”
```

推送到远程仓库进行保存

```
git push -u origin “分支名”
```

将本地分支合并到本地的 master 分支

```
git checkout master
git merge “分支名”
```

删除本地分支

```
git branch -d “分支名”
```

