import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: "/home",
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import("@/views/Home"),
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import("@/views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import("@/views/Register")
  },



  {
    path: '/userLogin',
    name: 'UserLogin',
    component: () => import("@/views/user/UserLogin")
  },
  {
    path: '/userHome',
    name: 'UserHome',
    component: () => import("@/views/user/UserHome")
  },
  {
    path: '/userPerson',
    name: 'UserPerson',
    component: () => import("@/views/user/UserPerson")
  },
  {
    path: '/userPassword',
    name: 'UserPassword',
    component: () => import("@/views/user/UserPassword")
  },
  {
    path: '/userQueryPile',
    name: 'UserQueryPile',
    component: () => import("@/views/user/UserQueryPile")
  },
  {
    path: '/userUsePile',
    name: 'UserUsePile',
    component: () => import("@/views/user/UserUsePile")
  },

  {
    path: '/test',
    name: 'Test',
    component: () => import("@/views/Test")
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 在刷新页面的时候重置当前路由
activeRouter()

function activeRouter() {
  const userStr = sessionStorage.getItem("user")
  if (userStr) {
    const user = JSON.parse(userStr)
    let root = {
      path: '/',
      name: 'Layout',
      component: Layout,
      redirect: "/home",
      children: []
    }
    user.permissions.forEach(p => {
      let obj = {
        path: p.path,
        name: p.name,
        component: () => import("@/views/" + p.name)
      };
      root.children.push(obj)
    })
    if (router) {
      router.addRoute(root)
    }
  }
}

router.beforeEach((to, from, next) => {
  if (to.path === '/login'
      || to.path === '/register'
      || to.path === '/test'
      || to.path === '/userLogin'
      || to.path === '/userHome'
      || to.path === '/userPerson'
      || to.path === '/userPassword'
      || to.path === '/userQueryPile'
      || to.path === '/userUsePile'
  ) {
    next()
    return
  }
  let user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
  if (!user.permissions || !user.permissions.length) {
    next('/login')
  } else if (!user.permissions.find(p => p.path === to.path)) {
    next('/login')
  } else {
    next()
  }
})

export default router
