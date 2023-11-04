// 员工退出登录
function logoutApi() {
  return $axios({
    url: '/employee/logout',
    method: 'post',
  })
}

// 修改员工密码
function editEmpPwdApi(data) {
  return $axios({
    url: '/employee/editPwd',
    method: 'put',
    data
  })
}

// 修改员工
function editEmpApi(data) {
  return $axios({
    url: '/employee/update',
    method: 'put',
    data
  })
}

