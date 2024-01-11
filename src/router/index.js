/*
 * @作者: kerwin
 */
// 下载vue-router

import { createRouter, createWebHashHistory } from "vue-router";
import Login from "../views/Login.vue";
import MainBox from "../views/MainBox.vue";
import NotFound from "../views/notfound/NotFound.vue";
import RouteConfig from "./config";
import { useRouterStore } from "../store/useRouterStore";
import { useUserStore } from "../store/useUserStore";
//#/login createWebHashHistory
///login createWebHistory
import NProgress from "nprogress";
import "nprogress/nprogress.css";
const routes = [
  {
    path: "/login",
    name: "login",
    component: Login, //如果路径是login就转到Login这个组件中去
  },
  {
    path: "/mainbox",
    name: "mainbox",
    component: MainBox,
  },
];

const router = createRouter({
  history: createWebHashHistory(), //#/login
  routes,
});

//添加路由
// router.addRoute("mainbox",{
//     path:"/index",   是在mainbox里面进行设置，所以index就是mainbox的孩子，所以直接访问index就可以直接调用mainbox了
//     component:Home
// })

//路由拦截
router.beforeEach((to, from, next) => {
  //显示进度条
  NProgress.start();

  const { isGetterRouter } = useRouterStore();
  //在pinia中调用然后会返回一个初值为false
  const { user } = useUserStore();
  // next()
  if (to.name === "login") {
    //如果你要去login的话就放行
    next();
  } else {
    if (!user.role) {
      //如果你从未登录过，进行登录
      //跳转登录
      next({
        path: "/login",
      });
    } else {
      if (!isGetterRouter) {
        //这样子的话，这里就不会一直进行配置了，因为你已经配置过一遍了

        //remove mainbox
        router.removeRoute("mainbox");
        ConfigRouter(); //已授权
        next({
          path: to.fullPath, //强行往这个路径上再跳转一次，因为是第一次，配置没有生效
        });
      } else {
        next();
      }
    }
  }
});

router.afterEach(() => {
  //关闭进度条
  NProgress.done();
});

const ConfigRouter = () => {
  //创建mainbox
  router.addRoute({
    path: "/mainbox",
    name: "mainbox",
    component: MainBox,
  });
  let { changeRouter } = useRouterStore();
  RouteConfig.forEach((item) => {
    checkPermission(item.path) && router.addRoute("mainbox", item);
    //进行判断身份
    //循环的去添加孩子组件5
  });

  //重定向
  router.addRoute("mainbox", {
    path: "/",
    redirect: "/index",
  });
  //默认跳转到/index

  //404
  router.addRoute("mainbox", {
    path: "/:pathMatch(.*)*",
    name: "not found",
    component: NotFound,
  });
  // console.log(router.getRoutes())

  //true
  changeRouter(true); //在这里将值改为true下次就不会再次调用了
  //走了一遍就不需要再进行配置
};

//
const checkPermission = (path) => {
  const { user } = useUserStore();

  return user.role.rights.includes(path);
  //判断当前角色的权限数组判断报不报含这个path
};
export default router;
