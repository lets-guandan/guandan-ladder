<script setup lang="ts">
import {reactive, ref, toRaw} from "vue";
import {UpdatePasswordVO} from "@/api/types";
import {isSuccess, updatePassword} from "@/api";

const updatePasswordDialog = ref(false)
const confirmPassword = ref('')

const formModel = reactive<UpdatePasswordVO>({
  oldPassword: '',
  newPassword: ''
})

function saveInputValue() {
// 处理保存输入框值的逻辑 调用修改密码接口 并 判断原密码
  if (formModel.oldPassword == "" || formModel.newPassword == "" || confirmPassword.value == "") {
    alert("空的不行哦")
  } else if (confirmPassword.value != formModel.newPassword) {
    alert("新密码和确认密码不一致哦")
  } else {
    // console.log(formModel)
    updatePassword(toRaw(formModel))
      .then(res => {
        if (isSuccess(res)) {
          alert("修改成功啦，下次用新密码吧")
          this.updatePasswordDialog = false;
          formModel.oldPassword = ""
          formModel.newPassword = ""
          confirmPassword.value = ""
        } else {
          alert('修改失败咯.' + res.message)
        }
      })
      .catch(e => {
        alert('修改失败咯!' + e.message)
      })
  }
}

defineExpose({
  open: () => updatePasswordDialog.value = true
})
</script>

<template>
  <v-dialog v-model="updatePasswordDialog" persistent>
    <v-card>
      <v-card-title>请输入信息</v-card-title>
      <v-card-text>
        <v-text-field v-model="formModel.oldPassword" label="原密码" required></v-text-field>
        <v-text-field v-model="formModel.newPassword" label="新密码" required></v-text-field>
        <v-text-field v-model="confirmPassword" label="确认密码" required></v-text-field>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" text="" @click="updatePasswordDialog = false">取消</v-btn>
        <v-btn color="primary" text="" @click="saveInputValue()">保存</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>


<style scoped>

</style>
