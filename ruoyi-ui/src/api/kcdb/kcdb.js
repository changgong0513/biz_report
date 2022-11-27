import request from '@/utils/request'

// 查询存库调拨列表
export function listKcdb(query) {
  return request({
    url: '/kcdb/mgr/list',
    method: 'get',
    params: query
  })
}

// 查询存库调拨详细
export function getKcdb(dbId) {
  return request({
    url: '/kcdb/mgr/' + dbId,
    method: 'get'
  })
}

// 新增存库调拨
export function addKcdb(data) {
  return request({
    url: '/kcdb/mgr',
    method: 'post',
    data: data
  })
}

// 修改存库调拨
export function updateKcdb(data) {
  return request({
    url: '/kcdb/mgr',
    method: 'put',
    data: data
  })
}

// 删除存库调拨
export function delKcdb(dh) {
  return request({
    url: '/kcdb/mgr/' + dh,
    method: 'delete'
  })
}
