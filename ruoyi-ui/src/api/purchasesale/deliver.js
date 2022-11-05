import request from '@/utils/request'

// 查询发货管理列表
export function listPurchasesale(query) {
  return request({
    url: '/purchasesale/purchasesale/list',
    method: 'get',
    params: query
  })
}

// 查询发货管理详细
export function getPurchasesale(deliverId) {
  return request({
    url: '/purchasesale/purchasesale/' + deliverId,
    method: 'get'
  })
}

// 新增发货管理
export function addPurchasesale(data) {
  return request({
    url: '/purchasesale/purchasesale',
    method: 'post',
    data: data
  })
}

// 修改发货管理
export function updatePurchasesale(data) {
  return request({
    url: '/purchasesale/purchasesale',
    method: 'put',
    data: data
  })
}

// 删除发货管理
export function delPurchasesale(deliverId) {
  return request({
    url: '/purchasesale/purchasesale/' + deliverId,
    method: 'delete'
  })
}
