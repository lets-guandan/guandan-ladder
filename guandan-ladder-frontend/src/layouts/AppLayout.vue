<template>
  <t-navbar title="掼蛋天梯榜" :fixed="true" style="z-index: 6000">
    <template #left>
      <app-icon size="24px" style="margin-right: 12px" @click="showLeftDrawer" />
      <home-icon size="24px" @click="toHome" />
    </template>
    <template #right>
      <account-menu />
    </template>
  </t-navbar>
  <div style="height: 48px" />

  <RouterView />

  <fab-add-button />
</template>

<script setup lang="ts">
import { AppIcon, HomeIcon } from 'tdesign-icons-vue-next'
import { useRouter } from 'vue-router'
import { ref } from 'vue'
import AccountMenu from '@/components/AccountMenu.vue'
import FabAddButton from '@/components/FabAddButton.vue'

const visible = ref(false)

function showLeftDrawer() {
  visible.value = true
}

const router = useRouter()

function toHome() {
  router.push('/')
}
</script>

<style scoped>
.navbar-placeholder {
  height: 48px;
}
</style>
