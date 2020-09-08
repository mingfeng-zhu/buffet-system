import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import api from './api/api'
import utils from './utils/utils.js'
import './assets/iconfont/iconfont.js'
import './assets/iconfont/icon.css'

import 'amfe-flexible/index.js'

import Vant from 'vant';
import 'vant/lib/index.css';
Vue.use(Vant);


Vue.config.productionTip = false
Vue.prototype.$utils = utils
Vue.prototype.$api = api

// eslint-disable-next-line no-unused-vars
router.afterEach((to, from,next)=>{

  window.scrollTo(0,0)

})


new Vue({
  render: h => h(App),
  router
}).$mount('#app')
