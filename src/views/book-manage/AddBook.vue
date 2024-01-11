<template>
  <div>
    <div
      style="min-height: 500px; justify-content: center; position: relative"
      id="map"
    />
    <el-dialog v-model="dialogVisible" :title="currentItem.t">
      <el-form
        ref="addFormRef"
        :model="addForm"
        :rules="rules"
        label-width="100px"
        class="ruleForm"
        status-icon
      >
        <el-form-item label="预约日期" prop="book_time">
          <el-date-picker
            v-model="addForm.book_time"
            type="date"
            placeholder="请选择日期"
            style="width: 100%"
            :disabled-date="disabledDate"
            @change="handleSelectChange"
          />
          <!-- 在每次时间进行更改的时候，都会调用@change这个方法，disabled-date动态绑定disabledDate用来禁用 -->
        </el-form-item>

        <el-form-item label="预约课节" prop="book_class">
          <el-select
            v-model="addForm.book_class"
            class="m-2"
            placeholder="请选择课节"
            size="large"
            style="width: 100%"
          >
            <el-option
              v-for="item in ClassType"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              :disabled="disableSelect.includes(item.value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="预约原因" prop="book_reason">
          <el-input v-model="addForm.book_reason" type="textarea" />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleCancel()">取消</el-button>
          <el-button type="primary" @click="handleAddConfirm()">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, ref, reactive } from "vue";
import { Scene, ImageLayer, PointLayer } from "@antv/l7";
import { Map } from "@antv/l7-maps";
import { ClassType } from "../../util/type";
import axios from "axios";
import { useUserStore } from "../../store/useUserStore";
import { ElMessage } from "element-plus";
import { url } from "../../util/urlconfig";

let scene;
const { user } = useUserStore();
onMounted(() => {
  scene = new Scene({
    id: "map",
    map: new Map({
      center: [500, 500],
      zoom: 3,
      version: "SIMPLE",
      mapSize: 1000,
      maxZoom: 5,
      minZoom: 2,
      pitchEnabled: false,
      rotateEnabled: false,
    }),
  });
  scene.setBgColor("rgb(94, 182, 140)");

  const imagelayer = new ImageLayer({}).source(url, {
    parser: {
      type: "image",
      extent: [360, 400, 640, 600],
    },
  });

  scene.on("loaded", () => {
    getList();
    scene.addLayer(imagelayer);
  });
});

const getList = async () => {
  var res = await axios.get("/adminapi/labs");
  // console.log(res.data)

  var list = res.data.map((item) => ({
    x: item.x,
    y: item.y,
    t: "预约" + item.title,
    id: item.id, //预约哪个实验室
  }));
  addTextLayer(list);
};

//添加
const dialogVisible = ref(false);
const addFormRef = ref();
const addForm = reactive({
  book_time: "",
  book_class: "",
  book_reason: "",
});
const rules = reactive({
  book_time: [{ required: true, message: "请选择日期", trigger: "blur" }],
  book_class: [{ required: true, message: "请选择课节", trigger: "blur" }],
  book_reason: [{ required: true, message: "请输入原因", trigger: "blur" }],
});

const handleAddConfirm = () => {
  addFormRef.value.validate(async (valid) => {
    if (valid) {
      dialogVisible.value = false;
      // console.log(addForm)
      //发axios.post
      await axios.post(`/adminapi/books`, {
        ...addForm,
        //将addForm展开之后，再加上book_username,lab_id字段
        book_username: user.username,
        lab_id: currentItem.value.id,
      });
      clear();

      ElMessage({
        message: "提交预约成功,审核中",
        type: "success",
      });
      //消息框，提交成功
    }
  });
};
const currentItem = ref({});

const addTextLayer = (data) => {
  const textlayer = new PointLayer({ zIndex: 2 })
    .source(data, {
      parser: {
        type: "json",
        x: "x",
        y: "y",
      },
    })
    .shape("t", "text")
    .size(14)
    .active({
      color: "#00f",
      mix: 0.9,
    })
    .color("rgb(13, 71, 161)")
    .style({
      textAnchor: "center", // 文本相对锚点的位置 center|left|right|top|bottom|top-left
      spacing: 2, // 字符间距
      fontWeight: "800",
      padding: [1, 1], // 文本包围盒 padding [水平，垂直]，影响碰撞检测结果，避免相邻文本靠的太近
      stroke: "#ffffff", // 描边颜色
      strokeWidth: 2, // 描边宽度
      textAllowOverlap: true,
    });

  textlayer.on("click", (e) => {
    // console.log(e)
    currentItem.value = e.feature;
    dialogVisible.value = true;
  });
  scene.addLayer(textlayer);
};

const handleCancel = () => {
  dialogVisible.value = false;
  clear();
};

const clear = () => {
  addForm.book_class = "";
  addForm.book_time = "";
  addForm.book_reason = "";
};
//禁用日期选择
const disabledDate = (time) => {
  return (
    time.getTime() < Date.now() ||
    time.getTime() > Date.now() + 3600 * 1000 * 24 * 7
  );
};

// 预约的逻辑：前端一直监听着一个change事件，每次改变完，取出哪些是被禁用的，最后在option中用disable进行禁用
// 在回调函数中，进行post请求向后端进行要数据，然后存放在disableSelect数组中

//改变日期的回调，关键
const handleSelectChange = async () => {
  addForm.book_class = "";
  //如果有人钻空子选了，我们就在每一次日期回调的过程中把book_class进行清空
  // lab_id book_time

  //post请求 book_time，post请求在后端能够接受到正确的信息
  var res = await axios.post(`/adminapi/books/select`, {
    book_time: addForm.book_time,
    lab_id: currentItem.value.id,
    //加入进来的预约时间和预约当前实验室的id
  });
  // console.log()

  //后端返回被禁用的时间，然后map进行一个映射，得到book_class然后存储到disableSelect数组中
  disableSelect.value = res.data.map((item) => item.book_class);
  // 前端接受课节数组，进行禁用
};

const disableSelect = ref([]);
</script>
