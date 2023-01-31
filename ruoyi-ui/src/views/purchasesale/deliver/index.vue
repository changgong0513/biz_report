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
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row>
          <!-- 发货编号 -->
          <!-- <el-col :span="8">
            <el-form-item label="发货编号" prop="deliverId">
              <el-input v-model="form.deliverId" :disabled="this.isUpdate" placeholder="请输入发货编号" style="width: 240px" />
            </el-form-item>
          </el-col> -->
          <!-- 销售合同编号 -->
          <el-col :span="8">
            <el-form-item label="销售合同编号" prop="saleContractId">
              <el-select
                v-model="form.saleContractId"
                filterable
                remote
                clearable
                reserve-keyword
                placeholder="请输入销售合同编号关键字"
                style="width: 240px"
                :remote-method="remoteMethodSaleContract"
                :loading="remoteLoadingSaleContract">
                <el-option
                  v-for="item in optionsSaleContract"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
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
        </el-row>
        <el-row>
          <!-- 客户编号 -->
          <el-col :span="8">
            <el-form-item label="客户编号" prop="clientId">{{form.clientId}}</el-form-item>
          </el-col>
          <!-- 客户姓名 -->
          <el-col :span="8">
            <el-form-item label="客户姓名" prop="clientName">
              <el-select
                v-model="form.clientName"
                filterable
                remote
                clearable
                reserve-keyword
                placeholder="请输入客户编号关键字"
                style="width: 240px"
                :remote-method="remoteMethodClientName"
                :loading="remoteLoadingSClientName"
                @change="selChangeClientName">
                <el-option
                  v-for="item in optionsClientName"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 物料编号 -->
          <el-col :span="8">
            <el-form-item label="物料编号" prop="materialId">
              <el-select
                v-model="form.materialId"
                filterable
                remote
                clearable
                reserve-keyword
                style="width: 240px"
                placeholder="请输入物料编号关键字"
                :remote-method="remoteMethodMaterialId"
                :loading="remoteLoadingMaterialId"
                @change="selChangeMaterialId">
                <el-option
                  v-for="item in optionsMaterialId"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 物料名称 -->
          <el-col :span="8">
            <el-form-item label="物料名称" prop="materialName">
              <el-select
                v-model="form.materialName"
                filterable
                remote
                clearable
                reserve-keyword
                style="width: 240px"
                placeholder="请输入物料名称关键字"
                :remote-method="remoteMethodMaterialName"
                :loading="remoteLoadingMaterialName"
                @change="selChangeMaterialName">
                <el-option
                  v-for="item in optionsMaterialName"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 合同单价 -->
          <el-col :span="8">
            <el-form-item label="合同单价" prop="contractPrice">
              <el-input v-model="form.contractPrice" placeholder="请输入合同单价" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 计量单位 -->
          <el-col :span="8">
            <el-form-item label="计量单位" prop="measurementUnit">
              <el-select
                v-model="form.measurementUnit"
                placeholder="计量单位"
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
          </el-col>
        </el-row>
        <el-row>
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
          <!-- 仓库编号 -->
          <el-col :span="8">
            <el-form-item label="仓库编号" prop="warehouseCode">
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
            <el-form-item label="仓库名称" prop="warehouseName">
              <el-select
                v-model="form.warehouseName"
                filterable
                remote
                clearable
                reserve-keyword
                placeholder="请输入仓库名称关键字"
                style="width: 240px"
                :remote-method="remoteWarehouseName"
                :loading="remoteLoadingWarehouseName"
                @change="selChangeWarehouseId">
                <el-option
                  v-for="item in optionsWarehouseName"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 发货数量 -->
          <el-col :span="8">
            <el-form-item label="发货数量" prop="deliverQuantity">
              <el-input v-model="form.deliverQuantity" placeholder="请输入发货数量" style="width: 240px" />
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
          <!-- 运输金额 -->
          <el-col :span="8">
            <el-form-item label="运输金额" prop="transportMoney">
              <el-input v-model="form.transportMoney" placeholder="请输入运输金额" style="width: 240px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 其他金额 -->
          <el-col :span="8">
            <el-form-item label="其他金额" prop="otherMoney">
              <el-input v-model="form.otherMoney" placeholder="请输入其他金额" style="width: 240px" />
            </el-form-item>
          </el-col>
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
        </el-row>
        <el-row>
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
              <el-input v-model="form.deliverRemark" type="textarea" style="width: 90%" maxlength="128"
                show-word-limit />
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
    <el-dialog :title="title" :visible.sync="openDetail" width="70%" append-to-body :close-on-click-modal="false">
      <el-form ref="formDetail" :model="formDetail" label-width="100px">
        <el-row>
          <!-- 发货编号 -->
          <el-col :span="8">
            <el-form-item label="发货编号">
              <el-input v-model="formDetail.deliverId" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 销售订单编号 -->
          <el-col :span="8">
            <el-form-item label="销售订单编号">
              <el-input v-model="formDetail.saleOrderId" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 销售合同编号 -->
          <el-col :span="8">
            <el-form-item label="销售合同编号">
              <el-input v-model="formDetail.saleContractId" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 经办人 -->
          <el-col :span="8">
            <el-form-item label="经办人" prop="handledBy">
              <el-input v-model="formDetail.handledBy" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 发货日期 -->
          <el-col :span="8">
            <el-form-item label="发货日期" prop="deliverDate">
              <el-input v-model="formDetail.deliverDate" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 客户编号 -->
          <el-col :span="8">
            <el-form-item label="客户编号" prop="clientId">
              <el-input v-model="formDetail.clientId" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 客户姓名 -->
          <el-col :span="8">
            <el-form-item label="客户姓名" prop="clientName">
              <el-input v-model="formDetail.clientName" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 物料编号 -->
          <el-col :span="8">
            <el-form-item label="物料编号" prop="materialId">
              <el-input v-model="formDetail.materialId" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 物料名称 -->
          <el-col :span="8">
            <el-form-item label="物料名称" prop="materialName">
              <el-input v-model="formDetail.materialName" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 合同单价 -->
          <el-col :span="8">
            <el-form-item label="合同单价" prop="contractPrice">
              <el-input v-model="formDetail.contractPrice" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 计量单位 -->
          <el-col :span="8">
            <el-form-item label="计量单位" prop="measurementUnit">
              <el-input v-model="formDetail.measurementUnit" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 发货方式 -->
          <el-col :span="8">
            <el-form-item label="发货方式" prop="deliverMode">
              <!-- <template>
                <dict-tag :options="dict.type.purchasesale_deliver_mode" :value="formDetail.deliverMode"/>
              </template> -->
              <el-select
                v-model="formDetail.deliverMode"
                clearable
                :disabled="true"
                style="width: 200px"
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
              <el-input v-model="formDetail.warehouseCode" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 仓库名称 -->
          <el-col :span="8">
            <el-form-item label="仓库名称" prop="warehouseName">
              <el-input v-model="formDetail.warehouseName" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 发货数量 -->
          <el-col :span="8">
            <el-form-item label="发货数量" prop="deliverQuantity">
              <el-input v-model="formDetail.deliverQuantity" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 核算数量 -->
          <el-col :span="8">
            <el-form-item label="核算数量" prop="checkQuantity">
              <el-input v-model="formDetail.checkQuantity" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 核算单价 -->
          <el-col :span="8">
            <el-form-item label="核算单价" prop="checkPrice">
              <el-input v-model="formDetail.checkPrice" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 核算金额 -->
          <el-col :span="8">
            <el-form-item label="核算金额" prop="checkMoney">
              <el-input v-model="formDetail.checkMoney" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 货损数量 -->
          <el-col :span="8">
            <el-form-item label="货损数量" prop="cargoDamageQuantity">
              <el-input v-model="formDetail.cargoDamageQuantity" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 货损金额 -->
          <el-col :span="8">
            <el-form-item label="货损金额" prop="cargoDamageMoney">
              <el-input v-model="formDetail.cargoDamageMoney" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 运输方式 -->
          <el-col :span="8">
            <el-form-item label="运输方式" prop="transportMode">
              <!-- <template>
                <dict-tag :options="dict.type.purchasesale_transport_mode" :value="formDetail.transportMode"/>
              </template> -->
              <el-select
                v-model="formDetail.transportMode"
                clearable
                :disabled="true"
                style="width: 200px"
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
              <el-input v-model="formDetail.transportNumber" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 运输金额 -->
          <el-col :span="8">
            <el-form-item label="运输金额" prop="transportMoney">
              <el-input v-model="formDetail.transportMoney" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 其他金额 -->
          <el-col :span="8">
            <el-form-item label="其他金额" prop="otherMoney">
              <el-input v-model="formDetail.otherMoney" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 预期到货日期 -->
          <el-col :span="8">
            <el-form-item label="预期到货日期" prop="expectArrivalDate">
              <el-input v-model="formDetail.expectArrivalDate" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 要求到货日期 -->
          <el-col :span="8">
            <el-form-item label="要求到货日期" prop="requireArrivalDate">
              <el-input v-model="formDetail.requireArrivalDate" :disabled="true" style="width: 200px" />
            </el-form-item>
          </el-col>
          <!-- 账期（关联采购（销售）订单信息表的账期） -->
          <el-col :span="8">
            <el-form-item label="账期" prop="accountPeriod">
              <div style="margin-left: 10px; width: 60px">
                <el-input v-model="formDetail.accountPeriod" :disabled="true" />（天）</div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 备注 -->
          <el-col :span="24">
            <el-form-item label="备注" prop="deliverRemark">
              <el-input v-model="formDetail.deliverRemark" :disabled="true" style="width: 200px" />
            </el-form-item>
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
import { listPurchase } from "@/api/purchasesale/purchasesale";
import { listClient } from "@/api/masterdata/client";
import { listMaterialData } from "@/api/masterdata/material";
import { listWarehouse } from "@/api/masterdata/warehouse";

export default {
  name: "Deliver",
  dicts: ['purchasesale_purchase_type', 'purchasesale_belong_dept', 'masterdata_warehouse_measurement_unit', 
          'purchasesale_arrival_terms', 'purchasesale_settlement_method', 'purchasesale_receipt_order_status', 
          'purchasesale_transport_mode', 'purchasesale_deliver_mode', 'masterdata_warehouse_measurement_unit'],
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
        saleContractId: null,
        companyName: null,
        recordFlag: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        saleContractId: [
          { required: true, message: "销售合同编号不能为空", trigger: "change" }
        ],
        clientId: [
          { required: true, message: "客户编号不能为空", trigger: "blur" }
        ],
        clientName: [
          { required: true, message: "客户姓名不能为空", trigger: "blur" }
        ],
        materialId: [
          { required: true, message: "物料编号不能为空", trigger: "blur" }
        ],
        materialName: [
          { required: true, message: "物料名称不能为空", trigger: "blur" }
        ],
        warehouseCode: [
          { required: true, message: "仓库编号不能为空", trigger: "blur" }
        ],
        warehouseName: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" }
        ],
        handledBy: [
          { required: true, message: "经办人不能为空", trigger: "blur" }
        ],
        deliverDate: [
          { required: true, message: "发货日期不能为空", trigger: "blur" }
        ],
        contractPrice: [
          { required: true, message: "合同单价不能为空", trigger: "blur" }
        ],
        measurementUnit: [
          { required: true, message: "计量单位不能为空", trigger: "blur" }
        ],
        deliverMode: [
          { required: true, message: "发货方式不能为空", trigger: "change" }
        ],
      },
      isUpdate: false,
      formDetail: {},
      openDetail: false,
      // 采购合同编号选择用
      optionsSaleContract: [],
      listSaleContract: [],
      remoteLoadingSaleContract: false,
      // 客户编号选择用
      optionsClientName: [],
      listClientName: [],
      remoteLoadingSClientName: false,
      // 物料编号选择用
      optionsMaterialId: [],
      listMaterialId: [],
      remoteLoadingMaterialId: false,
      // 物料名称选择用
      optionsMaterialName: [],
      listMaterialName: [],
      remoteLoadingMaterialName: false,
      // 仓库编号选择用
      purchaseOptionsWarehouse: [],
      purchaseOrderListWarehouse: [],
      remoteLoadingWarehouse: false,
      // 仓库名称选择用
      optionsWarehouseName: [],
      ListWarehouseName: [],
      remoteLoadingWarehouseName: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 根据输入销售合同编号关键字，取得合同编号列表 */
    remoteMethodSaleContract(query) {
      if (query !== '') {
        this.remoteLoadingSaleContract = true;
        this.queryParams.saleContractId = query;
        this.queryParams.contractType = "S";
        console.log("select远程方法调用" + JSON.stringify(this.queryParams));
        listPurchase(this.queryParams).then(response => {
          this.remoteLoadingSaleContract = false;
          this.listSaleContract = response.rows;
          this.optionsSaleContract = response.rows.map(item => {
            return { value: `${item.contractId}`, label: `${item.contractId}` };
          }).filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        });
      } else {
        this.optionsSaleContract = [];
      }
    },
    /** 根据输入客户姓名关键字，取得客户姓名列表 */
    remoteMethodClientName(query) {
      if (query !== '') {
        this.remoteLoadingSClientName = true;
        this.queryParams.companyName = query;
        this.queryParams.recordFlag = 2;
        console.log("select远程方法调用" + JSON.stringify(this.queryParams));
        listClient(this.queryParams).then(response => {
          this.remoteLoadingSClientName = false;
          this.listClientName = response.rows;
          this.optionsClientName = response.rows.map(item => {
            return { value: `${item.baseId}`, label: `${item.companyName}` };
          }).filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        });
      } else {
        this.optionsClientName = [];
      }
    },
    /** 根据输入物料编号关键字，取得物料编号列表 */
    remoteMethodMaterialId(query) {
      if (query !== '') {
        this.remoteLoadingMaterialId = true;
        this.queryParams.materialId = query;
        console.log("取得物料名称远程方法调用查询参数：" + JSON.stringify(this.queryParams));
        listMaterialData(this.queryParams).then(response => {
          this.remoteLoadingMaterialId = false;
          this.listMaterialId = response.rows;
          this.optionsMaterialId = response.rows.map(item => {
            return { value: `${item.materialId}`, label: `${item.materialId}` };
          }).filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        });
      } else {
        this.optionsMaterialId = [];
      }
    },
    /** 根据输入物料名称关键字，取得物料名称列表 */
    remoteMethodMaterialName(query) {
      if (query !== '') {
        this.remoteLoadingMaterialName = true;
        this.queryParams.materialName = query;
        console.log("取得物料名称远程方法调用查询参数：" + JSON.stringify(this.queryParams));
        listMaterialData(this.queryParams).then(response => {
          this.remoteLoadingMaterialName = false;
          this.listMaterialName = response.rows;
          this.optionsMaterialName = response.rows.map(item => {
            return { value: `${item.materialId}`, label: `${item.materialName}` };
          }).filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        });
      } else {
        this.optionsClientName = [];
      }
    },
    /** 根据输入仓库编号关键字，取得订单编号列表 */
    remoteWarehouseCode(query) {
      if (query !== '') {
        this.remoteLoadingWarehouse = true;
        this.queryParams.warehouseCode = query;
        listWarehouse(this.queryParams).then(response => {
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
    /** 根据输入仓库名称关键字，取得仓库名称列表 */
    remoteWarehouseName(query) {
      if (query !== '') {
        this.remoteLoadingWarehouseName = true;
        this.queryParams.warehouseName = query;
        listWarehouse(this.queryParams).then(response => {
          this.remoteLoadingWarehouseName = false;
          console.log(JSON.stringify(response.rows));
          this.ListWarehouseName = response.rows;
          this.optionsWarehouseName = response.rows.map(item => {
            return { value: `${item.warehouseCode}`, label: `${item.warehouseName}` };
          }).filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        });
      } else {
        this.optionsWarehouseName = [];
      }
    },
    /** 客户姓名下拉列表框，选择值改变后回调方法 */
    selChangeClientName(selValue) {
      console.log("输入的客户姓名关键字是：" + selValue);

      let selClient = this.listClientName.find(item => {
        return item.baseId === selValue;
      });
      
      console.log("选择的客户数据是：" + JSON.stringify(selClient));

      this.form.clientId = selClient.baseId; // 客户编号
    },
     /** 物料编号下拉列表框，选择值改变后回调方法 */
    selChangeMaterialId(selValue) {

      let selMaterialData = this.listMaterialId.find(item => {
        return item.materialId == selValue;
      });

      this.form.materialName = selMaterialData.materialName; // 物料名称
    },
    /** 物料名称下拉列表框，选择值改变后回调方法 */
    selChangeMaterialName(selValue) {
      this.form.materialId = selValue; // 物料编号
    },
    /** 仓库编号下拉列表框，选择值改变后回调方法 */
    selChangeWarehouse(selValue) {
      console.log("选择的仓库编号是：" + selValue);

      let warehouse = this.purchaseOrderListWarehouse.find(item => {
        return item.warehouseCode === selValue;
      });

      this.form.warehouseName = warehouse.warehouseName; // 仓库名称
    },
    /** 仓库编号下拉列表框，选择值改变后回调方法 */
    selChangeWarehouseId(selValue) {
      console.log("选择的仓库编号是：" + selValue);
      this.form.warehouseCode = selValue; // 仓库名称
    },
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
            console.log("新增" + JSON.stringify(this.form))
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
