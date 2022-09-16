import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 查询供应商/客户列表
export function listClient(query) {
  query.recordFlag = 1;
  return request({
    url: '/md/client/list',
    method: 'get',
    params: query
  })
}

// 查询供应商/客户详细
export function getClient(baseId) {
  console.log("供应商编号：" + baseId);
  return request({
    url: '/md/client/' + parseStrEmpty(baseId),
    method: 'get'
  })
}

// 新增客户
export function addClient(data) {
  console.log("新增客户类型：" + data.recordFlag);
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