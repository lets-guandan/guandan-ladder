import { defineStore } from 'pinia'
import { useLocalStorage } from '@vueuse/core'

const tokenKey = 'token'

export const useTokenStore = defineStore('tokenStore', {
  // 其他配置...
  state: () => ({
    token: useLocalStorage<string | undefined>(tokenKey, undefined, {
      writeDefaults: false
    }),
  }),
  actions: {
    clean() {
      // 不能调用 reset，因为初始值可能是从 localStorage 加载的
      this.token = undefined
    }
  }
})
