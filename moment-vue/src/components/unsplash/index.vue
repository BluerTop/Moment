<template>
  <div>
    <!--unsplash-->
    <div style="width: 95%; margin: 10px auto;padding-bottom: 100px;">
      <van-divider v-show="!unsplashShow">以下内容来自 Unsplash</van-divider>
      <el-skeleton v-show="unsplashShow" style="width: 100%; margin: 10px auto 20px auto;" animated>
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
      <div v-for="(item,index) in unsplash" style="margin-bottom: 20px;border-radius: 8px;background-color: white;padding: 10px" :key="index">
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
                          style="border: 1px #F2F2F2 solid;background-color: #F2F2F2;font-weight: 500;"
                          @click="unsplashDowTop(item)">
                {{ item.likes }}
              </van-button>
            </div>
          </div>
        </div>
        <!--数据-->
        <div>
          <!--图片数据-->
          <div style="padding-bottom: 20px">
            <el-image fit="cover" v-bind:style="{height : item.height,'background-color' : item.color}"
                      style="height:200px ; width: 100%;border-radius: 10px;"
                      :src="item.pictureData" lazy>
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
          <!--文字数据-->
          <div v-show="item.textData" style="margin: -15px 0 20px 10px ;">
              <span style="font-weight: 500">
                {{ item.textData }}
              </span>
          </div>
          <!--其他-->
          <div style="margin-top: -15px;display: flex">
            <div class="box">
              <div class="box1">
                <div class="box2" style="border: none">
                  <!--位置-->
                  <van-button icon="location-o" type="default" round disabled
                              style="border: none;background-color: #F2F2F2;font-weight: 500;text-align: center;margin-right: 10px"
                              size="small">
                    {{ item.location }}
                  </van-button>
                  <van-button icon="photo-o" type="default" round disabled
                              style="border: none;background-color: #F2F2F2;font-weight: 500;text-align: center;margin-right: 10px"
                              size="small">
                    {{ item.width }}*{{ item.height }}
                  </van-button>
                  <van-button type="default" round disabled
                              style="border: none;background-color: #F2F2F2;font-weight: 500;text-align: center;margin-right: 10px"
                              size="small">
                    主色调：{{ item.color }}
                  </van-button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <van-divider @click="getPhotosBefore" v-show="unsplashIndexShow">点击加载更多</van-divider>
      <van-divider v-show="!unsplashIndexShow">最新的都被你看完了...</van-divider>
    </div>
    <!--下载图片-->
    <van-action-sheet
      v-model="unsplashDowShow"
      :actions="actions"
      cancel-text="取消"
      description="下载图片"
      :close-on-click-action="true"
      :close-on-click-overlay="false"
      @select="unsplashDow"
    />
  </div>
</template>

<script>
import {Toast} from "vant";
import {getPhotos} from "../../api/other";
export default {
  name: "index",
  data() {
    return {
      // unsplash
      unsplash: [],
      // 页码
      unsplashIndex: 1,
      // 下一页加载显示
      unsplashIndexShow: true,
      // unsplash骨架屏
      unsplashShow: true,
      // unsplash下载显示
      unsplashDowShow: false,
      // unsplash下载图片
      unsplashDowList: [],
      // unsplash下载质量
      actions: [],
    }
  },
  mounted() {
    this.getPhotos()
  },
  methods:{
    getPhotosBefore() {
      this.unsplashIndex += 1;
      this.getPhotos();
    },
    // 获取unsplash随机图片
    getPhotos() {
      const vanToast = Toast.loading({
        overlay: true,
        forbidClick: true,
        message: 'Loading...'
      });
      getPhotos(this.unsplashIndex).then(res => {
        this.unsplashShow = false;
        const data = res.data;
        data.forEach(item => {
          this.unsplash.push(item)
        });
        setTimeout(() => {
          if (this.unsplashIndex === 10){
            this.unsplashIndexShow = false;
          }
          vanToast.clear()
        }, 200);
      })
    },
    // unsplash下载显示
    unsplashDowTop(item) {
      const vanToast = Toast.loading({
        overlay: true,
        forbidClick: true,
        message: 'Loading...'
      });
      this.unsplashDowList = item.pictureDataList;
      this.actions = [
        {name: '原图', subname: '原始尺寸' + item.width + '*' + item.height},
        {name: '优质', subname: '原始尺寸*85%'},
        {name: '常规', subname: '原始尺寸*80% 宽度1080px'},
        {name: '一般', subname: '原始尺寸*80% 宽度400px'},
        {name: '最低', subname: '原始尺寸*80% 宽度200px'},
      ];
      setTimeout(() => {
        this.unsplashDowShow = true;
        vanToast.clear()
      }, 1000);
    },
    // unsplash下载
    unsplashDow(action, index) {
      const vanToast = Toast.loading({
        overlay: true,
        forbidClick: true,
        message: 'Loading...'
      });
      const x = new XMLHttpRequest();
      x.open("GET", this.unsplashDowList[index], true);
      x.responseType = 'blob';
      x.onload = function (e) {
        const url = window.URL.createObjectURL(x.response);
        const a = document.createElement('a');
        a.href = url
        a.download = new Date().getTime() + '.png'
        a.name = new Date().getTime() + '.png'
        a.click()
        setTimeout(() => {
          vanToast.clear()
          this.unsplashDowShow = false;
        }, 200);
      }
      x.send();
      this.unsplashDowList = [];
    },
  }
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
