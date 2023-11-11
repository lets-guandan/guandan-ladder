import type { AxiosError } from 'axios'
import type { AlertMode, MessageInfo, RequestOptions } from '@/utils/axios/types'
import type { ApiResult } from '@/api/types'
import { isSuccess } from '@/api'
import SnackbarMessage from '@/hooks/use-message'

/**
 * 请求方法封装
 * @param request
 * @param requestOptions 请求配置
 */
export function doRequest<T = unknown>(
  request: Promise<ApiResult<T>>,
  requestOptions?: RequestOptions<T>
) {
  // 填充默认值
  const finalOptions: RequestOptions<T> = Object.assign(
    {
      checkSuccess: isSuccess,
      // 成功消息提示
      successMessage: false,
      // 失败消息提示
      failMessage: true,
      // 错误消息提示
      errorMessage: true
    },
    requestOptions
  )
  request
    .then((res) => {
      if (finalOptions.checkSuccess!(res)) {
        alertSuccessMessage(finalOptions, res)
        finalOptions.onSuccess?.(res)
      } else {
        alertFailMessage(finalOptions, res)
        finalOptions.onFail?.(res)
      }
    })
    .catch((e) => {
      alertErrorMessage(finalOptions, e)
      finalOptions.onError?.(e)
    })
    .finally(() => {
      finalOptions.onFinally?.()
    })
}

function alertMessage(
  type: string,
  optionMessage: string | boolean | MessageInfo,
  resMessage: string
) {
  if (optionMessage === false) {
    return
  }

  let message
  let mode
  if (typeof optionMessage == 'string') {
    message = optionMessage
  } else if (typeof optionMessage == 'object') {
    message = optionMessage.message || resMessage
    mode = optionMessage.mode
  } else {
    message = resMessage
  }

  alert({
    type,
    message,
    mode
  })
}

function alert(alertInfo: { message: string; mode?: AlertMode; type: string }) {
  const alertMessage = alertInfo.message
  SnackbarMessage(alertMessage)
}

function alertSuccessMessage<T>(requestOptions: RequestOptions<T>, res: ApiResult<T>) {
  alertMessage('success', requestOptions.successMessage!, res.message)
}

function alertFailMessage<T>(requestOptions: RequestOptions<T>, res: ApiResult<T>) {
  alertMessage('warning', requestOptions.failMessage!, res.message)
}

function alertErrorMessage<T>(requestOptions: RequestOptions<T>, e: AxiosError) {
  // 未被 axios拦截器处理过，则在这里继续处理
  if (requestOptions.errorMessage && !e.resolved) {
    const response = e.response
    const data = response?.data as ApiResult
    const errorMessage = data?.message || e.message || 'error request'
    alertMessage('error', requestOptions.errorMessage!, errorMessage)
  }
}
