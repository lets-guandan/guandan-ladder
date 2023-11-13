<template>
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
import { useRouter } from 'vue-router'
import { useVModel } from '@vueuse/core/index'

const props = defineProps<{
  modelValue: string
}>()
const emit = defineEmits(['update:modelValue'])

const visible = useVModel(props, 'modelValue', emit)

const router = useRouter()
function jumpToPage(path: string) {
  router.push(path)
  visible.value = false
}
</script>

<style scoped></style>
