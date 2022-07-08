<template>
  <div style="padding: 10px;width: 100%">
    <el-row :gutter="10" style="padding: 10px">
      <el-col :span="6">
        <el-card style="height: 200px">
          <div>
            <label>充电总金额 ￥{{totalAmount}}</label>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="height: 200px">
          <div>
            <div style="margin-bottom: 20px"><label>充电桩总数：{{totalPile}}</label></div>
            <div style="margin-left: 20px;margin-bottom: 5px"><label>空闲充电桩： {{totalIdlePile}}</label></div>
            <div style="margin-left: 20px;margin-bottom: 5px"><label>已预约充电桩：{{totalReservedPile}}</label></div>
            <div style="margin-left: 20px;margin-bottom: 5px"><label>充电中充电桩：{{totalChargingPile}}</label></div>
            <div style="margin-left: 20px;margin-bottom: 5px"><label>维修中充电桩：{{totalRepairPile}}</label></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12" style="margin-bottom: 10px">
        <el-card >
            <div>
            <el-table
                v-loading="loading"
                :data="tableData"
                border
                stripe
                style="width: 100%;">
              <el-table-column
                  prop="stationId"
                  label="充电站ID"
                  sortable
              >
              </el-table-column>

              <el-table-column
                  prop="stationName"
                  label="充电站名"
                  sortable
              >
              </el-table-column>

              <el-table-column
                  prop="count"
                  label="空闲充电桩数量"
                  sortable
              >
              </el-table-column>

            </el-table>

          </div>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card>
          <div id="myChart2" :style="{width: '100%', height: '600px'}"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div id="myChart1" :style="{width: '100%', height: '600px'}"></div>
        </el-card>
      </el-col>
    <el-card>
      <div id="myChart" :style="{width: '600px', height: '500px'}"></div>

    </el-card>
    </el-row>
  </div>
</template>

<script>
import request from "@/utils/request";
import * as echarts from "echarts"

export default {
  name: "Statistics",
  data() {
    return {
      totalAmount:0.00,
      totalIdlePile:0,
      totalReservedPile:0,
      totalChargingPile:0,
      totalRepairPile:0,
      totalPile:0,
      loading: true,
      tableData: [],
      stations: [],
    }
  },
  created() {
    request.get("/order/totalAmount").then(res => {
      if (res.code === '0') {
       this.totalAmount=res.data;
       console.log(this.totalAmount)
      }else{
        this.$message({
          type: "error",
          message: res.msg
        })
      }
    })
    request.get("/pile/totalPile").then(res => {
      if (res.code === '0') {
        this.totalPile=res.data.totalPile;
        this.totalIdlePile=res.data.totalIdlePile;
        this.totalChargingPile=res.data.totalChargingPile;
        this.totalReservedPile=res.data.totalReservedPile;
        this.totalRepairPile=res.data.totalRepairPile;
        console.log(this.totalPile)
      }else{
        this.$message({
          type: "error",
          message: res.msg
        })
      }
    })
    this.load()
  },
  mounted() {
    this.drawLine();
  },
  methods: {
    load() {
      this.loading = true
      request.get("/station/all").then(res => {
        this.stations = res.data
      })
      request.get("/pile/idle").then(res => {
        this.idlePiles = res.data
      })
      request.get("/station/countIdlePile").then(res => {
        this.tableData = res.data
      })
      this.loading = false

    },
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById('myChart'))
      let myChart1 = echarts.init(document.getElementById('myChart1'))
      let myChart2 = echarts.init(document.getElementById('myChart2'))


      let option = {
        title: {
          text: '各宿舍楼用户比例统计图',
          subtext: '',
          left: 'left'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          trigger: 'item',
          left: 'center'
        },
        toolbox: {
          show: true,
          feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        series: [
          {
            name: '用户比例',
            type: 'pie',
            radius: [30, 90],
            center: ['50%', '60%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: []
          }
        ]
      };

      let option1 = {
        title: {
          text: '空闲充电桩比例统计图',
          subtext: '',
          left: 'left'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          trigger: 'item',
          left: 'center'
        },
        toolbox: {
          show: true,
          feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        series: [
          {
            name: '用户比例',
            type: 'pie',
            radius: [50, 150],
            center: ['50%', '60%'],
            roseType: 'area',
            itemStyle: {
              borderRadius: 8
            },
            data: []
          }
        ]
      };

      // 定义使用的颜色数组
      const colors = ['#5470c6','#91cc75', '#fac858', '#ee6666', '#73c0de','#3ba272'];
      let option2 = {
        title: {
          text: '空闲充电桩统计图',
          subtext: '',
          left: 'left'
        },
        grid: {
          left: 25,
          right: 25,
          bottom: 20,
          top: 80,
          containLabel: true
        },
        tooltip: {
          trigger: 'item'
        },
        toolbox: {
          show: true,
          feature: {
            mark: {show: true},
            dataView: {show: true, readOnly: false},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '空闲充电桩：',
            roseType: 'area',
            itemStyle: {
              normal:{
                color: function(params){
                  return colors[params.dataIndex];
                  // 取每条数据的 index 对应 colors 中的 index 返回这个颜色
                }
              }
            },
            data: [],
            type: 'bar',
            axisLabel: {
              interval: 0,
            },
          }
        ]
      }

      request.get("/user/countAddress").then(res => {
        if (res.code === '0') {
          res.data.forEach(item => {
            option.series[0].data.push({name: item.address, value: item.count})
          })
          // 绘制图表
          myChart.setOption(option);
        }
      })
      request.get("/station/countIdlePile").then(res => {
        if (res.code === '0') {
          res.data.forEach(item => {
            option1.series[0].data.push({name: item.stationName, value: item.count})

          })
          // 绘制图表
          myChart1.setOption(option1);
        }
      })

      //空闲充电桩统计图的数据
      request.get("/station/countIdlePile").then(res => {
        if (res.code === '0') {
          res.data.forEach(item => {
            option2.series[0].data.push({name: item.stationName, value: item.count})
            option2.xAxis.data.push(item.stationName)
            console.log(item.stationName);
          })
          // 绘制图表
          myChart2.setOption(option2);
        }
      })

    }
  }
}
</script>

<style scoped>

</style>