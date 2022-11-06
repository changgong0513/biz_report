import request from '@/utils/request'

// 查询发票管理列表
export function listMain(query) {
  return request({
    url: '/fpgl/mgr/list',
    method: 'get',
    params: query
  })
}

// 查询开票明细列表
export function listFpmx(orderId) {
  console.log("查询开票明细列表");
  return request({
    url: '/fpgl/mgr/fpmxlist/' + orderId,
    method: 'get'
  })
}

// 查询发票管理详细
export function getMain(fpglId) {
  return request({
    url: '/fpgl/mgr/' + fpglId,
    method: 'get'
  })
}

// 新增发票管理
export function addMain(data) {
  return request({
    url: '/fpgl/mgr',
    method: 'post',
    data: data
  })
}

// 修改发票管理
export function updateMain(data) {
  return request({
    url: '/fpgl/mgr',
    method: 'put',
    data: data
  })
}

// 删除发票管理
export function delMain(fpglId) {
  return request({
    url: '/fpgl/mgr/' + fpglId,
    method: 'delete'
  })
}
