<template>
  <div style="height: 48px" />
  <t-fab :icon="iconFunc" @click="report" :style="style" />
</template>

<script setup lang="ts">
import { h } from 'vue'
import { AddIcon } from 'tdesign-icons-vue-next'
import { useRouter } from 'vue-router'
const iconFunc = () => h(AddIcon, { size: '24px' })

const style = 'right: 16px; bottom: 16px;'

const router = useRouter()
function report() {
  router.push('/record-report')
}
</script>
