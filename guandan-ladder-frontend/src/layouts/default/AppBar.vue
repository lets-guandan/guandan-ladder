<template>
  <v-app-bar color="cyan-lighten-1">

    <v-btn icon="mdi-menu" variant="text" @click="isShow = !isShow"/>
    <v-btn icon="mdi-home" variant="text" @click="toHome"/>
    <v-app-bar-title>掼蛋天梯</v-app-bar-title>
    <v-spacer></v-spacer>
    <v-dialog v-model="dialog" persistent width="auto">
      <template v-slot:activator="{ props }">
        <v-btn icon="mdi-power-cycle" v-bind="props"/>
      </template>
      <v-card>
        <v-card-text>确认退出登录？</v-card-text>
        <v-card-actions>
          <v-btn color="green-darken-1" variant="text" @click="dialog = false">
            取消
          </v-btn>
          <v-btn color="green-darken-1" variant="text" @click="dialog = false; exitLogin()">
            确认
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app-bar>
  <v-navigation-drawer
    v-model="isShow"
    location="left"
    temporary
  >
    <v-list>
      <v-list-item
        v-for="item in items"
        :key="item.title"
        :title="item.title"
        @click="jumpToPage(item)"
      ></v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import {useTokenStore} from "@/store/user-store";

const isShow = ref(false)

const dialog = ref(false)

const items = [
  {
    title: '待生效战绩',
    path: '/record/unconfirmed',
  },
  {
    title: '历史战绩',
    path: '/record/history',
  }
]

const router = useRouter();

function jumpToPage(item) {
  router.push(item.path)
}

function toHome() {
  router.push("/")
}

function exitLogin() {
  useTokenStore().token = undefined
  router.push("/login")
}
</script>
