import Vue from 'vue'
import Router from 'vue-router'
import LoginView from '../views/LoginView.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: LoginView
    }
  ]
})