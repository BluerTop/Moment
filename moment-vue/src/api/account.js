import request from '../utils/request'
import {getTokenValue} from "../utils/auth";

// 获取账户详细信息
export function accountInfo() {
  return request({
    url: '/momentAccount/info',
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    }
  })
}
