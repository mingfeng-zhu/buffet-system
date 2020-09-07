import request from '@/utils/request'

/**
 * 获取指定订单的时间线
 * @param {*} params
 */
export function listOrderStatusRecordByOrderId(params) {
  return request({
    url: '/api/orderStatus/listOrderStatusRecordByOrderId',
    method: 'get',
    params
  })
}
