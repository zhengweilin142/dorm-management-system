<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="98px">
      <el-form-item label="宿舍房间号" prop="roomNo">
        <el-input
          v-model="queryParams.roomNo"
          placeholder="请输入宿舍房间号"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['dorm:room:add']"
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
          v-hasPermi="['dorm:room:edit']"
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
          v-hasPermi="['dorm:room:remove']"
        >删除
        </el-button>
      </el-col>

    </el-row>

    <el-table v-loading="loading" :data="roomList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键Id" align="center" prop="id" width="200"/>
      <el-table-column label="宿舍房间号" align="center" prop="roomNo"/>
      <el-table-column label="宿舍楼" align="center" prop="buildName"/>
      <el-table-column label="楼层" align="center" prop="floorNum"/>
      <el-table-column label="房间最大入住人数" align="center" prop="maxCapacity"/>
      <el-table-column label="当前房间入住人数" align="center" prop="currentCapacity"/>
      <el-table-column label="剩余电/ ？度" align="center" prop="electricity"/>
      <el-table-column label="剩余？吨水" align="center" prop="water"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="240">
        <template slot-scope="scope"  >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['dorm:room:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['dorm:room:remove']"
          >删除
          </el-button>
          <el-button
            v-hasPermi="['dorm:room:query']"
            icon="el-icon-info"
            size="mini"
            type="text"
            @click="handleInfo(scope.row)"
          >详情
          </el-button>
          <el-button
            icon="el-icon-add"
            size="mini"
            type="text"
            @click="openUser(scope.row)"
            v-hasPermi="['dorm:user:add']"
          >添加人员
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

    <!-- 添加或修改宿舍对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-form-item label="宿舍房间号" prop="roomNo">
          <el-input v-model="form.roomNo" placeholder="请输入宿舍房间号"/>
        </el-form-item>
        <el-form-item label="宿舍楼号" prop="buildId">
          <el-select v-model="form.buildId" placeholder="请选择宿舍楼号">
            <el-option
              v-for="item in buildList"
              :key="item.buildId"
              :label="item.buildName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="楼层" prop="floorNum">
          <el-input v-model="form.floorNum" placeholder="请输入楼层"/>
        </el-form-item>
        <el-form-item label="房间最大入住人数" prop="maxCapacity">
          <el-input v-model="form.maxCapacity" placeholder="请输入房间最大入住人数" label-width="160px"/>
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


    <!--添加宿舍人员-->
    <el-dialog :title="title" :visible.sync="dialogFormVisible">
      <el-form :model="dormRoomUser" label-width="160px">
        <el-form-item label="宿舍号" label-width="160px">
          <el-input
            v-model="dormRoomUser.roomNo"
            autocomplete="off"
            disabled
          />
        </el-form-item>
        <el-form-item label="用户" label-width="160px">
          <el-select
            v-model="dormRoomUser.userIds"
            multiple
            placeholder="请选择用户"
          >
            <el-option
              v-for="item in userList"
              :label="item.userName"
              :value="item.userId"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="add">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addRoom, delRoom, getRoom, listRoom, updateRoom } from '@/api/dorm/dormRoom'
import { listAllUser } from '@/api/user/user'
import { listAllBuild } from '@/api/dorm/dormBuild'
import { addRoomUser } from '@/api/dorm/dormRoomUser'

export default {
  name: 'Room',
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
      // 宿舍表格数据
      roomList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
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
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        roomNo: [
          { required: true, message: '宿舍房间号不能为空', trigger: 'blur' }
        ],
        buildId: [
          { required: true, message: '宿舍楼号不能为空', trigger: 'blur' }
        ],
        floorNum: [
          { required: true, message: '楼层不能为空', trigger: 'blur' }
        ],
        maxCapacity: [
          { required: true, message: '房间最大入住人数不能为空', trigger: 'blur' }
        ],
        currentCapacity: [
          { required: true, message: '当前房间入住人数不能为空', trigger: 'blur' }
        ]
      },

      dialogFormVisible: false,
      dormRoomUser: {
        roomId: null,
        roomNo: '',
        userIds: []
      },
      userList: [],
      buildList: []
    }
  },
  created() {
    this.getList()
    listAllBuild().then(response => {
      this.buildList = response.data
    })
  },
  methods: {
    /** 查询宿舍列表 */
    getList() {
      this.loading = true
      listRoom(this.queryParams).then(response => {
        this.roomList = response.rows
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
        roomNo: null,
        buildId: null,
        floorNum: null,
        maxCapacity: null,
        currentCapacity: null,
        electricity: null,
        water: null,
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加宿舍'
      listAllBuild().then(response => {
        this.buildList = response.data
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getRoom(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改宿舍'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRoom(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addRoom(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除宿舍编号为"' + ids + '"的数据项？').then(function() {
        return delRoom(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },

    // 打开弹窗
    async openUser(row) {
      this.dormRoomUser.userIds = []
      this.dormRoomUser.roomId = row.id
      this.dormRoomUser.roomNo = row.roomNo

      this.dialogFormVisible = true
      let res

      res = await listAllUser({
        roomId: row.id
      })

      this.userList = res.data
    },

    add() {
      this.dialogFormVisible = false
      addRoomUser(this.dormRoomUser).then(response => {
        if (response.code === 200) {
          this.getList()
          this.$modal.msgSuccess('添加成功')

        } else {
          this.$modal.msgError('添加失败')
        }
      })

    },
    // 跳转详情
    handleInfo(row) {
      this.$router.push('/dorm/info/' + row.id)
    }
  }
}
</script>
