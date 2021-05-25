import request from '@/utils/request'

// Symm Code Version
export function version() {
  return request({
    url: '/asymm/version',
    method: 'get'
  })
}

export function genKeyPair(data) {
  return request({
    url: '/asymm/genKeyPair',
    method: 'post',
    data: data
  })
}

export function calPubKey(data) {
  return request({
    url: '/asymm/calPubKey',
    method: 'post',
    data: data
  })
}

// Symm Enc Function
export function enc(data) {
  return request({
    url: '/asymm/enc',
    method: 'post',
    data: data
  })
}

// Symm Dec Function
export function dec(data) {
  return request({
    url: '/asymm/dec',
    method: 'post',
    data: data
  })
}

// Symm Sign Function
export function sign(data) {
  return request({
    url: '/asymm/sign',
    method: 'post',
    data: data
  })
}

// Symm Verify Sign Function
export function vsign(data) {
  return request({
    url: '/asymm/vsign',
    method: 'post',
    data: data
  })
}
