import request from '@/utils/request'

// 查询回款认领列表
export function listHkrl(query) {
  return request({
    url: '/zjzy/hk/list',
    method: 'get',
    params: query
  })
}

// 查询回款认领详细
export function getHkrl(hkId) {
  return request({
    url: '/zjzy/hk/' + hkId,
    method: 'get'
  })
}

// 新增回款
export function addHk(data) {
  return request({
    url: '/zjzy/hk',
    method: 'post',
    data: data
  })
}

// 新增回款认领
export function addHkrl(data) {
  return request({
    url: '/zjzy/hkrl',
    method: 'post',
    data: data
  })
}

// 修改回款认领
export function updateHkrl(data) {
  return request({
    url: '/zjzy/hk',
    method: 'put',
    data: data
  })
}

// 删除回款认领
export function delHkrl(hkId) {
  return request({
    url: '/zjzy/hk/' + hkId,
    method: 'delete'
  })
}

// 查询回款认领合同编号列表
export function getHkrlHtbh(query) {
  return request({
    url: '/zjzy/hkrl/htbh/list',
    method: 'get',
    params: query
  })
}

// 查询回款认领详情列表
export function listHkrlDetail(query) {
  return request({
    url: '/zjzy/hkrl/list',
    method: 'get',
    params: query
  })
}



