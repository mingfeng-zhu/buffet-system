import request from '@/utils/request'

/**
 * 分页获取订单列表
 * @param {*} params
 */
export function listOrder(params) {
  return request({
    url: '/api/order/listOrder',
    method: 'get',
    params
  })
}

/**
 * 获取订单状态信息
 * @param {*} params
 */
export function listOrderStatusList() {
  return request({
    url: '/api/order/listOrderStatusList',
    method: 'get',
  })
}

/**
 * 获取指定订单详细信息
 * @param {*} params
 */
export function getOrderById(params) {
  return request({
    url: '/api/order/getOrderById',
    method: 'get',
    params
  })
}

/**
 * 修改订单状态，批量形式
 * @param {*} params
 */
export function updateOrderByIdList(data) {
  return request({
    url: '/api/order/updateOrderByIdList',
    method: 'post',
    data
  })
}

/**
 * 确认取消订单，批量形式
 * @param {*} params
 */
export function cancelOrderList(data) {
  return request({
    url: '/api/order/cancelOrderList',
    method: 'post',
    data
  })
}


