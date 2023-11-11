import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/demo/information/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/demo/information',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/demo/information/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/demo/information/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/demo/information',
    method: 'put',
    data: obj
  })
}
