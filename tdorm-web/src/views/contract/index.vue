<template>
  <div class="app-container">
    <template>
      <el-table
        :data="tableData"
        style="width: fit-content ">
        <el-table-column
          type="index"
          width="50">
        </el-table-column>
        <el-table-column
          property="contract"
          label="约定"
          width="200px">
        </el-table-column>
        <el-table-column
          align="left">
          <template slot="header">
            <el-button
              size="mini"
              type="primary"
              @click="open()">添加
            </el-button>
          </template>
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="danger"
              @click="deleteContract(scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </template>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    data() {
      return {
        tableData: [],
        dormNum:'',
      }
    },
    created: function() {
      let param = new URLSearchParams()
      let user = JSON.parse(localStorage.getItem('user'))
      this.dormNum = user.dormNum;
      param.append('dormNum', this.dormNum)
      axios.get('/api/getContract', { params: param }).then(resp => {
        if (resp.data.code === 200) {
          this.tableData = resp.data.data
        } else {
          this.errorHint('查询失败，请稍后再试')
        }
      }).catch(() => {
        this.errorHint('服务器内部错误')
      })
    },
    methods: {
      deleteContract(val) {
        this.$confirm('确认删除嘛', '删除消费', {
          confirmButtonText: '是',
          cancelButtonText: '否  ',
          type: 'success'
        }).then(() => {
          let param = new URLSearchParams()
          param.append('id', val.id)
          axios.post('/api/deleteContract', param).then(resp => {
            if (resp.data.code === 200) {
              this.successHint('删除成功')
              location.reload()
            } else {
              this.errorHint('查询失败，请稍后再试')
            }
          }).catch(() => {
            this.errorHint('服务器内部错误')
          })
        }).catch(() => {

        })

      },
      open(){
        this.$prompt('请输入合约', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
        }).then(({ value }) => {
          this.addContract(value);
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });
        });
      },
      addContract(message) {
        let param = new URLSearchParams()
        param.append('dormNum', this.dormNum)
        param.append('contract', message)
        axios.post('/api/addContract', param).then(resp => {
          if (resp.data.code === 200) {
            this.successHint('添加成功')
            location.reload()
          } else {
            this.errorHint('查询失败，请稍后再试')
          }
        }).catch(() => {
          this.errorHint('服务器内部错误')
        })
      }
    }
  }
</script>

<style scoped>
</style>

