import request from '@/utils/request'

export function getProductCategoryList(params) {
  return request({
    url: '/admin/product/getProductCategoryList',
    method: 'get',
    params
  })
}
