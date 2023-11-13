/**
 * 服务端统一返回信息
 */
export interface ApiResult<T = unknown> {
  // 状态码
  code: number
  // 数据
  data: T
  // 信息
  message: string
}

/**
 * 分页查询参数
 */
export interface PageParam {
  // 当前页码
  current?: number
  // 每页显示条数
  size?: number
  // 排序规则
  sort?: string
  // 其他额外属性
  [key: string]: any
}

/**
 * 分页返回结果
 */
export interface PageResult<T> {
  // 分页数据
  records: T[]
  // 数据总量
  total: number
}

export interface UserVO {
  /**
   * 用户id
   */
  uid: string

  /**
   * 用户名
   */
  username: string

  /**
   * 昵称
   */
  nickname: string

  /**
   * 个人简介
   */
  profile: string

  /**
   * 头像url
   */
  avatarUrl: string
}

export interface UserRankVO extends UserVO {
  winNum: number
  totalNum: number
  rank: number
}

/**
 * 比赛记录传输对象
 */
export interface GameRecordDTO {
  winUid1: string
  winUid2: string
  loseUid1: string
  loseUid2: string
  gameTime: string
}

/**
 * 用户登陆参数对象
 */
export interface LoginVO {
  userName: string
  password: string
}

/**
 * 修改密码参数对象
 */
export interface UpdatePasswordVO {
  oldPassword: string
  newPassword: string
  confirmPassword: string
}

/**
 * 排行榜类型枚举
 */
export enum RankListTypeEnum {
  // 胜场榜
  WIN_COUNT = 1,
  // 胜率榜
  WIN_RATE = 2
}
