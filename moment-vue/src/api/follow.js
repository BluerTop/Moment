import request from '../utils/request'
import {getTokenValue} from "../utils/auth";

// 关注
export function addFollow(followedId) {
  return request({
    url: '/momentFollow/add?followedId=' + followedId,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}

// 取消关注
export function delFollow(id) {
  return request({
    url: '/momentFollow/del?followedId=' + id,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}

// 关注列表
export function listFollow(id) {
  return request({
    url: '/momentFollow/list?followersId=' + id,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}
