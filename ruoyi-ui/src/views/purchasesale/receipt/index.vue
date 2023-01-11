<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="85px">
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
      <!-- 车船编号 -->
      <el-form-item label="车船编号" prop="receiptId">
        <el-input
          v-model="queryParams.receiptId"
          placeholder="请输入车船编号"
          clearable
        />
      </el-form-item>
      <!-- 供应商名称 -->
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
        />
      </el-form-item>
      <!-- 仓库名称 -->
      <el-form-item label="仓库名称" prop="warehouseName">
        <el-input
          v-model="queryParams.warehouseName"
          placeholder="请输入仓库名称"
          clearable
        />
      </el-form-item>
      
      <!-- 订单状态（数据来源于采购（销售）订单信息表，关联查询） -->
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select
          v-model="queryParams.contractType"
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
          <!-- <el-col :span="8">
            <el-form-item label="收货编号" prop="receiptId">
              <el-input v-model="form.receiptId" placeholder="请输入收货编号" :disabled="this.isUpdate" style="width: 240px" />
            </el-form-item>
          </el-col> -->
          <!-- 采购合同编号 -->
          <el-col :span="8">
            <el-form-item label="采购合同编号" prop="purchaseContractId">
              <el-select
                v-model="form.purchaseContractId"
                filterable
                remote
                clearable
                reserve-keyword
                placeholder="请输入采购合同编号关键字"
                style="width: 240px"
                :remote-method="remoteMethodPurchaseContract"
                :loading="remoteLoadingPurchaseContract"
                @change="selChangePurchaseContract">
                <el-option
                  v-for="item in optionsPurchaseContract"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
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
                style="width: 240px"
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
          <!-- 经办人 -->
          <el-col :span="8">
            <el-form-item label="经办人" prop="handledBy" style="width: 240px">{{form.handledBy}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
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
            <el-form-item label="供应商名称" prop="handledBy" style="width: 240px">{{form.supplierName}}</el-form-item>
          </el-col>
          <!-- 物料名称 -->
          <el-col :span="8">
            <el-form-item label="物料名称" prop="materialName" style="width: 240px">{{form.materialName}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 仓库编号 -->
          <el-col :span="8">
            <el-form-item label="仓库编号" prop="warehouseCode">
              <!-- <el-input v-model="form.warehouseCode" placeholder="请输入仓库编号" style="width: 240px" /> -->
              <el-select
                v-model="form.warehouseCode"
                filterable
                remote
                clearable
                reserve-keyword
                placeholder="请输入仓库编号关键字"
                style="width: 240px"
                :remote-method="remoteWarehouseCode"
                :loading="remoteLoadingWarehouse"
                @change="selChangeWarehouse">
                <el-option
                  v-for="item in purchaseOptionsWarehouse"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 仓库名称 -->
          <el-col :span="8">
            <el-form-item label="仓库名称" prop="warehouseName" style="width: 240px">{{form.warehouseName}}</el-form-item>
          </el-col>
          <!-- 批次号 -->
          <el-col :span="8">
            <el-form-item label="批次号" prop="batchNo">
              <el-input v-model="form.batchNo" placeholder="请输入批次号" style="width: 240px"
                maxlength="16" show-word-limit />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 车船编号 -->
          <el-col :span="8">
            <el-form-item label="车船编号" prop="ccbh">
              <el-input v-model="form.ccbh" placeholder="请输入车船编号" style="width: 240px"
                maxlength="64" show-word-limit />
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
              <el-input v-model="form.transportNumber" placeholder="请输入运输单号" style="width: 240px"
                maxlength="32" show-word-limit />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 卸货数量 -->
          <el-col :span="8">
            <el-form-item label="卸货数量" prop="expectReceiptQuantity">
              <el-input v-model="form.expectReceiptQuantity" placeholder="请输入卸货数量" style="width: 240px" />
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
              <el-input v-model="form.checkPrice" placeholder="核算单价" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 合同单价 -->
          <el-col :span="8">
            <el-form-item label="合同单价" prop=" htdj">
              <el-input v-model="form.htdj" placeholder="请输入核算数量" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 核算金额 -->
          <el-col :span="8">
            <el-form-item label="核算金额" prop="checkMoney" style="width: 240px">{{calCheckMoney}}</el-form-item>
          </el-col>
          <!-- 货损金额 -->
          <el-col :span="8">
            <el-form-item label="货损金额" prop="cargoDamageMoney" style="width: 240px">{{calCargoDamageMoney}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 货损数量 -->
          <el-col :span="8">
            <el-form-item label="货损数量" prop="cargoDamageQuantity">{{calCargoDamageQuantity}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 备注 -->
          <el-col :span="24">
            <el-form-item label="备注" prop="receiptRemark">
              <el-input v-model="form.receiptRemark" type="textarea" style="width: 90%"
                maxlength="256" show-word-limit />
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
              <el-input v-model="form.dryCalScaleRange" placeholder="请输入比例范围1.2-1.3之间" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 计算结果 -->
          <el-col :span="24">
            <el-form-item label="计算结果" prop="dryCalResult">
              <el-input v-model="form.dryCalResult" placeholder="请输入计算结果" style="width: 240px; margin-right: 50px" />
              <el-button type="success" icon="el-icon-check" size="mini" @click="dryCalculation">计算</el-button>
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
    <el-dialog :title="title" :visible.sync="openDetail" width="50%" append-to-body :close-on-click-modal="false">
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
          <!-- 车船编号 -->
          <el-col :span="8">
            <el-form-item label="车船编号" prop="ccbh">{{formDetail.ccbh}}</el-form-item>
          </el-col>
          <!-- 运输方式 -->
          <el-col :span="8">
            <el-form-item label="运输方式" prop="transportMode">
              <template>
                <dict-tag :options="dict.type.purchasesale_transport_mode" :value="formDetail.transportMode"/>
              </template>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 运输单号 -->
          <el-col :span="8">
            <el-form-item label="运输单号" prop="transportNumber">{{formDetail.transportNumber}}</el-form-item>
          </el-col>
          <!-- 卸货数量 -->
          <el-col :span="8">
            <el-form-item label="卸货数量" prop="expectReceiptQuantity">{{formDetail.expectReceiptQuantity}}</el-form-item>
          </el-col>
          <!-- 核算数量 -->
          <el-col :span="8">
            <el-form-item label="核算数量" prop="checkQuantity">{{formDetail.checkQuantity}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 核算单价 -->
          <el-col :span="8">
            <el-form-item label="核算单价" prop="checkPrice">{{formDetail.checkPrice}}</el-form-item>
          </el-col>
          <!-- 核算金额 -->
          <el-col :span="8">
            <el-form-item label="核算金额" prop="checkMoney">{{formDetail.checkMoney}}</el-form-item>
          </el-col>
          <!-- 合同单价 -->
          <el-col :span="8">
            <el-form-item label="合同单价" prop="checkMoney">{{formDetail.htdj}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
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
import { listPurchase } from "@/api/purchasesale/purchasesale";
import { listWarehouse } from "@/api/masterdata/warehouse";

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
        receiptDate: null,
        supplierName: null,
        warehouseName: null,
        contractId: null,
        contractType: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        receiptId: [
          { required: true, message: "收货编号不能为空", trigger: "blur" }
        ],
        purchaseOrderId: [
          { required: true, message: "采购订单编号不能为空", trigger: "blur" }
        ],
        receiptDate: [
          { required: true, message: "收货日期不能为空", trigger: "blur" }
        ],
        warehouseCode: [
          { required: true, message: "仓库编号不能为空", trigger: "blur" }
        ],
        batchNo: [
          { required: true, message: "批次号不能为空", trigger: "blur" }
        ],
        transportMode: [
          { required: true, message: "运输方式不能为空", trigger: "blur" }
        ],
        transportNumber: [
          { required: true, message: "运输单号不能为空", trigger: "blur" }
        ],
        expectReceiptQuantity: [
          { required: true, message: "预期收货数量不能为空", trigger: "blur" }
        ],
        checkQuantity: [
          { required: true, message: "核算数量不能为空", trigger: "blur" }
        ],
        ccbh: [
          { required: true, message: "车船编号不能为空", trigger: "blur" }
        ]
      },
      isUpdate: false,
      formDetail: {},
      openDetail: false,
      // 采购订单编号选择用
      remoteLoading: false,
      purchaseOptions: [],
      purchaseOrderList: [],
      // 仓库编号选择用
      remoteLoadingWarehouse: false,
      purchaseOptionsWarehouse: [],
      purchaseOrderListWarehouse: [],
      // 采购合同编号选择用
      optionsPurchaseContract: [],
      listPurchaseContract: [],
      remoteLoadingPurchaseContract: false
    };
  },
  created() {
    this.getList();
  },
  computed: {
    /** 核算金额 */
    calCheckMoney: function () {
      if (this.form.checkPrice && this.form.checkQuantity) {
        return Number(this.form.checkPrice) * Number(this.form.checkQuantity)
      }
      
      return 0;
    },
    /** 货损金额 */
    calCargoDamageMoney: function () {
      if (this.form.checkPrice && this.form.expectReceiptQuantity && this.form.checkQuantity) {
        return Number(this.form.checkPrice) * Number(this.form.expectReceiptQuantity) * Number(this.form.checkQuantity)
      }
      
      return 0;
    },
       /** 货损数量（货损数量=卸货数量-核算数量） */
    calCargoDamageQuantity: function () {
      if (this.form.expectReceiptQuantity && this.form.checkQuantity) {
        return Number(this.form.expectReceiptQuantity) * Number(this.form.checkQuantity)
      }
      
      return 0;
    }
  },
  methods: {
    /** 根据输入订单编号关键字，取得订单编号列表 */
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
    /** 根据输入仓库编号关键字，取得订单编号列表 */
    remoteWarehouseCode(query) {
      if (query !== '') {
        this.remoteLoadingWarehouse = true;
        listWarehouse(this.form).then(response => {
          this.remoteLoadingWarehouse = false;
          console.log(JSON.stringify(response.rows));
          this.purchaseOrderListWarehouse = response.rows;
          this.purchaseOptionsWarehouse = response.rows.map(item => {
            return { value: `${item.warehouseCode}`, label: `${item.warehouseCode}` };
          }).filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        });
      } else {
        this.purchaseOptionsWarehouse = [];
      }
    },
    /** 根据输入采购合同编号关键字，取得订单编号列表 */
    remoteMethodPurchaseContract(query) {
      if (query !== '') {
        this.remoteLoadingPurchaseContract = true;
        this.queryParams.contractId = query;
        this.queryParams.contractType = "P";
        console.log("select远程方法调用" + JSON.stringify(this.queryParams));
        listPurchase(this.queryParams).then(response => {
          this.remoteLoadingPurchaseContract = false;
          this.listPurchaseContract = response.rows;
          this.optionsPurchaseContract = response.rows.map(item => {
            return { value: `${item.orderId}`, label: `${item.orderId}` };
          }).filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        });
      } else {
        this.optionsPurchaseContract = [];
      }
    },
    /** 订单编号下拉列表框，选择值改变后回调方法 */
    selChange(selValue) {
      console.log("选择的订单编号是：" + selValue);

      let purchaseOrder = this.purchaseOrderList.find(item => {
        return item.orderId === selValue;
      });

      this.form.purchaseContractId = purchaseOrder.contractId; // 合同编号
      this.form.handledBy = purchaseOrder.handledBy;  // 经办人
      this.form.supplierName = purchaseOrder.supplierName;  // 供应商名称
      this.form.materialName = purchaseOrder.materialName;  // 物料名称
      this.form.checkPrice = purchaseOrder.unitPrice; // 核算单价
    },
    /** 仓库编号下拉列表框，选择值改变后回调方法 */
    selChangeWarehouse(selValue) {
      console.log("选择的仓库编号是：" + selValue);

      let warehouse = this.purchaseOrderListWarehouse.find(item => {
        return item.warehouseCode === selValue;
      });

      this.form.warehouseName = warehouse.warehouseName; // 仓库名称
    },
    /** 合同编号下拉列表框，选择值改变后回调方法 */
    selChangePurchaseContract(selValue) {
      console.log("选择的合同编号是：" + selValue);

      let purchaseContract = this.listPurchaseContract.find(item => {
        return item.contractId === selValue;
      });
      console.log("选择的合同数据是：" + JSON.stringify(purchaseContract));

      this.form.purchaseOrderId = purchaseContract.orderId; // 订单编号
      this.form.handledBy = purchaseContract.handledBy;  // 经办人
      this.form.supplierName = purchaseContract.supplierName;  // 供应商名称
      this.form.materialName = purchaseContract.materialName;  // 物料名称
      this.form.checkPrice = purchaseContract.unitPrice; // 核算单价
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
        dryCalResult: null,
        ccbh: null,
        htdj: null
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
      this.queryForm = {
        receiptDate: null,
        receiptId: null,
        supplierName: null,
        warehouseName: null,
        contractType: null
      };

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
            this.form.checkMoney = this.calCheckMoney; // 核算金额
            this.form.cargoDamageMoney = this.calCargoDamageMoney; // 货损金额
            this.form.cargoDamageQuantity = this.calCargoDamageQuantity; // 货损数量
            updateReceipt(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.checkMoney = this.calCheckMoney; // 核算金额
            this.form.cargoDamageMoney = this.calCargoDamageMoney; // 货损金额
            this.form.cargoDamageQuantity = this.calCargoDamageQuantity; // 货损数量
            addReceipt(this.form).then(response => {
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
    },
    /** 折干计算 */
    dryCalculation() {
      if (this.form.dryCalWaterValue == null) {
        this.$modal.msgError(`折干计算水分值为空，请输入水分值!`);
        return;
      }

      if (this.form.dryCalDryingRate == null) {
        this.$modal.msgError(`折干计算烘干率为空，请输入烘干率!`);
        return;
      }

      if (this.form.dryCalScaleRange == null) {
        this.$modal.msgError(`折干计算比例范围为空，请输入比例范围!`);
        return;
      }

      if (parseFloat(this.form.dryCalScaleRange) - 1.19 < 0.000001 || 
          parseFloat(this.form.dryCalScaleRange) - 1.30 > 0.000001) {
        this.$modal.msgError(`折干计算比例不是指定的范围内，请输入重新输入比例范围!`);
        return;
      }

      this.form.dryCalResult = (100 - 
        ((parseFloat(this.form.dryCalWaterValue) - 
          parseFloat(this.form.dryCalDryingRate)) * 
          parseFloat(this.form.dryCalScaleRange)))/100 * 15;
      
      this.form.dryCalResult = this.form.dryCalResult.toFixed(2);
    }
  }
};
</script>
