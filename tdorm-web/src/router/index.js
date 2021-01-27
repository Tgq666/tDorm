import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import UserRegister from '@/views/user/UserRegister'
import UserForget from '@/views/user/UserForget'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/register',
    name: 'UserRegister',
    component: UserRegister
  },
  {
    path: '/forget',
    name: 'UserForget',
    component: UserForget
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/pay',
    component: Layout,
    redirect: '/pay/paid',
    name: 'Example',
    meta: { title: '室费管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'paid',
        component: () => import('@/views/expense/index'), // Parent router-view
        name: 'paid',
        meta: { title: '室费查看',icon: 'table' },
        children:[
          {
            path: 'needPay',
            name: 'NeedPay',
            component: () => import('@/views/expense/paid/needPay'),
            meta: { title: '未付室费', icon: 'table' }
          },
          {
            path: 'havePaid',
            name: 'HavePaid',
            component: () => import('@/views/expense/paid/havePaid'),
            meta: { title: '已结室费', icon: 'table' }
          }
        ]
      },
      {
        path: 'entry',
        name: 'Entry',
        component: () => import('@/views/expense/entry/index'),
        meta: { title: '室费录入', icon: 'form' }
      }
    ]
  },

  // {
  //   path: '/contract',
  //   redirect: '/contract',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'contract',
  //       name: 'Contract',
  //       component: () => import('@/views/contract/index'),
  //       meta: { title: '寝室公约', icon: 'form' }
  //     }
  //   ]
  // },
  //
  // {
  //   path: '/nested',
  //   component: Layout,
  //   redirect: '/nested/menu1',
  //   name: 'Nested',
  //   meta: { title: '个人信息', icon: 'nested' },
  //   children: [
  //     {
  //       path: 'menu2',
  //       component: () => import('@/views/nested/menu2/index'),
  //       name: 'Menu2',
  //       meta: { title: 'menu2' }
  //     }
  //   ]
  // },

  // {
  //   path: 'external-link',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
  //       meta: { title: 'External Link', icon: 'link' }
  //     }
  //   ]
  // },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
