import request from '../utils/request'
import {getTokenValue} from "../utils/auth";

// 发布动态
export function addDynamic(data) {
  return request({
    url: '/momentDynamic/add',
    method: 'post',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
    data: data,
  })
}

// 发现动态列表
export function pages(index, type) {
  return request({
    url: '/momentDynamic/pages?index=' + index + "&type=" + type,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}

// 我的动态列表
export function queryByMy(index) {
  return request({
    url: '/momentDynamic/queryByMy?index=' + index,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}

// 我的关注动态列表
export function queryByMyFocus(index) {
  return request({
    url: '/momentDynamic/queryByMyFocus?index=' + index,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}

// 单条动态信息
export function getInfo(id) {
  return request({
    url: '/momentDynamic/getInfo?id=' + id,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}

// 删除动态/恢复动态
export function delOrRec(id, type) {
  return request({
    url: '/momentDynamic/delOrRec?id=' + id + '&type=' + type,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}
