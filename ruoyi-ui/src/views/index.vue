<template>
  <div class="dashboard-editor-container">
    <panel-group @handleSetLineChartData="handleSetLineChartData" 
      :purchaseCounts="purchaseCounts"
      :saleCounts="saleCounts"
      :zjzyTotal="calZjzyTotal"
      :hkrlTotal="hkrlTotal"
      :fkrlTotal="fkrlTotal"
      :zytjLxTotal="zytjLxTotal"
     />

    <!-- <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row> -->

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <!-- 父组件向子组件传递对象步骤2 -->
          <bar-chart :chart-data="barData" />
        </div>
      </el-col>
    </el-row>

    
  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'

import { getPurchaseContractCounts, getSaleContractCounts } from "@/api/purchasesale/purchasesale";
import { getHkrlTotal, getHkTotalByYearMonth, getHkrlTotalByBmbh } from "@/api/zjzy/hkrl";
import { getFkrlTotal, getZytjLxTotal, getFkrlTotalByBmbh, getZytjLxTotalByBmbh } from "@/api/zjzy/fkrl";
import Cookies from "js-cookie";

const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      lineChartData: lineChartData.newVisitis,
      purchaseCounts: 0, // 采购合同数量
      saleCounts: 0, // 销售合同数量
      zjzyTotal: 0, // 资金占用总额
      hkrlTotal: 0, // 回款认领总额
      fkrlTotal: 0, // 付款认领总额
      zytjLxTotal: 0, // 资金占用利息总金额
      // 父组件向子组件传递对象步骤1
      barData: {
        xAxisData: [],
        yAxisData: []
      }
    }
  },
  created() {
    this.handleSetPanelGroupPurchaseData();
    this.handleSetPanelGroupSaleData();
    this.handleSetPanelGroupHkrlData();
    this.handleSetPanelGroupFkrlData();
    this.handleSetPanelGroupHkTotalData();
    this.handleSetPanelGroupZytjLxData();
  },
  computed: {
    /** 资金占用 */
    calZjzyTotal: function () {
      return Number(this.fkrlTotal) - Number(this.hkrlTotal)
    },
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    },
    /** 取得采购合同总数 */
    handleSetPanelGroupPurchaseData() {
      getPurchaseContractCounts().then(response => {
        this.purchaseCounts = response.data;
      });
    },
    /** 取得销售合同总数 */
    handleSetPanelGroupSaleData() {
      getSaleContractCounts().then(response => {
        this.saleCounts = response.data;
      });
    },
    /** 取得回款总金额 */
    handleSetPanelGroupHkrlData() {
      const username = Cookies.get("username");
      if (username == "admin" || username == "zjltest") {
        getHkrlTotal().then(response => {
          this.hkrlTotal = response.data;
        });
      } else {
        getHkrlTotalByBmbh().then(response => {
          this.hkrlTotal = response.data;
        });
      }
    },
    /** 取得付款总金额 */
    handleSetPanelGroupFkrlData() {
      const username = Cookies.get("username");
      if (username == "admin" || username == "zjltest") {
        getFkrlTotal().then(response => {
          this.fkrlTotal = response.data;
        });
      } else {
        getFkrlTotalByBmbh().then(response => {
          this.fkrlTotal = response.data;
        });
      }
    },
    /** 取得各个部门和各个批次号资金占用利息总金额 */
    handleSetPanelGroupZytjLxData() {
      const username = Cookies.get("username");
      if (username == "admin" || username == "zjltest") {
        getZytjLxTotal().then(response => {
          this.zytjLxTotal = response.data;
        });
      } else {
        getZytjLxTotalByBmbh().then(response => {
          this.zytjLxTotal = response.data;
        });
      }
    },
    /** 根据年月分组，取得年月回款总金额 */
    handleSetPanelGroupHkTotalData() {
      getHkTotalByYearMonth().then(response => {
        console.log("根据年月分组，取得年月回款总金额" + JSON.stringify(response.rows))
        response.rows.forEach(element => {
          this.barData.xAxisData.push(element.hkYearMonth);
          this.barData.yAxisData.push(element.hkTotalJe);
        });
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
