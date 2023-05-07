<script setup lang="ts">
import {ref} from "vue";
import {useTokenStore} from "@/store/user-store";
import {useRouter} from "vue-router";

const dialog = ref(false)

const router = useRouter();

function exitLogin() {
  useTokenStore().token = undefined
  router.push("/login")
}

defineExpose({
  open: () => dialog.value = true
})
</script>

<template>
  <v-dialog v-model="dialog" persistent width="auto">
    <v-card>
      <v-card-text>确认退出登录？</v-card-text>
      <v-card-actions>
        <v-btn color="green-darken-1" variant="text" @click="dialog = false">
          取消
        </v-btn>
        <v-btn color="green-darken-1" variant="text" @click="dialog = false; exitLogin()">
          确认
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>
