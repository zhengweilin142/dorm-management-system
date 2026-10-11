<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--用户数据-->

      <el-col>
        <!-- 搜索条件-->
        <el-form
          v-show="showSearch"
          ref="queryForm"
          :inline="true"
          :model="queryParams"
          label-width="68px"
          size="small"
        >
          <el-form-item label="用户名称" prop="userName">
            <el-input
              v-model="queryParams.userName"
              clearable
              placeholder="请输入用户名称"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="手机号码" prop="phonenumber">
            <el-input
              v-model="queryParams.phonenumber"
              clearable
              placeholder="请输入手机号码"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="用户角色" prop="roleId">
            <el-select
              v-model="queryParams.roleId"
              clearable
              placeholder="用户角色"
              style="width: 240px"
            >
              <el-option
                v-for="item in roleList"
                :key="item.roleId"
                :label="item.roleName"
                :value="item.roleId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select
              v-model="queryParams.status"
              clearable
              placeholder="用户状态"
              style="width: 240px"
            >
              <el-option
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-search"
              size="mini"
              type="primary"
              @click="handleQuery"
            >搜索
            </el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
            >重置
            </el-button>
          </el-form-item>
        </el-form>
        <!--操作按钮-->
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              v-hasPermi="['system:user:add']"
              icon="el-icon-plus"
              plain
              size="mini"
              type="primary"
              @click="handleAdd"
            >新增
            </el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="userList">
          <el-table-column
            align="center"
            label="序号"
            type="index"
            width="50"
          />
          <el-table-column
            key="account"
            align="center"
            label="账号"
            prop="account"
          />
          <el-table-column
            key="userName"
            :show-overflow-tooltip="true"
            align="center"
            label="用户名称"
            prop="userName"
          />
          <el-table-column
            key="nickName"
            :show-overflow-tooltip="true"
            align="center"
            label="用户昵称"
            prop="nickName"
          />
          <el-table-column
            key="email"
            :show-overflow-tooltip="true"
            align="center"
            label="邮箱"
            prop="email"
          />
          <el-table-column
            key="phonenumber"
            align="center"
            label="手机号码"
            prop="phonenumber"
            width="120"
          />
          <el-table-column key="sex" align="center" label="用户性别" prop="sex">
            <template slot-scope="scope">
              <dict-tag
                :options="dict.type.sys_user_sex"
                :value="scope.row.sex"
              />
            </template>
          </el-table-column>
          <el-table-column key="status" align="center" label="状态">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="创建时间"
            prop="createTime"
            width="160"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            class-name="small-padding fixed-width"
            label="操作"
            width="160"
          >
            <template v-if="scope.row.userId !== 1" slot-scope="scope">
              <el-button
                v-hasPermi="['system:user:edit']"
                icon="el-icon-edit"
                size="mini"
                type="text"
                @click="handleUpdate(scope.row)"
              >修改
              </el-button>
              <el-button
                v-hasPermi="['system:user:remove']"
                icon="el-icon-delete"
                size="mini"
                type="text"
                @click="handleDelete(scope.row)"
              >删除
              </el-button>
              <el-dropdown
                v-hasPermi="['system:user:resetPwd', 'system:user:edit']"
                size="mini"
                @command="(command) => handleCommand(command, scope.row)"
              >
                <el-button icon="el-icon-d-arrow-right" size="mini" type="text"
                >更多
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item
                    v-hasPermi="['system:user:resetPwd']"
                    command="handleResetPwd"
                    icon="el-icon-key"
                  >重置密码
                  </el-dropdown-item>
                  <el-dropdown-item
                    v-hasPermi="['system:user:edit']"
                    command="handleAuthRole"
                    icon="el-icon-circle-check"
                  >分配角色
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total > 0"
          :limit.sync="queryParams.pageSize"
          :page.sync="queryParams.pageNum"
          :total="total"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="600px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户姓名" prop="userName">
              <el-input
                v-model="form.userName"
                maxlength="30"
                placeholder="请输入用户姓名"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户昵称" prop="nickName">
              <el-input
                v-model="form.nickName"
                maxlength="30"
                placeholder="请输入用户昵称"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phonenumber">
              <el-input
                v-model="form.phonenumber"
                maxlength="11"
                placeholder="请输入手机号码"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input
                v-model="form.email"
                maxlength="50"
                placeholder="请输入邮箱"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item
              v-if="form.userId == undefined"
              label="账号"
              prop="userName"
            >
              <el-input
                v-model="form.account"
                maxlength="11"
                placeholder="请输入用户账号"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              v-if="form.userId == undefined"
              label="用户密码"
              prop="password"
            >
              <el-input
                v-model="form.password"
                maxlength="20"
                placeholder="请输入用户密码"
                show-password
                type="password"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="用户性别">
              <el-select v-model="form.sex" placeholder="请选择性别">
                <el-option
                  v-for="dict in dict.type.sys_user_sex"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >
                  {{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="角色">
              <el-select
                v-model="form.roleIds"
                multiple
                placeholder="请选择角色"
              >
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input
                v-model="form.remark"
                placeholder="请输入内容"
                type="textarea"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addUser,
  changeUserStatus,
  delUser,
  getUser,
  listUser,
  resetUserPwd,
  updateUser
} from '@/api/system/user'
import { listRoleAll } from '@/api/user/role'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import 'splitpanes/dist/splitpanes.css'

export default {
  name: 'User',
  dicts: ['sys_normal_disable', 'sys_user_sex'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 角色名称
      roleName: undefined,
      // 默认密码
      initPassword: undefined,
      // 角色选项
      roleOptions: [],
      // 表单参数
      form: {},
      defaultProps: {
        children: 'children',
        label: 'label'
      },

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        roleId: undefined
      },
      queryRoleParams: {
        roleName: undefined,
        roleId: undefined
      },
      // 角色列表
      roleList: [],
      //角色表单
      roleForm: {},
      // 表单校验
      rules: {
        userName: [
          { required: true, message: '用户名称不能为空', trigger: 'blur' },
          {
            min: 2,
            max: 20,
            message: '用户名称长度必须介于 2 和 20 之间',
            trigger: 'blur'
          }
        ],
        nickName: [
          { required: true, message: '用户昵称不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '用户密码不能为空', trigger: 'blur' },
          {
            min: 5,
            max: 20,
            message: '用户密码长度必须介于 5 和 20 之间',
            trigger: 'blur'
          },
          {
            pattern: /^[^<>"'|\\]+$/,
            message: '不能包含非法字符：< > " \' \\\ |',
            trigger: 'blur'
          }
        ],
        email: [
          {
            type: 'email',
            message: '请输入正确的邮箱地址',
            trigger: ['blur', 'change']
          }
        ],
        phonenumber: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: '请输入正确的手机号码',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getRoleList()
  },
  methods: {
    /** 查询用户列表 */
    getList() {
      this.loading = true
      listUser(this.queryParams).then((response) => {
        this.userList = response.rows
        this.total = Number(response.total)
        this.loading = false
      })
    },
    // 查询角色列表
    async getRoleList() {
      const { data: res } = await listRoleAll(this.queryRoleParams)
      this.roleList = res
      this.queryParams.roleId = this.roleForm.roleId
      await this.getList()
    },

    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === '0' ? '启用' : '停用'
      this.$modal
        .confirm('确认要"' + text + '""' + row.userName + '"用户吗？')
        .then(function() {
          return changeUserStatus(row.userId, row.status)
        })
        .then(() => {
          this.$modal.msgSuccess(text + '成功')
        })
        .catch(function() {
          row.status = row.status === '0' ? '1' : '0'
        })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset: function() {
      this.form = {
        userId: undefined,
        account: undefined,
        userName: undefined,
        nickName: undefined,
        email: undefined,
        password: undefined,
        phonenumber: undefined,
        sex: undefined,
        status: '0',
        remark: undefined,
        roleIds: []
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
      this.dateRange = []
      this.resetForm('queryForm')
      this.queryParams.deptId = undefined
      this.handleQuery()
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case 'handleResetPwd':
          this.handleResetPwd(row)
          break
        case 'handleAuthRole':
          this.handleAuthRole(row)
          break
        default:
          break
      }
    },
    /** 新增按钮操作 */
    async handleAdd() {
      this.reset()
      const { data: res } = await listRoleAll(this.queryRoleParams)
      this.roleOptions = res
      this.open = true
      this.title = '添加用户'
      this.form.password = this.initPassword
    },
    /** 修改按钮操作 */
    async handleUpdate(row) {
      this.reset()
      const { data: res } = await listRoleAll(null)
      this.roleOptions = res
      const userId = row.userId || this.ids
      getUser(userId).then((response) => {
        this.form = { ...response.data }
        this.open = true
        this.title = '修改用户'
        this.form.password = ''
      })
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.userName + '"的新密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        closeOnClickModal: false,
        inputPattern: /^.{5,20}$/,
        inputErrorMessage: '用户密码长度必须介于 5 和 20 之间',
        inputValidator: (value) => {
          if (/<|>|"|'|\||\\/.test(value)) {
            return '不能包含非法字符：< > " \' \\\ |'
          }
        }
      })
        .then(({ value }) => {
          resetUserPwd(row.userId, value).then((response) => {
            this.$modal.msgSuccess('修改成功，新密码是：' + value)
          })
        })
        .catch(() => {
        })
    },
    /** 分配角色操作 */
    handleAuthRole: function(row) {
      const userId = row.userId
      this.$router.push('/user/user-auth/role/' + userId)
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.userId != undefined) {
            this.form.password = undefined
            updateUser(this.form).then((response) => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addUser(this.form).then((response) => {
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
      const userIds = row.userId || this.ids
      this.$modal
        .confirm('是否确认删除用户编号为"' + userIds + '"的数据项？')
        .then(function() {
          return delUser(userIds)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {
        })
    }
  }
}
</script>
<style lang="scss" scoped>
.detail-body {
  ul {
    margin: 0;
    padding: 0;

    li {
      justify-content: space-between;
      padding: 10px 20px;
      cursor: pointer;
      position: relative;

      &:hover {
        background: rgba($color: #1ab394, $alpha: 0.1);
      }

      .base-sport-container {
        color: var(--color-3);
      }

      .space-status {
        height: 18px;
        line-height: 18px;
        padding: 0px 10px;
        border-radius: 4px;
        color: #fff;
        letter-spacing: 2px;
      }

      .put {
        background-color: #1ab394;
      }

      .sold {
        background-color: #f56c6c;
      }
    }

    .active {
      background: rgba($color: #1ab394, $alpha: 0.1);

      &::before {
        content: "";
        height: 100%;
        width: 2px;
        background: var(--current-color);
        position: absolute;
        left: 1px;
        top: 0px;
      }
    }
  }
}
</style>
