import type { ApiResult, LoginVO } from '@/api/types'
import type { GameRecordDTO, UpdatePasswordVO, UserRankVO, UserVO } from '@/api/types'
import httpClient from '@/utils/axios'

export const isSuccess = (result: ApiResult) => {
  return result.code === 200
}

export function loginApi(loginVO: LoginVO) {
  return httpClient.post<ApiResult<string>>('/login', loginVO)
}

/** 获取排行榜接口 **/
export function rankListApi(rankType: number) {
  return httpClient.get<ApiResult<UserRankVO[]>>('/rank/list', {
    params: { rankType: rankType }
  })
}

/** 获取用户列表 */
export function listUserApi() {
  return httpClient.get<ApiResult<UserVO[]>>('/user/list')
}

/** 获取当前登陆用户信息 */
export function userInfoApi() {
  return httpClient.get<ApiResult<UserVO>>('/user/info')
}

/** 提交战局记录 */
export function reportGameRecordApi(gameRecord: GameRecordDTO) {
  return httpClient.post<ApiResult>('/game/record', gameRecord)
}

/** 获取待确认战局记录 */
export function getUnconfirmedRecordApi(myOrAll: number) {
  return httpClient.get<ApiResult>('/game/record/unconfirmed', {
    params: { myOrAll: myOrAll }
  })
}

/** 确认战局记录 */
export function confirmRecordApi(recordId: number) {
  return httpClient.post<ApiResult>('/game/confirm-record', { recordId: recordId })
}

/** 获取历史战绩列表 */
export function historyGameRecordApi(uid: number) {
  return httpClient.get<ApiResult>(`/game/list/${uid}`)
}

/** 获取本人历史战绩列表 */
export function myHistoryGameRecordApi() {
  return httpClient.get<ApiResult>('/game/list')
}

/** 修改密码 */
export function updatePasswordApi(updatePasswordVO: UpdatePasswordVO) {
  return httpClient.post<ApiResult>('/user/updatePassword', updatePasswordVO)
}
