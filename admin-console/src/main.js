import { createApp } from 'vue'
import App from './App.vue'

import 'vxe-table/lib/style.css'
import VXETable from 'vxe-table'
import router from "@/router/index.js";

const app = createApp(App)
app.use(VXETable)
app.use(router)
app.mount('#app')