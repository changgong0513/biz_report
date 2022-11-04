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
export function getPurchase(contractId) {
  return request({
    url: '/purchase/mgr/' + contractId,
    method: 'get'
  })
}

// 新增采购收货销售发货管理
export function addPurchase(data) {
  return request({
    url: '/purchase/mgr',
    method: 'post',
    data: data
  })
}

// 修改采购收货销售发货管理
export function updatePurchase(data) {
  return request({
    url: '/purchase/mgr',
    method: 'put',
    data: data
  })
}

// 删除采购收货销售发货管理
export function delPurchase(orderId) {
  return request({
    url: '/purchase/mgr/' + orderId,
    method: 'delete'
  })
}
