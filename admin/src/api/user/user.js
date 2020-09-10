import request from '@/utils/request'
/**
 * 获取用户列表
 */
export function getUserList(params) {
  return request({
    url: '/admin/user/list',
    method: 'get',
    params
  })
}
export function updateUserStatus(data) {
  return request({
    url: '/admin/user/updateUser',
    method: 'post',
    data
  })
}
export function updatePassword(data) {
  return request({
    url: '/api/user/updateUserPassword',
    method: 'post',
    data
  })
}
export function getCount() {
  return request({
    url: '/api/user/userCount',
    method: 'get'
  })
}
