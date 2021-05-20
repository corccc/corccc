import request from '@/utils/request'

// Hash Code Version
export function version() {
  return request({
    url: '/hash/version',
    method: 'get'
  })
}

// Hash Function
export function hash(data) {
  return request({
    url: '/hash/digest',
    method: 'post',
    data: data
  })
}
