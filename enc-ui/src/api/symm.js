import request from '@/utils/request'

// 查询ECA管理列表
export function listCa(query) {
  return request({
    url: '/ca/ca/list',
    method: 'get',
    params: query
  })
}

// 查询ECA管理详细
export function getCa(id) {
  return request({
    url: '/ca/ca/' + id,
    method: 'get'
  })
}

// 新增ECA管理
export function addCa(data) {
  return request({
    url: '/ca/ca',
    method: 'post',
    data: data
  })
}

// 修改ECA管理
export function updateCa(data) {
  return request({
    url: '/ca/ca',
    method: 'put',
    data: data
  })
}

// 删除ECA管理
export function delCa(id) {
  return request({
    url: '/ca/ca/' + id,
    method: 'delete'
  })
}

// 导出ECA管理
export function exportCa(query) {
  return request({
    url: '/ca/ca/export',
    method: 'get',
    params: query
  })
}

// 下载ECA管理请求
export function downloadTbs(data) {
  return request({
    url: '/ca/ca/downloadTbs',
    method: 'post',
    data: data
  })
}

// 下载ECA证书管理请求
export function downloadCert(data) {
  return request({
    url: '/ca/ca/downloadCert',
    method: 'post',
    data:data
  })
}

// 导入ECA管理
export function uploadCa(id) {
  return request({
    url: '/ca/ca/upload',
    method: 'post',
    data: id
  })
}
