<!--
 * @作者: kerwin
-->
<template>
    <div>
        <div style="min-height: 500px; justify-content: center;position: relative" id="map" />
        <el-dialog v-model="dialogVisible" title="添加实验室">

            <el-form ref="addFormRef" :model="addForm" :rules="rules" label-width="100px" class="ruleForm" status-icon>
                <el-form-item label="实验室名称" prop="title">
                    <el-input v-model="addForm.title" />
                </el-form-item>
                <el-form-item label="容纳人数" prop="capacity">
                    <el-input v-model="addForm.capacity" type="number" />
                </el-form-item>
                <el-form-item label="实验室类型" prop="lab_type">
                    <el-select v-model="addForm.lab_type" class="m-2" placeholder="请选择实验室类型" size="large"
                        style="width:100%">
                        <el-option v-for="item in LabType" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>

                </el-form-item>
                <el-form-item label="所属学院" prop="college_type">
                    <el-select v-model="addForm.college_type" class="m-2" placeholder="请选择学院" size="large"
                        style="width:100%">
                        <el-option v-for="item in CollegeType" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>

                </el-form-item>
            </el-form>

            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleAddConfirm()">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import { onMounted, ref, reactive } from 'vue';
import { Scene, ImageLayer, PointLayer, Popup } from '@antv/l7';
import { Map } from '@antv/l7-maps';
import {LabType,CollegeType} from '../../util/type'
import axios  from 'axios';
import { url } from '../../util/urlconfig';
let scene, popup;
onMounted(() => {
    scene = new Scene({
        id: 'map',
        map: new Map({
            center: [500, 500],
            zoom: 3,
            version: 'SIMPLE',
            mapSize: 1000,
            maxZoom: 5,
            minZoom: 2,
            pitchEnabled: false,
            rotateEnabled: false
        })
    });
    scene.setBgColor('rgb(94, 182, 140)');

    const imagelayer = new ImageLayer({}).source(
        url,
        {
            parser: {
                type: 'image',
                extent: [360, 400, 640, 600]
            }
        }
    );
    imagelayer.on('click', e => {
        console.log(e)
        let { lng, lat } = e.lngLat
        popup = new Popup({
            offsets: [0, 0],
            closeButton: false
        })
            .setLnglat(e.lngLat)
            .setHTML(`<button class="el-button el-button--primary" onclick="add_popup(${lng},${lat})">选择位置</button>`);
        scene.addPopup(popup);
    });


    scene.on('loaded', () => {
        getList()
        scene.addLayer(imagelayer);

    });

})


const getList =async  ()=>{
    var res = await axios.get("/adminapi/labs")
    // console.log(res.data)

    var list = res.data.map(item=>({
        x:item.x,
        y:item.y,
        t:item.title
    }))
    addTextLayer(list)
}

window.add_popup = (x, y) => {
    // console.log(x, y)
    dialogVisible.value = true

    //将pop隐藏
    popup.close()

    // x,y
    addForm.x = 500 + 2.8 * x
    addForm.y = 500 + 2.8 * y


}
//添加
const dialogVisible = ref(false)
const addFormRef = ref()
const addForm = reactive({
    title: "",
    capacity: "",
    lab_type: "",
    college_type: "",
    x: 0,
    y: 0
})
const rules = reactive({
    title: [
        { required: true, message: '请输入实验室名称', trigger: 'blur' },
    ],
    capacity: [
        { required: true, message: '请输入容纳人数', trigger: 'blur' },
    ],
    lab_type: [
        { required: true, message: '请选择实验室类型', trigger: 'blur' },
    ],
    college_type: [
        { required: true, message: '请选择学院', trigger: 'blur' },
    ],
})


const handleAddConfirm = () => {
    addFormRef.value.validate(async (valid) => {
        if (valid) {
            dialogVisible.value = false
            console.log(addForm)
            //发axios.post
            await axios.post(`/adminapi/labs`,addForm)
            //添加文字标注
            addTextLayer([
                {
                    "x":addForm.x,
                    "y":addForm.y,
                    "t":addForm.title
                }
            ])
        }
    })
}


const addTextLayer = (data) => {
    const textlayer = new PointLayer({ zIndex: 2 })
        .source(
            data,
            {
                parser: {
                    type: 'json',
                    x: 'x',
                    y: 'y'
                }
            }
        )
        .shape('t', 'text')
        .size(14)
        .active({
            color: '#00f',
            mix: 0.9
        })
        .color('rgb(13, 71, 161)')
        .style({
            textAnchor: 'center', // 文本相对锚点的位置 center|left|right|top|bottom|top-left
            spacing: 2, // 字符间距
            fontWeight: '800',
            padding: [1, 1], // 文本包围盒 padding [水平，垂直]，影响碰撞检测结果，避免相邻文本靠的太近
            stroke: '#ffffff', // 描边颜色
            strokeWidth: 2, // 描边宽度
            textAllowOverlap: true
        });
    scene.addLayer(textlayer);
}
</script>