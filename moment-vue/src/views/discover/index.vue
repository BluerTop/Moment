<template>
  <div style="background-color: #FAFAFA;padding-bottom: 200px">
    <div class="van-nav-bar van-hairline--bottom">
      <div class="van-nav-bar__content">
        <div class="van-nav-bar__title van-ellipsis" style="font-size: 18px;font-weight: 500">发现</div>
      </div>
    </div>
    <div>
<!--      <div>-->
<!--        <van-swipe :autoplay="3000">-->
<!--          <van-swipe-item v-for="(image, index) in 4" :key="index" style="height: 200px">-->
<!--            <img src="https://img01.yzcdn.cn/vant/apple-1.jpg"/>-->
<!--          </van-swipe-item>-->
<!--        </van-swipe>-->
<!--      </div>-->
      <van-sticky :offset-top="0">
        <van-tabs v-model="activeName" color="black">
          <van-tab title="推荐" name="Recommend"/>
          <van-tab title="文字" name="Word"/>
          <van-tab title="图文" name="Graphic"/>
          <van-tab title="图片" name="c"/>
          <van-tab title="音乐" name="Music"/>
          <van-tab title="视频" name="f"/>
          <van-tab title="文章" name="Article"/>
        </van-tabs>
      </van-sticky>
      <div style="margin-top: 10px;padding-bottom: 10px">
        <Recommend v-show="activeName === 'Recommend'"/>
        <Word v-show="activeName === 'Word'"/>
        <Graphic v-show="activeName === 'Graphic'"/>
        <Music v-show="activeName === 'Music'"/>
        <Article v-show="activeName === 'Article'"/>
      </div>

      <unsplash/>
    </div>

  </div>
</template>

<script>

import Recommend from './Recommend';
import Music from './Music';
import Article from './Article';
import Word from './Word';
import Graphic from './Graphic';

export default {
  name: "index",
  components: {
    Recommend,Music,Article,Word,Graphic
  },
  data() {
    return {
      //
      activeName: 'Recommend',
    }
  },
  mounted() {

  },
  activated: function () {

  },
  methods: {},
}
</script>

<style scoped>
.my-swipe .van-swipe-item {
  color: #fff;
  font-size: 20px;
  line-height: 150px;
  text-align: center;
  background-color: #39a9ed;
}
</style>
