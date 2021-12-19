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

// 分页查询动态列表 公共
export function pages(page, size) {
  return request({
    url: '/momentDynamic/pages/' + page + '/' + size,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}

// 查询我的动态列表
export function queryByMy(index) {
  return request({
    url: '/momentDynamic/queryByMy/' + index,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}

// 查询关注动态列表
export function queryByMyFocus() {
  return request({
    url: '/momentDynamic/queryByMyFocus',
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}

// 根据id获取单条动态信息 通用
export function getInfo(id) {
  return request({
    url: '/momentDynamic/getInfo/' + id,
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
    url: '/momentDynamic/delOrRec/' + id + '/' + type,
    method: 'get',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "application/json;charset=utf-8",
    },
  })
}
