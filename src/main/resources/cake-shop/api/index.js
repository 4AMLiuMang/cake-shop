// 员工登录
function empLoginApi(data) {
  return $axios({
    url: '/employee/login',
    method: 'post',
    data
  })
}

// 用户登录
function userLoginApi(data) {
  return $axios({
    url: '/user/login',
    method: 'post',
    data
  })
}

// 用户退出登录
function logoutApi() {
  return $axios({
    url: '/user/logout',
    method: 'post',
  })
}

// 注册用户
function userRegisterApi(data) {
  return $axios({
    url: '/user/register',
    method: 'post',
    data
  })
}

// 修改用户
function editUserApi(data) {
  return $axios({
    url: '/user/update',
    method: 'put',
    data
  })
}

//蛋糕分页查询
function getCakeListApi(params) {
  return $axios({
    url: '/cake/page',
    method: 'get',
    params
  })
}

//根据蛋糕id查询评论信息
function getCommentByCidApi(params) {
  return $axios({
    url: '/comment/getByCid',
    method: 'get',
    params
  })
}

//蛋糕购买
function buyCakeApi(params) {
  return $axios({
    url: '/orders/addOrders',
    method: 'post',
    data: { ...params }
  })
}

//根据id查询用户信息
function queryUserById(id) {
  return $axios({
    url: `/user/${id}`,
    method: 'get'
  })
}

//根据用户id删除|注销用户
function deleteUserById(ids) {
  return $axios({
    url: '/user/deleteUser',
    method: 'delete',
    params: { ids }
  })
}

// 修改用户密码
function editUserPwdApi(data) {
  return $axios({
    url: '/user/editPwd',
    method: 'put',
    data
  })
}

//根据用户id查询评论信息
function getCommentByUidApi(params) {
  return $axios({
    url: '/comment/getByUid',
    method: 'get',
    params
  })
}

//根据id查询蛋糕信息
function queryCakeById(id) {
  return $axios({
    url: `/cake/queryCake/${id}`,
    method: 'get'
  })
}

//根据id删除评论
function deleteCmtById(ids) {
  return $axios({
    url: '/comment/deleteCmt',
    method: 'delete',
    params: { ids }
  })
}

//根据用户id查询订单信息
function getOrdersByUidApi(params) {
  return $axios({
    url: '/orders/getByUid',
    method: 'get',
    params
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

//新增评论
function addCommentApi(params) {
  return $axios({
    url: '/comment/addComment',
    method: 'post',
    data: { ...params }
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