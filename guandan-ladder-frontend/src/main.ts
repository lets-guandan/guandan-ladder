import { createApp } from 'vue'
import { createPinia } from 'pinia'

// 引入组件库的少量全局样式变量
import 'tdesign-mobile-vue/es/style/index.css';
// 统一页面元素的默认样式
import 'tdesign-mobile-vue/dist/reset.css'

import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.mount('#app')
