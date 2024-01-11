<!--
 * @作者: kerwin
-->
<template>
  <div>
    <el-container style="height: 100vh">
      <SideMenu></SideMenu>
      <!-- //都放在components/Maninbox里面 -->
      <el-container direction="vertical">
        <!-- //因为在el-container中没有发现ehead，所以需要去传入一个参数vertical -->
        <TopHeader></TopHeader>
        <el-main>
          <el-scrollbar>
            <!-- //滚动条 -->
            <router-view></router-view>
          </el-scrollbar>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import SideMenu from "../components/MainBox/SideMenu.vue";
import TopHeader from "../components/MainBox/TopHeader.vue";
</script>
