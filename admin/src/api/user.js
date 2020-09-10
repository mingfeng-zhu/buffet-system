import request from '@/utils/request'

/**
 * 用户登录
 */
export function login(data) {
  return request({
    url: '/api/user/login',
    method: 'post',
    data
  })
}
/**
 * 获取用户信息
 */
export function getInfo(token) {
  return request({
    url: '/api/user/getUserInfo',
    method: 'get',
    params: {
      token
    }
  })
}
// export function login(data) {
//   return request({
//     url: '/vue-admin-template/user/login',
//     method: 'post',
//     data
//   })
// }

// export function getInfo(token) {
//   return request({
//     url: '/vue-admin-template/user/info',
//     method: 'get',
//     params: { token }
//   })
// }

export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}
