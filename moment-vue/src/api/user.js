import request from '../utils/request'
import {getTokenValue} from "../utils/auth";

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

// 推荐用户列表
export function recommend(page, size, name) {
  return request({
    url: '/momentUser/recommend?page=' + page + '&size=' + size + '&name=' + name,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    }
  })
}
