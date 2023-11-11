<template>
  <t-popover placement="bottom-right" theme="light" class="abcde">
    <template #triggerElement>
      <div style="display: flex; align-items: center">
        <t-avatar size="small" :image="user.avatarUrl"></t-avatar>
        <span style="margin-left: 8px">{{ user.nickname }}</span>
      </div>
    </template>
    <template #content>
      <div class="abcde">
        <t-cell-group class="abcde">
          <t-cell class="a">修改密码</t-cell>
          <t-cell class="a">退出登陆</t-cell>
        </t-cell-group>
      </div>
    </template>
  </t-popover>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { isSuccess, userInfoApi } from '@/api'
import type { UserVO } from '@/api/types'
import { TPopover } from 'tdesign-mobile-vue'

const logoutDialog = ref()
const updatePasswordDialog = ref()

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
</script>

<style scoped>
.a {
  --td-cell-horizontal-padding: 0px;
  --td-cell-vertical-padding: 8px;
}
</style>
