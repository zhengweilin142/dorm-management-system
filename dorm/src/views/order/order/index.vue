<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px" size="small">
      <el-form-item label="充值人" prop="userId">
        <el-input
          v-model="queryParams.userId"
          clearable
          placeholder="请输入充值人"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="宿舍Id" prop="dormId">
        <el-input
          v-model="queryParams.dormId"
          clearable
          placeholder="请输入宿舍Id"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="金额" prop="amount">
        <el-input
          v-model="queryParams.amount"
          clearable
          placeholder="请输入金额"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['order:order:add']"
          icon="el-icon-plus"
          plain
          size="mini"
          type="primary"
          @click="handleAdd"
        >新增
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          v-hasPermi="['order:order:edit']"-->
<!--          :disabled="single"-->
<!--          icon="el-icon-edit"-->
<!--          plain-->
<!--          size="mini"-->
<!--          type="success"-->
<!--          @click="handleUpdate"-->
<!--        >修改-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          v-hasPermi="['order:order:remove']"-->
<!--          :disabled="multiple"-->
<!--          icon="el-icon-delete"-->
<!--          plain-->
<!--          size="mini"-->
<!--          type="danger"-->
<!--          @click="handleDelete"-->
<!--        >删除-->
<!--        </el-button>-->
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="55"/>
      <el-table-column align="center" label="订单id" prop="orderId"/>
      <el-table-column align="center" label="充值人" prop="userId"/>
      <el-table-column align="center" label="宿舍Id" prop="roomNo"/>
      <el-table-column align="center" label="订单类型" prop="orderType"/>
      <el-table-column align="center" label="金额" prop="amount"/>
      <el-table-column align="center" label="状态" prop="status"/>
      <el-table-column align="center" label="备注" prop="remark"/>
<!--      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            v-hasPermi="['order:order:edit']"-->
<!--            icon="el-icon-edit"-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--          >修改-->
<!--          </el-button>-->
<!--          <el-button-->
<!--            v-hasPermi="['order:order:remove']"-->
<!--            icon="el-icon-delete"-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            @click="handleDelete(scope.row)"-->
<!--          >删除-->
<!--          </el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
    />

    <!-- 添加或修改订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="充值人" prop="userId">
          <el-select
            v-model="form.userId"
            placeholder="请选择用户"
          >
            <el-option
              v-for="item in userList"
              :label="item.userName"
              :value="item.userId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="宿舍" prop="dormId">
          <el-select
            v-model="form.dormId"
            placeholder="请输入宿舍"
          >
            <el-option
              v-for="item in roomList"
              :label="item.roomNo"
              :value="item.id"
            />
          </el-select>

        </el-form-item>
        <el-form-item label="充值类型" prop="orderType">
          <el-select
            v-model="form.orderType"
            placeholder="请输入充值类型"
          >
            <el-option label="电费" value="电费"/>
            <el-option label="水费" value="水费"/>
          </el-select>

        </el-form-item>
        <el-form-item label="金额" prop="amount">
          <el-input-number v-model="form.amount" placeholder="请输入金额" :min="0" :step="5" />
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
import { addOrder, delOrder, getOrder, listOrder, updateOrder } from '@/api/order/order'
import { listAllRoom } from '@/api/dorm/dormRoom'
import { listAllUser } from '@/api/user/user'

export default {
  name: 'Order',
  data() {
    return {
      // userIds
      userList: [],
      // 房间
      roomList: [],
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
      // 订单表格数据
      orderList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        dormId: null,
        orderType: null,
        amount: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: '充值人不能为空', trigger: 'blur' }
        ],
        dormId: [
          { required: true, message: '宿舍Id不能为空', trigger: 'blur' }
        ],
        orderType: [
          { required: true, message: '订单类型不能为空', trigger: 'change' }
        ],
        amount: [
          { required: true, message: '金额不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询订单列表 */
    getList() {
      this.loading = true
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows
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
        orderId: null,
        userId: null,
        dormId: null,
        orderType: null,
        amount: null,
        status: null,
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
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加订单'
      listAllUser().then(res => {
        this.userList = res.data
      })

      listAllRoom().then(res => {
        this.roomList = res.data
      })

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const orderId = row.orderId || this.ids
      getOrder(orderId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改订单'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.orderId != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addOrder(this.form).then(response => {
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
      const orderIds = row.orderId || this.ids
      this.$modal.confirm('是否确认删除订单编号为"' + orderIds + '"的数据项？').then(function() {
        return delOrder(orderIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    }
  }
}
</script>
