import request from '@/utils/request'

export function getAddressListByUserId(params) {
  return request({
    url: '/api/address/list',
    method: 'get',
    params
  })
}
