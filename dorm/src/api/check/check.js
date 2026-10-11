import request from '@/utils/request'

// 查询宿舍卫生检查，记录每次宿舍的卫生检查情况列表
export function listCheck(query) {
  return request({
    url: '/dormCheck/list',
    method: 'get',
    params: query
  })
}

// 查询宿舍卫生检查，记录每次宿舍的卫生检查情况详细
export function getCheck(checkId) {
  return request({
    url: '/dormCheck/' + checkId,
    method: 'get'
  })
}

// 新增宿舍卫生检查，记录每次宿舍的卫生检查情况
export function addCheck(data) {
  return request({
    url: '/dormCheck',
    method: 'post',
    data: data
  })
}

// 修改宿舍卫生检查，记录每次宿舍的卫生检查情况
export function updateCheck(data) {
  return request({
    url: '/dormCheck',
    method: 'put',
    data: data
  })
}

// 删除宿舍卫生检查，记录每次宿舍的卫生检查情况
export function delCheck(checkId) {
  return request({
    url: '/dormCheck/' + checkId,
    method: 'delete'
  })
}
