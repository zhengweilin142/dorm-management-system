import request from '@/utils/request'

// 统计学生人数
export function getStuNum() {
  return request({
    url: '/stu/stuNum', method: 'get'
  })
}

// 统计住宿人数
export function selectHaveRoomStuNum() {
  return request({
    url: '/room/selectHaveRoomStuNum', method: 'get'
  })
}

//报修统计
export function getOrderNum() {
  return request({
    url: '/repair/orderNum', method: 'get'
  })
}

export function getNoFullRoom() {
  return request({
    url: '/room/noFullRoom', method: 'get'
  })
}

export function getBuildingNum() {
  return request({
    url: '/getEachBuildingStuNum', method: 'get'
  })
}

export function getHomePageNotice() {
  return request({
    url: '/notice/homePageNotice', method: 'get'
  })
}



