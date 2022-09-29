<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!-- 收货编号 -->
      <el-form-item label="收货编号" prop="receiptId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入收货编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 采购订单 -->
      <el-form-item label="采购订单" prop="purchaseOrderId">
        <el-input
          v-model="queryParams.purchaseOrderId"
          placeholder="请输入采购订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 经办人 -->
      <el-form-item label="经办人" prop="handledBy">
        <el-input
          v-model="queryParams.handledBy"
          placeholder="请输入经办人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 物料名称 -->
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 仓库名称 -->
      <el-form-item label="仓库名称" prop="warehouseName">
        <el-input
          v-model="queryParams.warehouseName"
          placeholder="请输入仓库名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 收货日期 -->
      <el-form-item label="收货日期" prop="receiptDate">
        <el-date-picker
          v-model="queryParams.receiptDate"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
        ></el-date-picker>
      </el-form-item>
      <!-- 订单状态（数据来源于采购（销售）订单信息表，关联查询） -->
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select
          v-model="queryParams.orderStatus"
          placeholder="订单状态"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.purchasesale_receipt_order_status"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['purchasesale:purchasesale:add']"
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
          v-hasPermi="['purchasesale:purchasesale:edit']"
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
          v-hasPermi="['purchasesale:purchasesale:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['purchasesale:purchasesale:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="purchaseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="收货编号" align="center" prop="receiptId" width="150" />
      <el-table-column label="收货日期" align="center" prop="receiptDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.receiptDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="采购订单" align="center" prop="purchaseOrderId" width="150" />
      <el-table-column label="经办人" align="center" prop="handledBy" width="100" :show-overflow-tooltip="true" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" width="240" :show-overflow-tooltip="true" />
      <el-table-column label="物料名称" align="center" prop="materialName" width="100" />
      <el-table-column label="订单状态" align="center" prop="orderStatus" width="80" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row,scope.index)"
          >详细</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['purchasesale:purchasesale:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['purchasesale:purchasesale:remove']"
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

    <!-- 添加或修改采购收货销售发货管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <!-- 收货编号 -->
          <el-col :span="8">
            <el-form-item label="收货编号">
              <el-input v-model="orderId" :disabled="true" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 采购订单编号 -->
          <el-col :span="8">
            <el-form-item label="采购订单编号">
              <el-input v-model="orderId" :disabled="true" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 采购合同编号 -->
          <el-col :span="8">
            <el-form-item label="采购合同编号">
              <el-input v-model="orderId" :disabled="true" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 经办人 -->
          <el-col :span="8">
            <el-form-item label="经办人" prop="handledBy">
              <el-input v-model="form.handledBy" placeholder="请输入经办人" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 收货日期 -->
          <el-col :span="8">
            <el-form-item label="收货日期" prop="businessDate">
              <el-date-picker clearable
                v-model="form.businessDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择收货日期"
                style="width: 240px">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <!-- 供应商名称 -->
          <el-col :span="8">
            <el-form-item label="供应商名称" prop="supplierName">
              <el-input v-model="form.supplierName" placeholder="请输入供应商名称" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 物料名称 -->
          <el-col :span="8">
            <el-form-item label="物料名称" prop="materialName">
              <el-input v-model="form.materialName" placeholder="请输入物料名称" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 仓库编号 -->
          <el-col :span="8">
            <el-form-item label="仓库编号" prop="purchaseQuantity">
              <el-input v-model="form.purchaseQuantity" placeholder="请输入仓库编号" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 仓库名称 -->
          <el-col :span="8">
            <el-form-item label="仓库名称" prop="handledBy">
              <el-input v-model="form.handledBy" placeholder="请输入仓库名称" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 批次号 -->
          <el-col :span="8">
            <el-form-item label="批次号" prop="unitPrice">
              <el-input v-model="form.unitPrice" placeholder="请输入批次号" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 运输方式 -->
          <el-col :span="8">
            <el-form-item label="运输方式" prop="meteringUnit">
              <el-select
                v-model="form.meteringUnit"
                placeholder="运输方式"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.purchasesale_transport_mode"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 运输单号 -->
          <el-col :span="8">
            <el-form-item label="运输单号" prop="unitPrice">
              <el-input v-model="form.unitPrice" placeholder="请输入运输单号" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 预期收货数量 -->
          <el-col :span="8">
            <el-form-item label="预期收货数量" prop="accountPeriod">
              <el-input v-model="form.accountPeriod" placeholder="请输入预期收货数量" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 核算数量 -->
          <el-col :span="8">
            <el-form-item label="核算数量" prop="accountPeriod">
              <el-input v-model="form.accountPeriod" placeholder="请输入核算数量" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 核算单价 -->
          <el-col :span="8">
            <el-form-item label="核算单价" prop="accountPeriod">
              <el-input v-model="form.accountPeriod" placeholder="请输入核算单价" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 核算金额 -->
          <el-col :span="8">
            <el-form-item label="核算金额" prop="accountPeriod">
              <el-input v-model="form.accountPeriod" placeholder="请输入核算金额" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 货损数量 -->
          <el-col :span="8">
            <el-form-item label="货损数量" prop="accountPeriod">
              <el-input v-model="form.accountPeriod" placeholder="请输入货损数量" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 货损金额 -->
          <el-col :span="8">
            <el-form-item label="货损金额" prop="accountPeriod">
              <el-input v-model="form.accountPeriod" placeholder="请输入货损金额" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 备注 -->
          <el-col :span="24">
            <el-form-item label="备注" prop="orderRemark">
              <el-input v-model="form.orderRemark" type="textarea" style="width: 90%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <h3>折干计算</h3>
        <el-row>
          <!-- 水分值 -->
          <el-col :span="8">
            <el-form-item label="水分值" prop="accountPeriod">
              <el-input v-model="form.accountPeriod" placeholder="请输入水分值" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 烘干率 -->
          <el-col :span="8">
            <el-form-item label="烘干率" prop="accountPeriod">
              <el-input v-model="form.accountPeriod" placeholder="请输入烘干率" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 比例范围 -->
          <el-col :span="8">
            <el-form-item label="比例范围" prop="accountPeriod">
              <el-input v-model="form.accountPeriod" placeholder="请输入比例范围" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
           <!-- 计算结果 -->
           <el-col :span="24">
            <el-form-item label="计算结果" prop="arrivalTerms">
              <el-input v-model="form.accountPeriod" placeholder="请输入计算结果" style="width: 240px; margin-right: 50px" />
              <el-button type="success" icon="el-icon-check" size="mini" @click="handleQuery">计算</el-button>
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
// import { listPurchasesale, getPurchasesale, delPurchasesale, addPurchasesale, updatePurchasesale } from "@/api/purchasesale/purchasesale";

export default {
  name: "Purchase",
  dicts: ['purchasesale_purchase_type', 'purchasesale_belong_dept', 'masterdata_warehouse_measurement_unit', 
          'purchasesale_arrival_terms', 'purchasesale_settlement_method', 'purchasesale_receipt_order_status', 
          'purchasesale_transport_mode'],
  data() {
    return {
      // 遮罩层
      // loading: true,
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
      total: 1,
      // 采购收货销售发货管理表格数据
      // purchaseList: [],
      purchaseList: [
        {
          receiptId: "yw1202112221355",
          receiptDate: "2021/12/20",
          purchaseOrderId: "yw1202112221355",
          handledBy: "李四",
          warehouseName: "彰武直属库",
          materialName: "土豆",
          orderStatus: "在途"
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
        purchaseType: null,
        contractId: null,
        handledBy: null,
        belongDept: null,
        businessDate: null,
        materialName: null,
        purchaseQuantity: null,
        supplierName: null,
        unitPrice: null,
        meteringUnit: null,
        arrivalDate: null,
        requiredDeliveryDate: null,
        accountPeriod: null,
        arrivalTerms: null,
        arrivalTermsValue: null,
        settlementMethod: null,
        isInvoicing: null,
        orderRemark: null,
        bizVersion: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // rules: {
      //   purchaseType: [
      //     { required: true, message: "采购类型不能为空", trigger: "change" }
      //   ],
      //   contractId: [
      //     { required: true, message: "合同编号不能为空", trigger: "blur" }
      //   ],
      //   handledBy: [
      //     { required: true, message: "经办人不能为空", trigger: "blur" }
      //   ],
      //   belongDept: [
      //     { required: true, message: "所属部门不能为空", trigger: "blur" }
      //   ],
      //   businessDate: [
      //     { required: true, message: "业务日期不能为空", trigger: "blur" }
      //   ],
      //   materialName: [
      //     { required: true, message: "物料名称不能为空", trigger: "blur" }
      //   ],
      //   purchaseQuantity: [
      //     { required: true, message: "采购数量不能为空", trigger: "blur" }
      //   ],
      //   supplierName: [
      //     { required: true, message: "供应商名称不能为空", trigger: "blur" }
      //   ],
      //   unitPrice: [
      //     { required: true, message: "单价不能为空", trigger: "blur" }
      //   ],
      //   meteringUnit: [
      //     { required: true, message: "计量单位不能为空", trigger: "blur" }
      //   ],
      //   arrivalDate: [
      //     { required: true, message: "预计到货期不能为空", trigger: "blur" }
      //   ],
      //   requiredDeliveryDate: [
      //     { required: true, message: "要求交货期不能为空", trigger: "blur" }
      //   ],
      //   accountPeriod: [
      //     { required: true, message: "账期不能为空", trigger: "blur" }
      //   ],
      //   arrivalTerms: [
      //     { required: true, message: "到账条件不能为空", trigger: "blur" }
      //   ],
      //   arrivalTermsValue: [
      //     { required: true, message: "到账条件值不能为空", trigger: "blur" }
      //   ],
      //   settlementMethod: [
      //     { required: true, message: "结算方式不能为空", trigger: "blur" }
      //   ],
      //   isInvoicing: [
      //     { required: true, message: "是否开票不能为空", trigger: "blur" }
      //   ],
      //   createBy: [
      //     { required: true, message: "创建者不能为空", trigger: "blur" }
      //   ],
      //   createTime: [
      //     { required: true, message: "创建时间不能为空", trigger: "blur" }
      //   ],
      //   updateBy: [
      //     { required: true, message: "更新者不能为空", trigger: "blur" }
      //   ],
      //   updateTime: [
      //     { required: true, message: "更新时间不能为空", trigger: "blur" }
      //   ],
      //   bizVersion: [
      //     { required: true, message: "版本号不能为空", trigger: "blur" }
      //   ]
      // },
      orderId: null // 订单编号
    };
  },
  created() {
    // this.getList();
    console.log("created回调查询采购收货销售发货管理列表");
  },
  methods: {
    /** 查询采购收货销售发货管理列表 */
    getList() {
      // this.loading = true;
      // listPurchasesale(this.queryParams).then(response => {
      //   this.purchasesaleList = response.rows;
      //   this.total = response.total;
      //   this.loading = false;
      // });
      console.log("查询采购收货销售发货管理列表")
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        orderId: null,
        purchaseType: null,
        contractId: null,
        handledBy: null,
        belongDept: null,
        businessDate: null,
        materialName: null,
        purchaseQuantity: null,
        supplierName: null,
        unitPrice: null,
        meteringUnit: null,
        arrivalDate: null,
        requiredDeliveryDate: null,
        accountPeriod: null,
        arrivalTerms: null,
        arrivalTermsValue: null,
        settlementMethod: null,
        isInvoicing: null,
        orderRemark: null
      };
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
      // this.resetForm("queryForm");
      // this.handleQuery();
      alert("重置按钮操作");
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      // this.ids = selection.map(item => item.orderId)
      // this.single = selection.length!==1
      // this.multiple = !selection.length
      alert("多选框选中数据");
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加采购信息";
      this.orderId = uuid(32, 10);
      this.form.isInvoicing = "1";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      // this.reset();
      // const orderId = row.orderId || this.ids
      // getPurchasesale(orderId).then(response => {
      //   this.form = response.data;
      //   this.open = true;
      //   this.title = "修改采购收货销售发货管理";
      // });
      alert("修改按钮操作");
    },
    /** 提交按钮 */
    submitForm() {
      // this.$refs["form"].validate(valid => {
      //   if (valid) {
      //     if (this.form.orderId != null) {
      //       updatePurchasesale(this.form).then(response => {
      //         this.$modal.msgSuccess("修改成功");
      //         this.open = false;
      //         this.getList();
      //       });
      //     } else {
      //       addPurchasesale(this.form).then(response => {
      //         this.$modal.msgSuccess("新增成功");
      //         this.open = false;
      //         this.getList();
      //       });
      //     }
      //   }
      // });
      alert("提交按钮");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      // const orderIds = row.orderId || this.ids;
      // this.$modal.confirm('是否确认删除采购收货销售发货管理编号为"' + orderIds + '"的数据项？').then(function() {
      //   return delPurchasesale(orderIds);
      // }).then(() => {
      //   this.getList();
      //   this.$modal.msgSuccess("删除成功");
      // }).catch(() => {});
      alert("删除按钮操作");
    },
    /** 导出按钮操作 */
    handleExport() {
      // this.download('purchasesale/purchasesale/export', {
      //   ...this.queryParams
      // }, `purchasesale_${new Date().getTime()}.xlsx`)
      alert("导出按钮操作");
    }
  }
};
</script>
