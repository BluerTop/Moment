<template>
  <div>
    <div>
      <van-nav-bar
        :title="momentUser.userName"
        left-arrow
        :border="true"
        @click-left="onClickLeft"
      >
        <template #left>
          <van-icon name="arrow-left" size="15px" color="black"/>
        </template>
        <template #right>
          <el-button size="mini" round type="success" style="background-color: #04B431;border: #04B431 1px solid"
                     @click="addDynamic">发布
          </el-button>
        </template>
      </van-nav-bar>
    </div>

    <div v-show="!isShow">
      <van-field
        v-model="form.textData"
        :border="false"
        :autosize="autosize"
        type="textarea"
        maxlength="255"
        placeholder="快来记录一下吧"
      />
      <div style="width: 100%;margin: 0 auto;">
        <van-uploader
          style="margin: 10px 2px 10px 10px"
          v-model="fileList"
          :multiple="true"
          :max-count="9"
          :after-read="afterRead"
          :before-delete="beforeDelete"
          :max-size="20 * 1024 * 1024"
          @oversize="onOversize"
          upload-text="选择图片"
          :before-read="beforeRead"
          :preview-size="deviceWidth()"
        />
      </div>

      <div style="width:100%;margin-top: 10px">
        <van-cell title="标签" :value="form.label[0] + (labelLength !== 0 ? ' +'+ labelLength : '' ) " :clickable="true"
                  :border="false" @click="lableShow = !lableShow"
                  icon="label-o" is-link/>
        <van-cell title="可见范围" :value="authority" :clickable="true" :border="false"
                  @click="authorityShow = !authorityShow"
                  icon="browsing-history-o" is-link/>
        <p style="color: #82848a;font-size: 12px;font-weight: 500;text-align: center">最多上传9张图片，单张图片大小不超过20M</p>
      </div>
    </div>


    <van-popup round v-model="authorityShow" :close-on-click-overlay="false" position="bottom"
               :style="{ height: '300px' }" :close-on-popstate="true">
      <van-picker
        title="可见范围"
        show-toolbar
        :columns="authorityList"
        @confirm="onConfirm"
        @cancel="onCancel"
      />
    </van-popup>

    <van-popup round v-model="lableShow" :close-on-click-overlay="false" position="bottom" :style="{ height: '300px' }"
               :close-on-popstate="true">
      <van-nav-bar
        title="添加标签"
        :border="false"
        @click-left="lableShow = !lableShow"
        @click-right="focus"
      >
        <template #left>
          <span style="color: #82848a">取消</span>
        </template>
        <template #right>
          <span style="color: #576b95">保存</span>
        </template>
      </van-nav-bar>
      <div style="margin: 0 20px;">
        <vue-tags-input
          v-model="tag"
          :max-tags="10"
          style="width: 100%;"
          :tags="tags"
          @tags-changed="newTags => tags = newTags"
        />
      </div>
    </van-popup>

    <van-popup v-model="addShow" position="bottom" :style="{ height: '100%' }" :close-on-popstate="true">
      <el-col :sm="12" :lg="6">
        <el-result icon="success" title="发布成功" subTitle="您的动态已发布成功，快去查看吧">
          <template slot="extra">
            <el-button size="medium" @click="to('/')">返回首页</el-button>
            <el-button type="success" size="medium" @click="to('/')">去查看</el-button>
          </template>
        </el-result>
      </el-col>
    </van-popup>

    <van-popup v-model="isShow" position="bottom" :style="{ height: '100%' }" :close-on-popstate="true">
      <offline :type="isType"/>
    </van-popup>
  </div>
</template>

<script>
  import {userInfo} from "../../api/user";
  import {delMomentUser, delTokenValue} from "../../utils/auth";
  import {Toast} from "vant";
  import {uploadFile} from "../../api/other";
  import {addDynamic} from "../../api/dynamic";

  export default {
    name: "graphic",
    data() {
      return {
        // 用户
        momentUser: {},
        // 输入框高度
        autosize: {maxHeight: 300, minHeight: 100},
        // 表单
        form: {
          type: 'G',
          textData: '',
          pictureData: [],
          label: ['#图文'],
          authority: 'A',
          userId: ''
        },
        // 图片列表
        fileList: [
          // { url: 'https://img01.yzcdn.cn/vant/leaf.jpg' },
        ],
        // 可见范围
        authorityShow: false,
        authority: '所有人可见',
        authorityList: ['所有人可见', '亲密关系可见', '自己可见', '粉丝可见'],
        // 标签
        lableShow: false,
        tag: '',
        tags: [{"text": "图文", "tiClasses": ["ti-valid"]}],
        labelLength: 0,

        // 发布
        addShow: false,
        // 账户
        isShow: false,
        isType: '',

        // 上个页面url
        oldUrl:'',
      }
    },
    beforeRouteEnter (to, from, next){
      next(vm => {
        // 通过 `vm` 访问组件实例,将值传入oldUrl
        vm.oldUrl = from.path
      })
    },
    mounted() {
      this.init()
    },
    methods: {
      init() {
        var _t = this;
        var vanToast = Toast.loading({
          duration: 1000,
          overlay: true,
          message: '加载中...',
          forbidClick: true,
        });
        userInfo().then(res => {
          if (res.success) {
            _t.momentUser = res.data;
            _t.form.userId = res.data.userId;
            vanToast.clear()
          }
        });
      },

      // 文件选择完毕后
      afterRead(file) {
        var _t = this;
        file.status = 'uploading';
        file.message = '上传中...';
        var formData = new FormData();
        formData.append("file",file.file);
        // 上传图片
        uploadFile(formData, 'P').then(res => {
          if (res.success) {
            var urls = res.data;
            _t.form.pictureData.push(urls[0]);
            file.status = 'done';
            file.message = '上传成功';
          } else {
            file.status = 'failed';
            file.message = '上传失败';
            Toast(res.message);
          }
        });
      },
      // 返回布尔值
      beforeRead(file) {
        if (file.type === 'image/jpeg' || file.type === 'image/jpg' || file.type === 'image/png' || file.type === 'image/gif') {
          return new Promise((resolve) => {
            // 会将图片修正为正确方向
            new Compressor(file, {
              success: resolve,
              error(err) {
                console.log(err.message);
              },
            });
          });
        } else {
          Toast('请正确选择图片');
          return false;
        }
      },
      // 文件超过20M
      onOversize(file) {
        Toast('图片大小不能超过 20M');
      },
      // 删除图片
      beforeDelete(file,detail){
        this.fileList.splice(detail.index,1);
        this.form.pictureData.splice(detail.index,1)
      },

      // 发布动态
      addDynamic() {
        var _t = this;
        if (_t.form.textData === '') {
          Toast('说点东西呗...');
          return false;
        }
        if (_t.form.pictureData.length === 0) {
          Toast('您忘记上传图片了...');
          return false;
        }
        _t.form.label = JSON.stringify(_t.form.label);
        _t.form.pictureData = JSON.stringify(_t.form.pictureData);
        if (_t.form.label === '[]') {
          Toast('您忘记选择标签了...');
          return false;
        }
        var vanToast = Toast.loading({
          message: '加载中...',
          forbidClick: true,
        });
        addDynamic(this.form).then(res => {
          if (res.success) {
            setTimeout(() => {
              vanToast.clear();
              _t.addShow = true
            }, 1000);
          } else {
            _t.isType = res.code;
            if (res.code === 500) {
              Toast('发布失败，请稍后再试');
            } else if (res.code === 501) {
              Toast(res.message);
            } else if (res.code === 510 || res.code === 511) {
              delTokenValue();
              delMomentUser();
              _t.isShow = true;
            } else {
              Toast('您暂未登录，请先登录');
              _t.$router.push('/login');
            }
          }
        })
      },

      // 标签保存
      focus() {
        var _t = this;
        if (_t.tags.length === 0) {
          Toast('您忘记选择标签了...');
          return false;
        }
        if (_t.tags.length >= 5) {
          Toast('您选择的标签太多了，装不下了...');
          return false;
        }
        var tags = _t.tags;
        var label = [];
        for (let i in tags) {
          label.push('#' + tags[i].text)
        }
        _t.form.label = label;
        _t.labelLength = label.length - 1;
        _t.lableShow = false
      },
      // 可见范围保存
      onConfirm(value, index) {
        this.authority = value;
        if (index === 0) {
          this.form.authority = 'A';
        }
        if (index === 1) {
          this.form.authority = 'C';
        }
        if (index === 2) {
          this.form.authority = 'O';
        }
        if (index === 3) {
          this.form.authority = 'F';
        }
        this.authorityShow = false
      },
      onCancel() {
        this.authorityShow = false
      },

      onClickLeft() {
        this.$router.push(this.oldUrl);
      },
      to(path) {
        this.$router.push(path);
      },

      // 获取设备宽度 - 图片宽度
      deviceWidth() {
        var innerWidth = window.innerWidth;
        return (innerWidth - 44) / 3;
      },
    },
  }
</script>

<style scoped>

</style>
