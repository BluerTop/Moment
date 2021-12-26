<template>
  <div style="background-color: #FAFAFA">
    <div class="van-nav-bar__placeholder" style="height: 60px;">
      <div class="van-nav-bar " :class=" 'van-nav-bar--fixed'" style="z-index: 10">
        <div class="van-nav-bar__content van-hairline--bottom"
             style="padding: 10px 0 0 0;border-radius: 0 0 10px 10px;">
          <div style="margin-left: 20px;width: 100%;">
            <div style="float: left;margin-top: -3px">
              <span @click="actives('focus')" :class="active === 'focus' ? 'activeStyOn' : 'activeStyOf'"
                    style="margin-right: 20px">
                关注
              </span>

              <span @click="actives('mine')" :class="active === 'mine' ? 'activeStyOn' : 'activeStyOf'">
                我的
              </span>
            </div>
            <div style="float: right;margin-right: 20px;margin-top: -5px">
              <el-image src="https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/bitbug_favicon.ico"
                        style="width: 30px;"/>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div>
      <focus v-show="active === 'focus'"/>
      <mine v-show="active === 'mine'"/>
    </div>
  </div>
</template>

<script>
import mine from './mine';
import focus from './focus';
import {getFocus, getMine, setFocus, setMine} from "../../utils/scrollTop";
import {Toast} from "vant";

export default {
  name: "index",
  components: {
    mine, focus
  },
  data() {
    return {
      active: 'mine',
      interval: "",
    }
  },
  mounted() {
    const _t = this;
    _t.active = localStorage.getItem('active') === null ? 'mine' : localStorage.getItem('active')
  },
  activated: function () {
    const _t = this;
    const active = _t.active;
    if (active === 'focus') {
      document.documentElement.scrollTop = document.body.scrollTop = getFocus()
    }
    if (active === 'mine') {
      document.documentElement.scrollTop = document.body.scrollTop = getMine()
    }
    _t.interval = setInterval(() => {
      _t.scrollToTop()
    }, 500);
  },
  beforeRouteLeave(to, form, next) {
    // 清除定时器
    clearInterval(this.interval);
    clearInterval(this.interval);
    clearInterval(this.interval);
    next()
  },
  methods: {
    // 为了计算距离顶部的高度
    scrollToTop() {
      const scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
      const active = this.active;
      if (active === 'focus') {
        setFocus(scrollTop)
      }
      if (active === 'mine') {
        setMine(scrollTop)
      }
    },
    // 切换标签
    actives(active) {
      localStorage.setItem('active', active);
      this.active = active;
      if (active === 'focus') {
        document.documentElement.scrollTop = document.body.scrollTop = getFocus()
      }
      if (active === 'mine') {
        document.documentElement.scrollTop = document.body.scrollTop = getMine()
      }
    },
  }
}
</script>

<style scoped>
.activeStyOn {
  font-weight: 500;
  font-size: 18px;
  border-bottom: 3px black solid;
  padding-bottom: 5px;
}

.activeStyOf {
  font-weight: 400;
  font-size: 18px;
  color: #82848a;
}
</style>
