<template>
  <div>
    <div>
      <van-nav-bar
        :title="momentUser.userName ?  momentUser.userName : '未登录'"
        @click-right="onClickRight">
        <template #right>
          <van-icon name="setting-o" size="18px" color="black"/>
        </template>
      </van-nav-bar>
    </div>

    <div>
        <div style="margin-bottom: 150px">
          <div style="width: 100%;">
            <div class="van-cell van-cell--center">
              <i class="van-icon van-cell__left-icon" style="margin-top: -38px">
                <img :src="momentUser.userAvatar"
                     class="van-icon__image" :alt="momentUser.userName">
              </i>
              <div class="van-cell__title" style="margin-left: 10px">
                <span>{{momentUser.userName}}</span>
                <div class="van-cell__label">{{momentUser.userSign}}</div>
              </div>
              <div class="van-cell__value"><span>👉</span></div>
            </div>
          </div>
          <div style="margin-top: 10px">
            <van-grid :column-num="3" :border="false">
              <van-grid-item icon="like-o" style="color: red" text="423关注"/>
              <van-grid-item icon="fire-o" style="color: red;" text="13534粉丝"/>
              <van-grid-item icon="good-job-o" style="color: red" text="9999999+获赞"/>
            </van-grid>
          </div>

          <div style="width: 100%;margin: 10px 0">
            <van-button type="default" style="width: 46%;margin-left: 3%;border-radius: 5px">
              <span style="font-weight:bold">我的喜欢</span>
              <van-icon name="like" color="red"/>
            </van-button>
            <van-button type="default" style="width: 46%;margin-right: 3%;float: right;border-radius: 5px">
              <span style="font-weight:bold">我的收藏</span>
              <van-icon name="label"/>
            </van-button>
          </div>

          <van-grid :border="false">
            <van-grid-item icon="smile-o" text="文字"/>
            <van-grid-item icon="smile-o" text="图文"/>
            <van-grid-item icon="smile-o" text="想法"/>
            <van-grid-item icon="smile-o" text="视频"/>
            <van-grid-item icon="smile-o" text="音乐"/>
            <van-grid-item icon="smile-o" text="足迹"/>
            <van-grid-item icon="smile-o" text="草稿箱"/>
            <van-grid-item icon="smile-o" text="帮助/建议"/>
          </van-grid>

          <van-cell-group inset>
            <van-cell title="单元格" value=">" :border="false"/>
            <van-cell title="单元格" value=">" :border="false"/>
            <van-cell title="单元格" value=">" :border="false"/>
            <van-cell title="单元格" value=">" :border="false"/>
            <van-cell title="单元格" value=">" label="描述信息" :border="false"/>
          </van-cell-group>

          <div style="font-size: 12px;text-align: center;color: #82848a">
            <p>逃离片刻的喧闹，只属于片刻的宁静...</p>
            <p>深陷浮躁的世界与喧闹的社交环境，你有多久没有停下驻足，观照内心？</p>
          </div>
        </div>
    </div>

    <Nav active="/mine"/>
  </div>
</template>

<script>
  import {delMomentUser, delTokenValue} from "../../utils/auth";
  import {userInfo} from "../../api/user";
  import {Toast} from "vant";

  import preventBack from 'vue-prevent-browser-back';

  export default {
    mixins: [preventBack],//注入
    name: "index",
    data() {
      return {
        // 用户信息
        momentUser: {
          userId: 1,
          userName: "未登录",
          userGender: "K",
          userBirthday: "2021-11-27",
          userAvatar: "https://oss.bluer.top/blue/timeline/2021-07-10/%E5%A4%B4%E5%83%8F.png",
          userSign: "快来\"武装\"一下自己吧...",
          userDate: "2021-11-27 14:01:45",
          userStatus: "E"
        },
      }
    },
    mounted() {
      this.init()
    },
    methods: {
      init() {
        this.getMomentUserSer()
      },

      // 获取用户信息
      getMomentUserSer() {
        const _t = this;
        userInfo().then(res => {
          if (res.success) {
            _t.momentUser = res.data;
          } else {
            _t.$router.push('/login');
              delTokenValue();
              delMomentUser();
          }
        });
      },

      // 进入设置
      onClickRight() {
        this.$router.push('/login');
      }
    }
  }
</script>

<style scoped>
  .van-icon__image {
    width: 60px;
    height: 60px;
    object-fit: contain;
    border-radius: 8px;
  }
</style>
