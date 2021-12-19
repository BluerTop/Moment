import Vue from 'vue'
import Router from 'vue-router'
import Index from '../views/index/index'
import Login from '../views/login/index'
import Discover from '../views/discover/index'
import Msg from '../views/msg/index'
import Mine from '../views/mine/index'
import Word from '../views/release/word'
import Picture from '../views/release/picture'
import Graphic from '../views/release/graphic'
import Details from '../views/details/index'

Vue.use(Router);
// 解决重复点菜单报错问题
const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};

export default new Router({
  mode:'history',
  routes: [
    {
      path: '/',
      name: 'index',
      component: Index,
      meta: {
        keepAlive: true,
        scrollTop: 0,
      }
    },
    {
      path: '/discover',
      name: 'discover',
      component: Discover,
      meta: {
        keepAlive: true,
        scrollTop: 0,
      }
    },
    {
      path: '/msg',
      name: 'msg',
      component: Msg,
      meta: {
        keepAlive: true,
        scrollTop: 0,
      }
    },
    {
      path: '/mine',
      name: 'mine',
      component: Mine,
      meta: {
        keepAlive: false,
        scrollTop: 0,
      }
    },
    {
      path: '/details/:id',
      name: 'details',
      component: Details,
      meta: {
        keepAlive: false,
        scrollTop: 0,
      }
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
      meta: {
        keepAlive: false,
      }
    },
    {
      path: '/word',
      name: 'word',
      component: Word,
      meta: {
        keepAlive: false,
      }
    },
    {
      path: '/picture',
      name: 'picture',
      component: Picture,
      meta: {
        keepAlive: false,
      }
    },
    {
      path: '/graphic',
      name: 'graphic',
      component: Graphic,
      meta: {
        keepAlive: false,
      }
    }
  ]
})
