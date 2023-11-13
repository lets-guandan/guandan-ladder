<template>
  <t-cell :note="data ? showText : '请选择'" @click="visible = true" />
  <t-popup v-model="visible" placement="bottom" style="max-height: 60%; overflow-y: auto">
    <t-picker :columns="columns" @confirm="onConfirm" @cancel="visible = false" />
  </t-popup>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useVModel } from '@vueuse/core/index'
import type { PickerValue } from 'tdesign-mobile-vue/lib/picker'
import type { PickerColumnItem } from 'tdesign-mobile-vue/lib/picker/type'

const props = defineProps<{
  modelValue: string
  users: PickerColumnItem[]
}>()

const emit = defineEmits(['update:modelValue'])

const data = useVModel(props, 'modelValue', emit)

const visible = ref(false)
const showText = ref('请选择')
const columns = computed(() => [props.users])

const onConfirm = (value: PickerValue[], context) => {
  data.value = value[0] as string
  showText.value = columns.value[0][context.index].label
  visible.value = false
}
</script>

<style scoped>
.cell {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
