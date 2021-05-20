import request from '@/utils/request'

// Symm Code Version
export function version() {
  return request({
    url: '/asymm/version',
    method: 'get'
  })
}

// Symm Encrypt Function
export function genKeyPair(data) {
  return request({
    url: '/asymm/genKeyPair',
    method: 'post',
    data: data
  })
}

// Symm Decrypt Function
export function calPubKey(data) {
  return request({
    url: '/asymm/calPubKey',
    method: 'post',
    data: data
  })
}
