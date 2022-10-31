<!-- 合同管理 -->
<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-row>
        <!-- 合同编号 -->
        <el-col :span="8">
          <el-form-item label="合同编号" prop="contractId">
            <el-input
            v-model="queryParams.contractId"
            placeholder="请输入合同编号"
            clearable
            @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <!-- 合同名称 -->
        <el-col :span="8">
          <el-form-item label="合同名称" prop="contractName">
            <el-input
              v-model="queryParams.contractName"
              placeholder="请输入合同名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <!-- 客户名称 -->
        <el-col :span="8">
          <el-form-item label="客户名称" prop="oppositeCompanyName">
            <el-input
              v-model="queryParams.oppositeCompanyName"
              placeholder="请输入客户名称"
              clearable
              @keyup.enter.native="handleQuery" 
              style="width: 240px"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <!-- 合同总价 -->
        <el-col :span="16">
          <!-- 合同总价min -->
          <el-form-item label="合同总价" prop="contractTotal">
            <el-input
              v-model="queryParams.contractTotal"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <!-- 合同总价max -->
          <el-form-item label="~" prop="contractTotal" label-width="15px">
            <el-input
              v-model="queryParams.contractTotal"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <!-- 签约日期 -->
          <el-form-item label="签约日期" prop="signDate">
            <el-date-picker 
              clearable
              v-model="queryParams.signDate"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="请选择签约日期"
              style="width: 240px" >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24" align="right">
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
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
          v-hasPermi="['contract:contract:add']"
        >合同模板下载</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleContractSync"
        >合同同步</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contractList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="合同编号" align="center" prop="contractId" width="150" />
      <el-table-column label="签约日期" align="center" prop="signDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.signDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="合同名称" align="center" prop="contractName" width="280" :show-overflow-tooltip="true" />
      <el-table-column label="客户名称" align="center" prop="oppositeCompanyName" width="280" :show-overflow-tooltip="true" />
      <el-table-column label="合同总价" align="center" prop="contractTotal" width="80" />
      <el-table-column label="审批状态" align="center" prop="approvalStatus" width="80" />
      <el-table-column label="上传合同" align="center" class-name="small-padding fixed-width">
        <template>
          <!-- <el-upload 
            ref="field101"  
            :auto-upload="false"
            multiple 
            >
            <el-button size="mini" type="text" icon="el-icon-upload">上传</el-button>
          </el-upload> -->
          <el-button size="mini" type="text" icon="el-icon-upload">上传</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改合同管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <h3>合同内容详细</h3>
        <el-row>
          <el-col :span="8"><el-form-item label="货物名称">{{form.goodsName}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="合同类型">{{form.contractType}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="合同名称">{{form.contractName}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="8"><el-form-item label="合同编号">{{form.contractId}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="签约日期">{{form.signDate}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="交货日期">{{form.deliveryDate}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="8"><el-form-item label="我方单位名称">{{form.ourCompanyName}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="我方负责人">{{form.ourPrincipal}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="对方单位名称">{{form.oppositeCompanyName}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="8"><el-form-item label="对方负责人">{{form.oppositePrincipal}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="合同数量">{{form.contractQuantity}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="合同单价">{{form.contractPrice}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="8"><el-form-item label="合同总价">{{form.contractTotal}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="账期">{{form.accountingPeriod}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="交货方式">{{form.deliveryMethod}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="8"><el-form-item label="港口到厂运费">{{form.portToFactoryFare}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="港口到港口运费">{{form.portToPortFare}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="其他">{{form.contractOther}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="24"><el-form-item label="代理或合作方">{{form.contractAgent}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="24"><el-form-item label="备注">{{form.contractRemark}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="图片">
              <div class="demo-image__preview">
                <el-image 
                  style="width: 100px; height: 100px"
                  :src="url" 
                  :preview-src-list="srcList">
                </el-image>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="12"><el-form-item label="附件"></el-form-item></el-col>
        </el-row>
      </el-form>
      <el-divider />

      <el-row :gutter="10" class="mb8">
        <el-col :span="12">
          <h3 style="display:inline; margin-right: 15px">合同审批信息</h3>
          <el-switch
            v-model="showApproval"
            active-color="#13ce66">
          </el-switch>
        </el-col>
      </el-row>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px" v-show="showApproval">
        <el-row>
          <el-col :span="8"><el-form-item label="合同ID">{{form.contractId}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="审批编号">{{form.approvalId}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="标题">{{form.approvalTitle}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="8"><el-form-item label="审批状态">{{form.approvalStatus}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="审批结果">{{form.approvalResult}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="发起时间">{{form.launchTime}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="8"><el-form-item label="完成时间">{{form.completeTime}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="耗时">{{form.takeupTime}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="发起人工号">{{form.launchJobId}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="8"><el-form-item label="发起人ID">{{form.launchId}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="发起人姓名">{{form.launchName}}</el-form-item></el-col>
          <el-col :span="8"><el-form-item label="发起人部门">{{form.launchDepartment}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="8"><el-form-item label="审批人姓名">{{form.approvalName}}</el-form-item></el-col>
          <el-col :span="16"><el-form-item label="当前处理人姓名">{{form.processorName}}</el-form-item></el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="审批记录">
              <el-input
                type="textarea"
                :rows="6"
                v-model="form.approvalRecords">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
// import { listContract, getContract, delContract, addContract, updateContract } from "@/api/contract/contract";
import { syncContract } from "@/api/contract/contract";

export default {
  name: "Contract",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedContractAdditionalInfo: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 显示审批详细
      showApproval: true,
      // 总条数
      total: 1,
      // 合同管理表格数据
      contractList: [
        {
            contractId: "yw1202112221355",
            signDate: "2021/12/20",
            contractName: "辽宁盘锦正邦养殖有限公司饲料分公司",
            oppositeCompanyName: "辽宁盘锦正邦养殖有限公司饲料分公司",
            contractTotal: "23534",
            approvalStatus: "未完成"
        }
      ],
      // ${subTable.functionName}表格数据
      contractAdditionalInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractName: null,
        contractId: null,
        signDate: null,
        oppositeCompanyName: null,
        contractTotal: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        goodsId: [
          { required: true, message: "货物编号不能为空", trigger: "blur" }
        ],
        goodsName: [
          { required: true, message: "货物名称不能为空", trigger: "blur" }
        ],
        contractType: [
          { required: true, message: "合同类型不能为空", trigger: "change" }
        ],
        contractName: [
          { required: true, message: "合同名称不能为空", trigger: "blur" }
        ],
        contractId: [
          { required: true, message: "合同编号不能为空", trigger: "blur" }
        ],
      },
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      srcList: [
        'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg',
        'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg'
      ]
    };
  },
  created() {
    this.getList();
    console.log("created回调查询合同列表");
  },
  methods: {
    /** 查询合同管理列表 */
    getList() {
    //   this.loading = true;
    //   listContract(this.queryParams).then(response => {
    //     this.contractList = response.rows;
    //     this.total = response.total;
    //     this.loading = false;
    //   });
        this.loading = false;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        goodsId: null,
        goodsName: null,
        contractType: null,
        contractName: null,
        contractId: null,
        signDate: null,
        deliveryDate: null,
        ourCompanyName: null,
        ourPrincipal: null,
        oppositeCompanyName: null,
        oppositePrincipal: null,
        contractQuantity: null,
        contractPrice: null,
        contractTotal: null,
        accountingPeriod: null,
        deliveryMethod: null,
        portToFactoryFare: null,
        portToPortFare: null,
        contractOther: null,
        contractAgent: null,
        contractRemark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        bizVersion: null
      };
      this.contractAdditionalInfoList = [];
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
      this.ids = selection.map(item => item.goodsId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加合同管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const goodsId = row.goodsId || this.ids
      getContract(goodsId).then(response => {
        this.form = response.data;
        this.contractAdditionalInfoList = response.data.contractAdditionalInfoList;
        this.open = true;
        this.title = "修改合同管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.contractAdditionalInfoList = this.contractAdditionalInfoList;
          if (this.form.goodsId != null) {
            updateContract(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addContract(this.form).then(response => {
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
      const goodsIds = row.goodsId || this.ids;
      this.$modal.confirm('是否确认删除合同管理编号为"' + goodsIds + '"的数据项？').then(function() {
        return delContract(goodsIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** ${subTable.functionName}序号 */
    rowContractAdditionalInfoIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    /** ${subTable.functionName}添加按钮操作 */
    handleAddContractAdditionalInfo() {
      let obj = {};
      obj.uploadImagePath = "";
      obj.uplloadFilePath = "";
      this.contractAdditionalInfoList.push(obj);
    },
    /** ${subTable.functionName}删除按钮操作 */
    handleDeleteContractAdditionalInfo() {
      if (this.checkedContractAdditionalInfo.length == 0) {
        this.$modal.msgError("请先选择要删除的${subTable.functionName}数据");
      } else {
        const contractAdditionalInfoList = this.contractAdditionalInfoList;
        const checkedContractAdditionalInfo = this.checkedContractAdditionalInfo;
        this.contractAdditionalInfoList = contractAdditionalInfoList.filter(function(item) {
          return checkedContractAdditionalInfo.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleContractAdditionalInfoSelectionChange(selection) {
      this.checkedContractAdditionalInfo = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contract/contract/export', {
        ...this.queryParams
      }, `contract_${new Date().getTime()}.xlsx`)
    },
    /** 同步合同 */
    handleContractSync() {
      this.loading = true;
      syncContract(this.queryParams).then(response => {
        this.$modal.msgSuccess("同步合同成功");
        this.loading = false;
      });
    }
  }
};
</script>
