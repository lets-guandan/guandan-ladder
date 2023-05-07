<template>
  <div class="login-container">
    <v-form ref="loginForm" class="login-form"
            label-position="left" @submit.prevent>

      <div class="title-container">
        <h3 class="title">掼蛋天梯</h3>
      </div>
      <v-text-field
        v-model="loginVO.userName"
        :rules="nameRules"
        label="账号"
        prepend-inner-icon="mdi-account"
      ></v-text-field>

      <v-text-field
        v-model="loginVO.password"
        :rules="passwordRules"
        label="密码"
        prepend-inner-icon="mdi-lock"
      ></v-text-field>
      <v-btn color="info" style="width:100%; margin-bottom:30px; margin-top: 10px" @click="guandanLogin">登录</v-btn>
    </v-form>
  </div>
</template>

<script lang="ts" setup>
import {reactive, toRaw} from "vue";
import {LoginVO} from "@/api/types";
import {isSuccess, loginApi} from "@/api";
import {useRouter} from "vue-router";
import {useTokenStore} from "@/store/user-store";

const nameRules = [
  (v: string) => !!v || '账号不允许为空'
];
const passwordRules = [
  (v: string) => !!v || '密码不允许为空'
]
const router = useRouter();
const loginVO = reactive<LoginVO>({
  userName: '',
  password: ''
});

function guandanLogin() {
  if (loginVO.userName === '' || loginVO.password === '') {
    alert("用户名或密码不允许为空");
    return;
  }
  loginApi(toRaw(loginVO))
    .then((result) => {
      if (isSuccess(result) && result.data != '') {
        useTokenStore().token = result.data
        router.replace("/");
        return;
      }
      alert(result.message);
      router.replace("/login");
    });
}
</script>

<style lang="scss">

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container {
    color: $cursor;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }
}
</style>

