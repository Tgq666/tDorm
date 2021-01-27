import Vue from 'vue'
import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en' // lang i18n
import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'
import qs from 'qs'
import '@/icons' // icon
import '@/permission'
import { errorHint, normalHint, successHint, successNotification } from '@/utils/Hint'

Vue.prototype.errorHint = errorHint;
Vue.prototype.successHint = successHint;
Vue.prototype.normalHint = normalHint;
Vue.prototype.successNotification = successNotification;
Vue.prototype.$qs = qs

// set ElementUI lang to EN
Vue.use(ElementUI)
// 如果想要中文版 element-ui，按如下方式声明
// Vue.use(ElementUI)

Vue.config.productionTip = false
Vue.config.devtools = true

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
