<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="宿舍" prop="dormId">
        <el-select v-model="queryParams.dormId" clearable
                   @keyup.enter.native="handleQuery" placeholder="请选择宿舍"
        >
          <el-option
            v-for="room in roomList"
            :key="room.id"
            :label="room.roomNo"
            :value="room.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['check:check:add']"
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
          v-hasPermi="['check:check:edit']"
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
          v-hasPermi="['check:check:remove']"
        >删除
        </el-button>
      </el-col>

    </el-row>

    <el-table v-loading="loading" :data="checkList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="检查记录Id" align="center" prop="checkId"/>
      <el-table-column label="宿舍" align="center" prop="roomNo"/>
      <el-table-column label="检查日期" align="center" prop="checkDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检查人姓名" align="center" prop="checkerName"/>
      <el-table-column label="卫生评分" align="center" prop="cleanlinessScore"/>
      <el-table-column label="检查状态" align="center" prop="checkStatus"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['check:check:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['check:check:remove']"
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

    <!-- 添加或修改宿舍卫生检查，记录每次宿舍的卫生检查情况对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">

        <el-form-item label="宿舍" prop="dormId">
          <el-select v-model="form.dormId" placeholder="请选择宿舍">
            <el-option
              v-for="room in roomList"
              :key="room.id"
              :label="room.roomNo"
              :value="room.id"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="检查日期" prop="checkDate">
          <el-date-picker clearable
                          v-model="form.checkDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择检查日期"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item label="检查人姓名" prop="checkerName">
          <el-select v-model="form.checkerName" placeholder="请选择用户">
            <el-option
              v-for="item in userList"
              :label="item.userName"
              :value="item.userName"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否合格" prop="checkStatus">
          <el-select v-model="form.checkStatus" placeholder="请选择维修状态">
            <el-option
              v-for="item in stateList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="卫生评分" prop="cleanlinessScore">
          <el-input v-model="form.cleanlinessScore" placeholder="请输入卫生评分，0-100分"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
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
import { addCheck, delCheck, getCheck, listCheck, updateCheck } from '@/api/check/check'
import { parseTime } from '@/utils/common'
import { listAllUser } from '@/api/user/user'
import { listAllRoom } from '@/api/dorm/dormRoom'

export default {
  name: 'Check',
  data() {
    return {
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
      // 宿舍卫生检查，记录每次宿舍的卫生检查情况表格数据
      checkList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      stateList: [
        { label: '不合格', value: '不合格' },
        { label: '合格', value: '合格' }
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dormId: null,
        checkDate: null,
        checkerName: null,
        cleanlinessScore: null,
        checkStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        dormId: [
          { required: true, message: '宿舍ID不能为空', trigger: 'blur' }
        ],
        checkDate: [
          { required: true, message: '检查日期不能为空', trigger: 'blur' }
        ],
        checkerName: [
          { required: true, message: '检查人姓名不能为空', trigger: 'blur' }
        ],
        cleanlinessScore: [
          { required: true, message: '卫生评分，0-100分不能为空', trigger: 'blur' }
        ]
      },
      userList: [], // 存放用户列表
      roomList: []    // 存放房间列表
    }
  },
  created() {
    this.getList()
    this.fetchRoomList()
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
    /** 查询宿舍卫生检查，记录每次宿舍的卫生检查情况列表 */
    getList() {
      this.loading = true
      listCheck(this.queryParams).then(response => {
        this.checkList = response.rows
        this.total = response.total
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
        checkId: null,
        dormId: null,
        checkDate: null,
        checkerName: null,
        cleanlinessScore: null,
        checkStatus: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.ids = selection.map(item => item.checkId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加宿舍卫生检查'
      this.fetchUserList()
      this.fetchRoomList()
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const checkId = row.checkId || this.ids
      getCheck(checkId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改宿舍卫生检查'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.checkId != null) {
            updateCheck(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addCheck(this.form).then(response => {
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
      const checkIds = row.checkId || this.ids
      this.$modal.confirm('是否确认删除宿舍卫生检查，记录每次宿舍的卫生检查情况编号为"' + checkIds + '"的数据项？').then(function() {
        return delCheck(checkIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    }
  }
}
</script>
