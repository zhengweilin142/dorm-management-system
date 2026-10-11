import request from '@/utils/request'

// 查询申请换宿列表
export function listAdjustRoom(query) {
  return request({
    url: '/adjustRoom/list',
    method: 'get',
    params: query
  })
}

// 查询申请换宿详细
export function getAdjustRoom(id) {
  return request({
    url: '/adjustRoom/' + id,
    method: 'get'
  })
}

// 新增申请换宿
export function addAdjustRoom(data) {
  return request({
    url: '/adjustRoom',
    method: 'post',
    data: data
  })
}

// 修改申请换宿
export function updateAdjustRoom(data) {
  return request({
    url: '/adjustRoom',
    method: 'put',
    data: data
  })
}

// 删除申请换宿
export function deladjustRoom(id) {
  return request({
    url: '/adjustRoom/' + id,
    method: 'delete'
  })
}


// 新增申请换宿
export function changeAdjustRoom(data) {
  return request({
    url: '/adjustRoom/change',
    method: 'post',
    data: data
  })
}
