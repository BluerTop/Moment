<template>
  <div id="app">
    <keep-alive>
      <router-view v-if="$route.meta.keepAlive"/>
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive"/>
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
import {login} from "./api/auth";
import {userInfo} from "./api/user";

export default {
  name: 'App',
  data() {
    return {}
  },
  mounted() {
    var device = this.deviceType().device;
    // if (device === 'windows pc' || device === 'mac' || device === 'linux pc') {
    //   window.location.href = "https://moment.bluer.top";
    // }
    this.authLogin()
  },
  methods: {
    authLogin() {
      const momentAuth = getMomentAuth();
      if (momentAuth === null){
        return false;
      }
      verifyLogin().then(re => {
        if (re.data) {
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
        } else {
          delTokenValue();
          delMomentUser();
        }
      });
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

</style>
