import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import VueAMap from 'vue-amap';

// import 'dayjs/locale/zh-cn'
// import locale from 'element-plus/lib/locale/lang/zh-cn'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { User,Lock,Key,ArrowRight } from '@element-plus/icons-vue'

//这里引入或者使用好像有问题，现在使用单页面引入
import * as echarts from 'echarts'
import '@/assets/css/global.css'

import Plugin from 'v-fit-columns'

const app = createApp(App)

    .use(store)
    .use(router)
    .use(ElementPlus, {size: 'small'})
    .use(Plugin)
    .component('el-icon-user-solid', User)
    .component('el-icon-lock', Lock)
    .component('el-icon-key', Key)
    .component('ArrowRight', ArrowRight)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.mount('#app')



app.echarts = echarts
