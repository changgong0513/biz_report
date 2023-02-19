import request from '@/utils/request'

// 查询付款列表
export function listFk(query) {
  return request({
    url: '/zjzy/fk/list',
    method: 'get',
    params: query
  })
}

// 查询付款详细
export function getFk(fkId) {
  return request({
    url: '/zjzy/fk/' + fkId,
    method: 'get'
  })
}

// 新增付款
export function addFk(data) {
  return request({
    url: '/zjzy/fk',
    method: 'post',
    data: data
  })
}

// 修改付款
export function updateFk(data) {
  return request({
    url: '/zjzy/fk',
    method: 'put',
    data: data
  })
}

// 删除付款
export function delFk(fkId) {
  return request({
    url: '/zjzy/fk/' + fkId,
    method: 'delete'
  })
}

// 从钉钉同步付款数据
export function fkSync(query) {
  return request({
    url: '/contract/mgr/fk/sync',
    method: 'post'
  })
}

// 新增付款认领
export function addFkrl(data) {
  return request({
    url: '/zjzy/fkrl',
    method: 'post',
    data: data
  })
}

// 查询回款认领详情列表
export function listFkrlDetailList(query) {
  return request({
    url: '/zjzy/fkrl/list',
    method: 'get',
    params: query
  })
}

// 取得付款总金额
export function getFkrlTotal() {
  return request({
    url: '/zjzy/fk/total',
    method: 'get'
  })
}

// 查询占用统计列表
export function listZytj(query) {
  return request({
    url: '/zjzy/fk/zytj/list',
    method: 'get',
    params: query
  })
}


