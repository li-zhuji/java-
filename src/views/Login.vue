<template>
  <!-- 引用粒子库 -->
  <Particles
    id="tsparticles"
    :particlesInit="particlesInit"
    :particlesLoaded="particlesLoaded"
    :options="config"
  />

  <div class="formContainer">
    <h3>智慧实验室管理平台</h3>
    <el-form
      ref="ruleFormRef"
      :model="ruleForm"
      :rules="rules"
      label-width="120px"
      class="ruleForm"
      status-icon
    >
      <!--
            ref:拿到当前组件的示例，校验方法
            model:绑定文本域
            rules：验证规则
            -->
      <el-form-item label="用户名" prop="username">
        <el-input v-model="ruleForm.username" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="ruleForm.password" type="password" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm(ruleFormRef)">
          <!-- 一点击登录按钮就跳转到submitForm进行操作 -->
          登录
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import { useUserStore } from "../store/useUserStore";
import { useRouter } from "vue-router";
import { ref, reactive } from "vue";
import { loadFull } from "tsparticles";
import { config } from "../util/config";
import axios from "axios";
import { ElMessage } from "element-plus";

//粒子配置
const particlesInit = async (engine) => {
  await loadFull(engine);
};

const particlesLoaded = async (container) => {};

//ref获取表单对象
const ruleFormRef = ref();
const ruleForm = reactive({
  username: "",
  password: "",
});

const rules = reactive({
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
});

const { changeUser } = useUserStore();

const router = useRouter();

//登录回调
const submitForm = async (formEl) => {
  if (!formEl) return;
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      const res = await axios.post(`/adminapi/users/login`, ruleForm);
      let { code, data } = res.data;
      if (code === 0) {
        changeUser(data);
        router.push("/");
      } else {
        ElMessage.error("用户名密码不匹配");
      }
    } else {
      console.log("error submit!", fields);
    }
  });
};
</script>

<style lang="scss" scoped>
.formContainer {
  width: 500px;
  height: 300px;
  position: fixed; //屏幕的中心
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  color: white;
  text-shadow: 2px 2px 5px black;
  text-align: center;
  z-index: 100;
  .ruleForm {
    margin-top: 50px;
  }

  h3 {
    font-size: 40px;
  }

  :deep(.el-form-item__label) {
    color: white;
    font-size: 16px;
    font-weight: 700;
  }
}
</style>
