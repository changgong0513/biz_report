<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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

    <el-table v-loading="loading" :data="hkrlList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="hkId" />
      <el-table-column label="客户编号" align="center" prop="hkKhbh" />
      <el-table-column label="客户名称" align="center" prop="hkKhmc" />
      <el-table-column label="回款账号" align="center" prop="hkHkzh" />
      <el-table-column label="回款金额" align="center" prop="hkHkje" />
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
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:hkrl:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:hkrl:remove']"
          >删除</el-button>
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

    <!-- 添加或修改回款认领对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="回款账号" prop="hkHkzh">
          <el-input v-model="form.hkHkzh" placeholder="请输入回款账号" />
        </el-form-item>
        <el-form-item label="回款金额" prop="hkHkje">
          <el-input v-model="form.hkHkje" placeholder="请输入回款金额" />
        </el-form-item>
        <el-form-item label="回款状态" prop="hkHkzt">
          <el-select v-model="form.hkHkzt" placeholder="请选择回款状态">
            <el-option
              v-for="dict in dict.type.zjzy_hkrl_status"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="版本号" prop="bizVersion">
          <el-input v-model="form.bizVersion" placeholder="请输入版本号" />
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
import { listHkrl, getHkrl, delHkrl, addHkrl, updateHkrl } from "@/api/zjzy/hkrl";

export default {
  name: "Hkrl",
  dicts: ['zjzy_hkrl_status'],
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
      // 回款认领表格数据
      hkrlList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
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
      // 表单校验
      rules: {
        hkKhbh: [
          { required: true, message: "客户编号不能为空", trigger: "change" }
        ],
        hkKhmc: [
          { required: true, message: "客户名称不能为空", trigger: "change" }
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
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
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
            addHkrl(this.form).then(response => {
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
    }
  }
};
</script>
