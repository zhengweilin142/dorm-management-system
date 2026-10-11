<template>
  <el-card style="margin: 15px; min-height: calc(100vh - 100px)" class="dashboard-card">
    <!-- 头部数据 -->
    <div>
      <el-row :gutter="20" class="topInfo">
        <el-col :span="6">
          <div class="stat-card">
            <div class="stat-header">实时</div>
            <div class="stat-title">学生统计</div>
            <div class="stat-value">{{ this.studentNum }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-card">
            <div class="stat-header">实时</div>
            <div class="stat-title">住宿人数</div>
            <div class="stat-value">{{ this.haveRoomStudentNum }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-card">
            <div class="stat-header">实时</div>
            <div class="stat-title">报修统计</div>
            <div class="stat-value">{{ this.repairOrderNum }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-card">
            <div class="stat-header">实时</div>
            <div class="stat-title">空宿舍统计</div>
            <div class="stat-value">{{ this.noFullRoomNum }}</div>
          </div>
        </el-col>
      </el-row>
    </div>

    <!-- 下部 -->
    <div class="bottom-section">
      <div class="left-section">
        <span class="section-title">宿舍通告</span>
        <el-card class="notice-card" v-for="(activity, index) in activities.slice(0, 8)" :key="index">
          <div class="notice-header">
            <el-tag type="success" class="notice-tag">新通知</el-tag>
            <span class="notice-time">{{ activity.createTime }}</span>
          </div>
          <div class="notice-body">
            <span class="notice-title">{{ activity.noticeTitle }}</span>
          </div>
        </el-card>
      </div>


      <!-- 中部 -->
      <div class="center-section">
        <span class="section-title">宿舍学生人数分布图</span>
        <homeEcharts/>
      </div>

      <!-- 右侧 -->
<!--      <div class="right-section">-->

<!--        <el-card class="calendar-card">-->
<!--          <span class="calendar-title">日历</span>-->
<!--          <Calender/>-->
<!--        </el-card>-->
<!--      </div>-->
    </div>
  </el-card>
</template>

<script>

import Calender from '@/components/Calendar/Calendar.vue'
import homeEcharts from '@/components/echarts/homeEcharts.vue'
import { getHomePageNotice, getNoFullRoom, getOrderNum, getStuNum, selectHaveRoomStuNum } from '@/api/bi'

export default {
  components: {

    Calender,
    homeEcharts
  },
  name: 'Index',
  data() {
    return {
      studentNum: 12,
      haveRoomStudentNum: 20,
      repairOrderNum: 20,
      noFullRoomNum: 30,
      activities: []
    }
  },
  created() {
    // 获取数据
    getNoFullRoom().then(res => {
      this.noFullRoomNum = res.data
    })
    getOrderNum().then(res => {
      this.repairOrderNum = res.data
    })
    selectHaveRoomStuNum().then(res => {
      this.haveRoomStudentNum = res.data
    })
    getStuNum().then(res => {
      this.studentNum = res.data
    })
    getHomePageNotice().then(res => {
      this.activities = res.data
    })
  }
}
</script>

<style scoped>
@import '../assets/styles/css/Home.css';

/* Card 样式 */
.dashboard-card {
  background-color: #f4f6f9;
  border-radius: 10px;
  padding: 20px;
}

.stat-card {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.stat-header {
  font-size: 14px;
  color: #999;
}

.stat-title {
  font-size: 18px;
  color: #333;
  margin-top: 10px;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #1f79f6;
  margin-top: 10px;
}

.bottom-section {
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
}

.left-section,
.center-section,
.right-section {
  flex: 1;
  padding: 10px;
}

.section-title {
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}

.timeline-item-text {
  font-size: 15px;
  color: #333;
}

.calendar-card {
  width: 380px;
  max-height: 440px;
  margin-top: 17px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.calendar-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 15px;
  color: #333;
}

.el-timeline-item {
  margin-bottom: 10px;
}

/* 左侧通告部分 */
.left-section {
  flex: 1;
  padding: 10px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
  height: 100%;
  overflow-y: auto; /* 使通知区域支持滚动 */
  max-height: 480px; /* 设置最大高度，超出时出现滚动条 */
}

/* 通告卡片的样式 */
.notice-card {
  margin-bottom: 15px;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  background-color: #ffffff;
  padding: 20px;
  transition: transform 0.3s ease;
}

/* 通告的头部（时间和标签） */
.notice-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.notice-tag {
  font-size: 14px;
  padding: 0 12px;
  border-radius: 15px;
  margin-top: 0;
}

.notice-time {
  font-size: 12px;
  color: #999;
}

/* 通告的主体内容 */
.notice-body {
  font-size: 16px;
  color: #333;
  font-weight: 500;
}

.notice-title {
  line-height: 1.5;
}


</style>
