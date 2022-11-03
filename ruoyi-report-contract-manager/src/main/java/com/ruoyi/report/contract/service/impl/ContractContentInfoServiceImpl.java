package com.ruoyi.report.contract.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dingtalkworkflow_1_0.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.purchase.sale.domain.PurchaseSaleOrderInfo;
import com.ruoyi.purchase.sale.mapper.PurchaseSaleOrderInfoMapper;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.report.contract.mapper.ContractContentInfoMapper;
import com.ruoyi.report.contract.domain.ContractContentInfo;
import com.ruoyi.report.contract.service.IContractContentInfoService;

/**
 * 合同管理Service业务层处理
 * 
 * @author changgong
 * @date 2022-10-30
 */
@Service
public class ContractContentInfoServiceImpl implements IContractContentInfoService 
{
    @Autowired
    private ContractContentInfoMapper contractContentInfoMapper;

    @Autowired
    private PurchaseSaleOrderInfoMapper purchaseSaleOrderInfoMapper;

    private static com.aliyun.dingtalkworkflow_1_0.Client client = null;

    static {
        try {
            client = createClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询合同管理
     *
     * @param contactId 合同管理主键
     * @return 合同管理
     */
    @Override
    public ContractContentInfo selectContractContentInfoByContractId(String contactId)
    {
        return contractContentInfoMapper.selectContractContentInfoByContractId(contactId);
    }

    /**
     * 查询合同管理列表
     *
     * @param contractContentInfo 合同管理
     * @return 合同管理
     */
    @Override
    public List<ContractContentInfo> selectContractContentInfoList(ContractContentInfo contractContentInfo)
    {
        return contractContentInfoMapper.selectContractContentInfoList(contractContentInfo);
    }

    /**
     * 新增合同管理
     *
     * @param contractContentInfo 合同管理
     * @return 结果
     */
    @Override
    public int insertContractContentInfo(ContractContentInfo contractContentInfo)
    {
        contractContentInfo.setCreateTime(DateUtils.getNowDate());
        return contractContentInfoMapper.insertContractContentInfo(contractContentInfo);
    }

    /**
     * 修改合同管理
     *
     * @param contractContentInfo 合同管理
     * @return 结果
     */
    @Override
    public int updateContractContentInfo(ContractContentInfo contractContentInfo)
    {
        contractContentInfo.setUpdateTime(DateUtils.getNowDate());
        return contractContentInfoMapper.updateContractContentInfo(contractContentInfo);
    }

    /**
     * 批量删除合同管理
     *
     * @param contractId 需要删除的合同管理主键
     * @return 结果
     */
    @Override
    public int deleteContractContentInfoByContractIds(String[] contractId)
    {
        return contractContentInfoMapper.deleteContractContentInfoByContractIds(contractId);
    }

    /**
     * 删除合同管理信息
     *
     * @param goodsId 合同管理主键
     * @return 结果
     */
    @Override
    public int deleteContractContentInfoByContractId(String goodsId)
    {
        return contractContentInfoMapper.deleteContractContentInfoByContractId(goodsId);
    }

    /**
     * 从钉钉同步合同数据
     *
     * @return 结果
     */
    @Override
    public int syncContractContentInfo() throws Exception {
        // 获取当前企业钉钉访问令牌
        String accessToken = getDingTalkAccessToken();

        // 获取当前企业所有可管理的表单
        // getCurrentEnterpriseAllMgrForm(accessToken);

        // 获取审批实例ID列表(测试数据用)
        List<String> ids = getContractForDemo(accessToken);
        int size = ids.size();
        for (int i = 0; i < size; i++) {
            String id = ids.get(i);
            System.out.println("审批实例ID：" + id);
            ContractContentInfo contract = getContractData(accessToken, id);

            if (contract != null) {
                contract.setBizVersion(1L);
                contract.setCreateTime(DateUtils.getNowDate());
                contract.setUpdateTime(DateUtils.getNowDate());
                contract.setCreateBy(SecurityUtils.getUsername());
                contract.setUpdateBy(SecurityUtils.getUsername());
                System.out.println("------从钉钉同步合同数据：" + contract);

                ContractContentInfo selectContract = null;
                selectContract = contractContentInfoMapper.selectContractContentInfoByContractId(contract.getContractId());
                if (selectContract == null) {
                    contractContentInfoMapper.insertContractContentInfo(contract);
                } else {
                    System.out.println("------从钉钉同步的合同编号为：" + selectContract.getContractId() + "已经同步完成");
                }

                PurchaseSaleOrderInfo purchaseInfo = purchaseSaleOrderInfoMapper
                        .selectPurchaseSaleOrderInfoByContractId(contract.getContractId());
                if (purchaseInfo == null) {
                    purchaseInfo = new PurchaseSaleOrderInfo();
                    purchaseInfo.setOrderId(contract.getContractId());
                    purchaseInfo.setBizVersion(1L);
                    purchaseInfo.setCreateTime(DateUtils.getNowDate());
                    purchaseInfo.setUpdateTime(DateUtils.getNowDate());
                    purchaseInfo.setCreateBy(SecurityUtils.getUsername());
                    purchaseInfo.setUpdateBy(SecurityUtils.getUsername());

                    fillPurchaseInfoFromContract(contract, purchaseInfo);

                    purchaseSaleOrderInfoMapper.insertPurchaseSaleOrderInfo(purchaseInfo);
                } else {
                    fillPurchaseInfoFromContract(contract, purchaseInfo);
                    purchaseSaleOrderInfoMapper.updatePurchaseSaleOrderInfo(purchaseInfo);
                }


            }
        }

        return 1;
    }

    private static com.aliyun.dingtalkworkflow_1_0.Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkworkflow_1_0.Client(config);
    }

    /**
     * 获取当前企业钉钉访问令牌
     *
     * @return 结果
     */
    private String getDingTalkAccessToken() throws Exception {
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey("dinghdigcsx1pnri4ar2");
        request.setAppsecret("oXvRZJ_6SrD8TfVHM_wOBByF4_Dv7njrsIuR-mNMrq3ExoVnK_CB-pUSATEo1pk5");
        request.setHttpMethod("GET");

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenResponse response = client.execute(request);
        JSONObject respJSONObject = JSONObject.parseObject(response.getBody());
        String accessToken = respJSONObject.getString("access_token");

        return accessToken;
    }

    /**
     * 获取当前企业所有可管理的表单
     *
     * @param accessToken 钉钉访问令牌
     */
    private void getCurrentEnterpriseAllMgrForm(final String accessToken) throws Exception {
        com.aliyun.dingtalkworkflow_1_0.Client client = createClient();
        GetManageProcessByStaffIdHeaders getManageProcessByStaffIdHeaders = new GetManageProcessByStaffIdHeaders();
        getManageProcessByStaffIdHeaders.xAcsDingtalkAccessToken = accessToken;
        GetManageProcessByStaffIdRequest getManageProcessByStaffIdRequest = new GetManageProcessByStaffIdRequest()
                .setUserId("282350193529366375");

        try {
            GetManageProcessByStaffIdResponse resp = client
                    .getManageProcessByStaffIdWithOptions(getManageProcessByStaffIdRequest,
                            getManageProcessByStaffIdHeaders, new RuntimeOptions());

            int size = resp.getBody().getResult().size();
            for (int i = 0; i < size; i++) {
                GetManageProcessByStaffIdResponseBody.GetManageProcessByStaffIdResponseBodyResult responseBodyResult = null;
                responseBodyResult = resp.getBody().getResult().get(i);
                System.out.println(responseBodyResult.getFlowTitle() + "------" + responseBodyResult.getProcessCode());
            }
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println("TeaException");
                System.out.println(err.code);
                System.out.println(err.message);
            }
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println("Exception");
                System.out.println(err.code);
                System.out.println(err.message);
            }
        }
    }

    /**
     * 获取审批实例ID列表(测试用)
     *
     * @param accessToken
     * @throws Exception
     */
    private List<String> getContractForDemo(final String accessToken) throws Exception {
        com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsHeaders listProcessInstanceIdsHeaders = new com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsHeaders();
        listProcessInstanceIdsHeaders.xAcsDingtalkAccessToken = accessToken;
        com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsRequest listProcessInstanceIdsRequest = new com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsRequest()
                .setProcessCode("PROC-53AEE967-1CA5-43CF-9489-CAF178BC1E46") // 测试API
                .setStartTime(1661961600000L)
                .setNextToken(0L)
                .setMaxResults(10L);
        try {
            ListProcessInstanceIdsResponse resp = client.listProcessInstanceIdsWithOptions(listProcessInstanceIdsRequest, listProcessInstanceIdsHeaders, new com.aliyun.teautil.models.RuntimeOptions());
            System.out.println("审批实例ID列表：" + resp.getBody().getResult().getList());
            return resp.getBody().getResult().getList();
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println("TeaException");
                System.out.println(err.code);
                System.out.println(err.message);
            }
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println("Exception");
                System.out.println(err.code);
                System.out.println(err.message);
            }
        }

        return null;
    }

    /**
     * 根据实例ID，获取合同数据(测试用)
     *
     * @param accessToken
     * @param id
     */
    private ContractContentInfo getContractData(final String accessToken, final String id) {
        com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceHeaders getProcessInstanceHeaders = new com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceHeaders();
        getProcessInstanceHeaders.xAcsDingtalkAccessToken = accessToken;
        com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceRequest getProcessInstanceRequest = new com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceRequest()
                .setProcessInstanceId(id);

        ContractContentInfo contract = new ContractContentInfo();

        try {
            GetProcessInstanceResponse resp = client.getProcessInstanceWithOptions(getProcessInstanceRequest, getProcessInstanceHeaders, new com.aliyun.teautil.models.RuntimeOptions());
            System.out.println("合同状态------" + resp.getBody().getResult().status);
            contract.setContractStatus(resp.getBody().getResult().status);

            List<GetProcessInstanceResponseBody.GetProcessInstanceResponseBodyResultFormComponentValues> list = resp.getBody().getResult().formComponentValues;
            System.out.println("------合同项总数------" + list.size());
            System.out.println("------以下为合同项内容------");
            for (int i = 0; i < list.size(); i++) {
                GetProcessInstanceResponseBody.GetProcessInstanceResponseBodyResultFormComponentValues item = list.get(i);
                System.out.println(item.getName() + "------" + item.getValue());
                // 货物名称
                if (StringUtils.equals(item.getName(), "货物名称")) {
                    contract.setGoodsId(UUID.randomUUID().toString().trim().replace("-", ""));
                    contract.setGoodsName(item.getValue());
                }
                // 合同类型
                if (StringUtils.equals(item.getName(), "合同类型")) {
                    if (StringUtils.contains(item.getValue(), "收购合同")) {
                        contract.setContractType("1");
                    }
                }
                // 合同名称
                if (StringUtils.equals(item.getName(), "合同名称")) {
                    contract.setContractName(item.getValue());
                }
                // 合同编号
                if (StringUtils.equals(item.getName(), "合同编号")) {
                    contract.setContractId(item.getValue());
                }
                // 签约日期
                if (StringUtils.equals(item.getName(), "签约日期")) {
                    contract.setSignDate(DateUtils.dateTime(DateUtils.YYYY_MM_DD, item.getValue()));
                }
                // 交货日期
                if (StringUtils.equals(item.getName(), "交货日期")) {
                    contract.setDeliveryDate(DateUtils.dateTime(DateUtils.YYYY_MM_DD, item.getValue()));
                }
                // 我方单位名称
                if (StringUtils.equals(item.getName(), "我方单位名称")) {
                    contract.setOurCompanyName(item.getValue());
                }
                // 我方负责人
                if (StringUtils.equals(item.getName(), "我方负责人")) {
                    contract.setOurPrincipal(item.getValue());
                }
                // 对方单位名称
                if (StringUtils.equals(item.getName(), "对方单位名称")) {
                    contract.setOppositeCompanyName(item.getValue());
                }
                // 对方负责人
                if (StringUtils.equals(item.getName(), "对方负责人")) {
                    contract.setOppositePrincipal(item.getValue());
                }
                // 合同数量
                if (StringUtils.equals(item.getName(), "合同数量")) {
                    contract.setContractQuantity(item.getValue());
                }
                // 合同单价
                if (StringUtils.equals(item.getName(), "合同单价")) {
                    contract.setContractPrice(new BigDecimal(item.getValue()));
                }
                // 合同总价
                if (StringUtils.equals(item.getName(), "合同总价")) {
                    contract.setContractTotal(new BigDecimal(item.getValue()));
                }
                // 账期方式
                if (StringUtils.equals(item.getName(), "账期方式")) {
                    contract.setAccountingPeriod(item.getValue());
                }
                // 账期期限
                if (StringUtils.equals(item.getName(), "账期期限")) {
                    contract.setAccountingPeriod(contract.getAccountingPeriod() + item.getValue());
                }
                // 交货方式
                if (StringUtils.equals(item.getName(), "交货方式")) {
                    contract.setDeliveryMethod(item.getValue());
                }
                // 港口到厂运费
                if (StringUtils.equals(item.getName(), "港口到厂运费")) {
                    contract.setPortToFactoryFare(new BigDecimal(item.getValue()));
                }
                // 港口到港口运费
                if (StringUtils.equals(item.getName(), "港口到港口运费")) {
                    contract.setPortToPortFare(new BigDecimal(item.getValue()));
                }
                // 其他
                if (StringUtils.equals(item.getName(), "其他")) {
                    contract.setContractOther(item.getValue());
                }
                // 代理或合作方
                if (StringUtils.equals(item.getName(), "代理或合作方")) {
                    contract.setContractAgent(item.getValue());
                }
                // 备注
                if (StringUtils.equals(item.getName(), "备注")) {
                    contract.setContractRemark(item.getValue());
                }
            }

            return contract;
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println("TeaException");
                System.out.println(err.code);
                System.out.println(err.message);
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                System.out.println("Exception");
                System.out.println(err.code);
                System.out.println(err.message);
            }
        }

        return null;
    }

    private void fillPurchaseInfoFromContract(ContractContentInfo contractInfo,
                                              PurchaseSaleOrderInfo purchaseInfo) {
        // 采购类型 -> 收购合同对应企业采购
        purchaseInfo.setPurchaseType(contractInfo.getContractType());
        // 合同编号 -> 合同编号
        purchaseInfo.setContractId(contractInfo.getContractId());
        // 经办人 -> 我方负责人
        purchaseInfo.setHandledBy(contractInfo.getOurPrincipal());
        // 所属部门 -> 经办人所属部门
        purchaseInfo.setBelongDept("1");
        // 业务日期 -> 签约日期
        purchaseInfo.setBusinessDate(contractInfo.getSignDate());
        // 物料名称 -> 货物名称
        purchaseInfo.setMaterialName(contractInfo.getGoodsName());
        // 采购数量 -> 合同数量
        if (StringUtils.isNotBlank(contractInfo.getContractQuantity())) {
            purchaseInfo.setPurchaseQuantity(Long.parseLong(contractInfo.getContractQuantity()));
        } else {
            purchaseInfo.setPurchaseQuantity(0L);
        }
        // 供应商名称 -> 对方单位名称
        purchaseInfo.setSupplierName(contractInfo.getOppositeCompanyName());
        // 单价 -> 合同单价
        purchaseInfo.setUnitPrice(contractInfo.getContractPrice());
        // 计量单位 -> 平方米
        purchaseInfo.setMeteringUnit("3");
        // 预期到货日期 -> 交货日期
        purchaseInfo.setArrivalDate(contractInfo.getDeliveryDate());
        // 要求交货期 —> 交货日期
        purchaseInfo.setRequiredDeliveryDate(contractInfo.getDeliveryDate());
        // 账期 —> 账期期限中的数字部分
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(contractInfo.getAccountingPeriod());
        String result = m.replaceAll("").trim();
        if (StringUtils.isNotBlank(result)) {
            purchaseInfo.setPurchaseQuantity(Long.parseLong(result));
        } else {
            purchaseInfo.setAccountPeriod(0L);
        }
        // 到账条件 —>  账期期限
        purchaseInfo.setArrivalTerms(contractInfo.getAccountingPeriod());
        // 结算方式 -> 发货 = 发货数量结算
        if (StringUtils.contains(contractInfo.getAccountingPeriod(), "卸货")) {
            purchaseInfo.setSettlementMethod("1");
        } else if (StringUtils.contains(contractInfo.getAccountingPeriod(), "发货")) {
            purchaseInfo.setSettlementMethod("2");
        }
    }
}
