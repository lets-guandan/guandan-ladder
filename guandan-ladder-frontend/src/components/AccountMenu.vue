<template>
  <t-popover placement="bottom-right" theme="light">
    <template #triggerElement>
      <div style="display: flex; align-items: center">
        <t-avatar size="small" :image="user.avatarUrl"></t-avatar>
        <span style="margin-left: 8px">{{ user.nickname }}</span>
      </div>
    </template>
    <template #content>
      <t-cell-group>
        <t-cell class="menu_cell" @click="() => (updatePasswordDialog = true)">修改密码</t-cell>
        <t-cell class="menu_cell" @click="() => (logoutDialog = true)">退出登陆</t-cell>
      </t-cell-group>
    </template>
  </t-popover>

  <t-dialog
    v-model:visible="updatePasswordDialog"
    close-on-overlay-click
    title="修改密码"
    cancel-btn="取消"
  >
    <template #confirmBtn>
      <t-button
        class="t-dialog__button t-dialog__button--horizontal"
        theme="primary"
        type="submit"
        @click="submit"
      >
        提交
      </t-button>
    </template>

    <t-form ref="form" :data="formData" :rules="rules" show-error-message label-width="0">
      <t-form-item name="oldPassword">
        <t-input
          v-model="formData.oldPassword"
          borderless
          type="password"
          :clearable="false"
          placeholder="原密码"
        >
          <template #suffixIcon>
            <BrowseOffIcon />
          </template>
        </t-input>
      </t-form-item>

      <t-form-item name="newPassword">
        <t-input
          v-model="formData.newPassword"
          borderless
          type="password"
          :clearable="false"
          placeholder="新密码"
        >
          <template #suffixIcon>
            <BrowseOffIcon />
          </template>
        </t-input>
      </t-form-item>

      <t-form-item name="confirmPassword">
        <t-input
          v-model="formData.confirmPassword"
          borderless
          type="password"
          :clearable="false"
          placeholder="确认新密码"
        >
          <template #suffixIcon>
            <BrowseOffIcon />
          </template>
        </t-input>
      </t-form-item>
    </t-form>
  </t-dialog>

  <t-dialog
    v-model:visible="logoutDialog"
    title="确认退出登录？"
    cancel-btn="取消"
    confirm-btn="退出"
    @confirm="logout"
  >
  </t-dialog>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref, toRaw } from 'vue'
import { isSuccess, updatePasswordApi, userInfoApi } from '@/api'
import type { UpdatePasswordVO, UserVO } from '@/api/types'
import { TPopover } from 'tdesign-mobile-vue'
import { useTokenStore } from '@/stores/user-store'
import { useRouter } from 'vue-router'
import { BrowseOffIcon } from 'tdesign-icons-vue-next'
import type { FormInstanceFunctions } from 'tdesign-mobile-vue/es/form'

const logoutDialog = ref(false)
const updatePasswordDialog = ref(false)

const user = ref<UserVO>({
  uid: '',
  username: '',
  nickname: '',
  profile: '',
  avatarUrl: ''
})

userInfoApi().then((res) => {
  if (isSuccess(res)) {
    user.value = res.data
  }
})

const formData = reactive<UpdatePasswordVO>({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const rules = {
  oldPassword: [{ validator: (val: any) => val !== '', message: '不能为空' }],
  newPassword: [
    { validator: (val: any) => val !== '', message: '不能为空' },
    { validator: (val: any) => val.length >= 6, message: '密码长度不能小于6' }
  ],
  confirmPassword: [
    { validator: (val: any) => val !== '', message: '不能为空' },
    { validator: (val: any) => val === formData.newPassword, message: '两次输入密码不一致' }
  ]
}

const form = ref<FormInstanceFunctions>(null)

onMounted(() => {
  // @ts-ignore
  form.value.setValidateMessage(rules)
})

function submit() {
  form.value.validate().then((e) => e === true && updatePassword())
}

function updatePassword() {
  updatePasswordApi(toRaw(formData))
    .then((res) => {
      if (isSuccess(res)) {
        form.value.reset()
        updatePasswordDialog.value = false
        alert('修改密码成功！')
      } else {
        alert('修改失败咯.' + res.message)
      }
    })
    .catch((e) => {
      alert('修改失败咯!' + e.message)
    })
}

const router = useRouter()
const logout = () => {
  useTokenStore().token = undefined
  router.push('/login')
}
</script>

<style scoped>
.menu_cell {
  --td-cell-horizontal-padding: 0px;
  --td-cell-vertical-padding: 8px;
}
</style>
