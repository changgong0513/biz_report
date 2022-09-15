import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/ruoyi";

// 新增客户
export function addClient() {
    console.log("新增客户方法");
    return request({
      url: '/md/client',
      method: 'post'
    })
  }