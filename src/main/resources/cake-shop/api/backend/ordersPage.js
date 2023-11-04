//分页查询订单数据
function getOrderList(params) {
  return $axios({
    url: '/orders/page',
    method: 'get',
    params
  })
}

// 删除接口
function deleteOrders(ids) {
  return $axios({
    url: '/orders/deleteOrders',
    method: 'delete',
    params: { ids }
  })
}

//根据id查询订单信息//用于修改时的数据回显//因为VUE的双向数据绑定容易出现BUG，所以单独查询
function queryOrdersById(id) {
  return $axios({
    url: `/orders/${id}`,
    method: 'get'
  })
}

//根据id修改出餐状态
function modifySmStatus(params) {
  return $axios({
    url: `/orders/smStatus/${params.smStatus}`,
    method: 'post',
    params: { id: params.id }
  })
}

//根据id修改配送状态
function modifyDeStatus(params) {
  return $axios({
    url: `/orders/deStatus/${params.deStatus}`,
    method: 'post',
    params: { id: params.id }
  })
}

//根据id修改收货状态 1用户取消 2商家取消 3订单完成
function modifyOcStatus(params) {
  return $axios({
    url: `/orders/ocStatus/${params.ocStatus}`,
    method: 'post',
    params: { id: params.id }
  })
}

//根据id修改评分
function modifyCakeRating(params) {
  return $axios({
    url: `/orders/cakeRating/${params.cakeRating}`,
    method: 'post',
    params: { id: params.id }
  })
}
