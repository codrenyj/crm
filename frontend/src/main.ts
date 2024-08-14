import { createApp } from 'vue';
import { createPinia } from 'pinia';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import App from './App.vue';
import router from './router';
import { usePermissStore } from './store/permiss';
import 'element-plus/dist/index.css';
import './assets/css/icon.css';

import store from './store' 
import Api from "./api/index";

const app = createApp(App);
app.use(store)
app.use(router);

// ElementPlusアイコンを登録する
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}



app.config.globalProperties.$api=Api;



app.mount('#app');
