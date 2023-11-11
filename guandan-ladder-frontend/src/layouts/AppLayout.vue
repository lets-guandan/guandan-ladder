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
  <div class="navbar-placeholder" />

  <RouterView />

  <div style="height: 68px" />
  <t-fab :icon="iconFunc" @click="report" />

  <t-popup v-model="visible" placement="left" style="width: 256px">
    <t-cell-group>
      <t-cell title="组队排行榜" hover :bordered="false" @click="jumpToPage('/team-rank')" />
      <t-cell
        title="待生效战绩"
        hover
        :bordered="false"
        @click="jumpToPage('/record/unconfirmed')"
      />
      <t-cell title="历史战绩" hover :bordered="false" @click="jumpToPage('/record/history')" />
    </t-cell-group>
  </t-popup>
</template>

<script setup lang="ts">
import { AppIcon, HomeIcon, AddIcon } from 'tdesign-icons-vue-next'
import { useRouter } from 'vue-router'
import { ref, h } from 'vue'
import AccountMenu from '@/components/AccountMenu.vue'

const visible = ref(false)

function showLeftDrawer() {
  visible.value = true
}

const router = useRouter()

function toHome() {
  router.push('/')
}

function jumpToPage(path: string) {
  router.push(path)
  visible.value = false
}

const iconFunc = () => h(AddIcon, { size: '24px' })

function report() {}
</script>

<style scoped>
.navbar-placeholder {
  height: 48px;
}
</style>
