import request from '@/utils/request'

// 查询合同数据列表
export function listContract(query) {
  console.log("查询合同数据列表(contract.js)");
  return request({
    url: '/contract/mgr/list',
    method: 'get',
    params: query
  })
}

// 查询合同数据详细
export function getContract(contractId) {
  console.log("查询合同数据详细(contract.js)：" + contractId);
  return request({
    url: '/contract/mgr/' + contractId,
    method: 'get'
  })
}

// 新增合同数据
export function addContract(data) {
  console.log("新增合同数据(contract.js)：" + JSON.stringify(data));
  return request({
    url: '/contract/mgr',
    method: 'post',
    data: data
  })
}

// 修改合同数据
export function updateContract(data) {
  console.log("修改合同数据(contract.js)：" + JSON.stringify(data));
  return request({
    url: '/contract/mgr',
    method: 'put',
    data: data
  })
}

// 删除合同管理
export function delContract(contractId) {
  console.log("删除合同管理(contract.js)：" + contractId);
  return request({
    url: '/contract/mgr/' + contractId,
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
