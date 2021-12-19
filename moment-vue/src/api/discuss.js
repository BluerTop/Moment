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

// 根据动态id获取所有留言
export function getDiscussInfos(dynamicId) {
  return request({
    url: '/momentDiscuss/getInfos/'+dynamicId,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}
