<template>
  <div style="height: 100%;">
    <!--手机端-->
    <div>
      <van-nav-bar
        title="登录"
        left-arrow
        :border="false"
        @click-left="onClickLeft"
      >
        <template #left>
          <van-icon name="arrow-left" size="15px" color="black"/>
          <span>首页</span>
        </template>
      </van-nav-bar>
    </div>

    <div style="width: 90%;margin: 50px auto 0;text-align: center">
      <img style="width: 4rem" src="../../assets/logo.png" alt="..." />
      <h3>欢迎来到片刻</h3>

      <div style="margin-top: 40px">
        <div v-show="loginType === 'PC'">
          <van-cell-group :border="false" style="height: 100px;">
            <transition name="van-slide-left">
              <van-field v-model="from_PC.accountPhone" maxlength="11" type="number"
                         :border="false" :clearable="true"
                         placeholder="手机号"/>
            </transition>
            <van-field
              v-model="from_PC.accountCode" center clearable maxlength="6" type="number" :border="false"
              :clearable="true"
              placeholder="短信验证码">
              <template #button>
                <van-button size="small" round type="primary" @click="sendMessages" :loading="loadingCode"
                            :loading-text="loadingCodeText">点击获取
                </van-button>
              </template>
            </van-field>
          </van-cell-group>
          <van-button type="primary" round size="large" style="margin-top: 3rem" @click="login" :loading="loadingLogin"
                      :loading-text="loadingLoginCode">登录
          </van-button>
          <p style="font-size: 0.8rem;color: #82848a">未注册的手机号在通过验证后会自动创建片刻账号</p>
        </div>
        <div v-show="loginType === 'PP'">
          <van-cell-group :border="false" style="height: 100px;">
            <van-field v-model="from_PP.accountPhone" maxlength="11" type="number"
                       :border="false" :clearable="true"
                       placeholder="手机号"/>

            <van-field v-model="from_PP.accountPassword" type="password" placeholder="密码"/>
          </van-cell-group>
          <van-button type="primary" round size="large" style="margin-top: 3rem" @click="login" :loading="loadingLogin"
                      :loading-text="loadingLoginCode">登录
          </van-button>
          <p style="font-size: 0.8rem;color: #82848a" @click="sendMessages">忘记密码？点我记起密码</p>
        </div>
        <div v-show="loginType === 'MP'">
          <van-cell-group :border="false" style="height: 100px;">
            <van-field v-model="from_MP.accountMail"
                       :border="false" :clearable="true"
                       placeholder="邮箱"/>

            <van-field v-model="from_MP.accountPassword" type="password" placeholder="密码"/>
          </van-cell-group>
          <van-button type="primary" round size="large" style="margin-top: 3rem" @click="login" :loading="loadingLogin"
                      :loading-text="loadingLoginCode">登录
          </van-button>
          <p style="font-size: 0.8rem;color: #82848a" @click="switchLogin('PC')">推荐使用手机号登录</p>
        </div>
      </div>
    </div>

    <div
      style="font-size: 10px;color: #82848a;position:relative; margin-top: 45%;padding-bottom:10%;width:100%;text-align: center">
      <div style="float: left;width: 37%">
        <div style="float: right">
          <van-icon style="margin: 0 auto;text-align: center;" size="50" @click="switchLogin('PC')"
                    name="https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/phoneLogin.png"/>
          <p>手机登录</p>
        </div>
      </div>
      <div style="float: left;width: 26%">
        <van-icon style="margin: 0 auto;text-align: center;" size="50" @click="switchLogin('MP')"
                  name="https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/mailLogin.png"/>
        <p>邮箱登录</p>
      </div>
      <div style="float: left;width: 37%">
        <div style="float: left">
          <van-icon style="margin: 0 auto;text-align: center;" size="50" @click="switchLogin('PP')"
                    name="https://moment-back.oss-cn-hangzhou.aliyuncs.com/picture/system/passwordLogin.png"/>
          <p>密码登录</p>
        </div>
      </div>
    </div>
    <br/><br/><br/>
    <!--  底部  -->
    <div style="font-size: 10px;color: #82848a;width:100%;text-align: center">
      <p>Copyright © 2020 - 现在 </p>
      <p>bluer.top All Rights Reserved.</p>
    </div>
  </div>
</template>

<script>
  import {Toast} from 'vant';
  import {sendMessages} from "../../api/sms";
  import {login} from "../../api/auth";
  import {
    delMomentAuth,
    delMomentUser,
    delTokenValue,
    getMomentUser,
    getTokenValue, setMomentAuth,
    setMomentUser,
    setTokenValue
  } from "../../utils/auth";
  import {userInfo} from "../../api/user";

  export default {
    name: "index",
    data() {
      return {
        // 登录类型
        loginType: "PC",
        // 错误提示
        error: 'd',
        from_PC: {
          "loginType": "PC",
          "accountPhone": "",
          "accountCode": "",
          "describe": "C"
        },
        from_PP: {
          "loginType": "PP",
          "accountPhone": "",
          "accountPassword": ""
        },

        from_MP: {
          "loginType": "MP",
          "accountMail": "",
          "accountPassword": ""
        },

        // 发送验证码
        loadingCode: false,
        loadingCodeText: '',

        loadingLogin: false,
        loadingLoginCode: '',
      }
    },
    mounted() {
      var momentUser = getMomentUser();
      if (momentUser !== null){
        this.$router.push("/");
      }
      var tokenValue = getTokenValue();
      if (tokenValue !== null){
        this.$router.push("/");
      }
    },
    methods: {
      // 通用短信发送
      sendMessages() {
        Toast.loading({
          forbidClick: true,
        });
        var thit = this;
        thit.loadingCode = true;
        let second = 90;
        const timer = setInterval(() => {
          second--;
          if (second) {
            thit.loadingCodeText = second + 's';
          } else {
            clearInterval(timer);
            thit.loadingCode = false;
          }
        }, 1000);

        var reg = /^0?1[3|4|5|6|7|8][0-9]\d{8}$/;
        var data;
        if (thit.loginType === 'PC') {
          if (!reg.test(thit.from_PC.accountPhone)) {
            Toast({message: '手机号错误，请正确输入11位手机号'});
            clearInterval(timer);
            thit.loadingCode = false;
            return;
          }
          data = {
            "phone": thit.from_PC.accountPhone,
            "describe": 'C'
          };
        } else if (thit.loginType === 'PP') {
          if (!reg.test(thit.from_PP.accountPhone)) {
            Toast({message: '手机号错误，请正确输入11位手机号'});
            clearInterval(timer);
            thit.loadingCode = false;
            return;
          }
          data = {
            "phone": thit.from_PP.accountPhone,
            "describe": 'P'
          };
        } else {
          return;
        }
        sendMessages(data).then(res => {
          Toast({message: res.message});
        })
      },
      // 参数校验
      parameterVerification() {
        var thit = this;
        var reg = /^0?1[3|4|5|6|7|8][0-9]\d{8}$/;
        if (this.loginType === 'PC') {
          if (!reg.test(thit.from_PC.accountPhone)) {
            Toast({message: '手机号错误，请正确输入11位手机号'});
            return false;
          }
          if (thit.from_PC.accountCode === '') {
            Toast({message: '验证码错误，请正确输入6位验证码'});
            return false;
          }
        } else if (this.loginType === 'PP') {
          if (!reg.test(thit.from_PP.accountPhone)) {
            Toast({message: '手机号错误，请正确输入11位手机号'});
            return false;
          }
          if (thit.from_PP.accountPassword === '') {
            Toast({message: '密码为空'});
            return false;
          }
        } else if (this.loginType === 'MP') {
          var strRegex = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
          if (!strRegex.test(thit.from_MP.accountMail)) {
            Toast({message: '邮箱格式错误'});
            return false;
          }
          if (thit.from_MP.accountPassword === '') {
            Toast({message: '密码为空'});
            return false;
          }
        } else {
          return false;
        }
        return true;
      },

      // 登录
      login() {
        var thit = this;
        if (!thit.parameterVerification()) {
          return false;
        }
        thit.loadingLogin = true;
        var data;
        if (this.loginType === 'PC') {
          data = thit.from_PC;
        } else if (this.loginType === 'PP') {
          data = thit.from_PP;
        } else if (this.loginType === 'MP') {
          data = thit.from_MP;
        } else {
          thit.loadingLogin = false;
          return;
        }
        login(data).then(res => {
          delTokenValue();
          delMomentUser();
          delMomentAuth();
          if (!res.success) {
            Toast({message: res.message});
            thit.loadingLogin = false;
          } else {
            setTokenValue(res.data.tokenValue);
            setMomentAuth(JSON.stringify(data));
            document.cookie = res.data.tokenName + "=" + res.data.tokenValue;

            userInfo().then(res2 => {
              if (res2.success) {
                setMomentUser(JSON.stringify(res2.data));
              } else {
                Toast({message: '登录失败'});
                return false;
              }
            });

            const toast = Toast.loading({
              duration: 0, // 持续展示 toast
              forbidClick: true,
              message: '验证中...',
            });

            let second = 1;
            const timer = setInterval(() => {
              second--;
              if (second) {
                toast.message = `验证中...`;
              } else {
                clearInterval(timer);
                thit.loadingLogin = false;
                // 手动清除 Toast
                Toast.clear();
                thit.$router.push("/discover");
              }
            }, 1000);
          }
        }).catch(res => {
          thit.loadingLogin = false;
        })
      },

      onClickLeft() {
        this.$router.push('/')
      },
      onClickRight() {
        Toast('按钮');
      },

      // 切换登录方式
      switchLogin(type) {
        var thit = this;
        Toast.loading({
          message: '加载中...',
          overlay: true,
          forbidClick: true,
          duration: 300,
        });
        setTimeout(function () {
          thit.loginType = type;
        }, 300);
      },


      // 获取设备宽度
      deviceWidth() {
        return window.innerWidth;
      }
    },

    beforeRouteLeave(to, from, next) {
      from = null;
      next()
    }
  }
</script>

<style scoped>

</style>
