<template>
  <div class="login-container">
    <div class="title-container">
      <h3 class="title">掼蛋天梯</h3>
    </div>
    <t-form ref="loginForm" class="login-form" label-width="0">
      <t-form-item name="userName">
        <t-input v-model="formData.userName" borderless placeholder="用户名">
          <template #prefixIcon>
            <user-icon />
          </template>
        </t-input>
      </t-form-item>

      <t-form-item name="password" type="password">
        <t-input v-model="formData.password" borderless placeholder="密码">
          <template #prefixIcon>
            <lock-on-icon />
          </template>
        </t-input>
      </t-form-item>

      <t-button
        size="large"
        theme="primary"
        block
        style="width: 100%; margin-bottom: 30px; margin-top: 10px"
        @click="doLogin"
      >
        登录
      </t-button>
    </t-form>
  </div>

  <div>
    <a href="https://beian.miit.gov.cn" target="_blank">皖ICP备‪19023656‬号-1</a>
  </div>
</template>

<script lang="ts" setup>
import { reactive, toRaw } from 'vue'
import type { LoginVO } from '@/api/types'
import { isSuccess, loginApi } from '@/api'
import { useRouter } from 'vue-router'
import { useTokenStore } from '@/stores/user-store'

import { UserIcon, LockOnIcon } from 'tdesign-icons-vue-next'

const nameRules = [(v: string) => !!v || '账号不允许为空']
const passwordRules = [(v: string) => !!v || '密码不允许为空']
const router = useRouter()
const formData = reactive<LoginVO>({
  userName: '',
  password: ''
})

function doLogin() {
  if (formData.userName === '' || formData.password === '') {
    alert('用户名或密码不允许为空')
    return
  }
  loginApi(toRaw(formData)).then((result) => {
    if (isSuccess(result) && result.data != '') {
      useTokenStore().token = result.data
      router.replace('/')
      return
    }
    alert(result.message)
    router.replace('/login')
  })
}
</script>
