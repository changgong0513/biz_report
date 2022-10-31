import request from '@/utils/request'

// 查询合同管理列表
export function listContract(query) {
  console.log("查询合同管理列表(contract.js)");
  return request({
    url: '/contract/mgr/list',
    method: 'get',
    params: query
  })
}

// 查询合同管理详细
export function getContract(goodsId) {
  return request({
    url: '/contract/contract/' + goodsId,
    method: 'get'
  })
}

// 新增合同管理
export function addContract(data) {
  return request({
    url: '/contract/contract',
    method: 'post',
    data: data
  })
}

// 修改合同管理
export function updateContract(data) {
  return request({
    url: '/contract/contract',
    method: 'put',
    data: data
  })
}

// 删除合同管理
export function delContract(goodsId) {
  return request({
    url: '/contract/contract/' + goodsId,
    method: 'delete'
  })
}

// 从钉钉同步合同数据
export function syncContract(query) {
  console.log("从钉钉同步合同数据");
  return request({
    url: '/contract/mgr/sync',
    method: 'post'
  })
}
