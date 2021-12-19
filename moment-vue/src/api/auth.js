import request from '../utils/request'
import {getTokenValue} from "../utils/auth";

// 登录
export function login(data) {
  return request({
    url: '/momentAuth/login',
    method: 'post',
    headers: {
      'Content-Type': "application/json;charset=utf-8",
    },
    data: data,
  })
}

// 校验是否登录
export function verifyLogin() {
  return request({
    url: '/momentAuth/verifyLogin',
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}
