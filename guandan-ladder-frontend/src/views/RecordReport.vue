<template>
  <h1 class="title">提交战绩</h1>
  <t-form
    ref="form"
    :rules="rules"
    :data="formData"
    reset-type="initial"
    show-error-message
    label-align="left"
    @submit="onSubmit"
  >
    <t-form-item arrow label="胜者1" name="winUid1" content-align="right">
      <user-picker v-model="formData.winUid1" :users="users" />
    </t-form-item>

    <t-form-item arrow label="胜者2" name="winUid2" content-align="right">
      <user-picker v-model="formData.winUid2" :users="users" />
    </t-form-item>

    <t-form-item arrow label="败者1" name="loseUid1" content-align="right">
      <user-picker v-model="formData.loseUid1" :users="users" />
    </t-form-item>

    <t-form-item arrow label="败者2" name="loseUid2" content-align="right">
      <user-picker v-model="formData.loseUid2" :users="users" />
    </t-form-item>

    <t-form-item arrow label="掼蛋时间" name="gameTime" content-align="right">
      <date-time-picker v-model="formData.gameTime" />
    </t-form-item>

    <div class="button-group">
      <t-button theme="primary" type="submit" size="large">提交</t-button>
      <t-button theme="default" variant="base" type="reset" size="large">重置</t-button>
    </div>
  </t-form>

  <div id="message" style="display: none" />
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, toRaw } from 'vue'
import type { GameRecordDTO } from '@/api/types'
import DateTimePicker from '@/components/DateTimePicker.vue'
import UserPicker from '@/components/UserPicker.vue'
import { isSuccess, listUserApi, reportGameRecordApi } from '@/api'
import { PickerColumnItem } from 'tdesign-mobile-vue/lib/picker/type'
import { Message } from 'tdesign-mobile-vue'

const form = ref(null)

const formData = reactive<GameRecordDTO>({
  winUid1: '',
  winUid2: '',
  loseUid1: '',
  loseUid2: '',
  gameTime: ''
})

const users = ref<PickerColumnItem[]>([])
listUserApi().then((res) => {
  users.value = res.data.map((x) => ({ label: x.nickname, value: x.uid }))
})

const showMessage = (theme: string, content = '这是一条普通通知信息', duration = 5000) => {
  if (Message[theme]) {
    Message[theme]({
      offset: [60, 100],
      content,
      duration,
      icon: true,
      zIndex: 20000,
      context: document.querySelector('.title')
    })
  }
}

const onSubmit = (e: any) => {
  if (e.validateResult === true) {
    reportGameRecordApi(toRaw(formData))
      .then((res) => {
        if (isSuccess(res)) {
          showMessage('success', '战绩上报成功！')
        } else {
          showMessage('error', '战绩上报异常！')
        }
      })
      .catch((e) => {
        showMessage('error', '战绩上报异常！' + e.message)
      })
  }
}

const fields = ['winUid1', 'winUid2', 'loseUid1', 'loseUid2']
function uidValidators(field: string) {
  return [
    { validator: (val: any) => val !== '', message: '不能为空' },
    {
      validator: (val: any) => {
        console.log(1)
        for (let f of fields) {
          if (f !== field && val === formData[f]) {
            return false
          }
        }
        return true
      },
      message: '不能重复选择参战人员'
    }
  ]
}

const rules = {
  winUid1: uidValidators('winUid1'),
  winUid2: uidValidators('winUid2'),
  loseUid1: uidValidators('loseUid1'),
  loseUid2: uidValidators('loseUid2'),
  gameTime: [{ validator: (val: any) => val !== '', message: '不能为空' }]
}

onMounted(() => {
  // @ts-ignore
  form.value.setValidateMessage(rules)
})
</script>

<style scoped>
.title {
  font-size: 24px;
  font-weight: 700;
  line-height: 32px;
  padding: 24px 16px 0;
  color: #000000e6;
}

.button-group {
  background-color: #fff;
  box-sizing: border-box;
  padding: 16px;
  display: flex;
  justify-content: space-between;
  position: relative;
  border-bottom: 0.5px solid #e7e7e7;

  .t-button {
    height: 32px;
    flex: 1;

    &:not(:last-child) {
      flex: 1;
      margin-right: 16px;
    }
  }
}
</style>
