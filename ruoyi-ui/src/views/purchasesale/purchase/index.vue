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
      <el-table-column label="订单编号" align="center" prop="orderId" width="150" />
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
      <el-table-column label="供应商名称" align="center" prop="supplierName" width="240" :show-overflow-tooltip="true" />
      <el-table-column label="订单状态" align="center" prop="orderStatus" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.purchase_mgr_order_status" :value="scope.row.orderStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="物料名称" align="center" prop="materialName" width="100" :show-overflow-tooltip="true" />
      <el-table-column label="核算金额" align="center" prop="checkMoney" width="100" />
      <el-table-column label="完成率" align="center" prop="completionRate" width="100" />
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
          <!-- 订单编号 -->
          <el-col :span="8">
            <el-form-item label="订单编号" prop="orderId">
              <el-input v-model="form.orderId" 
                placeholder="请输入订单编号" 
                :disabled="this.isUpdate" 
                style="width: 240px"
                maxlength="32"
                show-word-limit />
            </el-form-item>
          </el-col>
          <!-- 采购类型 -->
          <el-col :span="8">
            <el-form-item label="采购类型" prop="purchaseType">
              <el-select
                v-model="form.purchaseType"
                placeholder="采购类型"
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.purchasesale_purchase_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <!-- 合同编号 -->
          <el-col :span="8">
            <el-form-item label="合同编号" prop="contractId">
              <el-input 
                v-model="form.contractId" 
                placeholder="请输入合同编号" 
                style="width: 240px"
                maxlength="32"
                show-word-limit />
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
          <!-- 所属部门 -->
          <el-col :span="8">
            <el-form-item label="所属部门" prop="belongDept">
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
        </el-row>
        <el-row>
          <!-- 物料名称 -->
          <el-col :span="8">
            <el-form-item label="物料名称" prop="materialName">
              <el-input 
                v-model="form.materialName" 
                placeholder="请输入物料名称" 
                style="width: 240px"
                maxlength="64"
                show-word-limit />
            </el-form-item>
          </el-col>
          <!-- 采购数量 -->
          <el-col :span="8">
            <el-form-item label="采购数量" prop="purchaseQuantity">
              <el-input v-model="form.purchaseQuantity" placeholder="请输入采购数量" style="width: 240px" />
            </el-form-item>
          </el-col>
          <!-- 供应商名称 -->
          <el-col :span="8">
            <el-form-item label="供应商名称" prop="supplierName">
              <el-input 
                v-model="form.supplierName" 
                placeholder="请输入供应商名称" 
                style="width: 240px"
                maxlength="128"
                show-word-limit />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!-- 单价 -->
          <el-col :span="8">
            <el-form-item label="单价" prop="unitPrice">
              <el-input v-model="form.unitPrice" placeholder="请输入单价" style="width: 240px" />
            </el-form-item>
          </el-col>
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
        </el-row>
        <el-row>
          <!-- 要求交货期 -->
          <el-col :span="8">
            <el-form-item label="要求交货期" prop="requiredDeliveryDate">
              <el-date-picker clearable
                v-model="form.requiredDeliveryDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择要求交货期"
                style="width: 240px">
              </el-date-picker>
            </el-form-item>
          </el-col>
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
        </el-row>
        <el-row>
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
          <!-- 是否开票 -->
          <el-col :span="16">
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
              <el-input 
                v-model="form.orderRemark" 
                type="textarea" 
                style="width: 90%" 
                maxlength="128"
                show-word-limit />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="2">
            <el-form-item label=""></el-form-item>
          </el-col>
          <el-col :span="22">
            <!-- 文件上传 start -->
            <div class="upload-file" style="height: 30px;">
              <el-upload
                multiple
                :action="uploadFileUrl"
                :headers="headers"
                :data="{uploadOrderId: form.orderId}"
                :before-upload="handleBeforeUpload"
                :on-error="handleUploadError"
                :on-exceed="handleExceed"
                :on-success="handleUploadSuccess"
                :file-list="fileList"
                :limit="limit"
                :show-file-list="false"
                accept=".xlsx, .xls, .docx, .doc, .pptx, .ppt, .pdf"
                class="upload-file-uploader"
                ref="fileUpload"
                v-show="form.orderId"
              >
                <!-- 上传按钮 -->
                <el-button size="mini" type="primary">选取文件</el-button>
                <!-- 上传提示 -->
                <div class="el-upload__tip" slot="tip" v-if="showTip">
                  请上传
                  <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
                  <template v-if="fileType"> 格式为 <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>
                  的文件
                </div>
              </el-upload>
              <!-- 文件列表 -->
              <transition-group class="upload-file-list el-upload-list el-upload-list--text" 
                name="el-fade-in-linear" tag="ul" style="width: 600px;">
                <li :key="file.url" 
                  class="el-upload-list__item ele-upload-list__item-content" 
                  v-for="(file, index) in fileList">
                  <el-link :href="`${baseUrl}${file.url}`" :underline="false" target="_blank">
                    <span class="el-icon-document" style="font-size: 12px;"> {{ getFileName(file.name) }} </span>
                  </el-link>
                  <div class="ele-upload-list__item-content-action">
                    <el-link :underline="false" @click="handleUploadDelete(index)" type="danger">删除</el-link>
                  </div>
                </li>
              </transition-group>
            </div>
            <!-- 文件上传 end -->
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 采购管理数据详细 -->
    <el-dialog title="采购管理数据详细" :visible.sync="openDetail" width="80%" append-to-body :close-on-click-modal="false">
      <el-form ref="formDetail" :model="formDetail" label-width="100px">
        <el-row>
          <!-- 订单编号 -->
          <el-col :span="8">
            <el-form-item label="订单编号">{{formDetail.orderId}}</el-form-item>
          </el-col>
          <!-- 采购类型 -->
          <el-col :span="8">
            <el-form-item label="采购类型">
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
          <!-- 采购数量 -->
          <el-col :span="8">
            <el-form-item label="采购数量" prop="purchaseQuantity">{{formDetail.purchaseQuantity}}</el-form-item>
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
              <dict-tag :options="dict.type.purchasesale_arrival_terms" :value="formDetail.arrivalTerms" style="display: inline" />
              {{formDetail.arrivalTermsValue}}（天）<!-- 到账条件值 -->
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
        <el-row>
          <el-col :span="2">
            <el-form-item label="附件" style="padding-top: 8px;"></el-form-item>
          </el-col>
          <el-col :span="22">
            <!-- 文件上传 start -->
            <div class="upload-file" style="height: 30px;">
              <!-- 文件列表 -->
              <transition-group class="upload-file-list el-upload-list el-upload-list--text" 
                name="el-fade-in-linear" tag="ul" style="width: 600px;">
                <li :key="file.url" 
                  class="el-upload-list__item ele-upload-list__item-content" 
                  v-for="(file) in fileListDetail">
                  <el-link :href="`${baseUrl}${file.url}`" :underline="false" target="_blank">
                    <span class="el-icon-document" style="font-size: 12px;"> {{ getFileName(file.name) }} </span>
                  </el-link>
                </li>
              </transition-group>
            </div>
            <!-- 文件上传 end -->
          </el-col>
        </el-row>
        <el-divider />
        <h3>收货明细</h3>
        <el-row>
          <el-table v-loading="loading" :data="this.receiptList">
            <el-table-column label="收货单编号" align="center" prop="receiptId" width="150" />
            <el-table-column label="收货日期" align="center" prop="receiptDate" width="100">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.receiptDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="仓库名称" align="center" prop="warehouseName" width="100" :show-overflow-tooltip="true" />
            <el-table-column label="批次号" align="center" prop="batchNo" width="240" :show-overflow-tooltip="true" />
            <el-table-column label="运输方式" align="center" prop="transportMode" width="100">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.purchasesale_transport_mode" :value="scope.row.transportMode"/>
              </template>
            </el-table-column>
            <el-table-column label="运输单号" align="center" prop="transportNumber" width="100" :show-overflow-tooltip="true" />
            <el-table-column label="核算金额" align="center" prop="checkMoney" width="100" />
            <el-table-column label="货损金额" align="center" prop="cargoDamageMoney" width="100" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="viewReceipt(scope.row)"
                  v-hasPermi="['kcdb:kcdb:edit']"
                >查看</el-button>
              </template>
            </el-table-column>
          </el-table>
          
          <pagination
            v-show="totalReceipt>0"
            :total="totalReceipt"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getReceiptList"
          />
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDetail">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import { listPurchase, getPurchase, delPurchase, addPurchase, updatePurchase, deleteUploadFile, getOrderAdditional } from "@/api/purchasesale/purchasesale";
import { listReceipt } from "@/api/purchasesale/receipt";
import { getToken } from "@/utils/auth";



export default {
  name: "Purchase",
  dicts: ['purchasesale_purchase_type', 'purchasesale_belong_dept', 'masterdata_warehouse_measurement_unit', 
          'purchasesale_arrival_terms', 'purchasesale_settlement_method', 'contractmgr_contract_approval_status', 
          'purchase_mgr_order_status', 'purchasesale_transport_mode'],
  // 文件上传用
  props: {
    // 值
    value: [String, Object, Array],
    // 数量限制
    limit: {
      type: Number,
      default: 5,
    },
    // 大小限制(MB)
    fileSize: {
      type: Number,
      default: 5,
    },
    // 文件类型, 例如['png', 'jpg', 'jpeg']
    fileType: {
      type: Array,
      default: () => ["doc", 'docx', "xls", 'xlsx', "ppt", 'pptx', "txt", "pdf"],
    },
    // 是否显示提示
    isShowTip: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      number: 0,
      uploadList: [],
      baseUrl: process.env.VUE_APP_BASE_API,
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/purchase/mgr/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken()
      },
      fileList: [],
      fileListDetail: [],
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
      totalReceipt: 1,
      // 采购收货销售发货管理表格数据
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
      rules: {
        orderId: [
          { required: true, message: "订单编号不能为空", trigger: "blur" }
        ],
        purchaseType: [
          { required: true, message: "采购类型不能为空", trigger: "change" }
        ],
        contractId: [
          { required: true, message: "合同编号不能为空", trigger: "blur" }
        ],
        handledBy: [
          { required: true, message: "经办人不能为空", trigger: "blur" }
        ],
        belongDept: [
          { required: true, message: "所属部门不能为空", trigger: "blur" }
        ],
        businessDate: [
          { required: true, message: "业务日期不能为空", trigger: "blur" }
        ],
        materialName: [
          { required: true, message: "物料名称不能为空", trigger: "blur" }
        ],
        purchaseQuantity: [
          { required: true, message: "采购数量不能为空", trigger: "blur" }
        ],
        supplierName: [
          { required: true, message: "供应商名称不能为空", trigger: "blur" }
        ],
        unitPrice: [
          { required: true, message: "单价不能为空", trigger: "blur" }
        ],
        meteringUnit: [
          { required: true, message: "计量单位不能为空", trigger: "blur" }
        ],
        arrivalDate: [
          { required: true, message: "预计到货期不能为空", trigger: "blur" }
        ],
        requiredDeliveryDate: [
          { required: true, message: "要求交货期不能为空", trigger: "blur" }
        ],
        accountPeriod: [
          { required: true, message: "账期不能为空", trigger: "blur" }
        ],
        arrivalTerms: [
          { required: true, message: "到账条件不能为空", trigger: "blur" }
        ],
        arrivalTermsValue: [
          { required: true, message: "到账条件值不能为空", trigger: "blur" }
        ],
        settlementMethod: [
          { required: true, message: "结算方式不能为空", trigger: "blur" }
        ]
      },
      isUpdate: false,
      formDetail: {},
      openDetail: false,
      fileList: [],
      selRow: {},
      receiptList: []
    };
  },
  created() {
    this.getList();
  },
  // 文件上传用
  watch: {
    value: {
      handler(val) {
        if (val) {
          let temp = 1;
          // 首先将值转为数组
          const list = Array.isArray(val) ? val : this.value.split(',');
          // 然后将数组转为对象数组
          this.fileList = list.map(item => {
            if (typeof item === "string") {
              item = { name: item, url: item };
            }
            item.uid = item.uid || new Date().getTime() + temp++;
            return item;
          });
        } else {
          this.fileList = [];
          return [];
        }
      },
      deep: true,
      immediate: true
    }
  },
  computed: {
    // 是否显示提示
    showTip() {
      return this.isShowTip && (this.fileType || this.fileSize);
    },
  },
  methods: {
    /** 查询采购收货销售发货管理列表 */
    getList() {
      this.loading = true;
      this.queryParams.contractType = "P";
      listPurchase(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        
        this.purchaseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getReceiptList() {
      listReceipt(this.selRow).then(response => {
        console.log(JSON.stringify(response.rows));
        this.receiptList = response.rows;
        this.totalReceipt = response.total;
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
    this.selRow = {},
    this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        orderId: null,
        purchaseOrderId: null,
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
      this.title = "添加采购订单数据";
      this.form.isInvoicing = "1";
      this.isUpdate = false;
      this.fileList = [];
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const contractId = row.contractId || this.ids
      getPurchase(contractId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改采购订单数据";
        this.isUpdate = true;
        this.fileList = [];
        getOrderAdditional(this.form.orderId).then(response => {
          console.log(JSON.stringify(response.rows));
          response.rows.forEach(element => {
            this.fileList.push({ name: element.uplloadFilePath, 
              url: element.uplloadFilePath });
          });
        });
      });
    },
    /** 提交按钮 */
    submitForm() {
      console.log("提交按钮");
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
      this.$modal.confirm('是否确认删除采购数据项？').then(function() {
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
      }, `采购管理_${new Date().getFullYear()}年${new Date().getMonth()+1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    },
     /** 查看合同数据 */ 
    handleView(row) {
      this.formDetail = row;
      this.fileListDetail = [];
      this.selRow = row;
      this.selRow.purchaseOrderId = this.formDetail.orderId;

      getOrderAdditional(this.formDetail.orderId).then(response => {
        console.log(JSON.stringify(response.rows));
        response.rows.forEach(element => {
          this.fileListDetail.push({ name: element.uplloadFilePath, 
            url: element.uplloadFilePath });
        });
      });

      this.getReceiptList(this.selRow);
      this.openDetail = true;
    },
    // 文件上传用
    // 上传前校检格式和大小
    handleBeforeUpload(file) {
      let isValidSuccess = true;

      // this.$refs["form"].validate(valid => {
      //   if (!valid) {
      //     isValidSuccess = false;
      //   }
      // });

      console.log("表单校验结果" + isValidSuccess);
      if (!isValidSuccess) {
        this.$modal.msgError(`添加上传文件时，表单必填字段不能为空，否则无法上传文件，请检查!`);
        this.$modal.closeLoading();
        return false;
      }

      // 校检文件类型
      if (this.fileType) {
        let fileExtension = "";
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
        }
        const isTypeOk = this.fileType.some((type) => {
          if (file.type.indexOf(type) > -1) return true;
          if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          return false;
        });
        if (!isTypeOk) {
          this.$modal.msgError(`文件格式不正确, 请上传${this.fileType.join("/")}格式文件!`);
          return false;
        }
      }
      // 校检文件大小
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$modal.msgError(`上传文件大小不能超过 ${this.fileSize} MB!`);
          return false;
        }
      }
      this.$modal.loading("正在上传文件，请稍候...");
      this.number++;
      return true;
    },
    // 上传失败
    handleUploadError(err) {
      this.$modal.msgError("上传图片失败，请重试");
      this.$modal.closeLoading()
    },
    // 文件个数超出
    handleExceed() {
      this.$modal.msgError(`上传文件数量不能超过 ${this.limit} 个!`);
    },
    // 上传成功回调
    handleUploadSuccess(res, file) {
      if (res.code === 200) {
        this.uploadList.push({ name: res.fileName, url: res.fileName });
        this.uploadedSuccessfully();
      } else {
        this.number--;
        this.$modal.closeLoading();
        this.$modal.msgError(res.msg);
        this.$refs.fileUpload.handleRemove(file);
        this.uploadedSuccessfully();
      }
    },
    // 删除文件
    handleUploadDelete(index) {
      deleteUploadFile(this.listToString(this.fileList)).then(response => {
        this.fileList.splice(index, 1);
        this.$emit("input", this.listToString(this.fileList));
      });
    },
    // 上传结束处理
    uploadedSuccessfully() {
      if (this.number > 0 && this.uploadList.length === this.number) {
        this.fileList = this.fileList.concat(this.uploadList);
        this.uploadList = [];
        this.number = 0;
        this.$emit("input", this.listToString(this.fileList));
        this.$modal.closeLoading();
      }
    },
    // 获取文件名称
    getFileName(name) {
      if (name.lastIndexOf("/") > -1) {
        return name.slice(name.lastIndexOf("/") + 1);
      } else {
        return "";
      }
    },
    // 对象转成指定字符串分隔
    listToString(list, separator) {
      let strs = "";
      separator = separator || ",";
      for (let i in list) {
        strs += list[i].url + separator;
      }
      return strs != '' ? strs.substr(0, strs.length - 1) : '';
    },
    // 库区维护
    viewReceipt(row) {
      // console.log("选择的仓库数据: " + JSON.stringify(row));
      this.$router.push({ path: "/cgmgr/shmgr", query: { selRow: row } });
    }
  }
};
</script>

<style scoped lang="scss">
.upload-file-uploader {
  margin-bottom: 5px;
}
.upload-file-list .el-upload-list__item {
  border: 1px solid #e4e7ed;
  line-height: 2;
  margin-bottom: 10px;
  position: relative;
}
.upload-file-list .ele-upload-list__item-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: inherit;
}
.ele-upload-list__item-content-action .el-link {
  margin-right: 10px;
}
</style>
