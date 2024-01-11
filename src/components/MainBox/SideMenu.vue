<template>
  <el-aside width="200px">
    <el-scrollbar>
      <el-menu
        :default-active="route.fullPath"
        class="el-menu-vertical-demo"
        style="height: 100vh"
        :router="true"
      >
        <!-- //default-active就是代表默认显示那个模块 -->
        <template v-for="data in datalist" :key="data.path">
          <el-sub-menu
            :index="data.path"
            v-if="data.children.length && checkAuth(data.path)"
          >
            <!-- //el-sub-menu就是代表着下拉列表的设置 -->
            <template #title>
              <el-icon>
                <component :is="mapIcons[data.icon]"></component>
              </el-icon>
              <span>{{ data.title }}</span>
            </template>
            <template v-for="item in data.children" :key="item.path">
              <el-menu-item :index="item.path" v-if="checkAuth(item.path)">
                <!-- //展开之后放的内容了 -->
                <el-icon>
                  <component :is="mapIcons[item.icon]"></component>
                  <!-- //动态组件，把图标设置成mapIcons -->
                </el-icon>
                <span>{{ item.title }}</span>
              </el-menu-item>
            </template>
          </el-sub-menu>
          <el-menu-item :index="data.path" v-else-if="checkAuth(data.path)">
            <el-icon>
              <component :is="mapIcons[data.icon]"></component>
            </el-icon>
            <span>{{ data.title }}</span>
          </el-menu-item>
        </template>
      </el-menu>
    </el-scrollbar>
  </el-aside>
</template>

<script setup>
import {
  HomeFilled,
  Key,
  OfficeBuilding,
  UploadFilled,
  List,
  User,
} from "@element-plus/icons-vue";

import { onMounted, ref } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";
import { useUserStore } from "../../store/useUserStore";

const route = useRoute();

onMounted(() => {
  getList();
});
const datalist = ref([]);
const getList = async () => {
  var res = await axios.get("/adminapi/rights");
  // console.log(res.data)
  datalist.value = res.data;
};

//图标映射
const mapIcons = {
  HomeFilled,
  Key,
  OfficeBuilding,
  UploadFilled,
  List,
  User,
};

const { user } = useUserStore();
const checkAuth = (path) => {
  return user.role.rights.includes(path);
};
</script>
