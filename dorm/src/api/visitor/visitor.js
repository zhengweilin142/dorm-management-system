import request from '@/utils/request'

// 查询到访列表
export function listVisitor(query) {
  return request({
    url: '/visitor/list',
    method: 'get',
    params: query
  })
}

// 查询到访详细
export function getVisitor(id) {
  return request({
    url: '/visitor/' + id,
    method: 'get'
  })
}

// 新增到访
export function addVisitor(data) {
  return request({
    url: '/visitor',
    method: 'post',
    data: data
  })
}

// 修改到访
export function updateVisitor(data) {
  return request({
    url: '/visitor',
    method: 'put',
    data: data
  })
}

// 删除到访
export function delVisitor(id) {
  return request({
    url: '/visitor/' + id,
    method: 'delete'
  })
}
