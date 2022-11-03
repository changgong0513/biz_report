import request from '@/utils/request'

// 查询采购收货销售发货管理列表
export function listPurchase(query) {
  return request({
    url: '/purchase/mgr/list',
    method: 'get',
    params: query
  })
}

// 查询采购收货销售发货管理详细
export function getPurchasesale(orderId) {
  return request({
    url: '/purchasesale/purchasesale/' + orderId,
    method: 'get'
  })
}

// 新增采购收货销售发货管理
export function addPurchasesale(data) {
  return request({
    url: '/purchasesale/purchasesale',
    method: 'post',
    data: data
  })
}

// 修改采购收货销售发货管理
export function updatePurchasesale(data) {
  return request({
    url: '/purchasesale/purchasesale',
    method: 'put',
    data: data
  })
}

// 删除采购收货销售发货管理
export function delPurchasesale(orderId) {
  return request({
    url: '/purchasesale/purchasesale/' + orderId,
    method: 'delete'
  })
}
