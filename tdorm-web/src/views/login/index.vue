<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
             label-position="left">

      <div class="title-container">
        <h3 class="title">请登录</h3>
      </div>

      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user"/>
        </span>
        <el-input
          ref="username"
          v-model="loginForm.username"
          placeholder="用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"/>
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model="loginForm.password"
          :type="passwordType"
          placeholder="密码"
          name="password"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
        </span>
      </el-form-item>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;"
                 @click.native.prevent="handleLogin">登录
      </el-button>
      <div class="tips">
        <el-link @click="register" type="primary" style="margin-right: 10px">注册</el-link>
        <el-link @click="forget" type="primary" id="forgetPwd">忘记密码？</el-link>
      </div>

    </el-form>
  </div>
</template>

<script>
  import axios from "axios";
  import { setCookie } from '../../utils/CookieUtil'
  export default {
    name: 'Login',
    data() {
      const validateUsername = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入用户名'))
        } else if (value.length < 6 || value.length > 15) {
          callback(new Error('用户名格式不正确'))
        } else {
          callback()
        }
      }
      const validatePassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'))
        } else if (value.length < 6 || value.length > 15) {
          callback(new Error('密码格式不正确'))
        } else {
          callback()
        }
      }
      return {
        loginForm: {
          username: '',
          password: ''
        },
        loginRules: {
          username: [{ required: true, trigger: 'blur', validator: validateUsername }],
          password: [{ required: true, trigger: 'blur', validator: validatePassword }]
        },
        loading: false,
        passwordType: 'password',
      }
    },
    methods: {
      register() {
        this.$router.replace("/register")
      },
      forget() {
        axios.get('/api/game').catch(reason => {
          this.errorHint('服务器内部错误')
        })
      },
      showPwd() {
        if (this.passwordType === 'password') {
          this.passwordType = ''
        } else {
          this.passwordType = 'password'
        }
        this.$nextTick(() => {
          this.$refs.password.focus()
        })
      },
      handleLogin() {
        this.$refs.loginForm.validate(valid =>{
          this.loading = true
          if (valid) {
            let passwordMd5 = this.$md5(this.loginForm.password);
            axios.post('api/doLogin',
                this.$qs.stringify({
                  username: this.loginForm.username,
                  password: passwordMd5,
                  rememberMe: true,
                }),
                {
                  withCredentials: true
                })
              .then(resp => {
                this.loading = false
                if (resp.data.code === 200) {
                  let user= JSON.stringify(resp.data.data);
                  let num = resp.data.data.dormNum;
                  let param = new URLSearchParams()
                  param.append('dormNum', num)
                  axios.get('/api/dormName',{params:param}).then(resp=>{
                    if(resp.data.code === 200){
                      localStorage.setItem("dormName",resp.data.data);
                      localStorage.setItem("user",user);
                      setCookie("login","success",24 * 60 * 60 * 1000 * 365);
                      //登录成功，跳转到主页
                      this.successHint("登录成功")
                      this.$router.push('/')
                    }
                  }).catch(reason => {
                    this.errorHint('服务器内部错误')
                  })
                } else {
                  this.errorHint(resp.data.msg)
                }
              }).catch((reason => {
              this.loading = false
              this.errorHint('服务器内部错误')
            }))
          } else {
            this.loading = false
            return false
          }
        })
      }
    }
  }
</script>

<style lang="scss">
  /* 修复input 背景不协调 和光标变色 */
  /* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

  $bg: #283443;
  $light_gray: #fff;
  $cursor: #fff;

  @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .login-container .el-input input {
      color: $cursor;
    }
  }

  /* reset element-ui css */
  .login-container {
    .el-input {
      display: inline-block;
      height: 47px;
      width: 85%;

      input {
        background: transparent;
        border: 0px;
        -webkit-appearance: none;
        border-radius: 0px;
        padding: 12px 5px 12px 15px;
        color: $light_gray;
        height: 47px;
        caret-color: $cursor;

        &:-webkit-autofill {
          box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: $cursor !important;
        }
      }
    }

    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      color: #454545;
    }
  }
</style>

<style lang="scss" scoped>
  $bg: #2d3a4b;
  $dark_gray: #889aa4;
  $light_gray: #eee;

  .login-container {
    min-height: 100%;
    width: 100%;
    background-color: $bg;
    overflow: hidden;

    .login-form {
      position: relative;
      width: 520px;
      max-width: 100%;
      padding: 160px 35px 0;
      margin: 0 auto;
      overflow: hidden;
    }

    .tips {
      font-size: 14px;
      color: #fff;
      margin-bottom: 10px;

      span {
        &:first-of-type {
          margin-right: 16px;
        }
      }
    }

    .svg-container {
      padding: 6px 5px 6px 15px;
      color: $dark_gray;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
    }

    .title-container {
      position: relative;

      .title {
        font-size: 26px;
        color: $light_gray;
        margin: 0px auto 40px auto;
        text-align: center;
        font-weight: bold;
      }
    }

    .show-pwd {
      position: absolute;
      right: 10px;
      top: 7px;
      font-size: 16px;
      color: $dark_gray;
      cursor: pointer;
      user-select: none;
    }
  }
</style>
