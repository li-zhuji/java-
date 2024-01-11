/*
 * @作者: kerwin
 */
import axios from 'axios'
import { ElLoading } from 'element-plus'

let loadingInstance1 = null
// Add a request interceptor
axios.interceptors.request.use(function (config) {
    // Do something before request is sent
    // console.log("请求之前")

    const token = localStorage.getItem("token")
    config.headers.token = token

    //显示loading
    loadingInstance1 = ElLoading.service({ fullscreen: true })

    return config;
  }, function (error) {
    // Do something with request error
    return Promise.reject(error);
  });

// Add a response interceptor
axios.interceptors.response.use(function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    // console.log("then响应处理之前")

    const res = response.data
    // console.log();
    res?.data?.token && localStorage.setItem("token",res?.data?.token);

    //隐藏loading
    loadingInstance1 && loadingInstance1.close()
    return response;
  }, function (error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    // console.log("catch响应处理之前",error.response)

     //隐藏loading

     loadingInstance1&&  loadingInstance1.close()
    const  {status} = error.response
    if(status===401){
        localStorage.removeItem("token")
        //重定向
        window.location.href="#/login"
    }
    return Promise.reject(error);
  });
