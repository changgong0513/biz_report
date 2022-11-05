import request from '@/utils/request'

// 查询发货管理列表
export function listSale(query) {
  console.log("查询发货管理列表");
  return request({
    url: '/sale/mgr/list',
    method: 'get',
    params: query
  })
}

// 查询发货管理详细
export function getSale(deliverId) {
  console.log("查询发货管理详细");
  return request({
    url: '/sale/mgr/' + deliverId,
    method: 'get'
  })
}

// 新增发货管理
export function addSale(data) {
  console.log("新增发货管理");
  return request({
    url: '/sale/mgr',
    method: 'post',
    data: data
  })
}

// 修改发货管理
export function updateSale(data) {
  console.log("修改发货管理");
  return request({
    url: '/sale/mgr',
    method: 'put',
    data: data
  })
}

// 删除发货管理
export function delSale(deliverId) {
  console.log("删除发货管理");
  return request({
    url: '/sale/mgr/' + deliverId,
    method: 'delete'
  })
}
