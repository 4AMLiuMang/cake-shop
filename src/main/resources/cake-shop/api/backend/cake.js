//分页查询用户数据
function getCakeList(params) {
  return $axios({
    url: '/cake/page',
    method: 'get',
    params
  })
}

// 删除接口
function deleteCake(ids) {
  return $axios({
    url: '/cake/deleteCake',
    method: 'delete',
    params: { ids }
  })
}

// 起用停用---批量起用停用接口
function enableOrDisableCake(params) {
  return $axios({
    url: `/cake/status/${params.cakeStatus}`,
    method: 'post',
    params: { ids: params.id }
  })
}

// 推荐接口
function enableOrDisableCakeRec(params) {
  return $axios({
    url: `/cake/cakeRec/${params.cakeRec}`,
    method: 'post',
    params: { ids: params.id }
  })
}

//根据id查询蛋糕信息//用于修改时的数据回显//因为VUE的双向数据绑定容易出现BUG，所以单独查询
function queryCakeById(id) {
  return $axios({
    url: `/cake/queryCake/${id}`,
    method: 'get'
  })
}

// 修改蛋糕
function editCakeApi(data) {
  return $axios({
    url: '/cake/update',
    method: 'put',
    data
  })
}

// 新增蛋糕
function addCakeApi(params) {
  return $axios({
    url: '/cake/addCake',
    method: 'post',
    data: { ...params }
  })
}