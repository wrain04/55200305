import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/orders/orderApi/ordersPage',
    method: 'get',
    params: query
  })
}

export function test(query){
  return request({
    url:'/goods/businessDetailApi/page',
  method:'get',
  params:query
  })
}

export function addObj(obj) {
  return request({
    url: '/demo/shoppingcar',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/orders/orderApi/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/demo/shoppingcar/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/orders/orderApi',
    method: 'put',
    data: obj
  })
}
