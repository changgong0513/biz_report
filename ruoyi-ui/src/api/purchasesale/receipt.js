import request from '@/utils/request'

// 查询收货管理列表
export function listReceipt(query) {
  console.log("查询收货管理列表");
  return request({
    url: '/receipt/mgr/list',
    method: 'get',
    params: query
  })
}

// 查询收货管理详细
export function getReceipt(receiptId) {
  console.log("查询收货管理详细" + receiptId);
  return request({
    url: '/receipt/mgr/' + receiptId,
    method: 'get'
  })
}

// 新增收货管理
export function addReceipt(data) {
  console.log("查询收货管理详细" + JSON.stringify(data));
  return request({
    url: '/receipt/mgr',
    method: 'post',
    data: data
  })
}

// 修改收货管理
export function updateReceipt(data) {
  console.log("修改收货管理" + JSON.stringify(data));
  return request({
    url: '/receipt/mgr',
    method: 'put',
    data: data
  })
}

// 删除收货管理
export function delReceipt(receiptId) {
  console.log("删除收货管理" + receiptId);
  return request({
    url: '/receipt/mgr/' + receiptId,
    method: 'delete'
  })
}
