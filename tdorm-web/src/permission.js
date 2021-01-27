import router from './router'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getCookie } from '@/utils/CookieUtil'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

router.beforeEach((to, from, next) => {
  //根目录，直接放行，进入跟页面（登录页面）
  var user = getCookie("login");
  if(to.path === "/forget" || to.path === "/register"){
    //注册页面和忘记密码页面直接放行
    next()
  } else if(to.path === "/login") {
    //没有登录，去登录
    if(user === undefined || user === ""){
      next();
    }else {
      //已经登录了，直接去首页
      next("/")
    }
  } else if(user === undefined || user === ""){
    //用户没有登陆过，且去的不是登录页面，跳转到登录页面
    next("/login")
  }else {
    //用户已经登陆过了,想去哪里去哪里
    next()
  }
});

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
