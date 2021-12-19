<template>
  <div>
  <van-nav-bar
    title="发现"
    :placeholder="true"
  />

    <van-pull-refresh v-model="refreshing" @refresh="onRefresh" style="padding-bottom: 150px;">
      <van-list
        v-model="loading"
        :finished="finished"
        :offset="300"
        loading-text="努力加载中..."
        finished-text="没有更多了..."
        error-text="请求失败，点击重新加载"
        @load="onLoad"
      >
        <div style="margin-top: 10px">
          <div style="width: 44%;margin: 0 2% 10px 4%;float: left" ref="itemsa">
            <div v-for="(item,index) in itemsa" :key="index">
              <div @click="toDetails(item.sid)">
                <!--图片-->
                <div v-show="item.type === 'P' || item.type === 'G'">
                  <div v-show="item.pictureDataListSize !== 1">
                    <van-badge :content="item.pictureDataListSize" color="slategrey" style="width: 100%">
                      <el-image fit="cover" v-show="item.pictureDataType"
                                style="width: 100%;height: 200px; border-radius: 10px;box-shadow: 5px 5px 5px rgba(0,0,0,.1);"
                                :src="item.pictureData">
                        <div slot="placeholder" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                          加载中...
                        </div>
                        <div slot="error" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                          加载失败
                        </div>
                      </el-image>
                      <el-image fit="cover" v-show="!item.pictureDataType"
                                style="width: 100%;height: 200px;border-radius: 10px;box-shadow: 5px 5px 5px rgba(0,0,0,.1);"
                                :src="item.pictureData+'?x-oss-process=image/resize,w_300/quality,Q_50/interlace,1'">
                        <div slot="placeholder" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                          加载中...
                        </div>
                        <div slot="error" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                          加载失败
                        </div>
                      </el-image>
                    </van-badge>
                  </div>
                  <div v-show="item.pictureDataListSize === 1">
                    <el-image fit="cover" v-show="item.pictureDataType"
                              style="width: 100%;height: 200px; border-radius: 10px;box-shadow: 5px 5px 5px rgba(0,0,0,.1);"
                              :src="item.pictureData">
                      <div slot="placeholder" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                        加载中...
                      </div>
                      <div slot="error" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                        加载失败
                      </div>
                    </el-image>
                    <el-image fit="cover" v-show="!item.pictureDataType"
                              style="width: 100%;height: 200px;border-radius: 10px;box-shadow: 5px 5px 5px rgba(0,0,0,.1);"
                              :src="item.pictureData+'?x-oss-process=image/resize,w_300/quality,Q_50/interlace,1'">
                      <div slot="placeholder" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                        加载中...
                      </div>
                      <div slot="error" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                        加载失败
                      </div>
                    </el-image>
                  </div>
                </div>
                <!--文字-->
                <div>
                      <span v-if="item.type === 'G'" class="van-multi-ellipsis--l3"
                            style="font-size: 14px;font-weight: 500;margin-top: 0">
                          {{ item.textData }}</span>
                  <span v-if="item.type === 'T'" class="van-multi-ellipsis--l3"
                        style="font-size: 14px;font-weight: 500;">
                          {{ item.textData }}</span>
                </div>
                <!--标签-->
                <div style="display: flex;width: 100%;margin-top: 5px">
                  <span style="font-size: 10px;margin: 0 3px;color: #82848a"
                        v-for="(lable) in item.labelList">{{lable}}</span>
                </div>
              </div>
              <!--个人信息-->
              <div style="width: 100%;margin: 5px 0 30px 0">
                <el-image fit="cover"
                          style="width: 20px; height: 20px;border-radius: 100%;float: left"
                          :src="item.userAvatar + '?x-oss-process=image/resize,w_300/quality,Q_50/interlace,1'"/>
                <div>
                          <span style="font-size: 10px;color: #82848a;margin-left: 5px"
                                class="van-multi-ellipsis--l1">{{item.userName}}</span>
                  <!--点赞-->
                  <van-button icon="like-o" type="default" size="small"
                              style="border: none;color: #82848a;margin-top: -7px; float: right;width: 50px" @click="start">2.5k
                  </van-button>
                </div>
              </div>
            </div>
          </div>
          <div style="width: 44%;margin: 0 4% 10px 2%;float: right" ref="itemsb">
            <div v-for="(item,index) in itemsb" :key="index">
              <div @click="toDetails(item.sid)">
                <!--图片-->
                <div v-show="item.type === 'P' || item.type === 'G'">
                  <div v-show="item.pictureDataListSize !== 1">
                    <van-badge :content="item.pictureDataListSize" color="slategrey" style="width: 100%">
                      <el-image fit="cover" v-show="item.pictureDataType"
                                style="width: 100%;height: 200px; border-radius: 10px;box-shadow: 5px 5px 5px rgba(0,0,0,.1);"
                                :src="item.pictureData">
                        <div slot="placeholder" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                          加载中...
                        </div>
                        <div slot="error" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                          加载失败
                        </div>
                      </el-image>
                      <el-image fit="cover" v-show="!item.pictureDataType"
                                style="width: 100%;height: 200px;border-radius: 10px;box-shadow: 5px 5px 5px rgba(0,0,0,.1);"
                                :src="item.pictureData+'?x-oss-process=image/resize,w_300/quality,Q_50/interlace,1'">
                        <div slot="placeholder" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                          加载中...
                        </div>
                        <div slot="error" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                          加载失败
                        </div>
                      </el-image>
                    </van-badge>
                  </div>
                  <div v-show="item.pictureDataListSize === 1">
                    <el-image fit="cover" v-show="item.pictureDataType"
                              style="width: 100%;height: 200px; border-radius: 10px;box-shadow: 5px 5px 5px rgba(0,0,0,.1);"
                              :src="item.pictureData">
                      <div slot="placeholder" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                        加载中...
                      </div>
                      <div slot="error" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                        加载失败
                      </div>
                    </el-image>
                    <el-image fit="cover" v-show="!item.pictureDataType"
                              style="width: 100%;height: 200px;border-radius: 10px;box-shadow: 5px 5px 5px rgba(0,0,0,.1);"
                              :src="item.pictureData+'?x-oss-process=image/resize,w_300/quality,Q_50/interlace,1'">
                      <div slot="placeholder" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                        加载中...
                      </div>
                      <div slot="error" class="image-slot" style="height: 200px;width: 100%;color: darkgrey;text-align: center;line-height: 200px">
                        加载失败
                      </div>
                    </el-image>
                  </div>
                </div>
                <!--文字-->
                <div>
                      <span v-if="item.type === 'G'" class="van-multi-ellipsis--l3"
                            style="font-size: 14px;font-weight: 500;margin-top: 0">
                          {{ item.textData }}</span>
                  <span v-if="item.type === 'T'" class="van-multi-ellipsis--l3"
                        style="font-size: 14px;font-weight: 500;">
                          {{ item.textData }}</span>
                </div>
                <!--标签-->
                <div style="display: flex;width: 100%;margin-top: 5px">
                  <span style="font-size: 10px;margin: 0 3px;color: #82848a"
                        v-for="(lable) in item.labelList">{{lable}}</span>
                </div>
              </div>
              <!--个人信息-->
              <div style="width: 100%;margin: 5px 0 30px 0">
                <el-image fit="cover"
                          style="width: 20px; height: 20px;border-radius: 100%;float: left"
                          :src="item.userAvatar + '?x-oss-process=image/resize,w_300/quality,Q_50/interlace,1'"/>
                <div>
                          <span style="font-size: 10px;color: #82848a;margin-left: 5px"
                                class="van-multi-ellipsis--l1">{{item.userName}}</span>
                  <!--点赞-->
                  <van-button icon="like-o" type="default" size="small"
                              style="border: none;color: #82848a;margin-top: -7px; float: right;width: 50px" @click="start">2.5k
                  </van-button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </van-list>
    </van-pull-refresh>

    <Nav active="/discover"/>

  </div>
</template>

<script>
import {pages} from "../../api/dynamic";
import {Toast} from "vant";
import preventBack from 'vue-prevent-browser-back';

export default {
  mixins: [preventBack],//注入
  name: "focus",
  data() {
    return {
      itemsa: [],
      itemsb: [],
      loading: false,
      finished: false,
      // 下拉刷新
      refreshing: false,
      list: [],
      // 分页
      pages: {
        page: 1,
        size: 2,
      },

      active: '发现',
      titleStyle: {
        'max-width': '60%',
        'margin': '0 auto',
        'font-weight': '500',
        'font-size': '16px'
      },
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      var vanToast = Toast.loading({
        overlay: true,
        forbidClick: true,
        message: '加载中...',
      });
      this.queryByPage();
      vanToast.clear();
    },
    // 分页查询动态列表 公共
    queryByPage() {
      const _t = this;
      if (this.refreshing) {
        this.itemsa = [];
        this.itemsb = [];
        this.refreshing = false;
      }
      const itemsa = this.$refs.itemsa.clientHeight;
      const itemsb = this.$refs.itemsb.clientHeight;
      pages(_t.pages.page, _t.pages.size).then(res => {
        if (res.success) {
          var data = res.data;
          if (data.size === 0) {
            this.finished = true;
          } else {
            if (itemsa > itemsb) {
              data.list.forEach(item => {
                _t.itemsb.push(item)
              })
            } else {
              data.list.forEach(item => {
                _t.itemsa.push(item)
              })
            }
          }
        } else {
          this.finished = true;
          this.loading = false;
          Toast(res.message)
        }
      }).catch(reason => {
        _t.loading = false;
        _t.finished = true;
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
    toIndex() {
      this.$router.push('/')
    },

    onLoad() {
      this.loading = true;
      setTimeout(() => {
        this.pages.page += 1;
        this.queryByPage();
        this.loading = false;
      }, 100);
    },
    onRefresh() {
      this.pages.page = 0;
      // 清空列表数据
      this.finished = false;

      // 重新加载数据
      // 将 loading 设置为 true，表示处于加载状态
      this.loading = true;
      this.onLoad();
    },
  },
}
</script>

<style scoped>

</style>
