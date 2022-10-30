import request from '@/utils/request'

// 查询仓库库区列表
export function listKq(query) {
  console.log("查询仓库库区列表");
  return request({
    url: '/md/kq/list',
    method: 'get',
    params: query
  })
}

// 查询Warehouse详细
export function getKq(kqCode) {
  return request({
    url: '/md/kq/' + kqCode,
    method: 'get'
  })
}

// 新增库区数据
export function addKq(data) {
  console.log("新增库区数据：" + JSON.stringify(data));
  return request({
    url: '/md/kq',
    method: 'post',
    data: data
  })
}

// 修改Warehouse
export function updateKq(data) {
  console.log("修改库区数据：" + JSON.stringify(data));
  return request({
    url: '/md/kq',
    method: 'put',
    data: data
  })
}

// 删除Warehouse
export function delKq(kqCode) {
  return request({
    url: '/md/kq/' + kqCode,
    method: 'delete'
  })
}
