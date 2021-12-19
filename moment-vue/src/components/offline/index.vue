<template>
  <div>
    <van-overlay :show="isShow">
      <div class="wrapper" @click.stop>
        <div class="block">
          <el-col :sm="12" :lg="6">
            <el-result v-show="type === 510" icon="warning" title="下线通知" subTitle="检测到你的账号在其他设备登录">
              <template slot="extra">
                <p style="color: #82848a;margin-top: -20px;margin-bottom: 40px;font-size: 14px">当前设备已自动下线，如有需要请重新登录</p>
                <el-button size="medium" @click="to('/')">返回首页</el-button>
                <el-button type="success" size="medium" @click="to('/login')">重新登录</el-button>
              </template>
            </el-result>
            <el-result v-show="type === 511" icon="warning" title="下线通知" subTitle="检测到你的账号登录已过期">
              <template slot="extra">
                <p style="color: #82848a;margin-top: -20px;margin-bottom: 40px;font-size: 14px">当前设备已自动下线，如有需要请重新登录</p>
                <el-button size="medium" @click="to('/')">返回首页</el-button>
                <el-button type="success" size="medium" @click="to('/login')">重新登录</el-button>
              </template>
            </el-result>
          </el-col>
        </div>
      </div>
    </van-overlay>
  </div>
</template>

<script>
  export default {
    name: "index",
    props:[
      'type'
    ],
    data() {
      return {
        isShow: true,
        subTitle: ''
      }
    },
    methods: {
      to(path) {
        this.$router.push(path);
      },
    }
  }
</script>

<style scoped>
  .wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 100%;
  }

  .block {
    width: auto;
    height: auto;
    background-color: #fff;
    border-radius: 10px;
  }
</style>
