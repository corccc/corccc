import request from '@/utils/request'


export function baseURL() {
  return request.baseURL
}

// Symm Code Version
export function version() {
  return request({
    url: '/cpk/version',
    method: 'get'
  })
}

export function pubMatrix(data) {
  return request({
    url: '/cpk/pubMatrix',
    method: 'post',
    data: data
  })
}

export function priMatrix(data) {
  return request({
    url: '/cpk/priMatrix',
    method: 'post',
    data: data
  })
}

export function calPub(data) {
  return request({
    url: '/cpk/calPub',
    method: 'post',
    data: data
  })
}

export function calPri(data) {
  return request({
    url: '/cpk/calPri',
    method: 'post',
    data: data
  })
}
