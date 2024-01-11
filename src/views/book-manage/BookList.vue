<template>
  <div>
    <!-- 在element组件中，搜索框需要把表的data写成filterTableData，进行一个查询 -->
    <el-table :data="filterTableData" style="width: 100%">
      <!-- <el-table-column prop="roleName" label="角色名称" width="180" /> -->
      <!-- <el-table-column prop="icon" label="图标" width="180" /> -->
      <el-table-column label="名称">
        <template #default="scope">
          <el-link type="primary" @click="handleLocation(scope.row)">{{
            scope.row.lab.title
          }}</el-link>
        </template>
      </el-table-column>

      <el-table-column label="预约人" v-admin>
        <template #header>
          <el-input v-model="search" size="small" placeholder="请搜索" />
        </template>
        <template #default="scope">
          <div>{{ scope.row.book_username }}</div>
        </template>
      </el-table-column>
      <el-table-column label="预约时间">
        <template #default="scope">
          <el-tag style="height: 40px; line-height: 20px"
            >{{ moment(scope.row.book_time).format("YYYY-MM-DD") }}
            <!-- 对时间进行格式化 -->
            <br />
            {{ class_method(scope.row.book_class) }}
            <!-- 新建方法进行格式化处理 -->
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="预约状态">
        <template #default="scope">
          <el-tag :type="Book_state_type[scope.row.book_state]">
            <!-- 动态绑定了Book_state_type，里面有不同的颜色信息 -->
            {{ Book_state_text[scope.row.book_state] }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" v-teacher>
        <template #default="scope">
          <el-popconfirm
            title="你确定要撤销吗?"
            @confirm="handleDelete(scope.row)"
            confirm-button-text="确定"
            cancel-button-text="取消"
          >
            <template #reference>
              <el-button type="danger" round>撤销</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      v-model="dialogVisible"
      title="实验室位置"
      :fullscreen="true"
      v-if="dialogVisible"
    >
      <LabMap
        :x="currentItem.x"
        :y="currentItem.y"
        :t="currentItem.title"
      ></LabMap>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import axios from "axios";
import {
  ClassType,
  Book_state_text,
  Book_state_type,
  ADMIN,
  TEACHER,
} from "../../util/type";
import LabMap from "../../components/lab-manage/LabMap.vue";
import { useUserStore } from "../../store/useUserStore";
import moment from "moment";
const tableData = ref([]);
const search = ref("");
onMounted(() => {
  getList();
});

//实现查询操作，支持大小写，逻辑就是filiter就是一个过滤，如果search.value为空，就都显示，如果不为空，就显示
//book_username是否包含了有search.value值
const filterTableData = computed(() => {
  return tableData.value.filter(
    (item) =>
      !search.value ||
      item.book_username.toLowerCase().includes(search.value.toLowerCase())
  );
});
const { user } = useUserStore();
//拿到当前user对象
const getList = async () => {
  var res;
  //如果它是管理员就不需要传参，把所有预约信息都拿出来
  if (user.role.roleType === ADMIN) {
    res = await axios.get(`/adminapi/books`);
  } else {
    res = await axios.get(`/adminapi/books?book_username=${user.username}`);
    //查询当前这个用户它的预约信息，所以需要进行传参
  }
  tableData.value = res.data;
};

const class_method = (value) => {
  return ClassType.find((item) => item.value === value).label;
};

const currentItem = ref({});
const dialogVisible = ref(false);
const handleLocation = (item) => {
  dialogVisible.value = true;
  currentItem.value = item.lab;
};

const handleDelete = async ({ id }) => {
  await axios.delete(`/adminapi/books/${id}`);
  await getList();
};

//el是一个dom元素，因为只有在操作这一段中，才引用了它，当它被挂载到页面上时，并且角色身份不为老师，就删除掉这一模块
const vTeacher = {
  mounted(el) {
    // console.log(el)
    if (user.role.roleType !== TEACHER) {
      el.remove();
    }
  },
};

const vAdmin = {
  mounted(el) {
    // console.log(el)
    if (user.role.roleType !== ADMIN) {
      el.remove();
    }
  },
};
</script>
