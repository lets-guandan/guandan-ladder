// Composables
import {createRouter, createWebHistory} from 'vue-router'
import {useTokenStore} from "@/store/user-store";

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/layouts/default/Login.vue')
  },
  {
    path: '/',
    component: () => import('@/layouts/default/Default.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "home" */ '@/views/Rank.vue'),
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = useTokenStore().token;
  if (to.path === '/login') {
    next()
  } else if (!token) {
    next('/login')
  } else {
    next()
  }
})

export default router
