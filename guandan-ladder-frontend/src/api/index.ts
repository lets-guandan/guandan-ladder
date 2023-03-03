import type {ApiResult} from '@/api/types'
import httpClient from "@/utils/axios";
import {GameRecordDTO, UserRankVO, UserVO} from "@/api/types";

export const isSuccess = (result: ApiResult) => {
  return result.code === 200
}

/** 获取排行榜接口 **/
export function rankListApi() {
  return httpClient.get<ApiResult<UserRankVO[]>>("/rank-list")
}


/** 获取用户列表 */
export function listUserApi() {
  return httpClient.get<ApiResult<UserVO[]>>('/user/list')
}

/** 提交战局记录 */
export function reportGameRecordApi(gameRecord: GameRecordDTO) {
  return httpClient.post<ApiResult>('/game/record', gameRecord)
}
