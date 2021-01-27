<template>
  <div class="app-container">
    <el-form ref="form"
             v-loading="loading"
             :model="form"
             :rules="addConsumeRules"
             label-width="120px">
      <el-form-item label="商品名" prop="goodsName">
        <el-input v-model="form.goodsName" placeholder="请输入商品名"/>
      </el-form-item>
      <el-form-item label="支付金额" prop="goodsPrice">
        <el-input v-model="form.goodsPrice" placeholder="请输入商品金额"/>
      </el-form-item>
      <el-form-item label="消费时间" prop="consumeTime">
        <el-col>
          <el-date-picker v-model="form.consumeTime" type="date" placeholder="请选择是那一天的消费" style="width: 100%;"/>
        </el-col>
      </el-form-item>
      <el-form-item label="支付人" prop="payName">
        <el-select v-model="form.payName" placeholder="请选择付钱的土豪">
          <el-option v-for="name in this.nameList" :label="name.name" :value="name.name"/>
        </el-select>
      </el-form-item>
      <el-form-item label="消费人" prop="consumeName">
        <el-checkbox-group v-model="consumeList">
          <el-checkbox v-for="name in this.nameList" :label="name.name" :value="name.name">{{name.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="备注" prop="note">
        <el-input v-model="form.note" type="textarea" placeholder="请输入备注"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">确定</el-button>
        <el-button @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    created: function() {
      let user = JSON.parse(localStorage.getItem('user'))
      this.dormNum = user.dormNum
      this.loading = true
      axios.get('/api/dormUser', { params: { dormNum: this.dormNum } }).then(resp => {
        this.loading = false
        if (resp.data.code === 200) {
          this.nameList = resp.data.data
        }
      }).catch(reason => {
        this.loading = false
        this.errorHint('服务器内部错误')
      })
    },
    data() {
      const validatePrice = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入金额'))
        } else if (!/^[0-9]*$/.test(value)) {
          callback(new Error('请输入数字'))
        } else {
          callback()
        }
      }
      return {
        loading: false,
        dormNum:null,
        nameList: [],
        consumeList: [],
        addConsumeRules: {
          goodsPrice: [{ required: true, trigger: 'blur', validator: validatePrice }],
          goodsName: [{ required: true, trigger: 'blur', message: '请输入商品名' }],
          consumeTime: [{ required: true, trigger: 'blur', message: '请选择消费时间' }],
          payName: [{ required: true, trigger: 'blur', message: '请选择付钱的人' }],
        },
        form: {
          goodsName: '',
          goodsPrice: '',
          consumeTime: '',
          consumeName: '',
          payName: '',
          note: ''
        }
      }
    },
    methods: {
      onSubmit() {
        this.$refs.form.validate(valid => {
          this.loading = true
          this.form.consumeId = []
          let length = this.consumeList.length
          for (let i = 0; i < length - 1; i++) {
            this.form.consumeName += this.consumeList[i] + ','
          }
          this.form.consumeName += this.consumeList[length - 1]
          if (valid) {
            let param = new URLSearchParams();
            param.append("goodsName",this.form.goodsName);
            param.append("goodsPrice",this.form.goodsPrice);
            param.append("consumeTime",this.form.consumeTime);
            param.append("consumeName",this.form.consumeName);
            param.append("settledName",this.form.payName)
            param.append("payName",this.form.payName);
            param.append("note",this.form.note);
            param.append("dormNum",this.dormNum);
            axios.post('/api/addConsume',param).then(resp => {
              this.loading = false
              if (resp.data.code === 200) {
                this.$confirm('是否继续添加', '添加成功', {
                  confirmButtonText: '是',
                  cancelButtonText: '否  ',
                  type: 'success'
                }).then(() => {
                  this.$refs.form.resetFields()
                }).catch(() => {
                  this.$router.push('/')
                })
              }
            }).catch(reason => {
              this.loading = false
              this.errorHint('服务器内部错误')
            })
          } else {
            this.loading = false
            return false
          }
        })
      },
      onCancel() {
        this.$refs.form.resetFields();
      }
    }
  }
</script>

<style scoped>
</style>

