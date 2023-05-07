<script setup lang="ts">
import {ref} from "vue";
import {isSuccess, userInfoApi} from "@/api";
import {UserVO} from "@/api/types";
import LogoutDialog from "@/components/LogoutDialog.vue";
import UpdatePasswordDialog from "@/components/UpdatePasswordDialog.vue";

const logoutDialog = ref()
const updatePasswordDialog = ref()

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
        <v-list-item-title @click="() => updatePasswordDialog.open()">修改密码</v-list-item-title>
      </v-list-item>
      <v-divider />
      <v-list-item>
        <v-list-item-title @click="() => logoutDialog.open()">退出登陆</v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>

  <updatePassword-dialog ref="updatePasswordDialog"/>
  <logout-dialog ref="logoutDialog"/>
</template>



<style scoped>

</style>
