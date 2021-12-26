<template>

  <div>
    <div v-show="tabbar">
      <div class="van-hairline--top-bottom van-tabbar van-tabbar--fixed" style="z-index: 1000">
        <div class="van-tabbar-item">
          <div class="van-tabbar-item__icon">
            <img :src="tabbarIcon('/')" @click="toPath('/')">
          </div>
          <div class="van-tabbar-item__text"><span :style="tabbarText('/')">首页</span></div>
        </div>
        <div class="van-tabbar-item">
          <div class="van-tabbar-item__icon">
            <img :src="tabbarIcon('/discover')" @click="toPath('/discover')">
          </div>
          <div class="van-tabbar-item__text"><span :style="tabbarText('/discover')">发现</span></div>
        </div>
        <div class="van-tabbar-item">
          <div class="van-tabbar-item__icon">
            <img :src="tabbarIcon('/msg')" @click="toPath('/msg')">
          </div>
          <div class="van-tabbar-item__text"><span :style="tabbarText('/msg')">消息</span></div>
        </div>
        <div class="van-tabbar-item">
          <div class="van-tabbar-item__icon">
            <img :src="tabbarIcon('/mine')" @click="toPath('/mine')">
          </div>
          <div class="van-tabbar-item__text"><span :style="tabbarText('/mine')">我的</span></div>
        </div>
      </div>
    </div>

  </div>

</template>

<script>
  import {verifyLogin} from "../../api/auth";
  import {Toast} from "vant";

  export default {
    name: "index",
    data() {
      return {
        // 底部导航栏
        icon: {
          index: 'https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/index.png',
          index_no: 'https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/index_no.png',
          discover: 'https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/discover.png',
          discover_no: 'https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/discover_no.png',
          add: 'https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/add.png',
          add_no: 'https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/add_no.png',
          mine: 'https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/mine.png',
          mine_no: 'https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/mine_no.png',
          msg: 'https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/msg.png',
          msg_no: 'https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/msg_no.png',
        },
        // 发布开关
        show: false,

        showPopover: false,
        // 通过 actions 属性来定义菜单选项
        actions: [{ text: '文字',uri:'/word' },{ text: '图片',uri:'/picture' },  { text: '图文',uri:'/graphic' },  { text: '音乐' },  { text: '视频' },  { text: '文章' }],
      }
    },
    props: [
      'active'
    ],
    mounted(){

    },
    methods: {
      // 判断是否显示底部导航栏
      tabbar() {
        var active = this.active;
        return active === '/' || active === '/discover' || active === '/mine' || active === '/msg';
      },
      // 判断显示底部导航栏图标
      tabbarIcon(active) {
        if (active === this.active) {
          if (active === '/') {
            return this.icon.index;
          }
          if (active === '/discover') {
            return this.icon.discover;
          }
          if (active === '/mine') {
            return this.icon.mine;
          }
          if (active === '/msg') {
            return this.icon.msg;
          }
        } else {
          if (active === '/') {
            return this.icon.index_no;
          }
          if (active === '/discover') {
            return this.icon.discover_no;
          }
          if (active === '/mine') {
            return this.icon.mine_no;
          }
          if (active === '/msg') {
            return this.icon.msg_no;
          }
        }
      },

      // 判断显示底部导航栏文字
      tabbarText(active) {
        if (active === this.active) {
          if (active === '/') {
            return 'color: #04B431';
          }
          if (active === '/discover') {
            return 'color: #04B431';
          }
          if (active === '/mine') {
            return 'color: #04B431';
          }
          if (active === '/msg') {
            return 'color: #04B431';
          }
        }
      },

      // 跳转页面
      toPath(path) {
        this.$router.push(path);
      },
      // 跳转页面
      toRel(path) {
        this.show = false;
        const _t = this;
        verifyLogin().then(res => {
          if (res.data){
              _t.$router.push(path);
          } else {
            _t.$router.push("/login");
          }
        })
      },
    },
  }
</script>

<style scoped>

</style>
