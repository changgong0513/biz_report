<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <!-- 公司名称 -->
      <el-form-item label="公司名称" prop="companyName">
        <el-input
          v-model="queryParams.companyName"
          placeholder="请输入公司名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 联系人姓名 -->
      <el-form-item label="联系人姓名" prop="contactsName">
        <el-input
          v-model="queryParams.contactsName"
          placeholder="请输入联系人姓名"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- 收件人姓名 -->
      <el-form-item label="收件人姓名" prop="sjrxm">
        <el-input
          v-model="queryParams.sjrxm"
          placeholder="请输入收件人姓名"
          clearable
          style="width: 240px"
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
          v-hasPermi="['system:role:add']"
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
          v-hasPermi="['system:role:edit']"
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
          v-hasPermi="['system:role:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:role:export']"
        >批量导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table 
      v-loading="loading" 
      :data="supplierList" 
      @selection-change="handleSelectionChange"
      @row-dblclick="handleView">
      <el-table-column type="selection" align="center" width="55" />
      <el-table-column label="公司名称" align="center" prop="companyName" :show-overflow-tooltip="true" width="240" />
      <el-table-column label="联系人姓名" align="center" prop="contactsName" width="150" />
      <el-table-column label="联系人电话" align="center" prop="contactsMobile"  width="150" />
      <el-table-column label="发票类型" align="center" prop="invoiceType" width="150">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.masterdata_invoice_type" :value="scope.row.invoiceType"/>
        </template>
      </el-table-column>
      <el-table-column label="收件人姓名" align="center" prop="sjrxm" width="140" />
      <el-table-column label="收件人电话" align="center" prop="sjrdh"  width="140" />
      <el-table-column label="收件人地址" align="center" prop="sjrdz" :show-overflow-tooltip="true" width="240" />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改客户数据对话框 -->
    <el-dialog 
      :title="title" 
      :visible.sync="open" 
      width="90%" 
      append-to-body
      :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <h3>基本信息</h3>
        <el-row>
          <el-col :span="8">
            <el-form-item label="公司名称" prop="companyName">
              <el-input 
                v-model="form.companyName" 
                placeholder="请输入公司名称"
                style="width: 280px"
                maxlength="50"
                show-word-limit />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="注册城市" prop="registerCity">
              <el-select
                v-model="form.registerCity"
                placeholder="注册城市"
                clearable
                style="width: 280px"
              >
                <el-option
                  v-for="dict in dict.type.masterdata_register_city"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="注册资金" prop="registeredCapital">
              <el-input 
                v-model="form.registeredCapital" 
                placeholder="请输入注册资金" 
                style="width: 280px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="企业法人" prop="legalPerson">
              <el-input 
                v-model="form.legalPerson" 
                placeholder="请输入企业法人"
                style="width: 280px"
                maxlength="50"
                show-word-limit />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="传真号码" prop="faxNumber">
              <el-input 
                v-model="form.faxNumber" 
                placeholder="请输入传真号码xxx-xxxxxxxx" 
                style="width: 280px"
                maxlength="12"
                show-word-limit />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="邮编" prop="zipCode">
              <el-input 
                v-model="form.zipCode" 
                placeholder="请输入邮编" 
                style="width: 280px" 
                maxlength="6"
                show-word-limit />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="公司地址" prop="companyAdress">
              <el-input 
                v-model="form.companyAdress" 
                placeholder="请输入公司地址"
                maxlength="128"
                show-word-limit />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="公司网址" prop="companyWebsite">
              <el-input 
                v-model="form.companyWebsite" 
                placeholder="请输入公司网址"
                maxlength="128"
                show-word-limit />
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <h3>联系人信息</h3>
        <el-row>
          <el-col :span="8">
            <el-form-item label="姓名" prop="contactsName">
              <el-input 
                v-model="form.contactsName" 
                placeholder="请输入姓名" 
                style="width: 280px" 
                maxlength="50"
                show-word-limit />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机" prop="contactsMobile">
              <el-input 
                v-model="form.contactsMobile" 
                placeholder="请输入手机" 
                style="width: 280px" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="电子邮箱" prop="contactsEmail">
              <el-input 
                v-model="form.contactsEmail" 
                placeholder="请输入电子邮箱" 
                style="width: 280px"
                maxlength="128"
                show-word-limit />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="办公地点" prop="contactsOfficeLocation">
              <el-input 
                v-model="form.contactsOfficeLocation" 
                placeholder="请输入办公地点"
                maxlength="128"
                show-word-limit />
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <h3>收件人信息</h3>
        <el-row>
          <el-col :span="8">
            <el-form-item label="姓名" prop="sjrxm">
              <el-input 
                v-model="form.sjrxm" 
                placeholder="请输入姓名" 
                style="width: 280px" 
                maxlength="50"
                show-word-limit />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="电话" prop="sjrdh">
              <el-input 
                v-model="form.sjrdh" 
                placeholder="请输入电话" 
                style="width: 280px" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="地址" prop="sjrdz">
              <el-input 
                v-model="form.sjrdz" 
                placeholder="请输入地址" 
                style="width: 280px"
                maxlength="128"
                show-word-limit />
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <h3>账户信息</h3>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开户行" prop="depositBank">
              <el-select
                v-model="form.depositBank"
                placeholder="开户行"
                clearable
                style="width: 280px"
              >
                <el-option
                  v-for="dict in dict.type.masterdata_deposit_bank"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账号" prop="accountNumber">
              <el-input 
                v-model="form.accountNumber" 
                placeholder="请输入账号" 
                style="width: 280px" 
                maxlength="32"
                show-word-limit />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="税号" prop="taxNumber">
              <el-input 
                v-model="form.taxNumber" 
                placeholder="请输入税号" 
                style="width: 280px" 
                maxlength="32"
                show-word-limit />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发票类型" prop="invoiceType">
              <el-select
                v-model="form.invoiceType"
                placeholder="发票类型"
                clearable
                style="width: 280px"
              >
                <el-option
                  v-for="dict in dict.type.masterdata_invoice_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="parseInt(dict.value)"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="发票地址" prop="invoiceAddress">
              <el-input 
                v-model="form.invoiceAddress" 
                placeholder="请输入发票地址" 
                maxlength="128"
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

    <!-- 客户数据详细 -->
    <el-dialog title="客户数据详细" 
      :visible.sync="openDetail" 
      width="90%" 
      append-to-body 
      v-show="showDetail" 
      :close-on-click-modal="false">
      <el-form ref="formDetail" :model="formDetail" :rules="rules" label-width="100px">
        <h3>基本信息</h3>
        <el-row>
          <el-col :span="8">
            <el-form-item label="公司名称">{{formDetail.companyName}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="注册城市">
              <template>
                <dict-tag :options="dict.type.masterdata_register_city" :value="formDetail.registerCity"/>
              </template>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="注册资金">{{formDetail.registeredCapital}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="企业法人">{{formDetail.legalPerson}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="传真号码">{{formDetail.faxNumber}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="邮编">{{formDetail.zipCode}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="公司地址">{{formDetail.companyAdress}}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="公司网址">{{formDetail.companyWebsite}}</el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <h3>联系人信息</h3>
        <el-row>
          <el-col :span="8">
            <el-form-item label="姓名">{{formDetail.contactsName}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机">{{formDetail.contactsMobile}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="电子邮箱">{{formDetail.contactsEmail}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="办公地点">{{formDetail.contactsOfficeLocation}}</el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <h3>收件人信息</h3>
        <el-row>
          <el-col :span="8">
            <el-form-item label="姓名">{{formDetail.sjrxm}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="电话">{{formDetail.sjrdh}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="地址">{{formDetail.sjrdz}}</el-form-item>
          </el-col>
        </el-row>
        <el-divider />
        <h3>账户信息</h3>
        <el-row>
          <el-col :span="12">
            <el-form-item label="开户行">
              <template>
                <dict-tag :options="dict.type.masterdata_deposit_bank" :value="formDetail.depositBank"/>
              </template>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账号">{{formDetail.accountNumber}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="税号">{{formDetail.taxNumber}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发票类型">
              <template>
                <dict-tag :options="dict.type.masterdata_invoice_type" :value="formDetail.invoiceType"/>
              </template>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="发票地址">{{formDetail.invoiceAddress}}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="openDetail = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listClient, addClient, getClient, updateClient, delClient } from "@/api/masterdata/client";

export default {
  name: "Supplier",
  dicts: ['masterdata_register_city', 'masterdata_deposit_bank', 'masterdata_invoice_type'],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 
      openDetail: false,
      // 总条数
      total: 0,
      // 供应商表格数据
      supplierList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 
      openDetail: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recordFlag: 2, //客户标志
        companyName: undefined,
        contactsName: undefined,
        sjrxm: undefined
      },
      // 表单参数
      form: {},
      //
      formDetail: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        // 公司名称
        companyName: [
          { required: true, message: "公司名称不能为空", trigger: "blur" }
        ],
        // 公司地址
        companyAdress: [
          { required: true, message: "公司地址不能为空", trigger: "blur" }
        ],
        // 企业法人
        legalPerson: [
          { required: true, message: "企业法人不能为空", trigger: "blur" }
        ],
        // 联系人姓名
        contactsName: [
          { required: true, message: "联系人姓名不能为空", trigger: "blur" }
        ],
        // 联系人手机
        contactsMobile: [
          { required: true, message: "联系人手机不能为空", trigger: "blur" }
        ],
        // 开户行
        depositBank: [
          { required: true, message: "开户行不能为空", trigger: "blur" }
        ],
        // 账号
        accountNumber: [
          { required: true, message: "账号不能为空", trigger: "blur" }
        ],
        // 税号
        taxNumber: [
          { required: true, message: "税号不能为空", trigger: "blur" }
        ],
        // 发票地址
        invoiceAddress: [
          { required: true, message: "发票地址不能为空", trigger: "blur" }
        ],
        // 发票类型
        invoiceType: [
          { required: true, message: "发票类型不能为空", trigger: "blur" }
        ],
        contactsMobile: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    // console.log("created回调------取得供应商表格数据");
    this.getList();
  },
  methods: {
    /** 查询供应商列表 */
    getList() {
      this.loading = true;
      listClient(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.supplierList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.baseId)
      // console.log("@@@@@@" + this.ids);
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加客户";
      this.showDetail = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const baseId = row.baseId || this.ids
      getClient(baseId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改客户";
        this.showDetail = false;
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.baseId != undefined) {
            this.form.recordFlag =2;
            updateClient(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            // 客户
            this.form.recordFlag = 2;
            addClient(this.form).then(response => {
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
      const baseIds = row.baseId || this.ids;
      this.$modal.confirm('是否确认删除供应商记录？').then(function() {
        return delClient(baseIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/md/client/export', {
        ...this.queryParams
      }, `主数据管理_客户列表_${new Date().getFullYear()}年${new Date().getMonth()+1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    },
    /** 详细按钮操作 */
    handleView(row) {
      this.openDetail = true;
      this.formDetail = row;
    }
  }
};
</script>