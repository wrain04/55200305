import request from '@/router/axios'

export function fetchList(query){
  return request({
    url: '/admin/informationApi/info/all',
    methods: 'get',
    params:query
  })
}
