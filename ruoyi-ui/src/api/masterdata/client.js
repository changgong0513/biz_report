import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询供应商/客户列表
export function listClient(query) {
  return request({
    url: '/md/client/list',
    method: 'get',
    params: query
  })
}

// 查询供应商/客户详细
export function getClient(baseId) {
  return request({
    url: '/md/client/' + parseStrEmpty(baseId),
    method: 'get'
  })
}

// 新增客户
export function addClient(data) {
  return request({
    url: '/md/client',
    method: 'post',
    data: data
  })
}

// 修改客户
export function updateClient(data) {
  return request({
    url: '/md/client',
    method: 'put',
    data: data
  })
}

// 删除客户
export function delClient(userId) {
  return request({
    url: '/md/client/' + userId,
    method: 'delete'
  })
}