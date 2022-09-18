import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询仓库列表
export function listWarehouse(query) {
  return request({
    url: '/md/warehouse/list',
    method: 'get',
    params: query
  })
}

// 查询仓库详细
export function getWarehouse(warehouseId) {
  return request({
    url: '/md/warehouse/' + parseStrEmpty(warehouseId),
    method: 'get'
  })
}

// 新增仓库数据
export function addWarehouse(data) {
  return request({
    url: '/md/warehouse',
    method: 'post',
    data: data
  })
}

// 修改仓库数据
export function updateWarehous(data) {
  return request({
    url: '/md/warehouse',
    method: 'put',
    data: data
  })
}

// 删除客户
export function delWarehous(warehouseId) {
  return request({
    url: '/md/warehouse/' + warehouseId,
    method: 'delete'
  })
}