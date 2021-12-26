<template>
  <div style="background-color: #FAFAFA;margin-top: 5px">
    <div v-show="status === 1">
      <van-cell-group inset :border="false">
        <van-cell title="您暂未登录" label="登录体验更多内容" value="前往登录" is-link @click="toLogin" :clickable="true"/>
      </van-cell-group>
    </div>
    <div v-show="status === 2">
      <van-cell-group inset :border="false" v-show="itemsNullShow">
        <van-cell title="你暂未发布动态" value="前往发布" label="快来参与片刻吧" is-link @click="toRel('/graphic')" :clickable="true"/>
      </van-cell-group>
    </div>

    <!--动态-->
    <div style="width: 95%; margin: 10px auto;padding-bottom: 20px" class="box">
      <el-skeleton v-show="(items.length === 0 || status === 1) || status === 0" style="width: 100%; margin: 10px auto 20px auto;" animated>
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
      <div v-for="(item,index) in items" :key="index" v-show="items.length > 0"
           style="margin-bottom: 20px;border-radius: 8px;background-color: white;padding: 10px">
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
            <div style="float: right;margin-right: -5px">
              <van-button icon="like-o" type="default" size="small" round
                          style="border: 1px #FAFAFA solid;background-color: #FAFAFA;font-weight: 500;"
                          @click="itemsDowTop(item.likes)">
                {{ item.likes }}
              </van-button>
            </div>
          </div>
        </div>
        <!--数据-->
        <div @click="toDetails(item.id)">
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
                <van-button v-show="item.type === 'P' || item.type === 'G'" type="default" round disabled
                            style="border: none;background-color: #F2F2F2;font-weight: 500;text-align: center;margin-right: 10px"
                            size="small">
                  数量：{{ item.pictureDataListSize }}
                </van-button>
                <van-button v-show="item.type === 'T' || item.type === 'G'" type="default" round disabled
                            style="border: none;background-color: #F2F2F2;font-weight: 500;text-align: center;margin-right: 10px"
                            size="small">
                  字数：{{ item.textData ? item.textData.length : 0 }}
                </van-button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <van-divider @click="getItemsBefore" v-show="itemsIndexShow && items.length > 0">点击加载更多</van-divider>
      <van-divider v-show="!itemsIndexShow && items.length > 0">到底了...</van-divider>
    </div>
    <unsplash/>
  </div>
</template>

<script>
import {queryByMy} from "../../api/dynamic";
import {Toast} from "vant";

import preventBack from 'vue-prevent-browser-back';
import {verifyLogin} from "../../api/auth";
import {delMomentUser, delTokenValue} from "../../utils/auth";
import {setMine} from "../../utils/scrollTop";

export default {
  mixins: [preventBack],//注入
  name: "index",
  data() {
    return {
      // 1-未登录、2-已登录
      status: 0,
      // 动态数据
      items: [],
      itemsIndex: 1,
      itemsIndexShow: true,
      // 动态提示单元格
      itemsNullShow: false,
      // 动态骨架屏
      itemsShow: true,
      active: '我的',
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
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
    },
    // 动态图片下载
    itemsDowTop(index) {
      Toast('获得点赞：' + index)
    },
    // 加载下一页前调
    getItemsBefore() {
      this.itemsIndex += 1;
      this.queryByMy();
    },
    // 查询我的动态列表
    queryByMy() {
      const vanToast = Toast.loading({
        forbidClick: true,
        message: 'Loading...'
      });
      const _t = this;
      queryByMy(this.itemsIndex).then(res => {
        if (res.success) {
          const data = res.data;
          if (data.length === 0) {
            if (_t.itemsIndex === 1) {
              _t.itemsNullShow = true;
            }
          } else {
            _t.itemsNullShow = false;
            _t.itemsShow = false;
            data.forEach(item => {
              _t.items.push(item)
            });
            if (data.length !== 10) {
              _t.itemsIndexShow = false;
            }
          }
          setTimeout(() => {
            vanToast.clear()
          }, 200);
        }
      });
    },

    // 点赞
    start() {
      Toast('点赞~');
    },
    // 查看详情
    toDetails(id) {
      this.$router.push('/details?id=' + id)
    },
    // 关注页
    toFocus() {
      this.$router.push('/focus')
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
