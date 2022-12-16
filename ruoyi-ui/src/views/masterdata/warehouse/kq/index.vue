<template>
  <div class="app-container">
    <!-- 仓库数据 -->
    <el-form ref="warehouseFormDetail" :model="warehouseFormDetail" label-width="100px" v-show="showSearch">
      <el-row>
        <el-col :span="8">
          <el-form-item label="仓库编码">{{warehouseFormDetail.warehouseCode}}</el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="仓库名称">{{warehouseFormDetail.warehouseName}}</el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="区划">
            <template>
              <dict-tag :options="dict.type.masterdata_warehouse_region" :value="warehouseFormDetail.warehouseRegion"/>
            </template>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="仓库地址">{{warehouseFormDetail.warehouseAddress}}</el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="管理部门">
            <template>
              <dict-tag :options="dict.type.masterdata_management_department" :value="warehouseFormDetail.managementDepartment"/>
            </template>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <!-- 管理人员 -->
          <el-form-item label="管理人员">{{warehouseFormDetail.warehouseManager}}</el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="联系方式1">{{warehouseFormDetail.contactMobile1}}</el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="联系方式2">{{warehouseFormDetail.contactMobile2}}</el-form-item>
        </el-col>
        <el-col :span="8">
          <!-- 仓库类别 -->
          <el-form-item label="仓库类别">
            <template>
              <dict-tag :options="dict.type.masterdata_warehouse_category" :value="warehouseFormDetail.warehouseCategory"/>
            </template>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="占地面积">{{warehouseFormDetail.useArea}}</el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="最大容量">{{warehouseFormDetail.maximumCapacity}}</el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="计量单位">
            <template>
              <dict-tag :options="dict.type.masterdata_warehouse_measurement_unit" :value="warehouseFormDetail.measurementUnit"/>
            </template>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="备注">{{warehouseFormDetail.warehouseRemarks}}</el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
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
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-download"
          size="mini"
          @click="toCkgl"
        >仓库管理</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="kqList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="库区编码" align="center" prop="kqCode" />
      <el-table-column label="库区名称" align="center" prop="kqName" />
      <el-table-column label="区划" align="center" prop="warehouseRegion">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.masterdata_warehouse_region" :value="scope.row.warehouseRegion"/>
        </template>
      </el-table-column>
      <el-table-column label="库区地址" align="center" prop="warehouseAddress" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改库区数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="库区编码" prop="kqCode">
              <el-input v-model="form.kqCode" placeholder="请输入库区编码" style="width: 280px" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="库区名称" prop="kqName">
              <el-input v-model="form.kqName" placeholder="请输入库区名称" style="width: 280px" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="区划" prop="warehouseRegion">
              <template>
                <dict-tag :options="dict.type.masterdata_warehouse_region" :value="form.warehouseRegion"/>
              </template>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="库区地址" prop="warehouseAddress">{{form.warehouseAddress}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="管理部门" prop="managementDepartment">
              <template>
                <dict-tag :options="dict.type.masterdata_management_department" :value="form.managementDepartment"/>
              </template>
            </el-form-item>
          </el-col>
          <el-col :span="8"><el-form-item label="管理人员">{{form.warehouseManager}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="8"><el-form-item label="联系方式1">{{form.contactMobile1}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="联系方式2">{{form.contactMobile2}}</el-form-item></el-col>
          <el-col :span="8">
            <el-form-item label="仓库类别" prop="warehouseCategory">
              <template>
                <dict-tag :options="dict.type.masterdata_warehouse_category" :value="form.warehouseCategory"/>
              </template>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="kqRemarks">
              <el-input v-model="form.kqRemarks" type="textarea" placeholder="请输入内容" />
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
import { listKq, getKq, delKq, addKq, updateKq } from "@/api/masterdata/kq";

export default {
  name: "Kq",
  dicts: ['masterdata_warehouse_region', 
          'masterdata_warehouse_category', 
          'masterdata_management_department',
          'masterdata_warehouse_measurement_unit'
  ],
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
      // 仓库库区表格数据
      kqList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        warehouseId: null,
        kqName: null,
        warehouseRegion: null,
        warehouseAddress: null,
        managementDepartment: null,
        warehouseManager: null,
        contactMobile1: null,
        contactMobile2: null,
        warehouseCategory: null,
        kqRemarks: null,
        bizVersion: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        kqName: [
          { required: true, message: "库区名称不能为空", trigger: "blur" }
        ],
        kqCode: [
          { required: true, message: "库区编码不能为空", trigger: "blur" }
        ]
      },
      // 从仓库管理中传递过来的仓库数据
      warehouseFormDetail: {},
      //
      actionFlag: null
    };
  },
  created() {
    // console.log("从仓库管理中传递过来的仓库数据：" + JSON.stringify(this.$route.query.selWarehouseRow));
    if (this.$route.query.selWarehouseRow.warehouseId === undefined) {
      console.log("从缓存中取出的仓库数据：" + this.$cache.local.getJSON('selWarehouseDataKey'));
      this.warehouseFormDetail = JSON.parse(this.$cache.local.getJSON('selWarehouseDataKey'));
    } else {
      console.log("从仓库管理中传递过来的仓库数据：" + JSON.stringify(this.$route.query.selWarehouseRow));
      this.$cache.local.setJSON('selWarehouseDataKey', JSON.stringify(this.$route.query.selWarehouseRow))
      this.warehouseFormDetail = this.$route.query.selWarehouseRow;
    }
    
    this.getList();
    this.actionFlag = null;
  },
  methods: {
    /** 查询Warehouse列表 */
    getList() {
      this.loading = true;
      listKq(this.queryParams).then(response => {
        this.kqList = response.rows;
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
        warehouseId: null,
        kqCode: null,
        kqName: null,
        warehouseRegion: null,
        warehouseAddress: null,
        managementDepartment: null,
        warehouseManager: null,
        contactMobile1: null,
        contactMobile2: null,
        warehouseCategory: null,
        kqRemarks: null
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
      this.ids = selection.map(item => item.kqCode)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.form = {
        warehouseId: this.warehouseFormDetail.warehouseId,
        warehouseRegion: this.warehouseFormDetail.warehouseRegion,
        warehouseAddress: this.warehouseFormDetail.warehouseAddress,
        managementDepartment: this.warehouseFormDetail.managementDepartment,
        warehouseManager: this.warehouseFormDetail.warehouseManager,
        contactMobile1: this.warehouseFormDetail.contactMobile1,
        contactMobile2: this.warehouseFormDetail.contactMobile2,
        warehouseCategory: this.warehouseFormDetail.warehouseCategory
      };
      this.open = true;
      this.title = "添加库区数据";
      this.actionFlag = "add";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const kqCode = row.kqCode || this.ids
      getKq(kqCode).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改库区数据";
        this.actionFlag = "update";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.actionFlag == "update") {
            updateKq(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addKq(this.form).then(response => {
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
      const kqCodes = row.kqCode || this.ids;
      this.$modal.confirm('是否确认删除库区编号为"' + kqCodes + '"的数据项？').then(function() {
        return delKq(kqCodes);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('md/kq/export', {
        ...this.queryParams
      }, `主数据管理_仓库库区列表_${new Date().getFullYear()}年${new Date().getMonth()+1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    },
    toCkgl() {
      this.$router.push({ path: "/to/ckgl" });
    }
  }
};
</script>

<style scoped>

</style>
