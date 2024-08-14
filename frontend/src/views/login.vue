<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">CRMシステム</div>
      <el-form
        :model="param"
        :rules="rules"
        ref="login"
        label-width="0px"
        class="ms-content"
      >
        <el-form-item prop="username">
          <el-input v-model="param.username" placeholder="ユーザー名">
            <template #prepend>
              <el-button :icon="User"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            placeholder="パスワード"
            v-model="param.password"
            @keyup.enter="submitForm(login)"
          >
            <template #prepend>
              <el-button :icon="Lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm(login)"
            >ログイン</el-button
          >
        </div>
        <el-checkbox
          class="login-tips"
          v-model="checked"
          label="パスワードを記憶する"
          size="large"
        />
        <p class="login-tips">
          Tips : ユーザー名とパスワードは適当に入力してください。
        </p>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
// import { useTagsStore } from '../store/tags';
import { usePermissStore } from "../store/permiss";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import type { FormInstance, FormRules } from "element-plus";
import { Lock, User } from "@element-plus/icons-vue";
import Api from "@/api/index";



interface LoginInfo {
  username: string;
  password: string;
}

const lgStr = localStorage.getItem("login-param");
const defParam = lgStr ? JSON.parse(lgStr) : null; //オブジェクトに変換して比較
const checked = ref(lgStr ? true : false); //レスポンシブデータを作成、チェックボックスが選択されているかどうかを判断

import { useUserStore } from '@/store/module/user' ;

const router = useRouter();
const param = reactive<LoginInfo>({
  //オブジェクトのレスポンシブプロキシを作成  データ連携レンダリングの鍵
  username: defParam ? defParam.username : "",
  password: defParam ? defParam.password : "",
});

const rules: FormRules = {
  username: [
    {
      required: true,
      message: "名前を入力してください",
      trigger: "blur",
    },
  ],
  password: [{ required: true, message: "パスワードを入力してください", trigger: "blur" }],
};
const permiss = usePermissStore();
const login = ref<FormInstance>();


const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate(async (valid: boolean) => {
    if (valid) {

      ElMessage.success('ログイン成功');
      const keys = permiss.defaultList[param.username == 'admin' ? 'admin' : 'user'];
      permiss.handleSet(keys);


      Api.post("/api/login/login",param).then(function(r){
        const userStore = useUserStore();

        console.log(r)

        userStore.id=r.data.id;
        userStore.username=r.data.username;
        userStore.token=r.data.token;

        router.push('/');

      })


    } else {
      ElMessage.error("ログイン失敗");
      return false;
    }
  });
};

// const tags = useTagsStore();
// tags.clearTags();
</script>

<style scoped>
.login-wrap {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  background-image: url(../assets/img/login-bg.jpg);
  background-size: 100%;
}
.ms-title {
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #333;
  font-weight: bold;
  padding-top: 10px;
}
.ms-login {
  width: 350px;
  border-radius: 5px;
  background: #fff;
}
.ms-content {
  padding: 10px 30px 30px;
}
.login-btn {
  text-align: center;
}
.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}
.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #333;
}
</style>
