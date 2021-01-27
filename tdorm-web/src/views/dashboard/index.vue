<template>
  <div class="dashboard-container">
    <h3>{{this.form.name}}，欢迎进入寝室</h3>
    <el-form ref="form"
             style="margin-left: 30px"
             :model="form"
             label-width="80px">
      <el-form-item label="寝室名称:">
        <a>{{this.form.dormName}}</a>
      </el-form-item>
      <el-divider></el-divider>
      <el-form-item label="寝室成员:">
        <a>{{this.form.memberName}}</a>
      </el-form-item>
      <el-divider></el-divider>
      <el-form-item label="寝室号码:">
        <a>{{this.form.dormNum}}</a>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      form: {
        name: "",
        dormName: "",
        dormNum: "",
        memberName: ""
      }
    }
  },
  created: function() {
    let user = JSON.parse(localStorage.getItem('user'))
    console.log(user)
    this.form.dormNum = user.dormNum;
    this.form.name = user.name;
    this.form.dormName = user.dormitory.dormName;
    axios.get('/api/dormUser', { params: { dormNum: user.dormNum } }).then(resp => {
      if (resp.data.code === 200) {
        let memberName = ''
        for (let i = 0; i < resp.data.data.length; i++) {
          memberName += resp.data.data[i].name
          if(i !== resp.data.data.length - 1){
            memberName += ',';
          }
        }
        this.form.memberName = memberName
      }
    }).catch(reason => {
      this.errorHint('服务器内部错误')
    })
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
