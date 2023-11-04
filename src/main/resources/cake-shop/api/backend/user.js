//分页查询用户数据
function getUserList(params) {
  return $axios({
    url: '/user/page',
    method: 'get',
    params
  })
}

// 删除接口
function deleteUser(ids) {
  return $axios({
    url: '/user/deleteUser',
    method: 'delete',
    params: { ids }
  })
}

// 起用停用---批量起用停用接口
function enableOrDisableUser(params) {
  return $axios({
    url: `/user/status/${params.userStatus}`,
    method: 'post',
    params: { ids: params.id }
  })
}

//根据id查询用户信息//用于修改时的数据回显//因为VUE的双向数据绑定容易出现BUG，所以单独查询
function queryUserById(id) {
  return $axios({
    url: `/user/${id}`,
    method: 'get'
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

