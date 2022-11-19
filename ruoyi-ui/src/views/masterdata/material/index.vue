<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物料编码" prop="materialId">
        <el-input
          v-model="queryParams.materialId"
          placeholder="请输入物料编码"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计量单位" prop="materialUnit">
        <el-input
          v-model="queryParams.materialUnit"
          placeholder="请输入计量单位"
          clearable
          style="width: 150px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开票别名" prop="billingAlias">
        <el-input
          v-model="queryParams.billingAlias"
          placeholder="请输入开票别名"
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
          v-hasPermi="['masterdata:masterdata:add']"
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
          v-hasPermi="['masterdata:masterdata:edit']"
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
          v-hasPermi="['masterdata:masterdata:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['masterdata:masterdata:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="masterdataList" @selection-change="handleSelectionChange" @row-dblclick="handleView">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="物料编码" align="center" prop="materialId" />
      <el-table-column label="物料名称" align="center" prop="materialName" />
      <el-table-column label="计量单位" align="center" prop="materialUnit">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.masterdata_warehouse_measurement_unit" :value="scope.row.materialUnit"/>
        </template>
      </el-table-column>
      <el-table-column label="开票别名" align="center" prop="billingAlias" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改物料信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="400px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="物料名称" prop="materialName">
          <el-input v-model="form.materialName" placeholder="请输入物料名称" style="width: 240px" />
        </el-form-item>
        <el-form-item label="计量单位" prop="materialUnit">
          <el-select
            v-model="form.materialUnit"
            placeholder="请输入计量单位"
            clearable
            style="width: 240px"
          >
            <el-option
              v-for="dict in dict.type.masterdata_warehouse_measurement_unit"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="开票别名" prop="billingAlias">
          <el-input v-model="form.billingAlias" placeholder="请输入开票别名，多个别名用逗号分隔" style="width: 240px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 物料数据详细 -->
    <el-dialog title="物料数据详细" :visible.sync="openDetail" append-to-body :close-on-click-modal="false">
      <el-form ref="formDetail" :model="formDetail" label-width="80px">
        <el-form-item label="物料编码">{{formDetail.materialId}}</el-form-item>
        <el-form-item label="物料名称">{{formDetail.materialName}}</el-form-item>
        <el-form-item label="计量单位">
          <template>
            <dict-tag :options="dict.type.masterdata_warehouse_measurement_unit" :value="formDetail.materialUnit"/>
          </template>
        </el-form-item>
        <el-form-item label="开票别名">{{formDetail.billingAlias}}</el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="openDetail = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMaterialData, getMaterialData, delMaterialData, addMaterialData, updatMaterialData } from "@/api/masterdata/material";

export default {
  name: "Material",
  dicts: ['masterdata_warehouse_measurement_unit'],
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
      // 主数据管理表格数据
      masterdataList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //
      openDetail: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialId: null,
        materialName: null,
        materialUnit: null,
        billingAlias: null,
      },
      // 表单参数
      form: {},
      //
      formDetail: {},
      // 表单校验
      rules: {
        materialId: [
          { required: true, message: "物料编码不能为空", trigger: "blur" }
        ],
        materialName: [
          { required: true, message: "物料名称不能为空", trigger: "blur" }
        ],
        materialUnit: [
          { required: true, message: "计量单位不能为空", trigger: "blur" }
        ],
      },
      actionFlag: "add"
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询主数据管理列表 */
    getList() {
      this.loading = true;
      listMaterialData(this.queryParams).then(response => {
        this.masterdataList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        materialId: null,
        materialName: null,
        materialUnit: null,
        billingAlias: null
      };
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
      this.ids = selection.map(item => item.materialId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.actionFlag = "add";
      this.title = "添加物料数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.actionFlag = "update";
      const materialId = row.materialId || this.ids
      getMaterialData(materialId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物料数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.actionFlag == "update") {
            updatMaterialData(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMaterialData(this.form).then(response => {
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
      const materialIds = row.materialId || this.ids;
      this.$modal.confirm('是否确认删除物料数据编号为"' + materialIds + '"的数据项？').then(function() {
        return delMaterialData(materialIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('md/material/export', {
        ...this.queryParams
      }, `主数据管理_供应商列表_${new Date().getFullYear()}年${new Date().getMonth()+1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    },
    /** 详细按钮操作 */
    handleView(row) {
      this.openDetail = true;
      this.formDetail = row;
    }
  }
};
</script>
