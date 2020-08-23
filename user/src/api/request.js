// import config from '../config/index'
import axios from 'axios'

var instance = axios.create({
    baseURL: '/api', // 公共接口url（如果有多个的公共接口的话，需要处理）
    timeout: 60000, // 请求超时 10s
});
// 头部
instance.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
// instance.defaults.headers.post['Content-Type'] = 'multipart/form-data;charset=UTF-8';
// 请求拦截
instance.interceptors.request.use(config => {
    let passport = localStorage.getItem('token');
    console.log('passport', passport)
    // config.headers.Authorization = 'Bearer ' + passport
    config.headers['X-token'] = passport
    return config;
  }, function (error) {
    return Promise.reject(error);
});
// 响应拦截
instance.interceptors.response.use(
    response => response.status === 200 ? Promise.resolve(response) : Promise.reject(response),
    error => {
      console.log('_______________',error.response)
    if (error.response.status == 401) {
        // Toast('用户未登录');
    }else if(error.response.status == 403){
        // Toast('登录过期，请重新登录');
    }else if(error.response.status == 404){
        // Toast('网络请求不存在');
    }else{
        // Toast('服务器请求错误')
    }
});
 export default instance
