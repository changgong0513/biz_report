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
          <el-form-item label="合同总价" prop="leftContractTotal">
            <el-input
              v-model="queryParams.leftContractTotal"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <!-- 合同总价max -->
          <el-form-item label="~" prop="rightContractTotal" label-width="15px">
            <el-input
              v-model="queryParams.rightContractTotal"
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
          icon="el-icon-refresh"
          size="mini"
          @click="handleContractSync"
        >合同同步</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
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
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contractList" @selection-change="handleSelectionChange"
      @row-dblclick="handleUpdate">
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
      <el-table-column label="审批状态" align="center" prop="contractStatus" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.contractmgr_contract_approval_status" :value="scope.row.contractStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="上传合同" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-upload
            ref="uploadRef"
            action=""
            accept=".xlsx, .xls, .docx, .doc, .pdf"
            :auto-upload="false"
            :on-change="(file, fileList) => { handleChange(file, fileList, scope.row.contractId) }"
            :show-file-list="false"
            :before-upload="beforeUpload"
          >
            <el-button type="text" size="mini">上传</el-button>
          </el-upload>
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

    <!-- 添加或修改合同内容对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="货物名称" prop="goodsName">
              <!-- <el-input v-model="form.goodsName" placeholder="请输入货物名称" style="width: 280px" /> -->
              <el-select
                v-model="form.goodsName"
                filterable
                remote
                clearable
                reserve-keyword
                style="width: 280px"
                placeholder="请输入货物名称关键字"
                :remote-method="remoteMethodGoodsName"
                :loading="remoteLoadingGoodsName">
                <el-option
                  v-for="item in contractOptionsGoodsName"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="合同类型" prop="contractType">
              <el-select
                v-model="form.contractType"
                placeholder="合同类型"
                clearable
                style="width: 280px"
              >
                <el-option
                  v-for="dict in dict.type.contractmgr_contract_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="合同名称" prop="contractName">
              <el-input v-model="form.contractName" placeholder="请输入合同名称"  style="width: 280px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="合同编号" prop="contractId">
              <el-input v-model="form.contractId" placeholder="请输入合同编号" :disabled="isUpdate" style="width: 280px" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="签约日期" prop="signDate">
              <el-date-picker clearable
                v-model="form.signDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择签约日期"
                style="width: 280px">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="交货日期" prop="deliveryDate">
              <el-date-picker clearable
                v-model="form.deliveryDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择交货日期"
                style="width: 280px">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="我方单位名称" prop="ourCompanyName">
              <el-input v-model="form.ourCompanyName" placeholder="请输入我方单位名称" style="width: 280px" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="我方负责人" prop="ourPrincipal">
              <el-input v-model="form.ourPrincipal" placeholder="请输入我方负责人" style="width: 280px" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="对方单位名称" prop="oppositeCompanyName">
              <el-input v-model="form.oppositeCompanyName" placeholder="请输入对方单位名称" style="width: 280px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="对方负责人" prop="oppositePrincipal">
              <el-input v-model="form.oppositePrincipal" placeholder="请输入对方负责人" style="width: 280px" />
            </el-form-item>
        </el-col>
          <el-col :span="8">
            <el-form-item label="合同数量" prop="contractQuantity">
              <el-input v-model="form.contractQuantity" placeholder="请输入合同数量" style="width: 280px" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="合同单价" prop="contractPrice">
              <el-input v-model="form.contractPrice" placeholder="请输入合同单价" style="width: 280px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="合同总价" prop="contractTotal">
              <el-input v-model="form.contractTotal" placeholder="请输入合同总价" style="width: 280px" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="交货方式" prop="deliveryMethod">
              <el-input v-model="form.deliveryMethod" placeholder="请输入交货方式" style="width: 280px" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="港口到厂运费" prop="portToFactoryFare">
              <el-input v-model="form.portToFactoryFare" placeholder="请输入港口到厂运费" style="width: 280px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="港口到港口运费" prop="portToPortFare">
              <el-input v-model="form.portToPortFare" placeholder="请输入港口到港口运费" style="width: 280px" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="其他" prop="contractOther">
              <el-input v-model="form.contractOther" placeholder="请输入其他" style="width: 280px" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="代理或合作方" prop="contractAgent">
              <el-input v-model="form.contractAgent" placeholder="请输入代理或合作方" style="width: 280px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="合同备注" prop="contractRemark">
              <el-input v-model="form.contractRemark" placeholder="请输入合同备注" style="width: 720px" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button type="success" @click="submitForm" v-show="this.isUpdate">保 存</el-button>
        <el-button type="warning" @click="submitForm" v-show="this.isUpdate" :disabled="form.constractIsExist == 1">生 成</el-button>
        <el-button @click="cancel(1)">取 消</el-button>
      </div>
    </el-dialog> 
  </div>
</template>

<script>
import { listContract, getContract, addContract, delContract, updateContract, syncContract, uploadFile, getContractAdditional, delteFile } from "@/api/contract/contract";
import { listMaterialData } from "@/api/masterdata/material";
import { download } from "@/utils/request";

export default {
  name: "Contract",
  dicts: ['contractmgr_contract_approval_status', 'contractmgr_contract_type'],
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
      contractList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示和合同详细弹出层
      openDetail: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contractName: null,
        contractId: null,
        signDate: null,
        oppositeCompanyName: null,
        leftContractTotal: null,
        rightContractTotal: null,
        materialName: null
      },
      // 表单参数
      form: {},
      // 合同详细表单
      formDetail: {},
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
      ],
      isUpdate: false,
      // 合同附件列表
      contractAdditionalList: [],
      remoteLoadingGoodsName: false,
      contractOptionsGoodsName: [],
      contractListGoodsName: []
    };
  },
  filters:{
    getDownloadFileName(value) {
      // let index = value.lastIndexOf("\\"); // 提交阿里云切换为Linux的路径分隔符/
      let index = value.lastIndexOf("/"); // 提交阿里云切换为Linux的路径分隔符/
      return value.substring(index + 1, index.length);
    }
  },
  created() {
    this.getList();
    console.log(process.env.VUE_APP_BASE_API);
  },
  methods: {
    /** 根据输入货物名称关键字，取得货物名称列表 */
    remoteMethodGoodsName(query) {
      if (query !== '') {
        this.remoteLoadingGoodsName = true;
        this.queryParams.materialName = query;
        console.log("取得货物名称远程方法调用查询参数：" + JSON.stringify(this.queryParams));
        listMaterialData(this.queryParams).then(response => {
          this.remoteLoadingGoodsName = false;
          this.contractListGoodsName = response.rows;
          this.contractOptionsGoodsName = response.rows.map(item => {
            return { value: `${item.materialId}`, label: `${item.materialName}` };
          }).filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        });
      } else {
        this.contractOptionsGoodsName = [];
      }
    },
    /** 查询合同管理列表 */
    getList() {
      this.loading = true;
      listContract(this.queryParams).then(response => {
        this.contractList = response.rows;
        console.log(response.rows);
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel(dialogType) {
      if (dialogType === 1) {
        this.open = false;
      } else {
        this.openDetail = false;
      }
      
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
        contractRemark: null
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
      this.title = "添加合同数据";
      this.isUpdate = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const contractId = row.contractId || this.ids
      getContract(contractId).then(response => {
        this.form = response.data;
        this.form.constractIsExist = row.constractIsExist;
        this.title = "修改合同数据";
        this.isUpdate = true;
      });

      getContractAdditional(row.contractId).then(response => {
        console.log(JSON.stringify(response.rows));
        this.contractAdditionalList = response.rows;
        this.open = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.isUpdate) {
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
      const contractId = row.contractId || this.ids;
      this.$modal.confirm('是否确认删除合同编号为"' + contractId + '"的数据项？').then(function() {
        return delContract(contractId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contract/mgr/export', {
        ...this.queryParams
      }, `合同管理_合同列表_${new Date().getFullYear()}年${new Date().getMonth()+1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    },
    /** 同步合同 */
    handleContractSync() {
      this.loading = true;
      syncContract(this.queryParams).then(response => {
        this.$modal.msgSuccess("同步合同成功");
        this.loading = false;
        this.getList();
      });
    },
    /** 查看合同数据 */ 
    // handleView(row) {
    //   getContractAdditional(row.contractId).then(response => {
    //       console.log(JSON.stringify(response.rows));
    //       this.contractAdditionalList = response.rows;
    //       this.formDetail = row;
    //       this.openDetail = true;
    //   });

    // },
    /** 文件上传 */
    handleChange(file, fileList, uploadContractId) {
      console.log("文件上传" + uploadContractId);
      if(file != null){
        let formData = new FormData();
        formData.append('file', file.raw)
        formData.append('uploadContractId', uploadContractId)
        uploadFile(formData).then(response => {
          console.log(response)
          this.$modal.msgSuccess("上传成功！");
          getContractAdditional(uploadContractId).then(response => {
              console.log(JSON.stringify(response.rows));
              this.contractAdditionalList = response.rows;
          });
        });
      }
    },
    // 上传预处理
    beforeUpload(file) {
      console.log("上传预处理file：" + file);
      // if (file.type.indexOf("image/") == -1) {
      //   this.$modal.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
      // } else if (file.type.indexOf(".xlsx") == -1 || file.type.indexOf(".xls") == -1) {
      //   this.$modal.msgError("文件格式错误，请上传图片类型,如：.xlsx，.xls后缀的文件。");
      // } else if (file.type.indexOf(".docx") == -1 || file.type.indexOf(".doc") == -1) {
      //   this.$modal.msgError("文件格式错误，请上传图片类型,如：.docx，.doc后缀的文件。");
      // }
      if (file.type.indexOf(".xlsx") == -1 || file.type.indexOf(".xls") == -1) {
        this.$modal.msgError("文件格式错误，请上传正确的类型,如：.xlsx，.xls, .docx，.doc, .pdf后缀的文件。");
      } else if (file.type.indexOf(".docx") == -1 || file.type.indexOf(".doc") == -1) {
        this.$modal.msgError("文件格式错误，请上传正确的类型,如：.xlsx，.xls, .docx，.doc, .pdf后缀的文件。");
      } else if (file.type.indexOf(".pdf") == -1 || file.type.indexOf(".PDF") == -1) {
        this.$modal.msgError("文件格式错误，请上传正确的类型,如：.xlsx，.xls, .docx，.doc, .pdf后缀的文件。");
      }
    },
    downLoadFile(filePath) {
      // let index = filePath.lastIndexOf("\\"); // 提交阿里云切换为Linux的路径分隔符/
      let index = filePath.lastIndexOf("/"); // 提交阿里云切换为Linux的路径分隔符/
      let fileName = filePath.substring(index + 1, index.length);

      let params = {
        resource:filePath
      }

      download("/common/download/resource", params, fileName);
    },
    delteFileByAdditionalId(contractId, additionalId) {
        delteFile(contractId, additionalId).then(response => {
          console.log(response)
          this.$modal.msgSuccess("删除附件成功！");
          console.log(JSON.stringify(response.rows));
          this.contractAdditionalList = response.rows;
      });
    }
  }
};
</script>

<style scoped>
  i:hover{
    cursor:pointer
  }
</style>
