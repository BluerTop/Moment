<template>
  <div id="app">
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive"/>
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive"/>

    <!--  底部  -->
    <div v-show="showNav($route.path)"
         style="color: #82848a;position: fixed; bottom: 70px; width:100%;text-align: center">
      <el-image :src="add" style="width: 40px;float: right;margin-right: 25px" @click="showShare =! showShare"/>
      <br/>
      <br/>
      <br/>
      <el-button type="primary" icon="el-icon-upload2" v-if="btnFlag" @click="backTop"
                 style="border: none;background-color: #576b95;position: absolute;margin-top: -30px;margin-left: -10px"
                 size="small"
                 circle></el-button>
    </div>
    <van-share-sheet
      v-model="showShare"
      title="选择发布类型"
      :options="options"
      @select="onSelect"
    />
    <Nav :active="$route.path" v-show="showNav($route.path)"/>
  </div>
</template>

<script>

import {
  delMomentUser,
  delTokenValue,
  getMomentAuth,
  setMomentAuth,
  setMomentUser,
  setTokenValue
} from "./utils/auth";
import {login, verifyLogin} from "./api/auth";
import {userInfo} from "./api/user";
import {Toast} from "vant";
import {delFocus, delMine} from "./utils/scrollTop";

export default {
  name: 'App',
  data() {
    return {
      // 发布图标
      add: 'https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/add.png',
      // 发布开关
      showShare: false,
      // 发布块
      options: [
        {name: '文字', icon: 'https://oss.bluer.top/blue/timeline/2021-07-08/%E6%96%87%E6%9C%AC.png', uri: '/word'},
        {name: '图片', icon: 'https://oss.bluer.top/blue/timeline/2021-07-08/%E5%9B%BE%E7%89%87.png', uri: '/picture'},
        {name: '图文', icon: 'https://img01.yzcdn.cn/vant/custom-icon-fire.png', uri: '/graphic'},
        {name: '音乐', icon: 'https://img01.yzcdn.cn/vant/custom-icon-fire.png',},
        {name: '视频', icon: 'https://img01.yzcdn.cn/vant/custom-icon-fire.png',},
        {name: '文章', icon: 'https://oss.bluer.top/blue/timeline/2021-07-08/%E6%96%87%E7%AB%A0.png',}
      ],

      // 回到顶部
      btnFlag: false,
      scrollTop: 0,
    }
  },
  mounted() {
    window.addEventListener('scroll', this.scrollToTop)
    delMine();
    delFocus();
    const device = this.deviceType().device;
    // if (device === 'windows pc' || device === 'mac' || device === 'linux pc') {
    //   window.location.href = "https://moment.bluer.top";
    // }
    this.authLogin()
  },

  destroyed() {
    window.removeEventListener('scroll', this.scrollToTop)
  },
  methods: {
    // 自动登录
    authLogin() {
      const momentAuth = getMomentAuth();
      if (momentAuth === null) {
        return false;
      }
      verifyLogin().then(re => {
        if (!re.data) {
          login(momentAuth).then(res => {
            delTokenValue();
            delMomentUser();
            if (res.success) {
              setTokenValue(res.data.tokenValue);
              setMomentAuth(JSON.stringify(data));
              document.cookie = res.data.tokenName + "=" + res.data.tokenValue;
              userInfo().then(res2 => {
                if (res2.success) {
                  setMomentUser(JSON.stringify(res2.data));
                } else {
                  return false;
                }
              });
            }
          })
        }
      });
    },
    // 跳转发布页
    onSelect(action) {
      this.toRel(action.uri)
    },

    // 点击图片回到顶部方法
    backTop() {
      this.scrollTop = 0
      document.documentElement.scrollTop = document.body.scrollTop = 0
    },

    // 为了计算距离顶部的高度，当高度大于60显示回顶部图标，小于60则隐藏
    scrollToTop() {
      this.scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
      this.btnFlag = this.scrollTop > 1000;
    },
    // 跳转页面
    toRel(path) {
      const _t = this;
      _t.showShare = false;
      verifyLogin().then(res => {
        if (res.data) {
          setTimeout(function () {
            _t.$router.push(path);
          }, 100);
        } else {
          Toast("请先登录...")
        }
      })
    },
    // 判断是否显示底部导航栏
    showNav(path) {
      if (path === '/') {
        return true
      } else if (path === '/discover') {
        return true
      } else if (path === '/msg') {
        return true
      } else if (path === '/mine') {
        return true
      } else {
        return false;
      }
    },
    // 判断设备类型
    deviceType() {
      let agent = navigator.userAgent.toLowerCase();
      return {
        device: function () {
          if (/windows/.test(agent)) {
            return 'windows pc';
          } else if (/iphone|ipod/.test(agent) && /mobile/.test(agent)) {
            return 'iphone';
          } else if (/ipad/.test(agent) && /mobile/.test(agent)) {
            return 'ipad';
          } else if (/android/.test(agent) && /mobile/.test(agent)) {
            return 'android';
          } else if (/linux/.test(agent)) {
            return 'linux pc';
          } else if (/mac/.test(agent)) {
            return 'mac';
          } else {
            return 'other';
          }
        }(),
      };
    },
    // 获取设备宽度
    deviceWidth() {
      return window.innerWidth;
    },
  },
}
</script>

<style>
* {
  -webkit-touch-callout: none;
  -moz-user-select: none;
  -webkit-user-select: none;
  -ms-user-select: none;
  -khtml-user-select: none;
  user-select: none;
}
</style>
