<template>
  <div id="echarts-dom" style="width: 650px;height: 500px"></div>
</template>

<script>
import * as echarts from 'echarts'
import { getBuildingNum } from '@/api/bi'

require('echarts/theme/macarons')

export default {
  name: 'home_echarts',
  data() {
    return {
      option: {
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '楼栋人数',
            type: 'pie',
            radius: '55%',  // 饼图的大小
            center: ['50%', '50%'],  // 饼图的中心位置
            data: []  // 饼图的数据显示数组
          }
        ]
      },
      myEcharts: ''
    }
  },
  created() {
    getBuildingNum().then(res => {

      // 假设返回的数据格式是 { "buildingName": 10, "buildingName2": 15, ... }
      const buildingNames = Object.keys(res.data)  // 获取楼栋名称数组
      const buildingNumMap = res.data  // 获取楼栋对应人数的Map

      // 设置series的数据，转换为饼图需要的数据格式
      this.option.series[0].data = buildingNames.map(name => ({
        value: buildingNumMap[name],
        name: name
      }))

      // 更新图表
      this.myEcharts.setOption(this.option, true)
    })
  },
  mounted() {
    this.createEcharts()
  },
  methods: {
    createEcharts() {
      const chartDmo = document.getElementById('echarts-dom')
      this.myEcharts = echarts.init(chartDmo, null)
      this.myEcharts.setOption(this.option, true)
    }
  }
}
</script>

<style scoped>
</style>
