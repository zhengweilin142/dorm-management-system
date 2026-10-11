<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="60px">
      <el-form-item label="报修人" prop="repairer">
        <el-input
          v-model="queryParams.repairer"
          placeholder="请输入报修人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb10">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['message:repair:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['message:repair:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['message:repair:remove']"
        >删除
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="repairList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="订单编号" align="center" prop="id" width="200"/>
      <el-table-column label="报修人" align="center" prop="repairer"/>
      <el-table-column label="报修宿舍楼" align="center" prop="buildName"/>
      <el-table-column label="报修宿舍房间号" align="center" prop="roomNo"/>
      <el-table-column label="标题" align="center" prop="title"/>
      <el-table-column label="保修内容" align="center" prop="content"/>
      <el-table-column label="订单状态" align="center" prop="state"/>
      <el-table-column label="订单创建时间" align="center" prop="orderBuildTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderBuildTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单完成时间" align="center" prop="orderFinishTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderFinishTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['message:repair:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['message:repair:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改保修对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-form-item label="报修人" prop="repairer">
          <el-select v-model="form.repairer" placeholder="请选择报修人">
            <el-option
              v-for="item in userList"
              :label="item.userName"
              :value="item.userName"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="保修宿舍" prop="roomId">
          <el-select v-model="form.roomId" placeholder="请选择报修宿舍房间号">
            <el-option
              v-for="room in roomList"
              :key="room.id"
              :label="room.roomNo"
              :value="room.id"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="报修标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入保修标题"/>
        </el-form-item>
        <el-form-item label="保修内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="维修状态" prop="state">
          <el-select v-model="form.state" placeholder="请选择维修状态">
            <el-option
              v-for="item in stateList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单创建时间" prop="orderBuildTime">
          <el-date-picker clearable
                          v-model="form.orderBuildTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择订单创建时间"
          >
          </el-date-picker>
        </el-form-item>


        <el-form-item label="订单完成时间" prop="orderFinishTime">
          <el-date-picker clearable
                          v-model="form.orderFinishTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择订单完成时间"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addRepair, delRepair, getRepair, listRepair, updateRepair } from '@/api/message/repair'
import { parseTime } from '@/utils/common'
import { listAllUser } from '@/api/user/user'
import { listAllRoom } from '@/api/dorm/dormRoom'

export default {
  name: 'Repair',
  data() {
    return {
      stateList: [
        { label: '完成', value: '完成' },
        { label: '未完成', value: '未完成' }
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 保修表格数据
      repairList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        repairer: null,
        buildId: null,
        roomId: null,
        title: null,
        content: null,
        state: null,
        orderBuildTime: null,
        orderFinishTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        repairer: [
          { required: true, message: '报修人不能为空', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '保修标题不能为空', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '保修内容不能为空', trigger: 'blur' }
        ],
        state: [
          { required: true, message: '订单状态不能为空', trigger: 'blur' }
        ],
        orderBuildTime: [
          { required: true, message: '订单创建时间不能为空', trigger: 'blur' }
        ]
      },
      userList: [], // 存放用户列表
      roomList: []    // 存放房间列表
    }
  },
  created() {
    this.getList()

  },
  methods: {
    fetchUserList() {
      listAllUser().then(response => {
        this.userList = response.data
      })
    },
    fetchRoomList() {

      listAllRoom().then(response => {
        this.roomList = response.data
      })
    },
    parseTime,
    /** 查询保修列表 */
    getList() {
      this.loading = true
      listRepair(this.queryParams).then(response => {
        this.repairList = response.rows
        this.total = Number(response.total)
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        repairer: null,
        buildId: null,
        buildName: null,
        roomId: null,
        roomNo: null,
        title: null,
        content: null,
        state: null,
        orderBuildTime: null,
        orderFinishTime: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加保修'
      this.fetchUserList()
      this.fetchRoomList()
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getRepair(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改保修'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRepair(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addRepair(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除保修编号为"' + ids + '"的数据项？').then(function() {
        return delRepair(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
  }
}
</script>
