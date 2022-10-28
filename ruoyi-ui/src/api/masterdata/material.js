import request from '@/utils/request'

// 查询主数据管理列表
export function listMaterialData(query) {
  return request({
    url: '/md/material/list',
    method: 'get',
    params: query
  })
}

// 查询主数据管理详细
export function getMaterialData(materialId) {
  return request({
    url: '/md/material/' + materialId,
    method: 'get'
  })
}

// 新增物料数据
export function addMaterialData(data) {
  console.log("addMaterialData");
  return request({
    url: '/md/material',
    method: 'post',
    data: data
  })
}

// 修改主数据管理
export function updateMaterialData(data) {
  return request({
    url: '/md/material',
    method: 'put',
    data: data
  })
}

// 删除主数据管理
export function delMaterialData(materialId) {
  return request({
    url: '/md/material/' + materialId,
    method: 'delete'
  })
}
