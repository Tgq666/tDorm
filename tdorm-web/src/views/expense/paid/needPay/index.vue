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
          style="text-align: center;width: fit-content ;">
        </el-table-column>
        <el-table-column
          prop="settledName"
          label="已支付"
          style="text-align: center;width: fit-content ;">
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
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="deleteBill(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pay" style="margin-top: 15px;">
      <a>您的总账单为：</a>
      <a style="color: red">{{this.pay}}</a>
      <a style="color: red">元</a>
      <el-popover
        placement="top"
        trigger="click"
        style="margin-left: 10px;width: fit-content"
        v-model="visible">
        <el-table :data="gridData">
          <el-table-column width="100" property="name" label="姓名"></el-table-column>
          <el-table-column width="100" property="price" label="账单"></el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="100">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="confirmBill(scope.row)">我已支付</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-button slot="reference" @click="">查看账单</el-button>
      </el-popover>
    </div>

  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    data() {
      return {
        visible: false,
        gridData:[],
        nameFilter:[],
        pay : 0,
        payText: "去支付",
        disabled :false,
        loading: true,
        tableData: []
      }
    },
    created: function() {
      let param = new URLSearchParams()
      let user = JSON.parse(localStorage.getItem('user'))
      param.append('dormNum', user.dormNum)
      axios.get('/api/unSdConsumeInfo', { params: param }).then(resp => {
        this.loading = false
        if (resp.data.code === 200) {
          //填充表格数据
          this.tableData = resp.data.data
          //计算当前总账单
          let username = user.name;
          let tempPay = 0;
          //计算和每个人的账单
          //和我账单相关的姓名
          let map = new Map();
          for (let i = 0; i < this.tableData.length; i++) {
            const curConsume = this.tableData[i];
            let perPrice = curConsume.goodsPrice / curConsume.consumeName.split(',').length;
            if(curConsume.payName === username){
              //如果我是支付人，那么，未支付的人都和我相关
              //已消费的姓名
              const consumeName = curConsume.consumeName.split(',');
              //已支付的姓名
              const settledName = curConsume.settledName.split(',');
              //查找未结清的姓名
              for (let j = 0; j < consumeName.length; j++) {
                for (let k = 0; k < settledName.length; k++) {
                  if(consumeName[j] === settledName[k]){
                    //如果消费名字在已结清的名字中有了，则该名字不是为结清的
                    break;
                  }else if(k === settledName.length - 1){
                    //查找到最后，但是该消费者还是没有结清，说明该消费者没有支付
                    if(!map.has(consumeName[j])){
                      //如果该名字不存在,则把该名字添加进账单中
                      map.set(consumeName[j],perPrice)
                    }else{
                      //如果名字存在，更新和该名字的账单
                      map.set(consumeName[j],map.get(consumeName[j]) + perPrice);
                    }
                  }
                }
              }
            }else{
              //如果我不是支付人，则当前账单肯定和我相关
              let curPayName = curConsume.payName;
              if(!map.has(curPayName)){
                //如果该名字不存在,则把该名字添加进账单中
                map.set(curPayName,-perPrice);
              }else{
                //如果名字存在，更新和该名字的账单
                map.set(curPayName,map.get(curPayName) - perPrice);
              }
            }
          }
          let tempBill = [];
          map.forEach(function (item, key, mapObj) {
            let tempData = {name:key,price : item.toFixed(2)};
            tempPay = item + tempPay;
            tempBill.push(tempData);
          });
          this.pay = tempPay.toFixed(2);
          this.gridData = tempBill;
        }
      }).catch(reason => {
        this.loading = false
        this.errorHint('服务器内部错误')
      })
    },
    methods: {
      confirmBill(data){
        if(data.price >= 0 ){
          this.successHint("您无需支付")
        }else{
          this.$confirm('确认已经支付了', '账单结算', {
            confirmButtonText: '是',
            cancelButtonText: '否  ',
            type: 'success'
          }).then(() => {
            console.log(this.tableData)
            let confirmName = data.name;
            let confirmList = [];
            let user = JSON.parse(localStorage.getItem('user'))
            let username = user.name;
            for (let i = 0; i < this.tableData.length; i++) {
              let curConsume = this.tableData[i];
              if(curConsume.payName === confirmName){
                //如果是结算的人支付的，那么我需要添加到结算列表中
                let curConfirm = {id : curConsume.id,settledName : username};
                confirmList.push(curConfirm);
              }else if(curConsume.payName === username){
                //已消费的姓名
                const consumeName = curConsume.consumeName.split(',');
                //已支付的姓名
                const settledName = curConsume.settledName.split(',');
                for (let j = 0; j < consumeName.length; j++) {
                  if(consumeName[j] === confirmName){
                    for (let k = 0; k < settledName.length; k++) {
                      if(settledName[k] === confirmName){
                        break;
                      }else if(k === settledName.length - 1){
                        //结算的人在我的支付中消费了，且没有结清，则需要添加到结算列表中
                        let curConfirm = {id : curConsume.id,settledName : confirmName};
                        confirmList.push(curConfirm);
                      }
                    }
                  }
                }
              }
            }
            axios.post('/api/confirmConsume',JSON.stringify(confirmList),{
              headers: { 'Content-Type':'application/json; charset=UTF-8' }
            }).then(resp=>{
              if(resp.data.code === 200){
                this.successHint("结算成功")
                location.reload();
              }else{
                this.successHint("结算失败,请稍后再试")
              }
            },err =>{
              console.log(err)
            })
          }).catch(() => {

          })
        }
      },
      deleteBill(data){
        this.$confirm('确认删除嘛', '删除消费', {
          confirmButtonText: '是',
          cancelButtonText: '否  ',
          type: 'success'
        }).then(() => {
          let params = new URLSearchParams();
          params.append("id",data.id);
          axios.post("/api/deleteConsume",params).then(resp=>{
            if(resp.data.code === 200){
              this.successHint("删除成功")
              location.reload()
            }
          }).catch(reason => {
            this.errorHint("服务器内部错误")
          })
        }).catch(() => {

        })
      }
    }
  }
</script>
