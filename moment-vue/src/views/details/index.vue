<template>
  <div style="background-color: #FAFAFA">
    <div class="van-nav-bar__placeholder" style="height: 60px;">
      <div class="van-nav-bar van-nav-bar--fixed" style="z-index: 10">
        <div class="van-nav-bar__content">
          <div class="van-nav-bar__left">
            <van-icon name="arrow-left" size="20" color="black" @click="onClickLeft"/>
          </div>
          <div class="van-nav-bar__title van-ellipsis">
            <img :src="item.userAvatar" style="width: 25px;border-radius: 100%;float: left">
            <span style="margin-left: 8px;font-weight: 500;line-height: 25px">{{ item.userName }}</span>
          </div>
          <div class="van-nav-bar__right">
            <van-icon name="bars" size="20" color="black" @click="rightShowTop"/>
          </div>
        </div>
      </div>
    </div>
    <div style="padding-bottom: 500px;margin-top: -5px">
      <div style="width: 95%;margin: 0 auto;background-color: white;border-radius: 10px">
        <el-skeleton v-show="itemShow" style="width: 100%; margin: 10px auto 20px auto;" animated>
          <template slot="template">
            <div style="width: 100%;">
              <div class="van-cell van-cell--center" style="border-radius: 8px 8px 0 0 ">
                <i class="van-icon van-cell__left-icon">
                  <el-skeleton-item
                    variant="image"
                    style="margin-left: -10px; width: 50px;height: 50px;margin-top: -10px;border-radius: 8px;"
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
        <!--作者信息-->
        <div class="van-cell van-cell--center" v-show="!itemShow" style="border-radius: 10px">
          <i class="van-icon van-cell__left-icon" style="margin-top: -30px;">
            <el-image fit="cover"
                      class="van-icon__image"
                      :src="item.userAvatar">
            </el-image>
          </i>
          <div class="van-cell__title" style="margin-left: 10px;">
            <span style="font-weight: 500">{{ item.userName }}</span>
            <div class="van-cell__label">{{ item.creatTime }}</div>
          </div>
          <div class="van-cell__value" style="margin-right: 10px">
            <van-button type="default" size="small" round
                        style="border: 1px #FAFAFA solid;background-color: #FAFAFA;"
                        @click="follow(item)" v-show="item.userId !== user.userId">
              <van-icon v-show="item.followStatus === 0" name="like-o" style="font-weight: 500;font-size: 10px"> 关注
              </van-icon>
              <van-icon v-show="item.followStatus === 1" name="like" color="red"
                        style="font-weight: 500;font-size: 10px">
                取消关注
              </van-icon>
            </van-button>
            <van-button icon="edit" round size="small" type="default" @click="showShare = !showShare"
                        v-show="item.userId === user.userId">编辑
            </van-button>
          </div>
        </div>

        <!--动态数据-->
        <div style="padding: 10px">
          <!--图片-->
          <div v-show="item.type === 'P' || item.type === 'G'" style="margin-bottom: 10px">
            <div v-for="(image, index) in item.pictureDataList" :key="index">
              <el-image fit="cover" v-bind:style="{height : item.imageHeight,'background-color' : item.imageColor}"
                        style="height: 200px; width: 100%;border-radius: 10px;box-shadow: 5px 5px 5px rgba(0,0,0,.1);"
                        :src="image" lazy @click="preview(item.pictureDataList,index)">
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
          <!--文字-->
          <div v-if="item.type === 'G'">
            <span style="font-size: 14px;font-weight: 500;margin-top: 0">{{ item.textData }}</span>
          </div>
          <!--文字-->
          <div v-if="item.type === 'T'">
            <span style="font-size: 14px;font-weight: 500;">{{ item.textData }}</span>
          </div>
          <!--标签-->
          <div style="display: flex;width: 100%;margin-top: 20px">
            <!--标签-->
            <van-button type="default" round disabled v-for="(lable,ind) in item.labelList" :key="ind"
                        style="border: none;background-color: #F2F2F2;font-weight: 500;text-align: center;margin-right: 10px"
                        size="small">
              {{ lable }}
            </van-button>
            <!--状态-->
            <van-button type="default" round disabled v-show="item.userId === user.userId"
                        style="border: none;background-color: #F2F2F2;font-weight: 500;text-align: center;margin-right: 10px"
                        size="small">
              状态：{{
                item.status === 'M' ? "正常" : item.status === 'F' ? "已删除" : item.status === 'R' ? "审核中" : item.status === 'F' ? "审核不通过" : ""
              }}
            </van-button>
            <!--可见范围 O-自己可见，F-粉丝可见，A-所有人可见，C-亲密关系可见-->
            <van-button icon="shield-o" type="default" round disabled v-show="item.userId === user.userId"
                        style="border: none;background-color: #F2F2F2;font-weight: 500;text-align: center;margin-right: 10px"
                        size="small">
              {{ item.authority === 'O' ? "自己可见" : "" }}
              {{ item.authority === 'F' ? "粉丝可见" : "" }}
              {{ item.authority === 'A' ? "所有人可见" : "" }}
              {{ item.authority === 'C' ? "亲密关系可见" : "" }}
            </van-button>
          </div>
        </div>
      </div>
      <!--留言区/点赞区-->
      <div style="width: 100%; margin: 0 auto;" v-if="!itemShow">
        <van-tabs color="black">
          <van-tab title="留言">
            <template #title> <van-icon name="chat" /> 留言 {{discussItemsSize}}</template>
            <div v-for="(ite) in discussItems" style="margin: 10px;">
              <div style="background-color: #ffffff;padding: 5px 5px 10px;border-radius: 10px;">
                <!--作者信息-->
                <div style="display: flex;margin: 5px 0 20px 0">
                  <div>
                    <el-image fit="cover"
                              style="width: 30px; height: 30px;border-radius: 100%;"
                              :src="ite.userAvatar + '?x-oss-process=image/resize,w_300/quality,Q_50'"/>
                  </div>
                  <div style="margin-left: 10px;margin-right: 10px">
              <span style="font-size: 14px;font-weight: 500; color: #82848a;line-height: 20px"
                    class="van-multi-ellipsis--l1">{{ ite.userName }}</span>
                    <van-tag type="success" v-show="ite.author==='M'">作者</van-tag>
                    <p style="font-weight: 500;font-size: 12px;margin-top: 5px;word-break: break-all;">
                      {{ ite.discussData }}
                    </p>
                  </div>
                </div>

                <div style="margin-left: 40px;margin-top: -25px;margin-right: 20px">
                  <span style="color: #82848a;font-size: 10px;">{{ ite.creatTime }}</span>
                  <span style="color: #82848a;float: right;font-size: 10px;margin-left: 10px"
                        v-show="ite.userId === item.userId"
                        @click="delDiscussTop(ite.id)">删除</span>
                  <span style="color: #82848a;float: right;font-size: 10px;"
                        @click="reply(ite.id)">回复</span>
                </div>
                <!--留言回复-->
                <div v-for="(it) in ite.momentDiscussAndUserVos" style="margin-left: 40px;">
                  <van-divider/>
                  <!--作者信息-->
                  <div style="display: flex;width: 100%;margin: 5px 0 20px 0">
                    <div>
                      <el-image fit="cover"
                                style="width: 20px; height: 20px;border-radius: 100%;"
                                :src="it.userAvatar + '?x-oss-process=image/resize,w_300/quality,Q_50'"/>
                    </div>
                    <div style="margin-left: 5px;margin-right: 10px">
                  <span style="font-size: 12px;font-weight: 500; color: #82848a;line-height: 20px"
                        class="van-multi-ellipsis--l1">{{ it.userName }}</span>
                      <van-tag type="success" v-show="it.author==='M'">作者</van-tag>
                    </div>
                  </div>
                  <p style="font-weight: 500;font-size: 10px;margin: -18px 0 5px;word-break: break-all;">
                    {{ it.discussData }}
                  </p>

                  <div style="margin-right: 20px;">
                    <span style="color: #82848a;font-size: 10px;">{{ it.creatTime }}</span>
                    <span style="color: #82848a;float: right;font-size: 10px" v-show="ite.userId === item.userId"
                          @click="delDiscussTop(it.id)">删除</span>
                  </div>
                </div>
              </div>
            </div>
          </van-tab>
          <van-tab title="点赞">
            <template #title> <van-icon name="like" color="red"/> 点赞 {{item.likes}}</template>
            <div v-for="(im,i) in likeItems" :key="i" style="margin: 0 10px 10px 10px;">
              <div style="width: 100%;background-color: white;margin: 10px auto;border-radius: 10px">
                <div style="width: 100%;">
                  <div class="van-cell van-cell--center" style="border-radius: 8px 8px 0 0 ">
                    <i class="van-icon van-cell__left-icon">
                      <img :src="im.userAvatar" class="van-icon__image"
                           style="margin-left: -10px; width: 40px;height: 40px;margin-top: -10px;border-radius: 8px;box-shadow: 2px 2px 2px rgba(0,0,0,.1);"
                           :alt="im.userName">
                    </i>
                    <div class="van-cell__title" style="margin-left: 10px">
                      <span style="font-weight: 500;">{{ im.userName }}</span>
                      <div class="van-cell__label" style="margin-top: 0">{{ im.userSign }}</div>
                    </div>
                    <div style="float: right;margin-right: -5px">
                      <van-icon name="arrow" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </van-tab>
        </van-tabs>
      </div>

      <!--  底部  -->
      <div style="position: fixed;bottom: 0;width:100%;height: 50px;text-align:center;background-color: white;"
           class="van-hairline--top">
        <div style="color: #82848a;display: flex; margin: 10px;background-color: white">
          <div style="width: 65%;font-size: 10px;" @click="lyOpen">
            <el-alert
              title="写下您的留言..."
              :closable="false"
              type="info">
            </el-alert>
          </div>
          <div style="width: 10%;margin: 3px 10px">
            <van-icon v-show="!item.likeStatus" name="like-o" size="18"/>
            <van-icon v-show="item.likeStatus" name="like" color="red" size="18"/>
            <span style="color: #82848a;font-size: 12px;font-weight: 500;padding: 5px" @click="start">{{
                item.likes
              }}</span>
          </div>
          <div style="width: 10%;margin: 3px 10px">
            <van-icon name="chat-o" size="18"/>
            <span style="color: #82848a;font-size: 12px;font-weight: 500;padding: 5px">{{ discussItemsSize }}</span>
          </div>
          <div style="width: 10%;margin: 3px 10px">
            <van-icon name="share-o" size="18"/>
            <span style="color: #82848a;font-size: 12px;font-weight: 500;padding: 5px">2.5k</span>
          </div>
        </div>
      </div>
    </div>

    <!--留言表单-->
    <van-popup round v-model="lyShow" position="bottom" :style="{ height: '40%' }" :close-on-popstate="true">
      <van-nav-bar
        title="写留言"
        :border="true"
      >
        <template #right>
          <el-button size="mini" round type="success" style="background-color: #04B431;border: #04B431 1px solid"
                     @click="addDiscuss">发布
          </el-button>
        </template>
      </van-nav-bar>

      <van-field
        v-model="messageForm.discussData"
        rows="1"
        :border="false"
        :autosize="autosize"
        type="textarea"
        maxlength="255"
        placeholder="说点什么吧..."
      />
    </van-popup>

    <!--操作面板-->
    <van-action-sheet
      v-model="rightShow"
      :actions="rightActions"
      cancel-text="取消"
      style="background-color: #576b95"
      close-on-click-action
      @select="rightCancel"
    />

    <!--删除留言面板-->
    <van-action-sheet
      v-model="delShow"
      :actions="delActions"
      cancel-text="取消"
      style="background-color: #576b95"
      close-on-click-action
      @select="delCancel"
    />

    <van-share-sheet
      v-model="showShare"
      title="编辑"
      :options="options"
      @select="onEdit"
    />

    <!--下载-->
    <el-button type="info" @click="show = !show" v-if="dow && !show" size="20"
               style="z-index: 9999;position: fixed;bottom:30px;right: 20px;" icon="el-icon-download"
               circle/>
    <van-action-sheet
      v-model="show"
      :actions="actions"
      cancel-text="取消"
      @select="onSelect"
      close-on-click-action
      @cancel="onCancel"
    />
  </div>
</template>

<script>
import {delOrRec, getInfo} from "../../api/dynamic";
import {Dialog, Toast, ImagePreview} from "vant";
import {addDiscuss, delDiscuss, getDiscussInfos} from "../../api/discuss";
import {verifyLogin} from "../../api/auth";
import {userInfo} from "../../api/user";
import {like, likeList} from "../../api/likes";
import {addFollow, delFollow} from "../../api/follow";

export default {
  name: "index",
  data() {
    return {
      // 动态id
      id: '',
      // 动态数据
      item: {
        "likes": 0,
      },
      // 留言数据
      discussItems: [],
      // 点赞列表
      likeItems: [],
      // 留言数
      discussItemsSize: 0,
      // 骨架屏状态
      itemShow: true,
      // 操作Show
      rightShow: false,
      // 操作面板
      rightActions: [],
      // 图片预览骨架状态
      ImagePreviewDialog: '',
      // 下载图片的url
      imgUrlDow: '',
      // 下载图片图标显示
      dow: false,
      show: false,
      actions: [{name: '保存原图'}],
      // 编辑开关
      showShare: false,
      // 编辑面板
      options: [
        {name: '删除', icon: 'https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/%E5%88%A0%E9%99%A4.png'},
      ],
      // 留言表单开关
      lyShow: false,
      // 留言表单输入框高度
      autosize: {maxHeight: 300, minHeight: 100},
      // 留言内容
      messageForm: {
        discussData: '',
        levelType: 1,
        levelId: null,
        dynamicId: ''
      },
      // 当前用户信息
      user: {},
      // 上个页面url
      oldUrl: '',
      // 删除留言面板开关
      delShow: false,
      // 删除留言面板
      delActions: [],
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      // 通过 `vm` 访问组件实例,将值传入oldUrl
      vm.oldUrl = from.path
    })
  },
  mounted() {
    this.id = this.$route.query.id;
    this.messageForm.dynamicId = this.id;
    if (this.id === undefined) {
      this.$router.push('/');
    }
    this.init()
    document.documentElement.scrollTop = document.body.scrollTop = 0
  },
  methods: {
    onCancel() {
      this.show = false;
    },
    init() {
      const _t = this;
      const vanToast = Toast.loading({
        forbidClick: true,
        message: 'Loading...',
      });
      userInfo().then(res => {
        if (res.success) {
          _t.user = res.data
        }
      })
      getInfo(_t.id).then(res => {
        if (res.success) {
          _t.item = res.data;
          _t.itemShow = false;
          _t.getDiscussInfos()
          _t.likeList()
          vanToast.clear();
        } else {
          Toast(res.message)
          _t.onClickLeft()
        }
      });
    },
    // 动态点赞列表
    likeList() {
      const _t = this;
      likeList(_t.id).then(res => {
        if (res.success) {
          _t.likeItems = res.data;
        } else {
          Toast(res.message)
        }
      })
    },
    // 获取所有留言
    getDiscussInfos() {
      const _t = this;
      getDiscussInfos(_t.id).then(res => {
        if (res.success) {
          _t.discussItems = res.data;
          _t.getdiscussItemsSize()
        } else {
          Toast(res.message)
        }
      })
    },
    getdiscussItemsSize() {
      const items = this.discussItems;
      let size = items.length;
      items.forEach(item => {
        size += item.momentDiscussAndUserVos.length;
      })
      this.discussItemsSize = size
    },
    // 关注
    follow(item) {
      const _t = this;
      const vanToast = Toast.loading({
        forbidClick: true,
        message: 'Loading...'
      });
      if (item.followStatus === 0) {
        addFollow(item.userId).then(res => {
          _t.init()
          vanToast.clear()
        })
      } else {
        delFollow(item.userId).then(res => {
          _t.init()
          vanToast.clear()
        })
      }
    },
    // 打开删除留言面板
    delDiscussTop(id) {
      this.delActions = [
        {name: '删除留言', id: id},
      ]
      this.delShow = true
    },
    // 删除留言
    delCancel(item) {
      delDiscuss(item.id).then(res => {
        if (res.data) {
          Toast('成功');
        }
        this.getDiscussInfos()
      })
    },

    // 点赞
    start() {
      const that = this;
      verifyLogin().then(res => {
        if (res.data) {
          const vanToast = Toast.loading({
            forbidClick: true,
            message: 'Loading...',
          });
          like(that.id).then(res => {
            that.item.likes = res.data[1]
            that.item.likeStatus = res.data[0] === 0;
            that.init()
            vanToast.clear();
          })
        } else {
          Toast('请先登录...');
        }
      })
    },
    // 打开留言板
    lyOpen() {
      verifyLogin().then(res => {
        if (res.data) {
          this.lyShow = true
        } else {
          Toast('请先登录...');
        }
      })
    },

    // 回复留言
    reply(id) {
      this.messageForm.levelId = id;
      this.messageForm.levelType = 2;
      this.lyShow = true;
    },

    // 发布留言
    addDiscuss() {
      const _t = this;
      if (_t.messageForm.discussData === '') {
        Toast('说点东西呗...');
        return false;
      }
      Toast.loading({
        duration: 1000,
        forbidClick: true,
        message: '发布中...',
      });
      addDiscuss(_t.messageForm).then(res => {
        Toast(res.message);
        if (res.success) {
          _t.getDiscussInfos();
          _t.lyShow = false;
          _t.messageForm.levelType = 1;
          _t.messageForm.discussData = '';
          _t.messageForm.levelId = null;
        }
      })
    },

    onClickLeft() {
      if (this.oldUrl === '') {
        this.$router.push('/');
      } else {
        this.$router.push(this.oldUrl);
      }
    },
    // 大图预览
    preview(teacherPhoto, index) {
      const _t = this;
      _t.imgUrlDow = teacherPhoto[index];
      _t.dow = true;
      this.ImagePreviewDialog = ImagePreview({
        images: teacherPhoto,
        startPosition: index,
        closeable: true,
        onChange(index) {
          _t.imgUrlDow = teacherPhoto[index];
        },
        onClose() {
          _t.dow = false;
        }
      });
    },
    // 下载图片
    onSelect() {
      const _t = this;
      const a = document.createElement('a');
      a.download = 'pic';
      // 设置图片地址
      a.href = _t.imgUrlDow;
      a.name = '片刻' + _t.item.userName;
      a.click();
    },
    // 操作打开
    rightShowTop() {
      this.rightShow = true;
      this.rightActions = [
        {name: '关注'},
        {name: '删除'},
        {name: '选项三', subname: ''},
      ]
    },
    // 操作选择
    rightCancel(item) {
      this.rightShow = false;
      Toast(item.name);
    },
    // 编辑
    onEdit(option) {
      const name = option.name;
      if (name === '删除') {
        this.delOrRec();
      }
      this.showShare = false;
    },
    // 删除动态
    delOrRec() {
      delOrRec(this.item.sid, 'F').then(res => {
        Toast(res.message)
        this.init()
      })
    }
  },
  beforeRouteLeave(to, from, next) {
    if (this.ImagePreviewDialog !== '') {
      this.ImagePreviewDialog.close();
    }
    next()
  },
}
</script>

<style scoped>
.van-icon__image {
  width: 50px;
  height: 50px;
  object-fit: contain;
  border-radius: 8px;
}
</style>
