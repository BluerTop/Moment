import request from '../utils/request'

// 通用消息发送
export function sendMessages(data) {
  return request({
    url: '/momentSms/sendMessages?phone='+data.phone+'&describe='+data.describe,
    method: 'get',
    headers: {
      'Content-Type': "application/json;charset=utf-8",
    },
    data: data,
  })
}
