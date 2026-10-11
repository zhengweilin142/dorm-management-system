import request from '@/utils/request'

// 查询申请换宿列表
export function listRoom(query) {
  return request({
    url: '/dormRoom/list',
    method: 'get',
    params: query
  })
}
// 查询申请换宿列表
export function listAllRoom(query) {
  return request({
    url: '/dormRoom/listAll',
    method: 'get',
    params: query
  })
}

// 查询申请换宿详细
export function getRoom(id) {
  return request({
    url: '/dormRoom/' + id,
    method: 'get'
  })
}

// 新增申请换宿
export function addRoom(data) {
  return request({
    url: '/dormRoom',
    method: 'post',
    data: data
  })
}

// 修改申请换宿
export function updateRoom(data) {
  return request({
    url: '/dormRoom',
    method: 'put',
    data: data
  })
}

// 删除申请换宿
export function delRoom(id) {
  return request({
    url: '/dormRoom/' + id,
    method: 'delete'
  })
}
