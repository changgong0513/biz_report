<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <!-- 仓库名称 -->
      <el-form-item label="仓库名称" prop="warehouseName">
        <el-input
          v-model="queryParams.warehouseName"
          placeholder="请输入仓库名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 区划 -->
      <el-form-item label="区划" prop="warehouseRegion">
        <el-select
          v-model="queryParams.warehouseRegion"
          placeholder="区划"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.masterdata_warehouse_region"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- 仓库类别 -->
      <el-form-item label="仓库类别" prop="warehouseCategory">
        <el-select
          v-model="queryParams.warehouseCategory"
          placeholder="仓库类别"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.masterdata_warehouse_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- 管理部门 -->
      <el-form-item label="管理部门" prop="managementDepartment">
        <el-select
          v-model="queryParams.managementDepartment"
          placeholder="管理部门"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.masterdata_management_department"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- 管理人员 -->
      <el-form-item label="管理人员" prop="warehouseManager">
        <el-input
          v-model="queryParams.warehouseManager"
          placeholder="请输入管理人员"
          clearable
          style="width: 240px"
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
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:role:export']"
        >批量导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="warehouseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="仓库名称" prop="warehouseName" width="150" />
      <el-table-column label="建库日期" prop="buildDate" :show-overflow-tooltip="true" width="100" />
      <el-table-column label="区划" prop="warehouseRegion" :show-overflow-tooltip="true" width="80" />
      <el-table-column label="仓库类别" prop="warehouseCategory" width="100" />
      <el-table-column label="地址" prop="warehouseAddress" align="center" width="300" />
      <el-table-column label="占地面积" prop="useArea" align="center"  width="100" />
      <el-table-column label="管理部门" prop="managementDepartment" width="100" />
      <el-table-column label="管理人员" prop="warehouseManager" width="100" />
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
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改仓库数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="仓库ID">
              <el-input v-model="warehouseId" :disabled="true" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="仓库编码" prop="warehouseCode">
              <el-input v-model="form.warehouseCode" placeholder="请输入仓库编码" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="仓库名称" prop="warehouseName">
              <el-input v-model="form.warehouseName" placeholder="请输入仓库名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="区划" prop="warehouseRegion">
              <el-select
                v-model="queryParams.warehouseRegion"
                placeholder="区划"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.masterdata_warehouse_region"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="仓库地址" prop="warehouseAddress">
              <el-input v-model="form.warehouseAddress" placeholder="请输入仓库地址" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="管理部门" prop="managementDepartment">
              <el-select
                v-model="queryParams.managementDepartment"
                placeholder="管理部门"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.masterdata_management_department"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <!-- 管理人员 -->
            <el-form-item label="管理人员" prop="warehouseManager">
              <el-input
                v-model="queryParams.warehouseManager"
                placeholder="请输入管理人员"
                clearable
                style="width: 240px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系方式1" prop="contactMobile1">
              <el-input v-model="form.contactMobile1" placeholder="请输入联系方式" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="联系方式2" prop="contactMobile2">
              <el-input v-model="form.contactMobile2" placeholder="请输入联系方式" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <!-- 仓库类别 -->
            <el-form-item label="仓库类别" prop="warehouseCategory">
              <el-select
                v-model="queryParams.warehouseCategory"
                placeholder="仓库类别"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.masterdata_warehouse_category"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="占地面积" prop="useArea">
              <el-input v-model="form.useArea" placeholder="请输入占地面积" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="最大容量" prop="maximumCapacity">
              <el-input v-model="form.maximumCapacity" placeholder="请输入最大容量" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="计量单位" prop="measurementUnit">
              <el-input v-model="form.measurementUnit" placeholder="请输入计量单位" />
            </el-form-item>
          </el-col>
          <el-col :span="8"></el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="form.remarks" placeholder="请输入备注" />
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
import { addRole, updateRole, dataScope } from "@/api/system/role";

export default {
  name: "Supplier",
  dicts: ['masterdata_warehouse_region', 'masterdata_warehouse_category', 'masterdata_management_department'],
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
      warehouseList: [
        {
          warehouseName: "昌图双庙直属库",
          buildDate: "2021/12/20",
          warehouseRegion: "辽宁省",
          warehouseCategory: "收纳粮库",
          warehouseAddress: "阜新市人民政府的地址在细河区中华路45号",
          useArea: "1002",
          managementDepartment: "商务部",
          warehouseManager: "张三"
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
      }, 
      warehouseId: "yw123456"
    };
  },
  created() {
    //this.getList();
    console.log("created回调------取得供应商表格数据");
  },
  methods: {
    /** 查询角色列表 */
    getList() {
      this.loading = true;
      listRole(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.warehouseList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
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
      this.title = "添加仓库";
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
      alert("导出按钮操作");
    }
  }
};
</script>