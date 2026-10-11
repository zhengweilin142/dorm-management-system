import request from '@/utils/request'

// 查询保修列表
export function listRepair(query) {
  return request({
    url: '/repair/list',
    method: 'get',
    params: query
  })
}

// 查询保修详细
export function getRepair(id) {
  return request({
    url: '/repair/' + id,
    method: 'get'
  })
}

// 新增保修
export function addRepair(data) {
  return request({
    url: '/repair',
    method: 'post',
    data: data
  })
}

// 修改保修
export function updateRepair(data) {
  return request({
    url: '/repair',
    method: 'put',
    data: data
  })
}

// 删除保修
export function delRepair(id) {
  return request({
    url: '/repair/' + id,
    method: 'delete'
  })
}
