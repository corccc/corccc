import request from '@/utils/request'

// Symm Code Version
export function version() {
  return request({
    url: '/symm/version',
    method: 'get'
  })
}

// Symm Encrypt Function
export function encrypt(data) {
  return request({
    url: '/symm/encrypt',
    method: 'post',
    data: data
  })
}

// Symm Decrypt Function
export function decrypt(data) {
  return request({
    url: '/symm/decrypt',
    method: 'post',
    data: data
  })
}
