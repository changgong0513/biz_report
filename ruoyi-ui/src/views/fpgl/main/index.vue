<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单类型" prop="contractType">
        <el-select
          v-model="queryParams.contractType"
          placeholder="请输入订单类型"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.fpgl_order_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发票状态" prop="fpglFpzt">
        <el-select
          v-model="queryParams.fpglFpzt"
          placeholder="请输入发票状态"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.fpgl_fp_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >批量导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mainList" @selection-change="handleSelectionChange">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="订单编号" align="center" prop="orderId" />
      <el-table-column label="订单类型" align="center" prop="contractType" width="180">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.fpgl_order_type" :value="scope.row.contractType"/>
        </template>
      </el-table-column>
      <el-table-column label="客户名称" align="center" prop="supplierName" />
      <el-table-column label="物料名称" align="center" prop="materialName" />
      <el-table-column label="合同金额" align="center" prop="contractTotal" />
      <el-table-column label="已开票金额" align="center" prop="fpglKpje" />
      <el-table-column label="发票状态" align="center" prop="fpglFpzt">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.fpgl_fp_status" :value="scope.row.fpglFpzt"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            :disabled="scope.row.fpglFpzt === '1'"
            @click="handleAdd(scope.row)"
          >申请开票</el-button>
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

    <!-- 添加或修改发票管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="订单编号" prop="orderId">{{form.orderId}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户名称" prop="supplierName">{{form.supplierName}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户税号" prop="taxNumber">{{form.taxNumber}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="物料名称" prop="materialName">{{form.materialName}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="合同金额" prop="contractTotal">{{form.contractTotal}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="已开票金额" prop="fpglKpje">{{form.fpglKpje}}</el-form-item>
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <h3>开票明细</h3>
        <el-row>
          <el-table v-loading="loading" :data="fpDetailList" @selection-change="handleSelectionChange">
            <el-table-column label="开票日期" align="center" prop="fpglKprq" />
            <el-table-column label="开票明细" align="center" prop="fpglKpmx" width="180" />
            <el-table-column label="开票数量" align="center" prop="fpglKpsl" />
            <el-table-column label="单价" align="center" prop="fpglKpdj" />
            <el-table-column label="开票金额" align="center" prop="fpglKpje" />
            <el-table-column label="申请人" align="center" prop="fpglSqr" />
          </el-table>
          <pagination
            v-show="kpmxTotal>0"
            :total="kpmxTotal"
            :page.sync="kpmxPageNum"
            :limit.sync="kpmxPageSize"
            @pagination="getKpmxList"
          />
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="8">
            <el-form-item label="开票明细" prop="fpglKpmx">
              <!-- <el-input v-model="form.fpglKpmx" placeholder="请输入开票明细" style="width: 240px" /> -->
              {{form.materialName}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="开票数量" prop="fpglKpsl">
              <el-input v-model="form.fpglKpsl" placeholder="请输入开票数量" style="width: 240px" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="开票单价" prop="fpglKpdj">
              <el-input v-model="form.fpglKpdj" placeholder="请输入开票单价" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="开票金额" prop="fpglKpje">{{calKpje}}</el-form-item>
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
import { listMain, listFpmx, getMain, delMain, addMain, updateMain } from "@/api/fpgl/fpgl";

export default {
  name: "Main",
  dicts: ['fpgl_order_type', 'fpgl_fp_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      kpmxloading: true,
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
      // 开票明细总条数
      kpmxTotal: 0,
      //
      kpmxPageNum: 1,
      //
      kpmxPageSize: 10,
      // 发票管理表格数据
      mainList: [],
      // 发票明细表格数据
      fpDetailList: [],
      // 表单校验
      rules: {
        fpglKpsl: [
          { required: true, message: "开票数量不能为空", trigger: "blur" }
        ],
        fpglKpdj: [
          { required: true, message: "开票单价不能为空", trigger: "blur" }
        ]
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fpglKprq: null,
        fpglKpmx: null,
        fpglKpsl: null,
        fpglKpdj: null,
        fpglSqr: null,
        fpglDdbh: null,
        bizVersion: null
      },
      // 表单参数
      form: {},
      isUpdate: false,
      sqkpOrderId: null
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询发票管理列表 */
    getList() {
      this.loading = true;
      listMain(this.queryParams).then(response => {
        this.mainList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询开票明细列表 */
    getKpmxList() {
      this.kpmxloading = true;
      listFpmx(this.sqkpOrderId).then(response => {
        this.fpDetailList = response.rows;
        this.kpmxTotal = response.total;
        this.kpmxloading = false;
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
        fpglKpmx: null,
        fpglKpsl: null,
        fpglKpdj: null,
        fpglKpje: null,
        fpDetailList: [],
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
      this.ids = selection.map(item => item.fpglId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 申请开票按钮操作 */
    handleAdd(row) {
      this.reset();
      this.sqkpOrderId = row.orderId;
      listFpmx(row.orderId).then(response => {
        this.open = true;
        this.title = "申请开票";
        this.form = row;
        this.isUpdate = false;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      // this.reset();
      // const fpglId = row.fpglId || this.ids
      // getMain(fpglId).then(response => {
      //   this.form = response.data;
      //   this.open = true;
      //   this.title = "修改发票管理";
      // });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.isUpdate) {
            updateMain(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.fpglDdbh = this.form.orderId;
            this.form.fpglKpje = this.calKpje;
            addMain(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.getKpmxList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      // const fpglIds = row.fpglId || this.ids;
      // this.$modal.confirm('是否确认删除发票管理编号为"' + fpglIds + '"的数据项？').then(function() {
      //   return delMain(fpglIds);
      // }).then(() => {
      //   this.getList();
      //   this.$modal.msgSuccess("删除成功");
      // }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('fpgl/mgr/export', {
        ...this.queryParams
      }, `发票管理_${new Date().getFullYear()}年${new Date().getMonth()+1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    }
  },
  computed: {
    calKpje: function () {
      if (this.form.fpglKpsl && this.form.fpglKpdj) {
        return Number(this.form.fpglKpsl) * Number(this.form.fpglKpdj)
      }
      
      return 0;
    }
  }
};
</script>
