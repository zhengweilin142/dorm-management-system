<template>
  <div class="app-container">
    <el-row class="full-height">
      <!-- 左侧部分 -->
      <el-col :span="11" class="left-panel">
        <el-card class="class-info-card">
          <h3>宿舍基本信息</h3>

          <el-form label-width="120px" size="small">
            <el-form-item label="楼名">
              <span>{{ dormInfo.buildName }}</span>
            </el-form-item>
            <el-form-item label="宿舍号">
              <span>{{ dormInfo.roomNo }}</span>
            </el-form-item>
            <el-form-item label="楼层">
              <span>第 {{ dormInfo.floorNum }} 楼</span>
            </el-form-item>
            <el-form-item label="最多入住人数">
              <span>{{ dormInfo.maxCapacity }} 人</span>
            </el-form-item>
            <el-form-item label="当前居住人数">
              <span>{{ dormInfo.currentCapacity }} 人</span>
            </el-form-item>
            <el-form-item label="水">
              <span>{{ dormInfo.electricity }} / 度</span>
            </el-form-item>
            <el-form-item label="电">
              <span>{{ dormInfo.maxCapacity }} / 吨</span>
            </el-form-item>
            <el-form-item label="备注">
              <span>{{ dormInfo.remark || '无' }}</span>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <!-- 右侧部分 -->
      <el-col :span="12" class="right-panel">
        <el-row class="right-content full-height">
          <!-- 右侧上部分 -->
          <el-card class="table-card">
            <h6>宿舍人员列表</h6>
            <el-table
              v-loading="loading"
              :data="userList"
              border
              size="mini"
            >
              <el-table-column
                align="center"
                label="序号"
                type="index"
                width="100"
              />
              <el-table-column
                align="center"
                label="姓名"
                prop="userName"
                width="150"
              />
              <el-table-column
                align="center"
                label="联系方式"
                prop="phonenumber"
                width="180"
              />
              <el-table-column
                align="center"
                class-name="small-padding fixed-width"
                label="操作"
              >
                <template slot-scope="scope">
                  <el-button
                    v-hasPermi="['class:teacher:remove']"
                    icon="el-icon-edit"
                    size="mini"
                    type="text"
                    @click="handUserDel(scope.row)"
                  >移除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>

            <pagination
              :limit.sync="queryParams.pageSize"
              :page.sync="queryParams.pageNum"
              :total="total"
              @pagination="getDormList"
            />
          </el-card>

        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getRoom } from '@/api/dorm/dormRoom'
import { listRoomUser, removeRoomUser } from '@/api/dorm/dormRoomUser'

export default {
  name: 'info',
  data() {
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roomNo: null,
        buildId: null,
        floorNum: null,
        maxCapacity: null,
        currentCapacity: null,
        electricity: null,
        water: null
      },
      // 遮罩层
      loading: true,
      roomId: null,
      dormInfo: {
        id: null,
        roomNo: null,
        buildId: null,
        floorNum: null,
        maxCapacity: null,
        currentCapacity: null,
        electricity: null,
        water: null
      },
      // 学生
      userList: [],
      // 总条数
      total: 0

    }
  },
  async created() {
    this.roomId = this.$route.params && this.$route.params.roomId
    await this.getDormInfo(this.roomId)
    await this.getUserList(this.roomId,this.queryParams)
  },
  methods: {
    //====================================  数据请求  ====================================
    async getDormInfo(id) {
      const { data: res } = await getRoom(id)
      this.dormInfo = { ...res }
    },
    async getUserList(roomId, searchQuery) {
      let query = {
        roomId,
        ...searchQuery
      }
      const res = await listRoomUser(query)

      this.userList = res.rows
      this.total = Number(res.total)
      this.loading = false

    },
    //====================================  逻辑处理类  ====================================
    // 移除
    handUserDel(row) {
      removeRoomUser({ roomId: this.roomId, userId: row.userId }).then(
        async() => {
          this.$modal.msgSuccess('移除成功')

          await this.getDormInfo(this.roomId)
          await this.getUserList(this.roomId,this.queryParams)
        }
      )
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  padding: 10px;
}

.class-info-card {
  height: calc(100vh - 120px) !important;
}

.full-height {
  height: calc(100vh - 120px) !important;
}

.right-panel {
  height: calc(100% - 84px) !important;
  margin-left: 20px;
}

.right-content {
  display: flex; /* 使用 Flexbox 布局 */
  flex-direction: column; /* 垂直排列子元素 */
  height: 100%;
}

.top-panel,
.bottom-panel {
  flex: 1;
  padding: 5px;
  overflow: auto; /* 禁止滚动 */
}

.table-card {
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>
