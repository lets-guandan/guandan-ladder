<template>
  <div class="login-container">
    <div class="login-container-header">
      <span class="login-title">掼蛋天梯榜</span>
      <span class="login-logo">
        <t-image class="image-container" alt="一张图片" :src="imageSrc" fit="cover" shape="circle" />
      </span>
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
    </t-form>

    <t-button
        size="large"
        theme="primary"
        style="margin: 30px 20px 10px 20px"
        @click="doLogin"
    >
      登录
    </t-button>

    <div class="login-footer">
      <a href="https://beian.miit.gov.cn" target="_blank">皖ICP备2023010551号-2</a>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive, toRaw } from 'vue'
import type { LoginVO } from '@/api/types'
import { isSuccess, loginApi } from '@/api'
import { useRouter } from 'vue-router'
import { useTokenStore } from '@/stores/user-store'

import { UserIcon, LockOnIcon } from 'tdesign-icons-vue-next'

const imageSrc = ref(new URL('../assets/logo.png', import.meta.url).href);

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

<style scoped>
.login-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: auto;
  background: #f0f2f5;
}

.login-container-header {
  color: #000000d9;
  font-weight: 600;
  font-size: 33px;
  margin: 40px 0;
}

.login-title {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 44px;
  line-height: 44px;
}

.login-logo {
  display: flex;
  align-items: center;
  justify-content: center;

  .image-container {
    width: 50%;
    max-width: 250px;
  }
}

.login-form {
  margin: 0 20px;
}

.login-footer {
  text-align: center;
  position: fixed;
  bottom: 10px;
  width: 100%;
}
</style>
