import request from '../utils/request'
import {getTokenValue} from "../utils/auth";

// 通用上传文件接口
export function uploadFile(data, describe) {
  return request({
    url: '/momentOther/uploadFile/' + describe,
    method: 'post',
    headers: {
      'moment-token': getTokenValue(),
      'Content-Type': "multipart/form-data;",
    },
    data: data,
  })
}

// 获取unsplash随机图片
export function getPhotos(index) {
  return request({
    url: '/momentOther/photos/'+index,
    method: 'get',
  })
}
