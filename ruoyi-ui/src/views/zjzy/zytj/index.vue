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
            <el-form-item label="所属部门" prop="belongDept">
              <treeselect v-model="queryParams.belongDept" 
                :options="deptOptions" 
                :show-count="true" 
                placeholder="请选择所属部门" 
                style="width: 260px;" />
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="zytjList" @row-dblclick="handleView">
      <el-table-column label="所属部门" align="center" prop="tjBmmc" style="width: 350px;" />
      <el-table-column label="批次号" align="center" prop="tjPch" />
      <el-table-column label="付款总额" align="center" prop="tjFkje" />
      <el-table-column label="回款总额" align="center" prop="tjHkje" />
      <el-table-column label="占用总额" align="center" prop="tjZyje" />
      <el-table-column label="利息" align="center" prop="tjLx" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listZytj } from "@/api/zjzy/fkrl";
import { deptTreeSelect } from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "zytj",
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
      // 占用统计列表
      zytjList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pch: null,
        belongDept: null
      },
      // 表单参数
      form: {},
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
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    /** 查询占用统计列表 */
    getList() {
      this.loading = true;
      listZytj(this.queryParams).then(response => {
        this.zytjList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
    /** 导出按钮操作 */
    handleExport() {
      this.download('masterdata/pch/export', {
        ...this.queryParams
      }, `占用统计_${new Date().getFullYear()}年${new Date().getMonth()+1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    },
    /** 查看占用统计详情 */ 
    handleView(row) {
      console.log(JSON.stringify(row));
    }
  }
}
</script>