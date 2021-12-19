<template>
  <div style="background-color: #FAFAFA">
    <div class="van-nav-bar__placeholder" style="height: 60px;">
      <div class="van-nav-bar van-nav-bar--fixed" style="z-index: 10">
        <div class="van-nav-bar__content van-hairline--bottom" style="padding: 10px 0 0 0;border-radius: 0 0 10px 10px;">
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

export default {
  name: "index",
  components: {
    mine, focus
  },
  data() {
    return {
      active: 'mine',
    }
  },
  methods: {
    actives(a) {
      this.active = a;
    }
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
  font-size: 16px;
  color: #82848a;
}
</style>
