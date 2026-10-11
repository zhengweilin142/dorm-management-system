import request from '@/utils/request'

// 查询用户列表
export function listRoomUser(query) {
  return request({
    url: '/dormRoomUser/list', method: 'get', params: query
  })
}

// 查询用户详细
export function getRoomUser(userId) {
  return request({
    url: '/dormRoomUser/' + userId, method: 'get'
  })
}

// 新增用户
export function addRoomUser(data) {
  return request({
    url: '/dormRoomUser', method: 'post', data: data
  })
}

// 修改用户
export function updateRoomUser(data) {
  return request({
    url: '/dormRoomUser', method: 'put', data: data
  })
}

// 删除用户
export function delRoomUser(userId) {
  return request({
    url: '/dormRoomUser/' + userId, method: 'delete'
  })
}

// 删除用户
export function removeRoomUser(data) {
  return request({
    url: '/dormRoomUser/remove' , method: 'post',data: data
  })
}


