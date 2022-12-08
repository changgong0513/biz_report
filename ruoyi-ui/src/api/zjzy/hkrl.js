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

// 取得回款总金额
export function getHkrlTotal() {
  return request({
    url: '/zjzy/hk/total',
    method: 'get'
  })
}

// 根据年月分组，取得年月回款总金额
export function getHkTotalByYearMonth() {
  return request({
    url: '/zjzy/hk/total/ym',
    method: 'get'
  })
}



