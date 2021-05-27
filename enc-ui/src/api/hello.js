import request from '@/utils/request'

export function imageUrl() {
  return request({
    url: 'https://gank.io/api/v2/random/category/Girl/type/Girl/count/1',
    method: 'get'
  })
}
