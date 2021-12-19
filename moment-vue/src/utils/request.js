import axios from 'axios'
import {getBaseUrl} from "./url";
import {Toast, Loading, Notify} from 'vant';
import router from '../router'
import {delMomentUser, delTokenValue} from "./auth";

// 创建axios实例
const service = axios.create({
  withCredentials: true,
  baseURL: getBaseUrl(), // api 的 base_url
  timeout: 30000 // 请求超时时间
});

// 响应拦截器
service.interceptors.response.use(res => {
  if (res.data.code === 511){
    delMomentUser();
    delTokenValue()
  }
  if (res.data.code === 510){
    delMomentUser();
    delTokenValue()
  }
  return res.data;
});
export default service
