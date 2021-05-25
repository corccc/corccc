import request from '@/utils/request'

// Symm Code Version
export function version() {
  return request({
    url: '/ecdh/version',
    method: 'get'
  })
}

export function genKeyPair(data) {
  return request({
    url: '/ecdh/genKeyPair',
    method: 'post',
    data: data
  })
}

export function calPubKey(data) {
  return request({
    url: '/ecdh/calPubKey',
    method: 'post',
    data: data
  })
}

export function calSecretKey(data) {
  return request({
    url: '/ecdh/calSecretKey',
    method: 'post',
    data: data
  })
}

