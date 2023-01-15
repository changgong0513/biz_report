import request from '@/utils/request'

// 查询批次号管理列表
export function listPch(query) {
  return request({
    url: '/masterdata/pch/list',
    method: 'get',
    params: query
  })
}

// 根据登录用户所属部门，查询批次号管理列表
export function listDeptPch() {
  return request({
    url: '/masterdata/pch/dept/list',
    method: 'get'
  })
}

// 查询批次号管理详细
export function getPch(id) {
  return request({
    url: '/masterdata/pch/' + id,
    method: 'get'
  })
}

// 新增批次号管理
export function addPch(data) {
  return request({
    url: '/masterdata/pch',
    method: 'post',
    data: data
  })
}

// 修改批次号管理
export function updatePch(data) {
  return request({
    url: '/masterdata/pch',
    method: 'put',
    data: data
  })
}

// 删除批次号管理
export function delPch(id) {
  return request({
    url: '/masterdata/pch/' + id,
    method: 'delete'
  })
}
