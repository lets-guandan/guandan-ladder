<script setup lang="ts">
import {ref} from "vue";
import {isSuccess, userInfoApi} from "@/api";
import {UserVO} from "@/api/types";

const user = ref<UserVO>({
  uid: "",
  username: "",
  nickname: '',
  profile: "",
  avatarUrl: ""
})

userInfoApi().then(res => {
  if (isSuccess(res)) {
    user.value = res.data
  }
})
</script>

<template>
  <v-menu
    min-width="200px"
    rounded
  >
    <template v-slot:activator="{ props }">
      <v-btn size="large" v-bind="props">
        <v-avatar>
          <v-img
            :src="user.avatarUrl"
            alt="User"
          ></v-img>
        </v-avatar>
        <span style="margin-left: 8px">{{ user.nickname }}</span>
      </v-btn>
    </template>
    <v-list>
      <v-list-item>
        <v-list-item-title>修改密码</v-list-item-title>
      </v-list-item>
      <v-divider />
      <v-list-item>
        <v-list-item-title>退出登陆</v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>
</template>

<style scoped>

</style>
