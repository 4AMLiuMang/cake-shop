//分页查询员工数据
function getEmployeeList(params) {
  return $axios({
    url: '/employee/page',
    method: 'get',
    params
  })
}

// 删除接口
function deleteEmployee(ids) {
  return $axios({
    url: '/employee/deleteEmp',
    method: 'delete',
    params: { ids }
  })
}

// 起用停用---批量起用停用接口
function enableOrDisableEmp(params) {
  return $axios({
    url: `/employee/status/${params.empStatus}`,
    method: 'post',
    params: { ids: params.id }
  })
}

//根据id查询员工信息//用于修改时的数据回显//因为VUE的双向数据绑定容易出现BUG，所以单独查询
function queryEmployeeById(id) {
  return $axios({
    url: `/employee/${id}`,
    method: 'get'
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

// // 新增员工
function addEmpApi(params) {
  return $axios({
    url: '/employee/register',
    method: 'post',
    data: { ...params }
  })
}
