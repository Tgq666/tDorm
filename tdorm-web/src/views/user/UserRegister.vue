<template>
  <div class="back">
    <el-container>
      <el-header>
        <div class="loginHead">
        </div>
      </el-header>
      <el-main>
        <div class="title-line">
          <span class="tit">注册</span>
        </div>
        <div>
          <el-form
            v-loading="loading"
            element-loading-text="正在登录"
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)"
            :model="registerForm"
            :rules="rules"
            ref="registerForm"
            label-width="100px"
            class="registerContainer">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="registerForm.username" placeholder="请输入用户名"/>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="registerForm.password" show-password placeholder="请输入密码"/>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input v-model="registerForm.confirmPassword" show-password placeholder="请确认密码"/>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
              <el-input v-model="registerForm.name" placeholder="请输入姓名"/>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="registerForm.sex">
                <el-radio label="男"/>
                <el-radio label="女"/>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="寝室号码" prop="dormNum">
              <el-input v-model="registerForm.dormNum" placeholder="请输入寝室号" style="width: 110px"/>
              <el-popover
                v-loading="loading"
                placement="top"
                width="160"
                v-model="visible">
                寝室名:
                <el-input v-model="dormName" autocomplete="off"
                          style="width:100px;margin-bottom: 10px;margin-left: 10px"/>
                <div style="text-align: right; margin: 0">
                  <el-button size="mini" type="text" @click="visible = false">取消</el-button>
                  <el-button type="primary" size="mini" @click="createDorm"
                             v-loading.fullscreen.lock="fullscreenLoading">
                    确定
                  </el-button>
                </div>
                <el-button type="text" slot="reference">没有？创建一个</el-button>
              </el-popover>
            </el-form-item>
            <el-form-item label="密保问题1" prop="question1">
              <el-input v-model="registerForm.question1" placeholder="请输入密保问题1"/>
            </el-form-item>
            <el-form-item label="密保答案1" prop="answer1">
              <el-input v-model="registerForm.answer1" placeholder="请输入密保答案1"/>
            </el-form-item>
            <el-form-item label="密保问题2" prop="question2">
              <el-input v-model="registerForm.question2" placeholder="请输入密保问题2"/>
            </el-form-item>
            <el-form-item label="密保答案2" prop="answer2">
              <el-input v-model="registerForm.answer2" placeholder="请输入密保答2"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('registerForm')">立即创建</el-button>
              <el-button @click="resetForm('registerForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-main>
      <el-footer></el-footer>
    </el-container>
  </div>

</template>

<script>
  import axios from "axios";

  export default {
    name: "UserRegister",
    data() {
      var validateConfirmPwd = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else if (value != this.registerForm.password) {
          callback(new Error('两次密码不一致'))
        } else {
          callback();
        }
      };
      var validateUsername = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入用户名'));
        } else if (value.length < 6 || value.length > 15) {
          callback(new Error('长度在 6 到 15'));
        } else {
          axios.get('/api/userExit', {
              params: {username: this.registerForm.username},
            }
          ).then((resp) => {
            if (!resp.data.data) {
              callback();
            } else {
              callback(new Error("用户名已存在，请更换"));
            }
          }).catch((reason => {
            this.errorHint("服务器内部错误");
            callback();
          }))
        }
      };
      var validateDormNum = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入想要加入的寝室号码'));
        } else {
          axios.get('/api/dormExit', { params: {dormNum: this.registerForm.dormNum}}).
          then((resp) => {
            if (resp.data.data) {
              callback();
            } else {
              callback(new Error("寝室不存在"));
            }
          }).catch((reason => {
            this.errorHint("服务器内部错误");
            callback();
          }))
        }
      };
      return {
        loading:false,
        fullscreenLoading: false,
        visible: false,
        dormName: '',
        registerForm: {
          username: '',
          password: '',
          confirmPassword: '',
          name: '',
          sex: '男',
          dormNum: '',
          question1: '',
          question2: '',
          answer1: '',
          answer2: '',
        },
        rules: {

          username: [
            {required: true, validator: validateUsername, trigger: 'blur'},
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 6, max: 15, message: '长度在 6 到 15', trigger: 'blur'}
          ],
          confirmPassword: [
            {required: true, validator: validateConfirmPwd, trigger: 'blur'}
          ],
          name: [
            {required: true, message: '请输入姓名', trigger: 'blur'},
          ],
          dormNum: [
            {required: true, validator: validateDormNum, trigger: 'blur'},
          ],
          sex: [
            {required: true, message: '请选择性别', trigger: 'blur'},
          ],
          question1: [
            {required: true, message: '问题不能为空', trigger: 'blur'},
          ],
          question2: [
            {required: true, message: '问题不能为空', trigger: 'blur'},
          ],
          answer1: [
            {required: true, message: '答案不能为空', trigger: 'blur'},
          ],
          answer2: [
            {required: true, message: '答案不能为空', trigger: 'blur'},
          ],

        }
      };
    },
    methods: {
      createDorm() {
        if (this.dormName === '') {
          this.errorHint("寝室名不能为空");
        } else if (this.dormName.indexOf(" ") === -1) {
          this.fullscreenLoading = true;
          let params = new URLSearchParams();
          params.append("dormName",this.dormName);
          axios.post('/api/createDorm',params).then(resp => {
            this.fullscreenLoading = false;
            this.visible = false;
            if (resp.data.code === 200) {
              this.registerForm.dormNum = resp.data.data;
              //创建成功，提示并关闭弹窗
              this.successNotification(resp.data.msg, '您的寝室号为：' + resp.data.data);
            } else {
              this.errorHint(resp.data.msg);
            }
          }).catch((reason => {
            this.fullscreenLoading = false;
            this.errorHint("服务器内部错误");
          }))
        } else {
          this.errorHint("名称中不能包含空格");
        }
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.loading = true;
            let params = new URLSearchParams();
            params.append("username",this.registerForm.username);
            params.append("password",this.registerForm.password);
            params.append("name",this.registerForm.name);
            params.append("sex",this.registerForm.sex);
            params.append("dormNum",this.registerForm.dormNum);
            params.append("question1",this.registerForm.question1);
            params.append("question2",this.registerForm.question2);
            params.append("answer1",this.registerForm.answer1);
            params.append("answer2",this.registerForm.answer2);
            axios.post("/api/register",params).then(resp=>{
              this.loading = false;
              if(resp.data.code === 200){
                this.successHint("注册成功");
                //注册成功，跳转到登录页面
                this.$router.replace("/");
              }
            }).catch(reason => {
              this.loading = false;
              this.errorHint("服务器内部错误");
            })
          } else {
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style scoped>
  .title-line {
    width: 100%;
    height: 28px;
    border-bottom: 1px solid #ddd;
    margin: 0 auto 28px;
    text-align: center;
  }

  .title-line .tit {
    height: 56px;
    line-height: 56px;
    margin: 0 auto;
    padding: 0 20px;
    font-size: 40px;
    background: #fff;
    text-align: center;
  }

  .registerContainer {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 50px auto;
    width: 350px;
    padding: 15px 25px 15px 10px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    max-width: fit-content;
    max-height: fit-content;
  }
</style>
