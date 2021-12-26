import request from '../utils/request'
import {getTokenValue} from "../utils/auth";

// 点赞/消赞
export function like(id) {
  return request({
    url: '/momentLikes/like?id=' + id,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}

// 动态点赞列表
export function likeList(id) {
  return request({
    url: '/momentLikes/likeList?id=' + id,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}
