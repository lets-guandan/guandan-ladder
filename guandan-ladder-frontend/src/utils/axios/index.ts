import type {AxiosError, AxiosResponse, InternalAxiosRequestConfig} from 'axios'
import type { ApiResult } from '@/api/types'

import { HttpClient } from '@/utils/axios/http-client'
import {AxiosRequestHeaders} from "axios";

const onRequestFulfilled = (requestConfig: InternalAxiosRequestConfig) => {
  const headers = requestConfig.headers || {}

  // token
  const accessToken = ''
  // Authorization 请求头不存在再进行追加
  if (accessToken && !headers['Authorization']) {
    // 让每个请求携带自定义 token 请根据实际情况自行修改
    headers['Authorization'] = 'Bearer ' + accessToken
  }

  // i18n
  // if (enableI18n) {
  //   const appLanguage = store.getters.lang
  //   if (appLanguage) {
  //     headers['Accept-Language'] = appLanguage
  //   }
  // }

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
    const data = error.response.data as unknown as ApiResult
    const errorStatus = error.response.status
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
