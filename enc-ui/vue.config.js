module.exports = {
  devServer: {
    open: true,
    host: 'localhost',
    port: 28081,
    https: false,
    //以上的ip和端口是我们本机的;下面为需要跨域的
    proxy: {  //配置跨域
      '/api': {
        target: 'http://localhost:28081/symm/encrypt',
        ws: true,
        changOrigin: true,  //允许跨域
        pathRewrite: {
          '^/api': ''  //请求的时候使用这个api就可以
        }
      }
    }
  }
}
