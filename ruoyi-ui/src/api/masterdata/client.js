import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 新增客户
export function addClient(data) {
    console.log("新增客户类型：" + data.recordFlag);
    return request({
      url: '/md/client',
      method: 'post',
      data: data
    })
  }