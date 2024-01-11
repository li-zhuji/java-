<!--
 * @作者: kerwin
-->
<template>
    <div>
        <el-table :data="tableData" style="width: 100%">
            <!-- <el-table-column prop="roleName" label="角色名称" width="180" /> -->
            <!-- <el-table-column prop="icon" label="图标" width="180" /> -->
            <el-table-column label="名称">
                <template #default="scope">
                    <el-link type="primary" @click="handleLocation(scope.row)">{{ scope.row.title }}</el-link>
                </template>
            </el-table-column>


            <el-table-column label="所属学院">
                <template #default="scope">
                    <el-tag :color="CollegesColorType[scope.row.college_type]">{{ college_method(scope.row.college_type)
                    }}</el-tag>
                </template>
            </el-table-column>

            <el-table-column label="实验室类型">
                <template #default="scope">
                    <el-tag :color="LabColorType[scope.row.lab_type]">{{ lab_method(scope.row.lab_type) }}</el-tag>
                </template>
            </el-table-column>

            <el-table-column prop="capacity" label="容纳人数" />
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button round type="warning" @click="handleUpdate(scope.row)">更新</el-button>
                    <el-popconfirm title="你确定要删除吗?" @confirm="handleDelete(scope.row)" confirm-button-text="确定"
                        cancel-button-text="取消">
                        <template #reference>
                            <el-button type="danger" round>删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>


        <el-dialog v-model="dialogVisible" title="实验室位置" :fullscreen="true" v-if="dialogVisible">
            <LabMap :x="currentItem.x" :y="currentItem.y" :t="currentItem.title"></LabMap>
        </el-dialog>


        <el-dialog v-model="dialogUpdateVisible" title="更新实验室">

            <el-form ref="updateFormRef" :model="updateForm" :rules="rules" label-width="100px" class="ruleForm" status-icon>
                <el-form-item label="实验室名称" prop="title">
                    <el-input v-model="updateForm.title" />
                </el-form-item>
                <el-form-item label="容纳人数" prop="capacity">
                    <el-input v-model.number="updateForm.capacity" type="number" />
                </el-form-item>
                <el-form-item label="实验室类型" prop="lab_type">
                    <el-select v-model="updateForm.lab_type" class="m-2" placeholder="请选择实验室类型" size="large"
                        style="width:100%">
                        <el-option v-for="item in LabType" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>

                </el-form-item>
                <el-form-item label="所属学院" prop="college_type">
                    <el-select v-model="updateForm.college_type" class="m-2" placeholder="请选择学院" size="large"
                        style="width:100%">
                        <el-option v-for="item in CollegeType" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>

                </el-form-item>
            </el-form>

            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogUpdateVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleUpdateConfirm()">
                        确定
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import { ref, onMounted,reactive } from 'vue'
import axios from 'axios';
import { CollegeType, LabType, LabColorType, CollegesColorType } from '../../util/type'
import LabMap from '../../components/lab-manage/LabMap.vue'
const tableData = ref([])
onMounted(() => {
    getList()
})

const getList = async () => {
    var res = await axios.get(`/adminapi/labs`)
    tableData.value = res.data
}

const college_method = (value) => {
    var obj = CollegeType.find(item => item.value === value)
    return obj.label
}

const lab_method = (value) => {
    var obj = LabType.find(item => item.value === value)
    return obj.label
}

//预览功能
const currentItem = ref({})
const dialogVisible = ref(false)
const handleLocation = (item) => {
    dialogVisible.value = true
    currentItem.value = item
}


//更新
const dialogUpdateVisible = ref(false)
const updateFormRef = ref()
const updateForm = reactive({
    title: "",
    capacity: "",
    lab_type: "",
    college_type: "",
  
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

const handleUpdate = (item)=>{
    dialogUpdateVisible.value = true

    updateForm.title = item.title
    updateForm.capacity = item.capacity
    updateForm.lab_type = item.lab_type
    updateForm.college_type = item.college_type

    currentItem.value = item
}

const handleUpdateConfirm  =()=>{
    updateFormRef.value.validate(async(valid)=>{
        if(valid){
            dialogUpdateVisible.value = false
            // console.log(updateForm)
            await axios.put(`/adminapi/labs/${currentItem.value.id}`,updateForm)

            await getList()
        }
    })
}

//删除
const handleDelete = async({id})=>{
    await axios.delete(`/adminapi/labs/${id}`)
    await getList()
}
</script>

<style scoped lang="scss">
.el-tag {
    --el-tag-text-color: white;
}
</style>