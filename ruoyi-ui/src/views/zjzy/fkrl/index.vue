<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户名称" prop="fkKhmc">
        <el-input
          v-model="queryParams.fkKhmc"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="付款账号" prop="fkZh">
        <el-input
          v-model="queryParams.fkZh"
          placeholder="请输入付款账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="付款状态" prop="fkZt">
        <el-input
          v-model="queryParams.fkZt"
          placeholder="请输入付款状态"
          clearable
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
          icon="el-icon-refresh"
          size="mini"
          @click="handleFkSync"
        >同步</el-button>
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

    <el-table v-loading="loading" :data="fkList" @row-dblclick="handleView">
      <el-table-column label="客户名称" align="center" prop="fkKhmc" style="width: 350px;" />
      <el-table-column label="付款账号" align="center" prop="fkZh" />
      <el-table-column label="付款金额" align="center" prop="fkJe" />
      <el-table-column label="付款状态" align="center" prop="fkZt">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.zjzy_hkrl_status" :value="scope.row.fkZt"/>
        </template>
      </el-table-column>
      <el-table-column label="付款事由" align="center" prop="fkSy" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleFkrl(scope.row)"
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

    <!-- 添加或修改付款认领对话框 -->
    <el-dialog :title="titleFkrl" :visible.sync="openFkrl" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="认领部门" prop="fkrlBmbh">
          <treeselect 
            v-model="form.fkrlBmbh" 
            :options="deptOptions" 
            :show-count="true" 
            placeholder="请选择认领部门" 
            style="width: 240px;" />
        </el-form-item>
        <el-form-item label="批次号" prop="fkrlPch">
          <el-select
            v-model="form.fkrlPch"
            filterable
            remote
            clearable
            reserve-keyword
            style="width: 240px;"
            placeholder="请输入付款批次号关键字"
            :remote-method="remoteMethodFkrlPch"
            :loading="remoteLoadingFkrlPch">
            <el-option
              v-for="item in optionsFkrlPch"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合同编号" prop="fkrlHtbh">
          <el-select
            v-model="form.fkrlHtbh"
            filterable
            remote
            clearable
            reserve-keyword
            placeholder="请输入合同编号关键字"
            style="width: 240px"
            :remote-method="remoteMethodFkrlHtbh"
            :loading="remoteLoadingFkrlHtbh">
            <el-option
              v-for="item in optionsFkrlHtbh"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="认领金额" prop="fkrlJe">
          <el-input v-model="form.fkrlJe" placeholder="请输入认领金额" style="width: 240px;" />
          <span style="margin: 10px;">元</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormFkrl">确 定</el-button>
        <el-button @click="cancelFkrl">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="titleFkrlDetail" :visible.sync="openFkrlDetail" 
      width="80%" append-to-body :close-on-click-modal="false">
      <el-table v-loading="loading" :data="listFkrlDetail">
      <el-table-column label="认领部门" align="center" prop="deptName" />
      <el-table-column label="批次号" align="center" prop="fkrlPch" />
      <el-table-column label="合同编号" align="center" prop="fkrlHtbh" />
      <el-table-column label="认领金额" align="center" prop="fkrlJe" />
    </el-table>
    <pagination
      v-show="fkrlDetailTotal > 0"
      :total="fkrlDetailTotal"
      :page.sync="fkrlDetailPageNum"
      :limit.sync="fkrlDetailPageSize"
      @pagination="getFkrlDetailList"
    />
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancelFkrlDetail">关 闭</el-button>
    </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFk, addFk, updateFk, fkSync, addFkrl, listFkrlDetailList } from "@/api/zjzy/fkrl";
import { getHkrlHtbh } from "@/api/zjzy/hkrl";
import { listClient } from "@/api/masterdata/client";
import { listPch } from "@/api/masterdata/pch";
import { deptTreeSelect } from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Fk",
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
      fkrlDetailTotal: 0,
      fkrlDetailPageNum: 1,
      fkrlDetailPageSize: 10,
      // 付款表格数据
      fkList: [],
      // 付款认领表格数据
      listFkrlDetail: [],
      // 弹出层标题
      title: "",
      titleFkrl: "",
      titleFkrlDetail: "",
      // 是否显示弹出层
      open: false,
      openFkrl: false,
      openFkrlDetail: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fkKhmc: null,
        fkZh: null,
        fkZt: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        fkrlBmbh: [
          { required: true, message: "付款认领部门不能为空", trigger: "blur" }
        ],
        fkrlPch: [
          { required: true, message: "付款认领批次号不能为空", trigger: "blur" }
        ],
        fkrlHtbh: [
          { required: true, message: "付款认领合同编号不能为空", trigger: "blur" }
        ],
        fkrlJe: [
          { required: true, message: "付款认领金额不能为空", trigger: "blur" }
        ]
      },
      // 部门树选项
      deptOptions: undefined,
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 付款单位名称选择用
      optionsClientName: [],
      listClientName: [],
      remoteLoadingClientName: false,
      // 付款认领批次号选择用
      optionsFkrlPch: [],
      listFkrl: [],
      remoteLoadingFkrlPch: false,
      // 合同编号选择用
      optionsFkrlHtbh: [],
      listHkrlHtbh: [],
      remoteLoadingFkrlHtbh: false
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
    remoteMethodFkrlPch(query) {
      if (query !== '') {
        this.remoteLoadingFkrlPch = true;
        this.queryParams.pch = query;
        console.log("select远程方法调用" + JSON.stringify(this.queryParams));
        listPch(this.queryParams).then(response => {
          this.remoteLoadingFkrlPch = false;
          this.listHkrl = response.rows;
          this.optionsFkrlPch = response.rows.map(item => {
            return { value: `${item.pch}`, label: `${item.pch}` };
          }).filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        });
      } else {
        this.optionsFkrlPch = [];
      }
    },
    /** 根据输入合同编号关键字，取得合同编号列表 */
    remoteMethodFkrlHtbh(query) {
      if (query !== '') {
        this.remoteLoadingFkrlHtbh = true;
        console.log("select远程方法调用" + JSON.stringify(this.queryParams));
        getHkrlHtbh(this.queryParams).then(response => {
          this.remoteLoadingFkrlHtbh = false;
          this.listFkrlHtbh = response.rows;
          this.optionsFkrlHtbh = response.rows.map(item => {
            return { value: `${item.contractId}`, label: `${item.contractId}` };
          }).filter(item => {
            return item.label.toLowerCase()
              .indexOf(query.toLowerCase()) > -1;
          });
        });
      } else {
        this.optionsFkrlHtbh = [];
      }
    },
    /** 查询付款列表 */
    getList() {
      this.loading = true;
      listFk(this.queryParams).then(response => {
        
        response.rows.forEach(element => {
          if(element.fkKhmc.startsWith('["')){
            element.fkKhmc = element.fkKhmc.substr(2, element.length);
          }

          if(element.fkKhmc.endsWith('"]'))  {
            let index = element.fkKhmc.indexOf(']');
            element.fkKhmc = element.fkKhmc.substr(0, index - 1);
          }

          element.fkKhmc = element.fkKhmc.replaceAll('"', '');
        });
        this.fkList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        fkId: null,
        fkKhbh: null,
        fkKhmc: null,
        fkZh: null,
        fkJe: null,
        fkZt: null,
        fkSy: null,
        fkWlbh: null,
        fkWlmc: null,
        fkQtpzmc: null,
        fkZjyt: null,
        fkDj: null,
        fkSl: null,
        fkSkzh: null,
        fkKhyh: null,
        fkYhhh: null,
        fkYsfs: null,
        fkZcdh: null,
        fkBz: null
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
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.fkId != null) {
            updateFk(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFk(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('zjzy/fk/export', {
        ...this.queryParams
      }, `fk_${new Date().getTime()}.xlsx`)
    },
    /** 同步付款数据 */
    handleFkSync() {
      this.loading = true;
      fkSync(this.queryParams).then(response => {
        this.$modal.msgSuccess("同步付款数据成功");
        this.loading = false;
        this.getList();
      });
    },
    /** 认领按钮操作 */
    handleFkrl(row) {
      this.form = {
        fkrlBmbh: null,
        fhkrlPch: null,
        fkrlHtbh: null,
        fkrlJe: null
      };

      this.titleFkrl = "付款认领";
      this.form.fkrlJe = row.fkJe;
      this.openFkrl = true;
    },
    /** 付款认领提交按钮 */
    submitFormFkrl() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (parseInt(this.form.fkJe) < parseInt(this.form.fkrlJe) ) {
            this.$modal.msgError("付款认领金额超过付款金额，请重新输入！");
            return false;
          }

          addFkrl(this.form).then(response => {
            this.$modal.msgSuccess("付款认领成功");
            this.openHkrl = false;
            this.getList();
          });
        }
      });
    }, 
    /** 取消按钮 */
    cancelFkrl() {
      this.openFkrl = false;
      this.resetFkrl();
    },
    /** 表单重置 */
    resetFkrl() {
      this.form = {
        fkrlBmbh: null,
        fhkrlPch: null,
        fkrlHtbh: null,
        fkrlJe: null
      };
      this.resetForm("form");
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
     // 付款认领详情取消按钮
    cancelFkrlDetail() {
      this.openFkrlDetail = false;
    },
     /** 查看付款认领详情 */ 
    handleView(row) {
      this.queryParams.fkId = row.fkId;
      this.getFkrlDetailList();
    },
    /** 查询付款认领明细列表 */
    getFkrlDetailList() {
      listFkrlDetailList(this.queryParams).then(response => {
        console.log("付款编号" + this.queryParams.fkId);
        console.log(JSON.stringify(response.rows));
        this.listFkrlDetail = response.rows;
        this.fkrlDetailTotal = response.total;
        this.titleFkrlDetail = "查看付款认领详情";
        this.openFkrlDetail = true;
      });
    },
  }
};
</script>
