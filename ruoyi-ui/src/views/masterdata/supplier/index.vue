<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <!-- 公司名称 -->
      <el-form-item label="公司名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入公司名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 企业法人 -->
      <el-form-item label="企业法人" prop="legalPerson">
        <el-input
          v-model="queryParams.legalPerson"
          placeholder="请输入企业法人"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 注册城市 -->
      <el-form-item label="注册城市" prop="registerCity">
        <el-select
          v-model="queryParams.registerCity"
          placeholder="注册城市"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.masterdata_register_city"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- 注册资金 -->
      <el-form-item label="注册资金" prop="registeredCapital">
        <el-input
          v-model="queryParams.registeredCapital"
          placeholder="请输入注册资金"
          clearable
          style="width: 240px"
        />
      </el-form-item>
      <!-- 成立日期 -->
      <el-form-item label="成立日期">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
        ></el-date-picker>
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
          v-hasPermi="['system:role:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:role:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:role:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="supplierList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="公司名称" prop="companyName" width="240" />
      <el-table-column label="成立日期" prop="dateRange" :show-overflow-tooltip="true" width="100" />
      <el-table-column label="注册城市" prop="registerCity" :show-overflow-tooltip="true" width="100" />
      <el-table-column label="地址" prop="address" width="240" />
      <el-table-column label="企业法人" prop="legalPerson" align="center" width="100" />
      <el-table-column label="注册资金" prop="registeredCapital" align="center"  width="100" />
      <el-table-column label="固定电话" prop="phone" width="150" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" v-if="scope.row.roleId !== 1">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:role:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:role:remove']"
          >删除</el-button>
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

    <!-- 添加或修供应商数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <h3>供应商基本信息</h3>
        <el-row>
          <el-col :span="8">
            <el-form-item label="公司名称" prop="companyName">
              <el-input v-model="form.nickName" placeholder="请输入公司名称" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="成立日期">
              <el-date-picker
                v-model="form.establishDate"
                style="width: 240px"
                value-format="yyyy-MM-dd"
                type="date"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="注册城市" prop="registerCity">
              <el-select
                v-model="queryParams.registerCity"
                placeholder="注册城市"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.masterdata_register_city"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="注册资金" prop="registeredCapital">
              <el-input v-model="form.registeredCapital" placeholder="请输入注册资金" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="企业法人" prop="legalPerson">
              <el-input v-model="form.legalPerson" placeholder="请输入企业法人" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="固定电话" prop="fixedPhone">
              <el-input v-model="form.fixedPhone" placeholder="请输入固定电话" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="传真号码" prop="faxNumber">
              <el-input v-model="form.faxNumber" placeholder="请输入传真号码" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="邮编" prop="zipCode">
              <el-input v-model="form.zipCode" placeholder="请输入邮编" maxlength="6" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="营业时间" prop="businessHours">
              <el-input v-model="form.fixedPhone" placeholder="请输入营业时间" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="公司网址" prop="companyWebsite">
              <el-input v-model="form.fixedPhone" placeholder="请输入公司网址" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <h3>供应商联系人信息</h3>
        <el-row>
          <el-col :span="8">
            <el-form-item label="姓名" prop="contactsName">
              <el-input v-model="form.contactsName" placeholder="请输入姓名" maxlength="10" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机" prop="contactsMobile">
              <el-input v-model="form.contactsMobile" placeholder="请输入手机" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="电子邮箱" prop="contactsEmail">
              <el-input v-model="form.contactsEmail" placeholder="请输入电子邮箱" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="办公地点" prop="contactsOfficeLocation">
              <el-input v-model="form.fixedPhone" placeholder="请输入办公地点" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <h3>供应商账户信息</h3>
        <el-row>
          <el-col :span="24">
            <el-form-item label="开户行" prop="depositBank">
              <el-select
                v-model="queryParams.depositBank"
                placeholder="开户行"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.masterdata_deposit_bank"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="账号" prop="accountNumber">
              <el-input v-model="form.accountNumber" placeholder="请输入账号" maxlength="32" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="税号" prop="taxNumber">
              <el-input v-model="form.taxNumber" placeholder="请输入税号" maxlength="32" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="发票地址" prop="invoiceAddress">
              <el-input v-model="form.invoiceAddress" placeholder="请输入发票地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发票类型" prop="invoiceType">
              <el-select
                v-model="queryParams.invoiceType"
                placeholder="发票类型"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.masterdata_invoice_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-form-item prop="roleKey">
          <span slot="label">
            <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasRole('admin')`)" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
            权限字符
          </span>
          <el-input v-model="form.roleKey" placeholder="请输入权限字符" />
        </el-form-item>
        <el-form-item label="角色顺序" prop="roleSort">
          <el-input-number v-model="form.roleSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addRole, updateRole, dataScope } from "@/api/system/role";

export default {
  name: "Supplier",
  dicts: ['masterdata_register_city', 'masterdata_deposit_bank', 'masterdata_invoice_type'],
  data() {
    return {
      // 遮罩层
      loading: false,
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
      // 供应商表格数据
      supplierList: [
        {
          companyName: "广西力达农牧科技有限公司",
          dateRange: "2021/12/20",
          registerCity: "唐山市",
          address: "唐山市高新区火炬路124号",
          legalPerson: "张三",
          registeredCapital: "23534",
          phone: "028-89991606"
        }
      ],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleName: undefined,
        roleKey: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        companyName: [
          { required: true, message: "公司名称不能为空", trigger: "blur" }
        ],
        legalPerson: [
          { required: true, message: "企业法人不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    //this.getList();
    console.log("created回调------取得供应商表格数据");
  },
  methods: {
    /** 查询角色列表 */
    getList() {
      // this.loading = true;
      // listRole(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
      //     this.roleList = response.rows;
      //     this.total = response.total;
      //     this.loading = false;
      //   }
      // );
      this.total = 2;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      // this.queryParams.pageNum = 1;
      // this.getList();
      alert("搜索按钮操作");
    },
    /** 重置按钮操作 */
    resetQuery() {
      // this.dateRange = [];
      // this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.roleId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加供应商";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      // this.reset();
      // const roleId = row.roleId || this.ids
      // const roleMenu = this.getRoleMenuTreeselect(roleId);
      // getRole(roleId).then(response => {
      //   this.form = response.data;
      //   this.open = true;
      //   this.$nextTick(() => {
      //     roleMenu.then(res => {
      //       let checkedKeys = res.checkedKeys
      //       checkedKeys.forEach((v) => {
      //           this.$nextTick(()=>{
      //               this.$refs.menu.setChecked(v, true ,false);
      //           })
      //       })
      //     });
      //   });
      //   this.title = "修改角色";
      // });
      alert("修改按钮操作");
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.roleId != undefined) {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            updateRole(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            addRole(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交按钮（数据权限） */
    submitDataScope: function() {
      if (this.form.roleId != undefined) {
        this.form.deptIds = this.getDeptAllCheckedKeys();
        dataScope(this.form).then(response => {
          this.$modal.msgSuccess("修改成功");
          this.openDataScope = false;
          this.getList();
        });
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      // const roleIds = row.roleId || this.ids;
      // this.$modal.confirm('是否确认删除角色编号为"' + roleIds + '"的数据项？').then(function() {
      //   return delRole(roleIds);
      // }).then(() => {
      //   this.getList();
      //   this.$modal.msgSuccess("删除成功");
      // }).catch(() => {});
      alert("删除按钮操作");
    },
    /** 导出按钮操作 */
    handleExport() {
      // this.download('system/role/export', {
      //   ...this.queryParams
      // }, `role_${new Date().getTime()}.xlsx`)
      alert(导出按钮操作);
    }
  }
};
</script>