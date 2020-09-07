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
 * 新增商品
 * @param {*} data 
 */
export function addProduct(data) {
  return request({
    url: '/admin/product/addProduct',
    method: 'put',
    data
  })
}

/**
 * 修改商品
 * @param {*} data 
 */
export function updateProduct(data) {
  return request({
    url: '/admin/product/updateProduct',
    method: 'post',
    data
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

/**
 * 获取商品属性
 * @param {*} params 
 */
export function getProductAttributeListByProductId(id) {
  return request({
    url: `/admin/product/getProductAttributeListByProductId/${id}`,
    method: 'get'
  })
}

/**
 * 新增商品规格属性
 * @param {*} data 
 */
export function addProductAttribute(data) {
  return request({
    url: '/admin/product/productAttribute',
    method: 'post',
    data: data
  })
}

/**
 * 修改商品规格属性
 * @param {*} data 
 */
export function updateProductAttribute(data) {
  return request({
    url: '/admin/product/productAttribute',
    method: 'put',
    data: data
  })
}

/**
 * 删除商品规格属性
 * @param {*} data 
 */
export function deleteProductAttribute(id) {
  return request({
    url: `/admin/product/productAttribute/${id}`,
    method: 'delete'
  })
}

/**
 * 删除商品规格属性值
 * @param {*} data 
 */
export function deleteProductAttributeValue(data) {
  return request({
    url: '/admin/product/productAttributeValue',
    method: 'delete',
    data: data
  })
}

/**
 * 添加商品规格属性值
 * @param {*} data 
 */
export function addProductAttributeValue(data) {
  return request({
    url: '/admin/product/productAttributeValue',
    method: 'put',
    data: data
  })
}

/**
 * 修改商品规格属性值
 * @param {*} data 
 */
export function updateProductAttributeValue(data) {
  return request({
    url: '/admin/product/productAttributeValue',
    method: 'post',
    data: data
  })
}

/**
 * 获取商品规格
 * @param {*} params 
 */
export function getProductSpecificationByProductId(id) {
  return request({
    url: `/admin/product/getProductSpecificationByProductId/${id}`,
    method: 'get'
  })
}

/**
 * 添加规格
 * @param {*} data 
 */
export function addSpecification(data) {
  return request({
    url: '/admin/product/productSpecification',
    method: 'post',
    data
  })
}

/**
 * 修改规格
 * @param {*} data 
 */
export function updateSpecification(data) {
  return request({
    url: '/admin/product/productSpecification',
    method: 'put',
    data
  })
}

/**
 * 删除规格
 * @param {*} id 
 */
export function deleteSpecification(id) {
  return request({
    url: `/admin/product/productSpecification/${id}`,
    method: 'delete'
  })
}