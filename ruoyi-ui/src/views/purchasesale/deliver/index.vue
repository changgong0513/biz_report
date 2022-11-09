<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!-- 收货编号 -->
      <el-form-item label="发货编号" prop="deliverId">
        <el-input
          v-model="queryParams.deliverId"
          placeholder="请输入发货编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 销售订单 -->
      <el-form-item label="销售订单" prop="saleOrderId">
        <el-input
          v-model="queryParams.saleOrderId"
          placeholder="请输入销售订单编号"
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
      <!-- 发货日期 -->
      <el-form-item label="发货日期" prop="deliverDate">
        <el-date-picker
          v-model="queryParams.deliverDate"
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
    <el-table v-loading="loading" :data="deliverList" @selection-change="handleSelectionChange"
      @row-dblclick="handleView">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="发货编号" align="center" prop="deliverId" width="150" />
      <el-table-column label="发货日期" align="center" prop="deliverDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliverDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="销售订单" align="center" prop="saleOrderId" width="150" />
      <el-table-column label="经办人" align="center" prop="handledBy" width="150" :show-overflow-tooltip="true" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" width="200" :show-overflow-tooltip="true" />
      <el-table-column label="物料名称" align="center" prop="materialName" width="200" :show-overflow-tooltip="true" />
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
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <!-- 发货编号 -->
          <el-col :span="8">
            <el-form-item label="发货编号" prop="deliverId">
              <el-input v-model="form.deliverId" :disabled="this.isUpdate" placeholder="请输入发货编号" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 销售订单编号 -->
          <el-col :span="8">
            <el-form-item label="销售订单编号" prop="saleOrderId">
              <el-input v-model="form.saleOrderId" placeholder="请输入销售订单编号" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 销售合同编号 -->
          <el-col :span="8">
            <el-form-item label="销售合同编号" prop="saleContractId">
              <el-input v-model="form.saleContractId" placeholder="请输入销售合同编号" style="width: 240px" />
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
          <!-- 发货日期 -->
          <el-col :span="8">
            <el-form-item label="发货日期" prop="deliverDate">
              <el-date-picker clearable
                v-model="form.deliverDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择发货日期"
                style="width: 240px">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <!-- 客户编号 -->
          <el-col :span="8">
            <el-form-item label="客户编号" prop="clientId">
              <el-input v-model="form.clientId" placeholder="请输入客户编号" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 客户姓名 -->
          <el-col :span="8">
            <el-form-item label="客户姓名" prop="clientName">
              <el-input v-model="form.clientName" placeholder="请输入客户姓名" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 物料编号 -->
          <el-col :span="8">
            <el-form-item label="物料编号" prop="materialId">
              <el-input v-model="form.materialId" placeholder="请输入物料编号" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 物料名称 -->
          <el-col :span="8">
            <el-form-item label="物料名称" prop="materialName">
              <el-input v-model="form.materialName" placeholder="请输入物料名称" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 合同单价 -->
          <el-col :span="8">
            <el-form-item label="合同单价" prop="contractPrice">
              <el-input v-model="form.contractPrice" placeholder="请输入合同单价" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 计量单位 -->
          <el-col :span="8">
            <el-form-item label="计量单位" prop="measurementUnit">
              <el-input v-model="form.measurementUnit" placeholder="请输入计量单位" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 发货方式 -->
          <el-col :span="8">
            <el-form-item label="发货方式" prop="deliverMode">
              <el-select
                v-model="form.deliverMode"
                placeholder="发货方式"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.purchasesale_deliver_mode"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
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
          <!-- 发货数量 -->
          <el-col :span="8">
            <el-form-item label="发货数量" prop="deliverQuantity">
              <el-input v-model="form.deliverQuantity" placeholder="请输入发货数量" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
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
          <!-- 核算金额 -->
          <el-col :span="8">
            <el-form-item label="核算金额" prop="checkMoney">
              <el-input v-model="form.checkMoney" placeholder="请输入核算金额" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
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
        </el-row>
        <el-row>
          <!-- 运输单号 -->
          <el-col :span="8">
            <el-form-item label="运输单号" prop="transportNumber">
              <el-input v-model="form.transportNumber" placeholder="请输入运输单号" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 运输金额 -->
          <el-col :span="8">
            <el-form-item label="运输金额" prop="transportMoney">
              <el-input v-model="form.transportMoney" placeholder="请输入运输金额" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 其他金额 -->
          <el-col :span="8">
            <el-form-item label="其他金额" prop="otherMoney">
              <el-input v-model="form.otherMoney" placeholder="请输入其他金额" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 预期到货日期 -->
          <el-col :span="8">
            <el-form-item label="预期到货日期" prop="expectArrivalDate">
              <el-date-picker clearable
                v-model="form.expectArrivalDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择预期到货日期"
                style="width: 240px">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <!-- 要求到货日期 -->
          <el-col :span="8">
            <el-form-item label="要求到货日期" prop="requireArrivalDate">
              <el-date-picker clearable
                v-model="form.requireArrivalDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择要求到货日期"
                style="width: 240px">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <!-- 账期（关联采购（销售）订单信息表的账期） -->
          <el-col :span="8">
            <el-form-item label="账期" prop="accountPeriod">
              <el-input v-model="form.accountPeriod" placeholder="天数" style="margin-left: 10px; width: 60px" />（天）
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 备注 -->
          <el-col :span="24">
            <el-form-item label="备注" prop="deliverRemark">
              <el-input v-model="form.deliverRemark" type="textarea" style="width: 90%" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--查看销售发货详细对话框 -->
    <el-dialog :title="title" :visible.sync="openDetail" width="80%" append-to-body :close-on-click-modal="false">
      <el-form ref="formDetail" :model="formDetail" label-width="100px">
        <el-row>
          <!-- 发货编号 -->
          <el-col :span="8">
            <el-form-item label="发货编号">{{formDetail.deliverId}}</el-form-item>
          </el-col>
          <!-- 销售订单编号 -->
          <el-col :span="8">
            <el-form-item label="销售订单编号">{{formDetail.saleOrderId}}</el-form-item>
          </el-col>
          <!-- 销售合同编号 -->
          <el-col :span="8">
            <el-form-item label="销售合同编号">{{formDetail.saleContractId}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 经办人 -->
          <el-col :span="8">
            <el-form-item label="经办人" prop="handledBy">{{formDetail.handledBy}}</el-form-item>
          </el-col>
          <!-- 发货日期 -->
          <el-col :span="8">
            <el-form-item label="发货日期" prop="deliverDate">{{formDetail.deliverDate}}</el-form-item>
          </el-col>
          <!-- 客户编号 -->
          <el-col :span="8">
            <el-form-item label="客户编号" prop="clientId">{{formDetail.clientId}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 客户姓名 -->
          <el-col :span="8">
            <el-form-item label="客户姓名" prop="clientName">{{formDetail.clientName}}</el-form-item>
          </el-col>
          <!-- 物料编号 -->
          <el-col :span="8">
            <el-form-item label="物料编号" prop="materialId">{{formDetail.materialId}}</el-form-item>
          </el-col>
          <!-- 物料名称 -->
          <el-col :span="8">
            <el-form-item label="物料名称" prop="materialName">{{formDetail.materialName}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 合同单价 -->
          <el-col :span="8">
            <el-form-item label="合同单价" prop="contractPrice">{{formDetail.contractPrice}}</el-form-item>
          </el-col>
          <!-- 计量单位 -->
          <el-col :span="8">
            <el-form-item label="计量单位" prop="measurementUnit">{{formDetail.measurementUnit}}</el-form-item>
          </el-col>
          <!-- 发货方式 -->
          <el-col :span="8">
            <el-form-item label="发货方式" prop="deliverMode">
              <template>
                <dict-tag :options="dict.type.purchasesale_deliver_mode" :value="formDetail.deliverMode"/>
              </template>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 仓库编号 -->
          <el-col :span="8">
            <el-form-item label="仓库编号" prop="warehouseCode">{{formDetail.warehouseCode}}</el-form-item>
          </el-col>
          <!-- 仓库名称 -->
          <el-col :span="8">
            <el-form-item label="仓库名称" prop="warehouseName">{{formDetail.warehouseName}}</el-form-item>
          </el-col>
          <!-- 发货数量 -->
          <el-col :span="8">
            <el-form-item label="发货数量" prop="deliverQuantity">{{formDetail.deliverQuantity}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 核算数量 -->
          <el-col :span="8">
            <el-form-item label="核算数量" prop="checkQuantity">{{formDetail.checkQuantity}}</el-form-item>
          </el-col>
          <!-- 核算单价 -->
          <el-col :span="8">
            <el-form-item label="核算单价" prop="checkPrice">{{formDetail.checkPrice}}</el-form-item>
          </el-col>
          <!-- 核算金额 -->
          <el-col :span="8">
            <el-form-item label="核算金额" prop="checkMoney">{{formDetail.checkMoney}}</el-form-item>
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
          <!-- 运输金额 -->
          <el-col :span="8">
            <el-form-item label="运输金额" prop="transportMoney">{{formDetail.transportMoney}}</el-form-item>
          </el-col>
          <!-- 其他金额 -->
          <el-col :span="8">
            <el-form-item label="其他金额" prop="otherMoney">{{formDetail.otherMoney}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 预期到货日期 -->
          <el-col :span="8">
            <el-form-item label="预期到货日期" prop="expectArrivalDate">{{formDetail.expectArrivalDate}}</el-form-item>
          </el-col>
          <!-- 要求到货日期 -->
          <el-col :span="8">
            <el-form-item label="要求到货日期" prop="requireArrivalDate">{{formDetail.requireArrivalDate}}</el-form-item>
          </el-col>
          <!-- 账期（关联采购（销售）订单信息表的账期） -->
          <el-col :span="8">
            <el-form-item label="账期" prop="accountPeriod">
              <div style="margin-left: 10px; width: 60px">{{formDetail.accountPeriod}}（天）</div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 备注 -->
          <el-col :span="24">
            <el-form-item label="备注" prop="deliverRemark">{{formDetail.deliverRemark}}</el-form-item>
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

import { listDeliver, getDeliver, delDeliver, addDeliver, updateDeliver } from "@/api/purchasesale/deliver";

export default {
  name: "Deliver",
  dicts: ['purchasesale_purchase_type', 'purchasesale_belong_dept', 'masterdata_warehouse_measurement_unit', 
          'purchasesale_arrival_terms', 'purchasesale_settlement_method', 'purchasesale_receipt_order_status', 
          'purchasesale_transport_mode', 'purchasesale_deliver_mode'],
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
      // 采购收货销售发货管理表格数据
      deliverList: [],
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
        deliverId: null,
        saleOrderId: null,
        handledBy: null,
        deliverDate: null,
        materialName: null,
        warehouseName: null,
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
    /** 查询采购收货销售发货管理列表 */
    getList() {
      this.loading = true;
      listDeliver(this.queryParams).then(response => {
        this.deliverList = response.rows;
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
        deliverId: null,
        saleOrderId: null,
        saleContractId: null,
        handledBy: null,
        deliverDate: null,
        clientId: null,
        clientName: null,
        materialId: null,
        materialName: null,
        contractPrice: null,
        measurementUnit: null,
        deliverMode: null,
        warehouseCode: null,
        warehouseName: null,
        deliverQuantity: null,
        checkQuantity: null,
        checkPrice: null,
        checkMoney: null,
        cargoDamageQuantity: null,
        cargoDamageMoney: null,
        transportMode: null,
        transportNumber: null,
        transportMoney: null,
        otherMoney: null,
        expectArrivalDate: null,
        requireArrivalDate: null,
        deliverRemark: null
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
      this.ids = selection.map(item => item.deliverId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加发货信息";
      this.isUpdate = false
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const deliverId = this.ids
      getDeliver(deliverId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改发货信息";
        this.isUpdate = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.isUpdate) {
            updateDeliver(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDeliver(this.form).then(response => {
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
      const deliverId = this.ids;
      this.$modal.confirm('是否确认删除发货管理编号为"' + deliverId + '"的数据项？').then(function() {
        return delDeliver(deliverId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('deliver/mgr/export', {
        ...this.queryParams
      }, `发货管理_${new Date().getFullYear()}年${new Date().getMonth()+1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    },
    /** 查看发货数据 */ 
    handleView(row) {
      this.formDetail = row;
      this.openDetail = true;
    }
  }
};
</script>
