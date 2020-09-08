import axios from './request'

// 获取商品分类
const getProductCategoryList = p => axios.get('product/getProductCategoryList', { params: p })
// 获取所有商品
const getAllProductList = p => axios.get('product/getAllProductList', { params: p })
// 搜索商品
const getProductListByProductName = (p) => axios.get(`/product/getProductListByProductName`, { params: p })
// 根据id获取规格列表
const getProductAttributeListByProductId = (p) => axios.get(`/product/getProductAttributeListByProductId/${p}`)
// 根据分类id获取商品列表
const getProductListByProductCategoryId = (p) => axios.get(`/product/getProductListByProductCategoryId`, { params: p })
// 根据商品id和商品规格获取该商品的详细信息
const getSpecificationByProductIdAndSpecification = (p) => axios.get(`/product/getSpecificationByProductIdAndSpecification`, { params: p })
// 根据商品id获取商品详细信息以及商品评价
const getProductDetailAndCommentList = (p) => axios.get(`/product/getProductDetailAndCommentList/${p}`)
// 获取验证码
const getCode = (p) => axios.get(`/user/getCode`,{ params: p })
// 注册
const signUp = (p) => axios.post(`/user/signUp`, p)
// 登录
const login = (p) => axios.post(`/user/login`, p)
// 通过手机号登录
const loginByPhoneNumber = (p) => axios.post(`/user/loginByPhoneNumber`, p)
// 加入购物车
const addShopCartRecord = (p) => axios.post(`/shopCart/addShopCartRecord`, p)
// 添加订单
const addOrder = (p) => axios.post(`/order/addOrder`, p)
// 上传图片
const upload = (p) => axios.post(`/upload`, p)
// 获取购物车列表
const listShopCartByUserId = (p) => axios.get(`shopCart/listShopCartByUserId`, { params: p })
//生成订单
const creatOrder = (p) => axios.post('/order/addOrder',p)
//支付
const payOrder = (p) => axios.post('/order/payOrder',p)
//获取订单列表
const getOrder = (p) => axios.get('/order/listOrderByCurrentUser',{ params: p })
//获取指定订单信息
const getOrderById = (p) => axios.get('/order/getOrderById',{ params: p })
//取消订单
const cancelOrder = (p) => axios.post('/order/cancelOrder',p)
//修改订单状态
const editOrderStatus = (p) => axios.post('/order/editOrderStatus',p)
// 获取用户信息
const getUserInfo = (p) => axios.get('/user/getUserInfo', { params: p })
// 获取地址列表
const addressList = (p) => axios.get('/address/list', { params: p })
// 获取某一个地址
const getAddressById = (p) => axios.get('address/getAddressById', { params: p })
//修改用户信息
const updateUser = (p) => axios.post('/user/updateUser',p)
// 新增地址
const insert = (p) => axios.post('address/insert',p)
// 编辑地址
const update = (p) => axios.post('address/update',p)
// 删除地址
const deleteAddress = (p) => axios.delete(`address/delete/${p}`)
export default {
    getProductCategoryList,
    getAllProductList,
    getProductListByProductName,
    getProductAttributeListByProductId,
    getProductListByProductCategoryId,
    getSpecificationByProductIdAndSpecification,
    getCode,
    listShopCartByUserId,
    upload,
    signUp,
    login,
    getProductDetailAndCommentList,
    addOrder,
    addShopCartRecord,
    loginByPhoneNumber,
    creatOrder,
    payOrder,
    getOrder,
    getOrderById,
    cancelOrder,
    editOrderStatus,
    getUserInfo,
    updateUser,
    insert,
    addressList,
    getAddressById,
    update,
    deleteAddress
}
