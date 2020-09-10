<template>
  <div class="dashboard-container">
    <el-row :gutter="40">
      <el-col :span="6">
        <el-card shadow="hover" body-style="background:#409EFF; height:150px;">

          <el-row :gutter="20">
            <el-col :span="8"><i class="el-icon-s-order" style="color: #ffffff; font-size: 100px;"></i></el-col>
            <el-col :span="12" style="margin-left: 15px;">
              <p style="color: #ffffff; font-size: 20px; font-weight: bold;">总交易单数</p>
              <p style="color: #ffffff; font-size: 18px;">{{total.totalNumber}}个</p>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" body-style="background:#67C23A; height:150px;">

          <el-row :gutter="20">
            <el-col :span="8"><i class="el-icon-s-goods" style="color: #ffffff; font-size: 100px;"></i></el-col>
            <el-col :span="12" style="margin-left: 15px;">
              <p style="color: #ffffff; font-size: 20px; font-weight: bold;">总交易金额</p>
              <p style="color: #ffffff; font-size: 18px;">{{total.totalMoney}}元</p>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" body-style="background:#F56C6C; height:150px;">

          <el-row :gutter="20">
            <el-col :span="8"><i class="el-icon-tickets" style="color: #ffffff; font-size: 100px;"></i></el-col>
            <el-col :span="12" style="margin-left: 15px;">
              <p style="color: #ffffff; font-size: 20px; font-weight: bold;">今日下单数</p>
              <p style="color: #ffffff; font-size: 18px;">{{total.currentDayNumber}}个</p>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" body-style="background:#F56C6C; height:150px;">

          <el-row :gutter="20">
            <el-col :span="8"><i class="el-icon-goods" style="color: #ffffff; font-size: 100px;"></i></el-col>
            <el-col :span="12" style="margin-left: 15px;">
              <p style="color: #ffffff; font-size: 20px; font-weight: bold;">今日交易额</p>
              <p style="color: #ffffff; font-size: 18px;">{{total.currentDayMoney}}元</p>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24"><div id="myChart" :style="{height: '400px'}"></div></el-col>
    </el-row>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getTotalNumberAndMoney, listOrder, getEchartsData } from '@/api/order/order'
export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      total: {
        'totalMoney': 0,
        'totalNumber': 0,
        'totalProductNumber': 0,
        'currentDayNumber':0,
        'currentDayMoney':0,
      },
      orderDataList: [],
      dateList: [0,0,0,0,0,0,0],
      countList: [],
      moneyList: []
    }
  },
  mounted(){
    this.totalDateGet();
  },
  methods: {
    drawLine(){
      let that = this
      // 基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById('myChart'))
      // 绘制图表

      myChart.setOption({
        title: {
          text: '七日数据统计'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          data: ['当日交易数', '当日交易额']
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: that.dateList
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '当日交易数',
            type: 'line',
            stack: '总量',
            areaStyle: {
            },
            itemStyle: {
              normal:{
                color:'#f56c6c'    // 设置图形的颜色
              }
            },
            data: that.countList
          },
          {
            name: '当日交易额',
            type: 'line',
            stack: '总量',
            areaStyle: {},
            itemStyle: {
              normal:{
                color:'#409EFF'    // 设置图形的颜色
              }
            },
            data: that.moneyList
          }
        ]
      });
    },
    totalDateGet() {
      let that = this
      getTotalNumberAndMoney().then(response => {
        that.total.totalNumber = response.data.totalNumber
        that.total.totalMoney = response.data.totalMoney
        that.total.currentDayNumber = response.data.currentDayNumber
        that.total.currentDayMoney = response.data.currentDayMoney
      })
      let param = {
        dayCount: 7
      }
      //获取七日日期
      let dateList = []

      for(let count = 6; count >= 0; count --) {
        dateList.push(this.getDay(-count))
      }
      that.dateList = dateList
      //获取七日数据
      getEchartsData(param).then(response => {
        that.countList = response.data.countList
        that.moneyList = response.data.moneyList
        that.drawLine();
      })

    },
    getDay(day){
      let today = new Date();
      let targetday_milliseconds = today.getTime() + 1000*60*60*24*day;

      today.setTime(targetday_milliseconds); //注意，这行是关键代码

      let tYear = today.getFullYear();
      let tMonth = today.getMonth();
      let tDate = today.getDate();
      tMonth = this.doHandleMonth(tMonth + 1);
      tDate = this.doHandleMonth(tDate);
      return tYear+"-"+tMonth+"-"+tDate;
    },
    doHandleMonth(month){
      let m = month;
      if(month.toString().length == 1){
        m = "0" + month;
      }
      return m;
    }
  },

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

.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
