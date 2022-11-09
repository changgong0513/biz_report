<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!-- 订单编号 -->
      <el-form-item label="订单编号" prop="contractId">
        <el-input
          v-model="queryParams.contractId"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 所属部门 -->
      <el-form-item label="所属部门" prop="belongDept">
        <el-select
          v-model="queryParams.belongDept"
          placeholder="请输入所属部门"
          clearable
          style="width: 200px"
        >
          <el-option
            v-for="dict in dict.type.purchasesale_belong_dept"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
      <!-- 核算金额min -->
      <el-form-item label="核算金额" prop="checkMoney">
        <el-input
          v-model="queryParams.checkMoney"
          placeholder="请输入核算金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 核算金额max -->
      <el-form-item label="~" prop="checkMoney" label-width="15px">
        <el-input
          v-model="queryParams.checkMoney"
          placeholder="请输入核算金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 业务日期 -->
      <el-form-item label="业务日期" prop="businessDate">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
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
    <el-table v-loading="loading" :data="purchaseList" @selection-change="handleSelectionChange"
    @row-dblclick="handleView">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单编号" align="center" prop="orderId" width="150" :show-overflow-tooltip="true" />
      <el-table-column label="业务日期" align="center" prop="businessDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.businessDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属部门" align="center" prop="belongDept" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.purchasesale_belong_dept" :value="scope.row.belongDept"/>
        </template>
      </el-table-column>
      <el-table-column label="经办人" align="center" prop="handledBy" width="100" :show-overflow-tooltip="true" />
      <el-table-column label="客户名称" align="center" prop="supplierName" width="240" :show-overflow-tooltip="true" />
      <el-table-column label="订单状态" align="center" prop="orderStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contractmgr_contract_approval_status" :value="scope.row.orderStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="物料名称" align="center" prop="materialName" width="150" :show-overflow-tooltip="true" />
      <el-table-column label="核算金额" align="center" prop="checkMoney" width="80" :show-overflow-tooltip="true" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改销售收货销售发货管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <!-- 订单编号 -->
          <el-col :span="8">
            <el-form-item label="订单编号">
              <el-input v-model="form.orderId" placeholder="请输入订单编号" :disabled="this.isUpdate" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 合同编号 -->
          <el-col :span="8">
            <el-form-item label="合同编号" prop="contractId">
              <el-input v-model="form.contractId" placeholder="请输入合同编号" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 经办人 -->
          <el-col :span="8">
            <el-form-item label="经办人" prop="handledBy">
              <el-input v-model="form.handledBy" placeholder="请输入经办人" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 所属部门 -->
          <el-col :span="8">
            <el-form-item label="所属部门">
              <el-select
                v-model="form.belongDept"
                placeholder="所属部门"
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.purchasesale_belong_dept"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 业务日期 -->
          <el-col :span="8">
            <el-form-item label="业务日期" prop="businessDate">
              <el-date-picker clearable
                v-model="form.businessDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择业务日期"
                style="width: 240px">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <!-- 客户名称 -->
          <el-col :span="8">
            <el-form-item label="客户名称" prop="supplierName">
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
          <!-- 销售数量 -->
          <el-col :span="8">
            <el-form-item label="销售数量" prop="purchaseQuantity">
              <el-input v-model="form.purchaseQuantity" placeholder="请输入销售数量" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 单价 -->
          <el-col :span="8">
            <el-form-item label="单价" prop="unitPrice">
              <el-input v-model="form.unitPrice" placeholder="请输入单价" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 计量单位 -->
          <el-col :span="8">
            <el-form-item label="计量单位" prop="meteringUnit">
              <el-select
                v-model="form.meteringUnit"
                placeholder="计量单位"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.masterdata_warehouse_measurement_unit"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 预计到货期 -->
          <el-col :span="8">
            <el-form-item label="预计到货期" prop="arrivalDate">
              <el-date-picker clearable
                v-model="form.arrivalDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择预计到货期"
                style="width: 240px">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <!-- 要求到货期 -->
          <el-col :span="8">
            <el-form-item label="要求到货期" prop="requiredDeliveryDate">
              <el-date-picker clearable
                v-model="form.requiredDeliveryDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择要求到货期"
                style="width: 240px">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 账期 -->
          <el-col :span="8">
            <el-form-item label="账期" prop="accountPeriod">
              <el-input v-model="form.accountPeriod" placeholder="请输入账期" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 到账条件 -->
          <el-col :span="8">
            <el-form-item label="到账条件" prop="arrivalTerms">
              <el-select
                v-model="form.arrivalTerms"
                placeholder="到账条件"
                clearable
                style="width: 150px"
              >
                <el-option
                  v-for="dict in dict.type.purchasesale_arrival_terms"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
              <!-- 到账条件值 -->
              <el-input v-model="form.arrivalTermsValue" placeholder="天数" style="margin-left: 10px; width: 60px" />（天）
            </el-form-item>
          </el-col>
          <!-- 结算方式 -->
          <el-col :span="8">
            <el-form-item label="结算方式" prop="settlementMethod">
              <el-select
                v-model="form.settlementMethod"
                placeholder="结算方式"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.purchasesale_settlement_method"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 是否开票 -->
          <el-col :span="24">
            <el-form-item label="是否开票" prop="isInvoicing">
              <el-switch
                active-value="1"
                inactive-value="0"
                v-model="form.isInvoicing"
              ></el-switch>
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 销售管理数据详细 -->
    <el-dialog title="销售管理数据详细" :visible.sync="openDetail" width="90%" append-to-body :close-on-click-modal="false">
      <el-form ref="formDetail" :model="formDetail" :rules="rules" label-width="100px">
        <el-row>
          <!-- 订单编号 -->
          <el-col :span="8">
            <el-form-item label="订单编号">{{formDetail.orderId}}</el-form-item>
          </el-col>
          <!-- 销售类型 -->
          <el-col :span="8">
            <el-form-item label="销售类型">
              <template>
                <dict-tag :options="dict.type.purchasesale_purchase_type" :value="formDetail.purchaseType"/>
              </template>
            </el-form-item>
          </el-col>
          <!-- 合同编号 -->
          <el-col :span="8">
            <el-form-item label="合同编号" prop="contractId">{{formDetail.contractId}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 经办人 -->
          <el-col :span="8">
            <el-form-item label="经办人" prop="handledBy">{{formDetail.handledBy}}</el-form-item>
          </el-col>
          <!-- 所属部门 -->
          <el-col :span="8">
            <el-form-item label="所属部门">
              <template>
                <dict-tag :options="dict.type.purchasesale_belong_dept" :value="formDetail.belongDept"/>
              </template>
            </el-form-item>
          </el-col>
          <!-- 业务日期 -->
          <el-col :span="8">
            <el-form-item label="业务日期" prop="businessDate">{{formDetail.businessDate}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 物料名称 -->
          <el-col :span="8">
            <el-form-item label="物料名称" prop="materialName">{{formDetail.materialName}}</el-form-item>
          </el-col>
          <!-- 销售数量 -->
          <el-col :span="8">
            <el-form-item label="销售数量" prop="purchaseQuantity">{{formDetail.purchaseQuantity}}</el-form-item>
          </el-col>
          <!-- 供应商名称 -->
          <el-col :span="8">
            <el-form-item label="供应商名称" prop="supplierName">{{formDetail.supplierName}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 单价 -->
          <el-col :span="8">
            <el-form-item label="单价" prop="unitPrice">{{formDetail.unitPrice}}</el-form-item>
          </el-col>
          <!-- 计量单位 -->
          <el-col :span="8">
            <el-form-item label="计量单位" prop="meteringUnit">
              <template>
                <dict-tag :options="dict.type.masterdata_warehouse_measurement_unit" :value="formDetail.meteringUnit"/>
              </template>
            </el-form-item>
          </el-col>
          <!-- 预计到货期 -->
          <el-col :span="8">
            <el-form-item label="预计到货期" prop="arrivalDate">{{formDetail.arrivalDate}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 要求交货期 -->
          <el-col :span="8">
            <el-form-item label="要求交货期" prop="requiredDeliveryDate">{{formDetail.requiredDeliveryDate}}</el-form-item>
          </el-col>
          <!-- 账期 -->
          <el-col :span="8">
            <el-form-item label="账期" prop="accountPeriod">{{formDetail.accountPeriod}}</el-form-item>
          </el-col>
          <!-- 到账条件 -->
          <el-col :span="8">
            <el-form-item label="到账条件" prop="arrivalTerms">
              <template>
                <dict-tag :options="dict.type.purchasesale_arrival_terms" :value="formDetail.arrivalTerms"/>
              </template>
              <!-- 到账条件值 -->
              {{formDetail.arrivalTermsValue}}（天）
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 结算方式 -->
          <el-col :span="8">
            <el-form-item label="结算方式" prop="settlementMethod">
              <template>
                <dict-tag :options="dict.type.purchasesale_settlement_method" :value="formDetail.settlementMethod"/>
              </template>
            </el-form-item>
          </el-col>
          <!-- 是否开票 -->
          <el-col :span="16">
            <el-form-item label="是否开票" prop="isInvoicing">
              <div v-if="formDetail.isInvoicing == '1'">开</div>
              <div v-else>否</div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 备注 -->
          <el-col :span="24">
            <el-form-item label="备注" prop="orderRemark">{{formDetail.orderRemark}}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDetail">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import { listPurchase, getPurchase, delPurchase, addPurchase, updatePurchase } from "@/api/purchasesale/purchasesale";

export default {
  name: "Purchase",
  dicts: ['purchasesale_purchase_type', 'purchasesale_belong_dept', 'masterdata_warehouse_measurement_unit', 
          'purchasesale_arrival_terms', 'purchasesale_settlement_method', 'contractmgr_contract_approval_status'],
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
      // 销售收货销售发货管理表格数据
      purchaseList: [],
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
        orderId: null,
        belongDept: null,
        handledBy: null,
        materialName: null,
        startBusinessDate: null,
        endbusinessDate: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      // rules: {
      //   purchaseType: [
      //     { required: true, message: "销售类型不能为空", trigger: "change" }
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
      //     { required: true, message: "销售数量不能为空", trigger: "blur" }
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
      isUpdate: false,
      formDetail: {},
      openDetail: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询销售收货销售发货管理列表 */
    getList() {
      this.loading = true;
      this.queryParams.contractType = "S";
      listPurchase(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        
        this.purchaseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
      // 取消按钮
    cancelDetail() {
      this.openDetail = false;
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
      this.ids = selection.map(item => item.contractId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加销售订单数据";
      this.form.isInvoicing = "1";
      this.isUpdate = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const contractId = row.contractId || this.ids
      getPurchase(contractId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改销售订单数据";
        this.isUpdate = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.form.purchaseType = "S";
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.isUpdate) {
            updatePurchase(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPurchase(this.form).then(response => {
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
      const orderIds = row.orderId || this.ids;
      this.$modal.confirm('是否确认删除销售订单数据项？').then(function() {
        return delPurchase(orderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('purchase/mgr/export', {
        ...this.queryParams
      }, `销售管理_${new Date().getFullYear()}年${new Date().getMonth()+1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    },
     /** 查看合同数据 */ 
     handleView(row) {
      this.formDetail = row;
      this.openDetail = true;
    }
  }
};
</script>
