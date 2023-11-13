<template>
  <t-cell :note="data || '年 月 日 时 分'" @click="visible = true" />
  <t-popup v-model="visible" placement="bottom">
    <t-date-time-picker
      :mode="['date', 'minute']"
      title="选择日期"
      :defaultValue="nowTime()"
      @confirm="onConfirm"
      @cancel="onCancel"
    />
  </t-popup>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { useVModel } from '@vueuse/core'

const props = defineProps<{
  modelValue: string
}>()
const emit = defineEmits(['update:modelValue'])

const data = useVModel(props, 'modelValue', emit)

const visible = ref(false)
const pickerValueText = ref('')

const onCancel = () => {
  visible.value = false
}
const onConfirm = (value) => {
  data.value = value
  pickerValueText.value = value
  visible.value = false
}

function nowTime() {
  const now = new Date()
  const year = now.getFullYear()
  const month = String(now.getMonth() + 1).padStart(2, '0')
  const day = String(now.getDate()).padStart(2, '0')
  const hours = String(now.getHours()).padStart(2, '0')
  const minutes = String(now.getMinutes()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}:00`
}
</script>
