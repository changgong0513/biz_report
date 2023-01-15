<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="批次号" prop="pch">
              <el-input
                v-model="queryParams.pch"
                placeholder="请输入批次号"
                clearable
                style="width: 260px;"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="名称" prop="pchmc">
              <el-input
                v-model="queryParams.pchmc"
                placeholder="请输入名称"
                clearable
                style="width: 260px;"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所属部门" prop="belongDept">
              <treeselect v-model="queryParams.belongDept" 
                :options="deptOptions" 
                :show-count="true" 
                placeholder="请选择所属部门" 
                style="width: 260px;" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="所属年份" prop="ssnf">
              <el-date-picker clearable
                v-model="queryParams.ssnf"
                type="year"
                value-format="yyyy"
                placeholder="请选择所属年份"
                style="width: 260px;">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="状态" prop="pchzt">
              <el-select
                v-model="queryParams.pchzt"
                placeholder="请输入状态"
                clearable
                style="width: 260px;"
              >
                <el-option
                  v-for="dict in dict.type.masterdata_pch_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
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

    <el-table v-loading="loading" :data="pchList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="批次号" align="center" prop="pch" />
      <el-table-column label="名称" align="center" prop="pchmc" />
      <el-table-column label="所属年份" align="center" prop="ssnf" width="180">
        <template slot-scope="scope">
          <!-- <span>{{ parseTime(scope.row.ssnf, '{y}-{m}-{d}') }}</span> -->
          <span>{{ scope.row.ssnf }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="pchzt">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.masterdata_pch_status" :value="scope.row.pchzt"/>
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

    <!-- 添加或修改批次号管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="批次号" prop="pch">
          <el-input v-model="form.pch" placeholder="请输入批次号" />
        </el-form-item>
        <el-form-item label="名称" prop="pchmc">
          <el-input v-model="form.pchmc" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="所属部门" prop="belongDept">
          <treeselect v-model="form.belongDept" 
            :options="deptOptions" 
            :show-count="true" 
            placeholder="请选择所属部门" 
            style="width: 240px;" />
        </el-form-item>
        <el-form-item label="所属年份" prop="ssnf">
          <el-date-picker clearable
            v-model="form.ssnf"
            type="year"
            value-format="yyyy"
            placeholder="请选择所属年份">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="状态" prop="pchzt">
          <el-select
            v-model="queryParams.pchzt"
            placeholder="请输入状态"
            clearable
          >
            <el-option
              v-for="dict in dict.type.masterdata_pch_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPch, getPch, delPch, addPch, updatePch } from "@/api/masterdata/pch";
import { deptTreeSelect } from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Pch",
  dicts: ['masterdata_pch_status'],
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
      // 批次号管理表格数据
      pchList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pch: null,
        pchmc: null,
        ssnf: null,
        pchzt: null,
        bizVersion: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        pch: [
          { required: true, message: "批次号不能为空", trigger: "blur" }
        ]
      },
      // 部门树选项
      deptOptions: [],
      defaultProps: {
        children: "children",
        label: "label"
      }
    };
  },
  created() {
    this.getList();
    this.getDeptTree();
  },
  methods: {
    /** 查询批次号管理列表 */
    getList() {
      this.loading = true;
      listPch(this.queryParams).then(response => {
        this.pchList = response.rows;
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
        id: null,
        pch: null,
        pchmc: null,
        ssnf: null,
        pchzt: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        bizVersion: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加批次号管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPch(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改批次号管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePch(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPch(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除批次号管理编号为"' + ids + '"的数据项？').then(function() {
        return delPch(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('masterdata/pch/export', {
        ...this.queryParams
      }, `批次号管理_${new Date().getFullYear()}年${new Date().getMonth()+1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    }
  }
};
</script>
