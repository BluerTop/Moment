// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Vant from 'vant';
import 'vant/lib/index.css';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import { Cell, CellGroup } from 'vant';
import offline from './components/offline/index'
import VueTagsInput from '@johmun/vue-tags-input';
import AliPlayer from './components/AliPlayer/index';
import Nav from './components/NavigationBar/index';
import unsplash from './components/unsplash/index';

Vue.config.productionTip = false;
Vue.use(Vant);
Vue.use(ElementUI);
Vue.component("offline", offline);
Vue.component("VueTagsInput", VueTagsInput);
Vue.component("AliPlayer", AliPlayer);
Vue.component("Nav", Nav);
Vue.component("unsplash", unsplash);
Vue.use(Cell);
Vue.use(CellGroup);
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});
