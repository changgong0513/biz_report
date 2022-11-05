import request from '@/utils/request'

// 查询发货管理列表
export function listDeliver(query) {
  console.log("查询发货管理列表");
  return request({
    url: '/deliver/mgr/list',
    method: 'get',
    params: query
  })
}

// 查询发货管理详细
export function getDeliver(deliverId) {
  console.log("查询发货管理详细");
  return request({
    url: '/deliver/mgr/' + deliverId,
    method: 'get'
  })
}

// 新增发货管理
export function addDeliver(data) {
  console.log("新增发货管理");
  return request({
    url: '/deliver/mgr',
    method: 'post',
    data: data
  })
}

// 修改发货管理
export function updateDeliver(data) {
  console.log("修改发货管理");
  return request({
    url: '/deliver/mgr',
    method: 'put',
    data: data
  })
}

// 删除发货管理
export function delDeliver(deliverId) {
  console.log("删除发货管理");
  return request({
    url: '/deliver/mgr/' + deliverId,
    method: 'delete'
  })
}
