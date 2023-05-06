<template>
  <v-app-bar color="cyan-lighten-1">

    <v-btn variant="text" icon="mdi-menu" @click="isShow = !isShow" />
    <v-btn variant="text" icon="mdi-home" @click="toHome" />

    <v-app-bar-title>掼蛋天梯</v-app-bar-title>

    <v-spacer></v-spacer>
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

const isShow = ref(false)

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
</script>
