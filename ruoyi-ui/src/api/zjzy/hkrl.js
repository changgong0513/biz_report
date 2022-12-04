import request from '@/utils/request'

// 查询回款认领列表
export function listHkrl(query) {
  return request({
    url: '/system/hkrl/list',
    method: 'get',
    params: query
  })
}

// 查询回款认领详细
export function getHkrl(hkId) {
  return request({
    url: '/system/hkrl/' + hkId,
    method: 'get'
  })
}

// 新增回款认领
export function addHkrl(data) {
  return request({
    url: '/system/hkrl',
    method: 'post',
    data: data
  })
}

// 修改回款认领
export function updateHkrl(data) {
  return request({
    url: '/system/hkrl',
    method: 'put',
    data: data
  })
}

// 删除回款认领
export function delHkrl(hkId) {
  return request({
    url: '/system/hkrl/' + hkId,
    method: 'delete'
  })
}
