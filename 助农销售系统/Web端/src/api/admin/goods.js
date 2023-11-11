import request from '@/router/axios'

export function fetchList(params) {
  return request({
    url: '/goods/goodApi/page',
    method: 'post',
    params: params
  })
}

export function addObj(obj) {
  return request({
    url: '/demo/goods',
    method: 'post',
    data: obj
  })
}

export function getObj(goodsId) {
  return request({
    url: '/goods/goodApi/' + goodsId,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/goods/goodApi/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/goods/goodApi/updateGoods',
    method: 'put',
    data: obj
  })
}
