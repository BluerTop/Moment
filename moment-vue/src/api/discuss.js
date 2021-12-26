import request from '../utils/request'
import {getTokenValue} from "../utils/auth";

// 发布留言
export function addDiscuss(data) {
  return request({
    url: '/momentDiscuss/add',
    method: 'post',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
    data: data,
  })
}

// 动态所有留言
export function getDiscussInfos(dynamicId) {
  return request({
    url: '/momentDiscuss/getInfos?dynamicId=' + dynamicId,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}

// 删除留言
export function delDiscuss(dynamicId) {
  return request({
    url: '/momentDiscuss/del?id=' + dynamicId,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}
