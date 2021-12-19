<template>
  <div style="background-color: #FAFAFA;margin-top: 5px">
    <div v-show="status === 1">
      <van-cell-group inset :border="false">
        <van-cell title="您暂未登录" label="登录体验更多内容" value="前往登录" is-link @click="toLogin" :clickable="true"/>
      </van-cell-group>
    </div>
    <div v-show="status === 2">
      <van-cell-group inset :border="false" v-show="itemsNullShow">
        <van-cell title="你暂未关注任何人" value="前往关注" is-link @click="toRel('/graphic')" :clickable="true"/>
      </van-cell-group>
    </div>

    <!--动态-->
    <div v-show="status === 2 && !itemsNullShow" style="width: 90%; margin: 10px auto;padding-bottom: 20px">
      <el-skeleton v-show="itemsShow" style="width: 100%; margin: 10px auto 20px auto;" animated>
        <template slot="template">
          <div style="width: 100%;">
            <div class="van-cell van-cell--center" style="border-radius: 8px 8px 0 0 ">
              <i class="van-icon van-cell__left-icon">
                <el-skeleton-item
                  variant="image"
                  style="margin-left: -10px; width: 40px;height: 40px;margin-top: -10px;border-radius: 8px;"
                />
              </i>
              <div class="van-cell__title" style="margin-left: 10px">
                <el-skeleton-item variant="text" style="width: 30%;"/>
                <br/>
                <el-skeleton-item variant="text" style="width: 50%;"/>
              </div>
              <el-skeleton-item variant="button" style="width: 60px;height: 30px;border-radius: 20px"/>
            </div>
          </div>
          <div>
            <!--图片数据-->
            <div style="padding-bottom: 20px">
              <el-skeleton-item
                variant="image"
                style="width: 100%;height: 200px;border-radius: 10px;box-shadow: 5px 5px 5px rgba(0,0,0,.1);"
              />
            </div>
            <br/>
            <!--文字数据-->
            <div style="margin: -20px 0 20px 0 ;">
              <el-skeleton-item variant="text" style="width: 100%;height: 20px"/>
            </div>
            <!--其他-->
            <div style="margin-top: -15px;display: flex">
              <el-skeleton-item variant="button"
                                style="width: 80px;height: 25px;margin-right: 10px;border-radius: 20px"/>
              <el-skeleton-item variant="button"
                                style="width: 80px;height: 25px;margin-right: 10px;border-radius: 20px"/>
              <el-skeleton-item variant="button"
                                style="width: 80px;height: 25px;margin-right: 10px;border-radius: 20px"/>
            </div>
          </div>
        </template>
      </el-skeleton>
      <div v-for="(item,index) in items" :key="index" style="margin-bottom: 20px">
        <div style="width: 100%;">
          <div class="van-cell van-cell--center" style="border-radius: 8px 8px 0 0 ">
            <i class="van-icon van-cell__left-icon">
              <img :src="item.userAvatar" class="van-icon__image"
                   style="margin-left: -10px; width: 40px;height: 40px;margin-top: -10px;border-radius: 8px;box-shadow: 2px 2px 2px rgba(0,0,0,.1);"
                   :alt="item.userName">
            </i>
            <div class="van-cell__title" style="margin-left: 10px">
              <span style="font-weight: 500;">{{ item.userName }}</span>
              <div class="van-cell__label" style="margin-top: 0">{{ item.creatTime }}</div>
            </div>
            <div style="float: right">
              <van-button icon="like-o" type="default" size="small" round
                          style="border: 1px #FAFAFA solid;background-color: #FAFAFA;font-weight: 500;"
                          @click="itemsDowTop(item)">
                {{ item.likes }}
              </van-button>
            </div>
          </div>
        </div>
        <!--数据-->
        <div @click="toDetails(item.sid)">
          <!--图片数据-->
          <div style="padding-bottom: 20px" v-show="item.type === 'P' || item.type === 'G'">

            <!--外部嵌套层-->
            <div class="box">
              <div class="box1" style="display: flex;align-items: center;border-radius: 10px">
                <div class="box2" v-for="(image, index) in item.pictureDataList" :key="index">
                  <el-image v-bind:style="{width : deviceWidth()*0.90 + 'px','background-color' : item.imageColor}"
                            style="float: left;height:100%;border: 1px snow solid;border-radius: 8px;overflow:hidden;text-overflow:ellipsis;display:-webkit-box;-webkit-box-orient:vertical;"
                            :src="image"
                            fit="cover">
                    <div slot="placeholder" class="image-slot"
                         style="height: 300px;width: 100%;color: darkgrey;text-align: center;line-height: 300px">
                      <van-loading size="50"/>
                    </div>
                    <div slot="error" class="image-slot"
                         style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                      <van-icon name="photo-o" size="50"/>
                    </div>
                  </el-image>
                </div>
              </div>
            </div>
          </div>
          <!--文字数据-->
          <div v-show="item.type === 'G'" style="margin: -15px 0 20px 0 ;">
              <span style="font-weight: 500;font-size: 14px">
                {{ item.textData }}
              </span>
          </div>
          <div v-show="item.type === 'T'" style="margin: 0 0 20px 0 ;">
              <span style="font-weight: 500;font-size: 14px;">
                {{ item.textData }}
              </span>
          </div>
        </div>
        <!--其他-->
        <div style="margin-top: -15px;display: flex">
          <div class="box">
            <div class="box1">
              <div class="box2" style="border: none">
                <!--可见范围 O-自己可见，F-粉丝可见，A-所有人可见，C-亲密关系可见-->
                <van-button icon="shield-o" type="default" round disabled
                            style="border: none;background-color: #F2F2F2;font-weight: 500;text-align: center;margin-right: 10px"
                            size="small">
                  {{ item.authority === 'O' ? "自己可见" : "" }}
                  {{ item.authority === 'F' ? "粉丝可见" : "" }}
                  {{ item.authority === 'A' ? "所有人可见" : "" }}
                  {{ item.authority === 'C' ? "亲密关系可见" : "" }}
                </van-button>
                <!--标签-->
                <van-button type="default" round disabled v-for="(lable,ind) in item.labelList" :key="ind"
                            style="border: none;background-color: #F2F2F2;font-weight: 500;text-align: center;margin-right: 10px"
                            size="small">
                  {{ lable }}
                </van-button>
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>
    <unsplash/>
    <div style="z-index: 100;position: fixed; bottom: 70px;width:100%;text-align:center">
      <el-button type="primary" icon="el-icon-upload2" v-if="btnFlag" @click="backTop"
                 style="border: none;background-color: #576b95" size="mini" circle></el-button>
    </div>
    <Nav active="/"/>
  </div>
</template>

<script>
import {queryByMyFocus} from "../../api/dynamic";
import {Toast} from "vant";

import preventBack from 'vue-prevent-browser-back';
import {verifyLogin} from "../../api/auth";
import {delMomentUser, delTokenValue} from "../../utils/auth";

export default {
  mixins: [preventBack],//注入
  name: "index",
  data() {
    return {
      // 1-未登录、2-已登录
      status: 1,

      // 动态数据
      items: [],
      // 动态提示单元格
      itemsNullShow: false,
      // 动态骨架屏
      itemsShow: true,

      // 回到顶部
      btnFlag: false,
      scrollTop: 0,

      active: '关注',
    }
  },
  mounted() {
    this.init()
    window.addEventListener('scroll', this.scrollToTop)
  },
  destroyed() {
    window.removeEventListener('scroll', this.scrollToTop)
  },
  methods: {
    init() {
      const vanToast = Toast.loading({
        overlay: true,
        forbidClick: true,
        message: 'Loading...'
      });
      verifyLogin().then(res => {
        if (res.data) {
          this.status = 2;
          this.queryByMy();
        } else {
          delMomentUser();
          delTokenValue();
          this.itemsShow = false;
          this.status = 1;
        }
      });

      setTimeout(() => {
        vanToast.clear()
      }, 200);
    },
    // 动态图片下载
    itemsDowTop() {
      Toast('点赞~')
    },

    // 查询我的动态列表
    queryByMy() {
      const _t = this;
      queryByMyFocus().then(res => {
        if (res.success) {
          var data = res.data;
          if (data.length === 0) {
            _t.itemsNullShow = true;
          } else {
            _t.itemsNullShow = false;
            _t.itemsShow = false;
            _t.items = data
          }
        }
      }).catch(reason => {
        _t.itemsNullShow = true;
      });
    },

    // 点赞
    start() {
      Toast('点赞~');
    },
    // 查看详情
    toDetails(id) {
      this.$router.push('/details/' + id)
    },
    // 关注页
    toIndex() {
      this.$router.push('/')
    },
    // 登录页
    toLogin() {
      this.$router.push('/login')
    },
    // 前往发布
    toRel(path) {
      this.show = false;
      const _t = this;
      verifyLogin().then(res => {
        if (res.data) {
          setTimeout(function () {
            _t.$router.push(path);
          }, 100);
        } else {
          _t.$router.push("/login");
        }
      })
    },

    // 点击图片回到顶部方法，加计时器是为了过渡顺滑
    backTop() {
      const that = this;
      const vanToast = Toast.loading({
        duration: 0,
        overlay: true,
        forbidClick: true,
        message: 'Loading...'
      });
      setTimeout(() => {
        that.scrollTop = 0
        document.documentElement.scrollTop = document.body.scrollTop = 0
        vanToast.clear()
      }, 200);
    },

    // 为了计算距离顶部的高度，当高度大于60显示回顶部图标，小于60则隐藏
    scrollToTop() {
      const that = this
      that.scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop
      that.btnFlag = that.scrollTop > 1000;
    },

    // 获取设备宽度
    deviceWidth() {
      return window.innerWidth;
    },
  },
}
</script>

<style scoped>
.custom-image .van-empty__image {
  width: 90px;
  height: 90px;
}

.box {
  overflow: hidden;
}

.box1 {
  white-space: nowrap; /*文本不会换行，文本会在在同一行上继续*/
  overflow-y: auto; /*可滑动*/
}

/*自定义滚动条的伪对象选择器, CSS 可以隐藏滚动条*/
.box::-webkit-scrollbar {
  display: none;
}

/*自定义滚动条的伪对象选择器, CSS 可以隐藏滚动条*/
.box1::-webkit-scrollbar {
  display: none;
}

.box2 {
  display: inline-block; /*行内块元素*/
}
</style>
