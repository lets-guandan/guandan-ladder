<template>
  <t-tab-bar v-model="value" shape="round" theme="tag" :split="false" class="wrapper">
    <t-tab-bar-item v-for="item in list" :key="item.value" :value="item.value">
      {{ item.label }}
    </t-tab-bar-item>
  </t-tab-bar>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const value = ref('label_1')
const list = ref([
  { value: 'label_1', label: '单人榜', icon: 'home' },
  { value: 'label_4', label: '组队榜', icon: 'user' }
])
</script>

<style scoped>
.wrapper {
  bottom: 68px !important;
  --td-tab-bar-height: 20px;
  width: 160px;
  left: 25%;
  right: 50%;
}
</style>
