//分页查询评论数据
function getCommentList(params) {
  return $axios({
    url: '/comment/page',
    method: 'get',
    params
  })
}

// 删除接口
function deleteComment(ids) {
  return $axios({
    url: '/comment/deleteCmt',
    method: 'delete',
    params: { ids }
  })
}