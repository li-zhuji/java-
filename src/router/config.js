/*
 * @作者: kerwin
 */
//将对应的组件进行导入
import Home from "../views/home/Home.vue";
// 用户管理
import UserList from "../views/user-manage/UserList.vue";
// 权限管理
import RightList from "../views/right-manage/RightList.vue";
import RoleList from "../views/right-manage/RoleList.vue";

//实验室管理
import LabList from "../views/lab-manage/LabList.vue";
import AddLab from "../views/lab-manage/AddLab.vue";

//预约管理
import AuditList from "../views/book-manage/AuditList.vue";
import BookList from "../views/book-manage/BookList.vue";
import AddBook from "../views/book-manage/AddBook.vue";
const routes = [
  //固定的组件，访问哪个路径，就访问相对应的组件
  {
    path: "/index",
    component: Home,
  },
  {
    path: "/user-manage/list",
    component: UserList,
  },
  {
    path: "/right-manage/rolelist",
    component: RoleList,
  },
  {
    path: "/right-manage/rightlist",
    component: RightList,
  },
  {
    path: "/lab-manage/lablist",
    component: LabList,
  },
  {
    path: "/lab-manage/addlab",
    component: AddLab,
  },
  {
    path: "/book-manage/auditlist",
    component: AuditList,
  },
  {
    path: "/book-manage/booklist",
    component: BookList,
  },
  {
    path: "/book-manage/addbook",
    component: AddBook,
  },
];

export default routes;
