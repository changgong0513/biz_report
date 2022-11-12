<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!-- 收货编号 -->
      <el-form-item label="收货编号" prop="receiptId">
        <el-input
          v-model="queryParams.receiptId"
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
          placeholder="请输入收货日期"
          value-format="yyyy-MM-dd"
          type="date"
        ></el-date-picker>
      </el-form-item>
      <!-- 订单状态（数据来源于采购（销售）订单信息表，关联查询） -->
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select
          v-model="queryParams.orderStatus"
          placeholder="请输入订单状态"
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
    <el-table v-loading="loading" :data="receiptList" @selection-change="handleSelectionChange"
      @row-dblclick="handleView">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="收货编号" align="center" prop="receiptId" width="150" />
      <el-table-column label="收货日期" align="center" prop="receiptDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.receiptDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="采购订单" align="center" prop="purchaseOrderId" width="150" />
      <el-table-column label="经办人" align="center" prop="handledBy" width="150" :show-overflow-tooltip="true" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" width="240" :show-overflow-tooltip="true" />
      <el-table-column label="物料名称" align="center" prop="materialName" width="150" :show-overflow-tooltip="true" />
      <el-table-column label="订单状态" align="center" prop="orderStatus" width="100" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改采购收货销售发货管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row>
          <!-- 收货编号 -->
          <el-col :span="8">
            <el-form-item label="收货编号" prop="receiptId">
              <el-input v-model="form.receiptId" placeholder="请输入收货编号" :disabled="this.isUpdate" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 采购订单编号 -->
          <el-col :span="8">
            <el-form-item label="采购订单编号" prop="purchaseOrderId">
              <!-- <el-input v-model="form.purchaseOrderId" placeholder="请输入采购订单编号" style="width: 240px" /> -->
              <el-select
                v-model="form.purchaseOrderId"
                filterable
                remote
                clearable
                reserve-keyword
                placeholder="请输入采购订单编号关键字"
                :remote-method="remoteMethod"
                :loading="remoteLoading"
                @change="selChange">
                <el-option
                  v-for="item in purchaseOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 采购合同编号 -->
          <el-col :span="8">
            <el-form-item label="采购合同编号" prop="purchaseContractId">
              <el-input v-model="form.contractId" placeholder="请输入采购合同编号" style="width: 240px" />
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
            <el-form-item label="收货日期" prop="receiptDate">
              <el-date-picker clearable
                v-model="form.receiptDate"
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
            <el-form-item label="仓库编号" prop="warehouseCode">
              <el-input v-model="form.warehouseCode" placeholder="请输入仓库编号" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 仓库名称 -->
          <el-col :span="8">
            <el-form-item label="仓库名称" prop="warehouseName">
              <el-input v-model="form.warehouseName" placeholder="请输入仓库名称" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 批次号 -->
          <el-col :span="8">
            <el-form-item label="批次号" prop="batchNo">
              <el-input v-model="form.batchNo" placeholder="请输入批次号" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 运输方式 -->
          <el-col :span="8">
            <el-form-item label="运输方式" prop="transportMode">
              <el-select
                v-model="form.transportMode"
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
            <el-form-item label="运输单号" prop="transportNumber">
              <el-input v-model="form.transportNumber" placeholder="请输入运输单号" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 预期收货数量 -->
          <el-col :span="8">
            <el-form-item label="预期收货数量" prop="expectReceiptQuantity">
              <el-input v-model="form.expectReceiptQuantity" placeholder="请输入预期收货数量" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 核算数量 -->
          <el-col :span="8">
            <el-form-item label="核算数量" prop="checkQuantity">
              <el-input v-model="form.checkQuantity" placeholder="请输入核算数量" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 核算单价 -->
          <el-col :span="8">
            <el-form-item label="核算单价" prop="checkPrice">
              <el-input v-model="form.checkPrice" placeholder="请输入核算单价" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 核算金额 -->
          <el-col :span="8">
            <el-form-item label="核算金额" prop="checkMoney">
              <el-input v-model="form.checkMoney" placeholder="请输入核算金额" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 货损数量 -->
          <el-col :span="8">
            <el-form-item label="货损数量" prop="cargoDamageQuantity">
              <el-input v-model="form.cargoDamageQuantity" placeholder="请输入货损数量" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 货损金额 -->
          <el-col :span="8">
            <el-form-item label="货损金额" prop="cargoDamageMoney">
              <el-input v-model="form.cargoDamageMoney" placeholder="请输入货损金额" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 备注 -->
          <el-col :span="24">
            <el-form-item label="备注" prop="receiptRemark">
              <el-input v-model="form.receiptRemark" type="textarea" style="width: 90%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <h3>折干计算</h3>
        <el-row>
          <!-- 水分值 -->
          <el-col :span="8">
            <el-form-item label="水分值" prop="dryCalWaterValue">
              <el-input v-model="form.dryCalWaterValue" placeholder="请输入水分值" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 烘干率 -->
          <el-col :span="8">
            <el-form-item label="烘干率" prop="dryCalDryingRate">
              <el-input v-model="form.dryCalDryingRate" placeholder="请输入烘干率" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 比例范围 -->
          <el-col :span="8">
            <el-form-item label="比例范围" prop="dryCalScaleRange">
              <el-input v-model="form.dryCalScaleRange" placeholder="请输入比例范围" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 计算结果 -->
          <el-col :span="24">
            <el-form-item label="计算结果" prop="dryCalResult">
              <el-input v-model="form.dryCalResult" placeholder="请输入计算结果" style="width: 240px; margin-right: 50px" />
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

    <!--查看采购收货详细对话框 -->
    <el-dialog :title="title" :visible.sync="openDetail" width="80%" append-to-body :close-on-click-modal="false">
      <el-form ref="formDetail" :model="formDetail" label-width="100px">
        <el-row>
          <!-- 收货编号 -->
          <el-col :span="8">
            <el-form-item label="收货编号" prop="receiptId">{{formDetail.receiptId}}</el-form-item>
          </el-col>
          <!-- 采购订单编号 -->
          <el-col :span="8">
            <el-form-item label="采购订单编号" prop="purchaseOrderId">{{formDetail.purchaseOrderId}}</el-form-item>
          </el-col>
          <!-- 采购合同编号 -->
          <el-col :span="8">
            <el-form-item label="采购合同编号" prop="purchaseContractId">{{formDetail.purchaseContractId}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 经办人 -->
          <el-col :span="8">
            <el-form-item label="经办人" prop="handledBy">{{formDetail.handledBy}}</el-form-item>
          </el-col>
          <!-- 收货日期 -->
          <el-col :span="8">
            <el-form-item label="收货日期" prop="receiptDate">{{formDetail.receiptDate}}</el-form-item>
          </el-col>
          <!-- 供应商名称 -->
          <el-col :span="8">
            <el-form-item label="供应商名称" prop="supplierName">{{formDetail.supplierName}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 物料名称 -->
          <el-col :span="8">
            <el-form-item label="物料名称" prop="materialName">{{formDetail.materialName}}</el-form-item>
          </el-col>
          <!-- 仓库编号 -->
          <el-col :span="8">
            <el-form-item label="仓库编号" prop="warehouseCode">{{formDetail.warehouseCode}}</el-form-item>
          </el-col>
          <!-- 仓库名称 -->
          <el-col :span="8">
            <el-form-item label="仓库名称" prop="warehouseName">{{formDetail.warehouseName}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 批次号 -->
          <el-col :span="8">
            <el-form-item label="批次号" prop="batchNo">{{formDetail.batchNo}}</el-form-item>
          </el-col>
          <!-- 运输方式 -->
          <el-col :span="8">
            <el-form-item label="运输方式" prop="transportMode">
              <template>
                <dict-tag :options="dict.type.purchasesale_transport_mode" :value="formDetail.transportMode"/>
              </template>
            </el-form-item>
          </el-col>
          <!-- 运输单号 -->
          <el-col :span="8">
            <el-form-item label="运输单号" prop="transportNumber">{{formDetail.transportNumber}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 预期收货数量 -->
          <el-col :span="8">
            <el-form-item label="预期收货数量" prop="expectReceiptQuantity">{{formDetail.expectReceiptQuantity}}</el-form-item>
          </el-col>
          <!-- 核算数量 -->
          <el-col :span="8">
            <el-form-item label="核算数量" prop="checkQuantity">{{formDetail.checkQuantity}}</el-form-item>
          </el-col>
          <!-- 核算单价 -->
          <el-col :span="8">
            <el-form-item label="核算单价" prop="checkPrice">{{formDetail.checkPrice}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 核算金额 -->
          <el-col :span="8">
            <el-form-item label="核算金额" prop="checkMoney">{{formDetail.checkMoney}}</el-form-item>
          </el-col>
          <!-- 货损数量 -->
          <el-col :span="8">
            <el-form-item label="货损数量" prop="cargoDamageQuantity">{{formDetail.cargoDamageQuantity}}</el-form-item>
          </el-col>
          <!-- 货损金额 -->
          <el-col :span="8">
            <el-form-item label="货损金额" prop="cargoDamageMoney">{{formDetail.cargoDamageMoney}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 备注 -->
          <el-col :span="24">
            <el-form-item label="备注" prop="receiptRemark">{{formDetail.receiptRemark}}</el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <h3>折干计算</h3>
        <el-row>
          <!-- 水分值 -->
          <el-col :span="8">
            <el-form-item label="水分值" prop="dryCalWaterValue">{{formDetail.dryCalWaterValue}}</el-form-item>
          </el-col>
          <!-- 烘干率 -->
          <el-col :span="8">
            <el-form-item label="烘干率" prop="dryCalDryingRate">{{formDetail.dryCalDryingRate}}</el-form-item>
          </el-col>
          <!-- 比例范围 -->
          <el-col :span="8">
            <el-form-item label="比例范围" prop="dryCalScaleRange">{{formDetail.dryCalScaleRange}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 计算结果 -->
          <el-col :span="24">
            <el-form-item label="计算结果" prop="dryCalResult">{{formDetail.dryCalResult}}</el-form-item>
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
import { listReceipt, getReceipt, addReceipt, delReceipt, updateReceipt } from "@/api/purchasesale/receipt";
import { listPurchase, getPurchase, delPurchase, addPurchase, updatePurchase, deleteUploadFile, 
  getOrderAdditional } from "@/api/purchasesale/purchasesale";
export default {
  name: "Purchase",
  dicts: ['purchasesale_purchase_type', 'purchasesale_belong_dept', 'masterdata_warehouse_measurement_unit', 
          'purchasesale_arrival_terms', 'purchasesale_settlement_method', 'purchasesale_receipt_order_status', 
          'purchasesale_transport_mode'],
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
      total: 1,
      receiptList: [],
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
        receiptId: null,
        purchaseOrderId: null,
        contractType: null,
        handledBy: null,
        materialName: null,
        warehouseName: null,
        receiptDate: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        receiptId: [
          { required: true, message: "收货编号为空", trigger: "blur" }
        ],
        purchaseOrderId: [
          { required: true, message: "采购订单编号为空", trigger: "blur" }
        ],
        purchaseContractId: [
          { required: true, message: "采购合同编号为空", trigger: "blur" }
        ],
      },
      isUpdate: false,
      formDetail: {},
      openDetail: false,
      purchaseOptions: [],
      purchaseOrderList: [],
      remoteLoading: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    remoteMethod(query) {
      if (query !== '') {
        this.remoteLoading = true;
        this.queryParams.orderId = query;
        this.queryParams.contractType = "P";
        console.log("select远程方法调用" + JSON.stringify(this.queryParams));
        listPurchase(this.queryParams).then(response => {
          this.remoteLoading = false;
          this.purchaseOrderList = response.rows;
          this.purchaseOptions = response.rows.map(item => {
            return { value: `${item.orderId}`, label: `${item.orderId}` };
          }).filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        });
      } else {
        this.purchaseOptions = [];
      }
    },
    selChange(selValue) {
      console.log("选择的订单编号是：" + selValue);
      this.form.contractId = this.purchaseOrderList.find(item => {
        return item.orderId === selValue;
      }).contractId;
      console.log("查找到的合同编号是：" + this.form.contractId);
    },
    /** 查询采购收货销售发货管理列表 */
    getList() {
      this.loading = true;
      listReceipt(this.queryParams).then(response => {
        this.receiptList = response.rows;
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
        receiptId: null,
        purchaseOrderId: null,
        purchaseContractId: null,
        handledBy: null,
        receiptDate: null,
        supplierName: null,
        materialName: null,
        warehouseCode: null,
        warehouseName: null,
        batchNo: null,
        transportMode: null,
        transportNumber: null,
        expectReceiptQuantity: null,
        checkQuantity: null,
        checkPrice: null,
        checkMoney: null,
        cargoDamageQuantity: null,
        cargoDamageMoney: null,
        receiptRemark: null,
        dryCalWaterValue: null,
        dryCalDryingRate: null,
        dryCalScaleRange: null,
        dryCalResult: null
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
      this.ids = selection.map(item => item.receiptId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加收货信息";
      this.isUpdate = false;
    },
    /** 修改按钮操作 */
    handleUpdate() {
      this.reset();
      const receiptId = this.ids
      console.log(receiptId);
      getReceipt(receiptId).then(response => {
        this.form = response.data;
        this.open = true;
        this.isUpdate = true;
        this.title = "修改收货信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.isUpdate) {
            updateReceipt(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReceipt(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              //this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const receiptId = row.receiptId || this.ids;
      this.$modal.confirm('是否确认删除发货编号为"' + receiptId + '"的数据项？').then(function() {
        return delReceipt(receiptId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('receipt/mgr/export', {
        ...this.queryParams
      }, `收货管理_${new Date().getFullYear()}年${new Date().getMonth()+1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    },
    /** 查看收货数据 */ 
    handleView(row) {
      this.formDetail = row;
      this.openDetail = true;
    }
  }
};
</script>
