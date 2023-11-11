import type { AxiosError, AxiosResponse, InternalAxiosRequestConfig } from 'axios'
import type { AxiosRequestHeaders } from 'axios'

import { HttpClient } from '@/utils/axios/http-client'
import { useTokenStore } from '@/stores/user-store'
import router from '@/router'

const onRequestFulfilled = (requestConfig: InternalAxiosRequestConfig) => {
  const headers = requestConfig.headers || {}

  // token
  const token = useTokenStore().token
  // Authorization 请求头不存在再进行追加
  if (token && !headers['token']) {
    // 让每个请求携带自定义 token 请根据实际情况自行修改
    headers['token'] = token
  }

  if (requestConfig.headers) {
    requestConfig.headers = headers
  }
  return requestConfig
}

// 响应成功处理函数
const onResponseFulfilled = (response: AxiosResponse) => {
  const headers = response.headers
  if (
    headers != null &&
    headers['content-type'] &&
    headers['content-type'].startsWith('application/json')
  ) {
    return response.data
  } else {
    return response
  }
}

// 响应失败处理函数
const onResponseRejected = (error: AxiosError) => {
  if (error.response) {
    const errorStatus = error.response.status
    switch (errorStatus) {
      case 401:
        error.resolved = true
        useTokenStore().clean()
        if (router.currentRoute.value.path !== '/login') {
          router.push({
            path: '/login',
            query: { redirect: router.currentRoute.value.fullPath }
          })
        }
        break
    }
  } else {
    error.resolved = true
  }
  return Promise.reject(error)
}

const httpClient = new HttpClient({
  defaultRequestConfig: {
    headers: {} as AxiosRequestHeaders,
    baseURL: '/api', // api base_url
    timeout: 6000 // 请求超时时间
  },
  interceptorOptions: {
    onRequestFulfilled: onRequestFulfilled,
    onResponseFulfilled: onResponseFulfilled,
    onResponseRejected: onResponseRejected
  }
})

export default httpClient
