import request from '@/utils/request'

// 查询供应商/客户列表
export function listClient(query) {
  query.recordFlag = 1;
  return request({
    url: '/md/client/list',
    method: 'get',
    params: query
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