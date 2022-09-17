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
              <el-input v-model="warehouseId" :disabled="true" style="width: 280px" />
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
                v-model="form.warehouseRegion"
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
                v-model="form.managementDepartment"
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
                v-model="form.warehouseManager"
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
                v-model="form.warehouseCategory"
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
              <el-input v-model="form.warehouseRemarks" placeholder="请输入备注" />
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
import { uuid } from "@/utils/xmy";
import { listWarehouse, getWarehouse, addWarehouse, updateWarehous, delWarehous } from "@/api/masterdata/warehouse";

export default {
  name: "Warehouse",
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
      warehouseList: null,
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
        warehouseName: undefined,
        warehouseRegion: undefined,
        warehouseCategory: undefined,
        managementDepartment: undefined,
        warehouseManager: undefined
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        warehouseCode: [
          { required: true, message: "仓库编码不能为空", trigger: "blur" }
        ],
        warehouseName: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" }
        ],
        warehouseRegion: [
          { required: true, message: "区划不能为空", trigger: "blur" }
        ],
        warehouseAddress: [
          { required: true, message: "仓库地址不能为空", trigger: "blur" }
        ],
        managementDepartment: [
          { required: true, message: "管理部门不能为空", trigger: "blur" }
        ],
        warehouseManager: [
          { required: true, message: "管理人员不能为空", trigger: "blur" }
        ],
        contactMobile1: [
          { required: true, message: "联系方式1不能为空", trigger: "blur" }
        ],
        warehouseCategory: [
          { required: true, message: "仓库类别不能为空", trigger: "blur" }
        ],
        useArea: [
          { required: true, message: "占地面积不能为空", trigger: "blur" }
        ],
        maximumCapacity: [
          { required: true, message: "最大容量不能为空", trigger: "blur" }
        ],
        measurementUnit: [
          { required: true, message: "计量单位不能为空", trigger: "blur" }
        ]
      }, 
      // warehouseId: uuid(32, 10)
      warehouseId: null
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询仓库列表 */
    getList() {
      this.loading = true;
      listWarehouse(this.queryParams).then(response => {
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
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.warehouseId)
      console.log("ids = " + this.ids);
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加仓库";
      this.warehouseId = uuid(32, 10);
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const warehouseId = row.warehouseId || this.ids[0]
      this.warehouseId = warehouseId;
      getWarehouse(warehouseId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改仓库";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      console.log("进入提交");
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.warehouseId != undefined) {
            updateWarehous(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            console.log("@@@@@@提交添加");
            this.form.warehouseId = this.warehouseId;
            console.log("@@@@@@提交添加的warehouseId=" + this.form.warehouseId);
            addWarehouse(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const warehouseId = row.warehouseId || this.ids;
      this.$modal.confirm('是否确认删除仓库记录？').then(function() {
        return delWarehous(warehouseId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/md/warehouse/export', {
        ...this.queryParams
      }, `主数据管理_仓库列表_${new Date().getFullYear()}年${new Date().getMonth()+1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    }
  }
};
</script>