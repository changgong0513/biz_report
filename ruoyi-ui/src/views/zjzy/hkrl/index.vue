<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户编号" prop="hkKhbh">
        <el-input
          v-model="queryParams.hkKhbh"
          placeholder="请输入客户编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户名称" prop="hkKhmc">
        <el-input
          v-model="queryParams.hkKhmc"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="回款账号" prop="hkHkzh">
        <el-input
          v-model="queryParams.hkHkzh"
          placeholder="请输入回款账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="回款金额" prop="hkHkje">
        <el-input
          v-model="queryParams.hkHkje"
          placeholder="请输入回款金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="回款状态" prop="hkHkzt">
        <el-select v-model="queryParams.hkHkzt" placeholder="请选择回款状态" clearable>
          <el-option
            v-for="dict in dict.type.zjzy_hkrl_status"
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
          v-hasPermi="['system:hkrl:add']"
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
          v-hasPermi="['system:hkrl:edit']"
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
          v-hasPermi="['system:hkrl:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:hkrl:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="hkrlList" @selection-change="handleSelectionChange" 
      @row-dblclick="handleView">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="客户编号" align="center" prop="hkKhbh" />
      <el-table-column label="客户名称" align="center" prop="hkKhmc" />
      <el-table-column label="回款账号" align="center" prop="hkHkzh" />
      <el-table-column label="回款金额" align="center" prop="hkHkje" />
      <el-table-column label="认领金额" align="center" prop="hkrlJe" />
      <el-table-column label="回款状态" align="center" prop="hkHkzt">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.zjzy_hkrl_status" :value="scope.row.hkHkzt"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleHkrl(scope.row)"
          >认领</el-button>
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

    <!-- 添加或修改回款对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="回款单位编号" prop="hkKhbh">{{form.hkKhbh}}</el-form-item>
        <el-form-item label="回款单位名称" prop="hkKhmc">
          <el-select
            v-model="form.hkKhmc"
            filterable
            remote
            clearable
            reserve-keyword
            style="width: 300px;"
            placeholder="请输入回款单位名称关键字"
            :remote-method="remoteMethodClientName"
            :loading="remoteLoadingClientName"
            @change="selChangeClientName">
            <el-option
              v-for="item in optionsClientName"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="回款账号" prop="hkHkzh">{{form.hkHkzh}}</el-form-item>
        <el-form-item label="回款金额" prop="hkHkje">
          <el-input v-model="form.hkHkje" placeholder="请输入回款金额" style="width: 300px;" />
          <span style="margin: 10px;">元</span>
        </el-form-item>
        <el-form-item label="回款状态" prop="hkHkzt">
          <el-select v-model="form.hkHkzt" placeholder="请选择回款状态" style="width: 300px;">
            <el-option
              v-for="dict in dict.type.zjzy_hkrl_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 回款导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <!-- <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据
          </div> -->
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改回款认领对话框 -->
    <el-dialog :title="titleHkrl" :visible.sync="openHkrl" width="500px" append-to-body>
      <el-form ref="formHkrl" :model="formHkrl" :rules="rulesHkrl" label-width="110px">
        <el-form-item label="认领部门" prop="hkrlBmbh">
          <treeselect 
            v-model="formHkrl.hkrlBmbh" 
            :options="deptOptions" 
            :show-count="true" 
            placeholder="请选择认领部门" 
            style="width: 240px;" />
        </el-form-item>
        <el-form-item label="批次号" prop="hkrlPch">
          <el-select
            v-model="formHkrl.hkrlPch"
            filterable
            remote
            clearable
            reserve-keyword
            style="width: 240px;"
            placeholder="请输入回款批次号关键字"
            :remote-method="remoteMethodHkrlPch"
            :loading="remoteLoadingHkrlPch">
            <el-option
              v-for="item in optionsHkrlPch"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合同编号" prop="hkrlHtbh">
          <el-select
            v-model="formHkrl.hkrlHtbh"
            filterable
            remote
            clearable
            reserve-keyword
            placeholder="请输入合同编号关键字"
            style="width: 240px"
            :remote-method="remoteMethodHkrlHtbh"
            :loading="remoteLoadingHkrlHtbh">
            <el-option
              v-for="item in optionsHkrlHtbh"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="认领金额" prop="hkrlJe">
          <el-input ref="inputHkrlJe" v-model="formHkrl.hkrlJe" placeholder="请输入认领金额" style="width: 240px;" />
          <span style="margin: 10px;">元</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormHkrl">确 定</el-button>
        <el-button @click="cancelHkrl">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="titleHkrlDetail" :visible.sync="openHkrlDetail" 
      width="80%" append-to-body :close-on-click-modal="false">
      <el-table v-loading="loading" :data="listHkrlDetail">
      <el-table-column label="认领部门" align="center" prop="deptName" />
      <el-table-column label="批次号" align="center" prop="hkrlPch" />
      <el-table-column label="合同编号" align="center" prop="hkrlHtbh" />
      <el-table-column label="认领金额" align="center" prop="hkrlJe" />
    </el-table>
    <pagination
      v-show="hkrlDetailTotal > 0"
      :total="hkrlDetailTotal"
      :page.sync="hkrlDetailPageNum"
      :limit.sync="hkrlDetailPageSize"
      @pagination="getHkrlDetailList"
    />
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancelHkrlDetail">关 闭</el-button>
    </div>
    </el-dialog>
  </div>
</template>

<script>
import { listHkrl, getHkrl, delHkrl, addHk, updateHkrl, getHkrlHtbh, addHkrl, listHkrlDetail } from "@/api/zjzy/hkrl";
import { listClient } from "@/api/masterdata/client";
import { listPch } from "@/api/masterdata/pch";
import { getToken } from "@/utils/auth";
import { deptTreeSelect } from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Hkrl",
  dicts: ['zjzy_hkrl_status'],
  components: { Treeselect },
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
      total: 0,
      hkrlDetailTotal: 0,
      hkrlDetailPageNum: 1,
      hkrlDetailPageSize: 10,
      // 回款表格数据
      hkrlList: [],
      // 回款认领表格数据
      listHkrlDetail: [],
      // 弹出层标题
      title: "",
      titleHkrl: "",
      titleHkrlDetail: "",
      // 是否显示弹出层
      open: false,
      openHkrl: false,
      openHkrlDetail: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hkKhbh: null,
        hkKhmc: null,
        hkHkzh: null,
        hkHkje: null,
        hkHkzt: null,
      },
      // 表单参数
      form: {},
      formHkrl: {},
      // 表单校验
      rules: {
        hkKhbh: [
          { required: true, message: "客户编号不能为空", trigger: "blur" }
        ],
        hkKhmc: [
          { required: true, message: "客户名称不能为空", trigger: "blur" }
        ],
        hkHkzh: [
          { required: true, message: "回款账号不能为空", trigger: "blur" }
        ],
        hkHkje: [
          { required: true, message: "回款金额不能为空", trigger: "blur" }
        ],
        hkHkzt: [
          { required: true, message: "回款状态不能为空", trigger: "change" }
        ],
      },
      rulesHkrl: {
        hkrlBmbh: [
          { required: true, message: "回款认领部门不能为空", trigger: "blur" }
        ],
        hkrlPch: [
          { required: true, message: "回款认领批次号不能为空", trigger: "blur" }
        ],
        hkrlHtbh: [
          { required: true, message: "回款认领合同编号不能为空", trigger: "blur" }
        ],
        hkrlJe: [
          { required: true, message: "回款认领金额不能为空", trigger: "blur" }
        ]
      },
      upload: {
        // 是否显示弹出层（回款导入）
        open: false,
        // 弹出层标题（回款导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的回款数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/zjzy/hk/importData"
      },
      // 部门树选项
      deptOptions: undefined,
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 回款单位名称选择用
      optionsClientName: [],
      listClientName: [],
      remoteLoadingClientName: false,
      // 回款认领批次号选择用
      optionsHkrlPch: [],
      listHkrl: [],
      remoteLoadingHkrlPch: false,
      // 合同编号选择用
      optionsHkrlHtbh: [],
      listHkrlHtbh: [],
      remoteLoadingHkrlHtbh: false
    };
  },
  created() {
    this.getList();
    this.getDeptTree();
  },
  methods: {
    /** 根据输入客户姓名关键字，取得客户姓名列表 */
    remoteMethodClientName(query) {
      if (query !== '') {
        this.remoteLoadingClientName = true;
        this.queryParams.companyName = query;
        this.queryParams.recordFlag = 2;
        console.log("select远程方法调用" + JSON.stringify(this.queryParams));
        listClient(this.queryParams).then(response => {
          this.remoteLoadingClientName = false;
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
    /** 根据输入批次号关键字，取得批次号列表 */
    remoteMethodHkrlPch(query) {
      if (query !== '') {
        this.remoteLoadingHkrlPch = true;
        this.queryParams.pch = query;
        console.log("select远程方法调用" + JSON.stringify(this.queryParams));
        listPch(this.queryParams).then(response => {
          this.remoteLoadingHkrlPch = false;
          this.listHkrl = response.rows;
          this.optionsHkrlPch = response.rows.map(item => {
            return { value: `${item.pch}`, label: `${item.pch}` };
          }).filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        });
      } else {
        this.optionsHkrlPch = [];
      }
    },
    /** 根据输入合同编号关键字，取得合同编号列表 */
    remoteMethodHkrlHtbh(query) {
      if (query !== '') {
        this.remoteLoadingHkrlHtbh = true;
        console.log("select远程方法调用" + JSON.stringify(this.queryParams));
        getHkrlHtbh(this.queryParams).then(response => {
          this.remoteLoadingHkrlHtbh = false;
          this.listHkrlHtbh = response.rows;
          this.optionsHkrlHtbh = response.rows.map(item => {
            return { value: `${item.contractId}`, label: `${item.contractId}` };
          }).filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        });
      } else {
        this.optionsHkrlHtbh = [];
      }
    },
    /** 客户姓名下拉列表框，选择值改变后回调方法 */
    selChangeClientName(selValue) {
      console.log("输入的客户姓名关键字是：" + selValue);

      let selClient = this.listClientName.find(item => {
        return item.baseId === selValue;
      });
      
      console.log("选择的客户数据是：" + JSON.stringify(selClient));

      this.form.hkKhbh = selClient.baseId; // 客户编号
      this.form.hkKhmc = selClient.companyName; // 客户名称
      this.form.hkHkzh = selClient.accountNumber; // 账号
    },
    /** 查询回款认领列表 */
    getList() {
      this.loading = true;
      listHkrl(this.queryParams).then(response => {
        this.hkrlList = response.rows;
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
    cancelHkrl() {
      this.openHkrl = false;
      this.resetHkrl();
    },
    // 表单重置
    reset() {
      this.form = {
        hkId: null,
        hkKhbh: null,
        hkKhmc: null,
        hkHkzh: null,
        hkHkje: null,
        hkHkzt: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        bizVersion: null
      };
      this.resetForm("form");
    },
    // 表单重置
    resetHkrl() {
      this.formHkrl = {
        hkrlBmbh: null,
        hkrlPch: null,
        hkrlHtbh: null,
        hkrlJe: null,
        hkId: null,
        hkHkje: null
      };
      this.resetForm("formHkrl");
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
      this.ids = selection.map(item => item.hkId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加回款认领";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const hkId = row.hkId || this.ids
      getHkrl(hkId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改回款认领";
      });
    },
     /** 认领按钮操作 */
    handleHkrl(row) {
      this.resetHkrl();
      this.openHkrl = true;
      this.titleHkrl = "回款认领";
      this.formHkrl.hkId = row.hkId;
      this.formHkrl.hkHkje = row.hkHkje;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.hkId != null) {
            updateHkrl(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHk(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 回款认领提交按钮 */
    submitFormHkrl() {
      this.$refs["formHkrl"].validate(valid => {
        if (valid) {
          if (parseInt(this.formHkrl.hkHkje) < parseInt(this.formHkrl.hkrlJe) ) {
            this.$modal.msgError("回款认领金额超过回款金额，请重新输入！");
            return false;
          }

          addHkrl(this.formHkrl).then(response => {
            this.$modal.msgSuccess("回款认领成功");
            this.openHkrl = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const hkIds = row.hkId || this.ids;
      this.$modal.confirm('是否确认删除回款认领编号为"' + hkIds + '"的数据项？').then(function() {
        return delHkrl(hkIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/hkrl/export', {
        ...this.queryParams
      }, `hkrl_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "回款导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('/zjzy/hk/importTemplate', {}, `回款导入模板_${new Date().getTime()}.xlsx`)
    },
    /** 文件上传中处理 */
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    /** 文件上传成功处理 */
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 
    /** 提交上传文件 */
    submitFileForm() {
      this.$refs.upload.submit();
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // 回款认领详情取消按钮
    cancelHkrlDetail() {
      this.openHkrlDetail = false;
    },
    /** 查看回款认领详情 */ 
    handleView(row) {
      this.queryParams.hkId = row.hkId;
      this.getHkrlDetailList();
    },
    /** 查询回款认领明细列表 */
    getHkrlDetailList() {
      listHkrlDetail(this.queryParams).then(response => {
        console.log("回款编号" + this.queryParams.hkId);
        console.log(JSON.stringify(response.rows));
        this.listHkrlDetail = response.rows;
        this.hkrlDetailTotal = response.total;
        this.titleHkrlDetail = "查看回款认领详情";
        this.openHkrlDetail = true;
      });
    },
  }
};
</script>
