// Composables
import {createRouter, createWebHistory} from 'vue-router'

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

router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next()
  } else if (!window.localStorage.getItem('token')) {
    next('/login')
  } else {
    next()
  }
})

export default router
