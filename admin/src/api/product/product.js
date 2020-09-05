import request from '@/utils/request'

/**
 * 分页获取商品分类列表
 * @param {*} params 
 */
export function getProductCategoryList(params) {
  return request({
    url: '/admin/product/getProductCategoryList',
    method: 'get',
    params
  })
}

/**
 * 更新商品分类信息
 * @param {*} data 
 */
export function updateProductCategory(data) {
  return request({
    url: '/admin/product/productCategory',
    method: 'put',
    data: data
  })
}

/**
 * 新增商品分类信息
 * @param {*} data 
 */
export function addProductCategory(data) {
  return request({
    url: '/admin/product/productCategory',
    method: 'post',
    data: data
  })
}

/**
 * 删除商品分类信息
 * @param {*} id 
 */
export function deleteProductCategory(id) {
  return request({
    url: `/admin/product/productCategory/${id}`,
    method: 'delete',
  })
}

/**
 * 分页获取商品列表
 * @param {*} params 
 */
export function getProductList(params) {
  return request({
    url: '/admin/product/getProductList',
    method: 'get',
    params
  })
}

/**
 * 删除商品信息
 * @param {*} id 
 */
export function deleteProduct(id) {
  return request({
    url: `/admin/product/deleteProduct/${id}`,
    method: 'delete',
  })
}

/**
 * 下架商品
 * @param {*} id 
 */
export function dropProduct(id) {
  return request({
    url: `/admin/product/dropProduct/${id}`,
    method: 'put',
  })
}

/**
 * 上架商品
 * @param {*} id 
 */
export function upProduct(id) {
  return request({
    url: `/admin/product/upProduct/${id}`,
    method: 'put',
  })
}
