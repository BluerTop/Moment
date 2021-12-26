<template>
  <div>
    <div style="width: 95%;background-color: #FAFAFA;margin: 0 auto;">
      <div v-for="(item,ind) in items" :key="ind">
        <div style="background-color: white;padding: 10px;border-radius: 10px;margin: 10px auto;" @click="toDetails(item.id)">
          <div>
            <div class="van-cell van-cell--center" style="border-radius: 8px 8px 0 0 ;">
              <i class="van-icon van-cell__left-icon">
                <img :src="item.userAvatar" class="van-icon__image"
                     style="margin-left: -10px; width: 40px;height: 40px;margin-top: -10px;border-radius: 8px;box-shadow: 2px 2px 2px rgba(0,0,0,.1);"
                     :alt="item.userName">
              </i>
              <div class="van-cell__title" style="margin-left: 10px;">
                <span style="font-weight: 500;font-size: 12px">{{ item.userName }}</span><br/>
                <div class="van-cell__label" style="margin-top: 0">{{ item.creatTime }}</div>
              </div>
            </div>
          </div>
          <div style="margin-left:60px;margin-top: -10px;">
            <!--其他-->
            <div style="margin-top: 10px;display: flex">
              <div class="box">
                <div class="box1">
                  <div class="box2" style="border: none">
                    <!--标签-->
                    <van-button type="default" round disabled v-for="(lable,ind) in item.labelList" :key="ind"
                                style="border: none;background-color: #F2F2F2;font-weight: 500;text-align: center;margin-right: 10px;"
                                size="small">
                      {{ lable }}
                    </van-button>
                  </div>
                </div>
              </div>
            </div>
            <p style="font-weight: 500;font-size: 14px;text-align: left;">{{item.textData}}</p>
          </div>
        </div>
      </div>

      <van-divider @click="getItemsBefore" v-show="itemsIndexShow && items.length > 0">点击加载更多</van-divider>
      <van-divider v-show="!itemsIndexShow && items.length > 0">到底了...</van-divider>
    </div>
  </div>
</template>

<script>
import {pages} from "../../api/dynamic";
import {Toast} from "vant";

export default {
  name: "Word",
  data() {
    return {
      type: 'T',
      index: 1,
      items: [],
      itemsIndexShow: true
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    init() {
      const t = this;
      const vanToast = Toast.loading({
        forbidClick: true,
        message: 'Loading...'
      });
      pages(t.index, t.type).then(res => {
        const data = res.data;
        if (data.size === 0) {
          t.itemsIndexShow = false
        }
        const list = data.list;
        list.forEach(item => {
          t.items.push(item)
        })

        setTimeout(() => {
          vanToast.clear()
        }, 200);
      })
    },
    // 加载下一页前调
    getItemsBefore() {
      this.index += 1;
      this.init();
    },

    // 查看详情
    toDetails(id) {
      this.$router.push('/details?id=' + id)
    },
  }
}
</script>

<style scoped>

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
