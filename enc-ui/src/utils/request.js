import axios from 'axios'
// import { Message } from 'element-ui'
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

const token = ""
const request = axios.create({
  baseURL: "http://localhost:28085/",
  timeout: 80000
})

// request 拦截器
request.interceptors.request.use(
  config => {
    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  error => {
    // Do something with request error
    // console.log("出错啦",error) // for debug
    Promise.reject(error).then(r => {
      console.log("Error:", error);
    })
  }
)

request.interceptors.response.use(
  response => response,

  error => {
    console.log('err' + error) // for debug
    return Promise.reject(error)
  }
)

export default request
