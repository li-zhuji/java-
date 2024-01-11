import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
//引入elementplus，一个vue组件
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import zhCn from "element-plus/dist/locale/zh-cn.mjs";

//粒子库
import Particles from "vue3-particles";

//导入axios配置
import "./util/axios.config";
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate); //持久化插件

createApp(App)
  .use(router)
  .use(pinia)
  .use(ElementPlus, {
    locale: zhCn,
  })
  .use(Particles)
  .mount("#app");
