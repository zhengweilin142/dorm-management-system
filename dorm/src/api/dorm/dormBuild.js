import request from '@/utils/request'

// 查询宿舍楼列表
export function listBuild(query) {
  return request({
    url: '/dormBuild/list',
    method: 'get',
    params: query
  })
}

// 查询宿舍楼列表
export function listAllBuild(query) {
  return request({
    url: '/dormBuild/listAll',
    method: 'get',
    params: query
  })
}

// 查询宿舍楼详细
export function getBuild(id) {
  return request({
    url: '/dormBuild/' + id,
    method: 'get'
  })
}

// 新增宿舍楼
export function addBuild(data) {
  return request({
    url: '/dormBuild',
    method: 'post',
    data: data
  })
}

// 修改宿舍楼
export function updateBuild(data) {
  return request({
    url: '/dormBuild',
    method: 'put',
    data: data
  })
}

// 删除宿舍楼
export function delBuild(id) {
  return request({
    url: '/dormBuild/' + id,
    method: 'delete'
  })
}
