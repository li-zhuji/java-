/*
 * @作者: kerwin
 */
//实验室类型
const LabType = [
    {
        label: "物理实验室",
        value: 1,
    },
    {
        label: "化学实验室",
        value: 2,
    }, {
        label: "电子设计实验室",
        value: 3,
    },
    {
        label: "生命科学研究实验室",
        value: 4,
    }
]

const CollegeType = [
    {
        label: "理学院",
        value: 1,
    },
    {
        label: "信息科学工程学院",
        value: 2,
    }, {
        label: "经济管理学院",
        value: 3,
    },
    {
        label: "人文学院",
        value: 4,
    }
]

 const  LabColorType = {
    1:"#02909d",
    2:"#eb5c20",
    3:"#a1d4bd",
    4:"#d05667"
}

 const  CollegesColorType = {
    1:"#fbcb4a",
    2:"#14c145",
    3:"#eedeb0",
    4:"#065279"
}

const ClassType = [
    {
        label:"第一节课",
        value:1
    },
    {
        label:"第二节课",
        value:2
    }, {
        label:"第三节课",
        value:3
    },
    {
        label:"第四节课",
        value:4
    }
]

const Book_state_text=["审核中","已批准","被驳回"]
const Book_state_type=["warning","success","danger"]

const ADMIN = 1
const TEACHER = 2


const AUDIT = 0;
const APPROVE = 1;
const REJECT  =2;

export {
    LabType,
    CollegeType,
    LabColorType,
    CollegesColorType,
    ClassType,
    Book_state_text,
    Book_state_type,
    ADMIN,
    TEACHER,
    AUDIT,
    APPROVE,
    REJECT
}