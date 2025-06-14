import { createApp } from 'vue'
import App from './App.vue'

import 'vxe-table/lib/style.css'
import VXETable from 'vxe-table'

const app = createApp(App)
app.use(VXETable)

app.mount('#app')