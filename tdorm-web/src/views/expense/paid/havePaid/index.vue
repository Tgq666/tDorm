<template>
  <div class="app-container">
    <div class="table">
      <el-table
        v-loading="loading"
        ref="filterTable"
        :data="tableData"
        height="500"
        stripe
        highlight-current-row
        style="width: 100%">
        <el-table-column
          prop="consumeTime"
          label="消费日期"
          sortable
          width="130"
          column-key="consumeTime"
        >
        </el-table-column>
        <el-table-column
          prop="payName"
          label="支付人"
          width="120"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="goodsName"
          label="商品名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="consumeName"
          label="消费人"
          style="text-align: center;width: fit-content">
        </el-table-column>
        <el-table-column
          prop="goodsPrice"
          sortable
          label="价格"
          width="120">
        </el-table-column>
        <el-table-column
          prop="note"
          label="备注"
          style="text-align: center;width: fit-content"
        >
        </el-table-column>
        <el-table-column
          prop="updateTime"
          label="结算日期"
          sortable
          width="130"
          column-key="updateTime">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    data() {
      return {
        loading: true,
        tableData: []
      }
    },
    created: function() {
      let param = new URLSearchParams()
      param.append('dormNum', '10007')
      axios.get('/api/sdConsumeInfo', { params: param }).then(resp => {
        this.loading = false
        if (resp.data.code === 200) {
          //填充表格数据
          this.tableData = resp.data.data
        }
      }).catch(reason => {
        this.loading = false
        this.errorHint('服务器内部错误')
      })
    },
  }
</script>
