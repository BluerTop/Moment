import request from '../utils/request'
import {getTokenName, getTokenValue} from "../utils/auth";

// 获取用户详细信息
export function userInfo() {
  return request({
    url: '/momentUser/info',
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    }
  })
}
