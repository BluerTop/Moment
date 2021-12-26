<template>
  <div style="background-color: #FAFAFA;">
    <div class="van-nav-bar__placeholder" style="height: 60px;">
      <div class="van-nav-bar van-nav-bar--fixed van-hairline--bottom" style="height: 60px;">
        <div class="van-nav-bar__content" style="height: 60px;">
          <div class="van-nav-bar__left">
            <i class="van-icon van-icon-arrow-left" style="color: black; font-size: 18px;" @click="toFocus"/>
          </div>
          <div style="margin-left: 30px;width: 100%;padding: 8px">
            <van-search
              v-model="value"
              shape="round"
              show-action
              placeholder="请输入用户名"
            >
              <template #action>
                <div @click="onSearch">查找</div>
              </template>
            </van-search>
          </div>
        </div>
      </div>
    </div>
    <!--用户列表-->
    <div style="width: 100%;margin: 0 auto;padding-bottom: 100px">
      <el-skeleton style="width: 100%" v-show="!items.list">
        <template slot="template">
          <div style="width: 95%;background-color: white;margin: 10px auto;border-radius: 10px">
            <div style="width: 100%;">
              <div class="van-cell van-cell--center" style="border-radius: 8px 8px 0 0 ">
                <i class="van-icon van-cell__left-icon">
                  <el-skeleton-item variant="image"
                                    style="margin-left: -10px; width: 40px;height: 40px;margin-top: -10px;border-radius: 8px;box-shadow: 2px 2px 2px rgba(0,0,0,.1);"/>
                </i>
                <div class="van-cell__title" style="margin-left: 10px">
                  <el-skeleton-item variant="text" style="width: 100px"/>
                  <br/>
                  <el-skeleton-item variant="text" style="width: 120px"/>
                </div>
                <div style="float: right;margin-right: -5px">
                  <el-skeleton-item variant="button"
                                    style="width: 60px;height: 30px;background-color: #FAFAFA;border-radius: 20px"/>
                </div>
              </div>
            </div>

            <div style="text-align: center;padding: 5px">
              <el-skeleton-item variant="button"
                                style="width: 100px;height: 30px;background-color: #FAFAFA;border-radius: 20px"/>
              <el-skeleton-item variant="button"
                                style="width: 100px;height: 30px;background-color: #FAFAFA;border-radius: 20px"/>
            </div>
          </div>
        </template>
      </el-skeleton>
      <div v-for="(item,index) in items.list" :key="index">
        <div
          style="width: 95%;background-color: white;margin: 10px auto;border-radius: 10px;box-shadow: 2px 2px 2px rgba(0,0,0,.05);">
          <div style="width: 100%;">
            <div class="van-cell van-cell--center" style="border-radius: 8px 8px 0 0 ">
              <i class="van-icon van-cell__left-icon">
                <img :src="item.userAvatar" class="van-icon__image"
                     style="margin-left: -10px; width: 40px;height: 40px;margin-top: -10px;border-radius: 8px;box-shadow: 2px 2px 2px rgba(0,0,0,.1);"
                     :alt="item.userName">
              </i>
              <div class="van-cell__title" style="margin-left: 10px">
                <span style="font-weight: 500;">{{ item.userName }}</span>
                <div class="van-cell__label" style="margin-top: 0">{{ item.userSign }}</div>
              </div>
              <div style="float: right;margin-right: -5px">
                <van-button type="default" size="small" round
                            style="border: 1px #FAFAFA solid;background-color: #FAFAFA;"
                            @click="follow(item)">
                  <van-icon v-show="item.followStatus === 0" name="like-o" style="font-weight: 500;font-size: 10px"> 关注
                  </van-icon>
                  <van-icon v-show="item.followStatus === 1" name="like" color="red"
                            style="font-weight: 500;font-size: 10px">
                    取消关注
                  </van-icon>
                </van-button>
              </div>
            </div>

            <div style="text-align: center;padding: 5px">
              <van-button icon="like-o" type="default" round disabled
                          style="border: none;background-color: #F2F2F2;font-weight: 500;text-align: center;margin-right: 10px"
                          size="small">
                关注数： {{ item.followNum }}
              </van-button>
              <van-button icon="fire-o" type="default" round disabled
                          style="border: none;background-color: #F2F2F2;font-weight: 500;text-align: center;margin-right: 10px"
                          size="small">
                粉丝数： {{ item.fanNum }}
              </van-button>
            </div>
          </div>
        </div>
      </div>
      <van-pagination
        style="position: fixed;bottom: 0;width: 100%;background-color: white;padding: 10px 5px 5px 5px;border: none"
        v-show="items.list" v-model="page" :page-count="items.pages" :items-per-page="size" mode="simple"
        @change="recommend"/>
    </div>
  </div>
</template>

<script>
import {Toast} from "vant";
import {recommend} from "../../api/user";
import {addFollow, delFollow} from "../../api/follow";

export default {
  name: "index",
  data() {
    return {
      value: '',
      page: 1,
      size: 10,
      // 推荐用户列表
      items: []
    }
  },
  mounted() {
    this.init()

  },
  methods: {
    init() {
      const _t = this;
      _t.recommend()
    },
    // 获取用户列表
    recommend() {
      const _t = this;
      const vanToast = Toast.loading({
        forbidClick: true,
        message: 'Loading...'
      });
      recommend(
        _t.page, _t.size, _t.value
      ).then(res => {
        _t.items = res.data
        setTimeout(() => {
          document.documentElement.scrollTop = document.body.scrollTop = 0
          vanToast.clear()
        }, 200);
      })
    },
    // 搜索
    onSearch() {
      const _t = this;
      _t.page = 1
      _t.size = 10
      _t.recommend()
    },
    // 关注/取消关注
    follow(item) {
      const _t = this;
      const vanToast = Toast.loading({
        forbidClick: true,
        message: 'Loading...'
      });
      if (item.followStatus === 0) {
        addFollow(item.userId).then(res => {
          _t.recommend()
          vanToast.clear()
        })
      } else {
        delFollow(item.userId).then(res => {
          _t.recommend()
          vanToast.clear()
        })
      }
    },
    toFocus() {
      this.$router.push('/');
    }
  }
}
</script>

<style scoped>

</style>
