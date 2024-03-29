package com.ruoyi.report.contract.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dingtalkworkflow_1_0.models.*;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.github.pagehelper.util.StringUtil;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.fpgl.domain.FpglMainInfo;
import com.ruoyi.fpgl.mapper.FpglMainInfoMapper;
import com.ruoyi.purchase.sale.domain.PurchaseSaleOrderInfo;
import com.ruoyi.purchase.sale.mapper.PurchaseSaleOrderInfoMapper;
import com.ruoyi.report.contract.domain.ContractApprovalInfo;
import com.ruoyi.report.contract.domain.ContractApprovalRecordsInfo;
import com.ruoyi.report.contract.mapper.ContractApprovalInfoMapper;
import com.ruoyi.report.contract.mapper.ContractApprovalRecordsInfoMapper;
import com.ruoyi.report.masterdata.domain.MasterDataClientInfo;
import com.ruoyi.report.masterdata.domain.MasterDataMaterialInfo;
import com.ruoyi.report.masterdata.mapper.MasterDataClientInfoMapper;
import com.ruoyi.report.masterdata.mapper.MasterDataMaterialInfoMapper;
import com.ruoyi.zjzy.domain.ZjzyFkInfo;
import com.ruoyi.zjzy.mapper.ZjzyFkInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.report.contract.mapper.ContractContentInfoMapper;
import com.ruoyi.report.contract.domain.ContractContentInfo;
import com.ruoyi.report.contract.service.IContractContentInfoService;

import javax.validation.Validator;

/**
 * 合同管理Service业务层处理
 * 
 * @author changgong
 * @date 2022-10-30
 */
@Service
public class ContractContentInfoServiceImpl implements IContractContentInfoService {

    private static final Logger log = LoggerFactory.getLogger(ContractContentInfoServiceImpl.class);

    @Autowired
    private ContractContentInfoMapper contractContentInfoMapper;

    @Autowired
    private PurchaseSaleOrderInfoMapper purchaseSaleOrderInfoMapper;

    @Autowired
    private ContractApprovalInfoMapper contractApprovalInfoMapper;

    @Autowired
    private ContractApprovalRecordsInfoMapper contractApprovalRecordsInfoMapper;

    @Autowired
    private ZjzyFkInfoMapper zjzyFkInfoMapper;

    @Autowired
    private MasterDataClientInfoMapper masterDataClientInfoMapper;

    @Autowired
    private MasterDataMaterialInfoMapper masterDataMaterialInfoMapper;

    @Autowired
    private FpglMainInfoMapper fpglMainInfoMapper;

    @Autowired
    protected Validator validator;

    private static String companyBaseId = "";

    private static com.aliyun.dingtalkworkflow_1_0.Client client = null;

    private Map<String, Integer> materialMap = new HashMap<>();

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
    public ContractContentInfo selectContractContentInfoByContractId(String contactId) {

        // 取得所有采购销售订单
        PurchaseSaleOrderInfo purchaseSaleOrderInfo = new PurchaseSaleOrderInfo();
        List<PurchaseSaleOrderInfo> purchaseSaleOrderInfoList = purchaseSaleOrderInfoMapper
                .selectPurchaseSaleOrderInfoList(purchaseSaleOrderInfo);

        List<String> contractIdList = purchaseSaleOrderInfoList
                .stream()
                .map(PurchaseSaleOrderInfo::getContractId)
                .collect(Collectors.toList());

        ContractContentInfo contractContentInfo = contractContentInfoMapper
                .selectContractContentInfoByContractId(contactId);
        if (contractIdList.contains(contractContentInfo.getContractId())) {
            contractContentInfo.setConstractIsExist(1);
        } else {
            contractContentInfo.setConstractIsExist(0);
        }

        // 取得所有客户主数据
        List<MasterDataClientInfo> clientList =  masterDataClientInfoMapper
                .selectMasterDataClientInfoList(new MasterDataClientInfo());
        Map<String, String> clientMap = clientList
                .stream()
                .collect(Collectors.toMap(MasterDataClientInfo::getBaseId, MasterDataClientInfo::getCompanyName));

        // 设置显示的公司名称
        if (clientMap.containsKey(contractContentInfo.getOppositeCompanyName())) {
            contractContentInfo.setBaseId(contractContentInfo.getOppositeCompanyName());
            contractContentInfo.setCompanyName(clientMap.get(contractContentInfo.getOppositeCompanyName()));
        }

        return contractContentInfo;
    }

    /**
     * 查询合同管理列表
     *
     * @param contractContentInfo 合同管理
     * @return 合同管理
     */
    @Override
    public List<ContractContentInfo> selectContractContentInfoList(ContractContentInfo contractContentInfo) {
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

        // 取得主数据管理中所有物料列表
        List<MasterDataMaterialInfo> masterDataMaterialInfoList =  masterDataMaterialInfoMapper
                .selectMasterDataMaterialInfoList(new MasterDataMaterialInfo());
        // 取得物料名称 -> 物料编号的Map
        materialMap = masterDataMaterialInfoList.stream().collect(Collectors
                .toMap(MasterDataMaterialInfo::getMaterialName, MasterDataMaterialInfo::getMaterialId));

        // 获取当前企业钉钉访问令牌
        String accessToken = getDingTalkAccessToken();

        // 获取当前企业所有可管理的表单
//        List <String> processCodeList = getCurrentEnterpriseAllMgrForm(accessToken);
//        if (processCodeList != null && processCodeList.isEmpty()) {
//            return 10001; //  未取得所有可管理的表单列表
//        }

        // 获取审批实例ID列表(测试数据用)
        List <String> processCodeList = new ArrayList<>();
        processCodeList.add("PROC-14C71A8A-12BA-4CD2-99C7-2B8E9F9DB32B"); // 经营一部-合同审批
        processCodeList.add("PROC-7B80921A-0746-4574-8C93-8C47DCC0B2CC"); // 经营二部-合同审批
        for (String processCode : processCodeList) {

            List<String> contractIdIsEmptyList = new ArrayList<>();

            List<String> ids = getContract(accessToken, processCode);
            int size = ids.size();
            for (int i = 0; i < size; i++) {
                String id = ids.get(i);
                System.out.println("审批实例ID：" + id);
                ContractContentInfo contract = getContractData(accessToken, id);
                if (StringUtils.isNotBlank(contract.getContractId())) {
                    // 设置合同所属部门编号
                    if (StringUtils.equals(processCode, "PROC-14C71A8A-12BA-4CD2-99C7-2B8E9F9DB32B")) {
                        contract.setBelongDeptId(209L);
                    } else if (StringUtils.equals(processCode, "PROC-7B80921A-0746-4574-8C93-8C47DCC0B2CC")) {
                        contract.setBelongDeptId(214L);
                    } else {
                        contract.setBelongDeptId(0L);
                    }

                    // 检查合同是否已经导入合同表
                    ContractContentInfo selectContract = null;
                    selectContract = contractContentInfoMapper
                            .selectContractContentInfoByContractId(contract.getContractId());
                    if (selectContract == null) {
                        contract.setBizVersion(1L);
                        contract.setCreateTime(DateUtils.getNowDate());
                        contract.setUpdateTime(DateUtils.getNowDate());
                        contract.setCreateBy(SecurityUtils.getUsername());
                        contract.setUpdateBy(SecurityUtils.getUsername());
                        contractContentInfoMapper.insertContractContentInfo(contract);
                    } else {
                        contract.setUpdateTime(DateUtils.getNowDate());
                        contract.setUpdateBy(SecurityUtils.getUsername());
                        contractContentInfoMapper.updateContractContentInfo(contract);
                    }

                    // 取得审批数据，添加到审批表和审批记录表
                    ContractApprovalInfo ccontractApprovalInfo = getContractApprovaData(accessToken, id);

                    // 检查审批数据是否已经导入合同审批信息表
                    ContractApprovalInfo selContractApprovalInfo = null;
                    selContractApprovalInfo = contractApprovalInfoMapper.selectContractApprovalInfoByApprovalId(ccontractApprovalInfo.getApprovalId());
                    if (selContractApprovalInfo == null) {
                        ccontractApprovalInfo.setBizVersion(1L);
                        ccontractApprovalInfo.setCreateTime(DateUtils.getNowDate());
                        ccontractApprovalInfo.setUpdateTime(DateUtils.getNowDate());
                        ccontractApprovalInfo.setCreateBy(SecurityUtils.getUsername());
                        ccontractApprovalInfo.setUpdateBy(SecurityUtils.getUsername());
                        contractApprovalInfoMapper.insertContractApprovalInfo(ccontractApprovalInfo);
                    } else {
                        ccontractApprovalInfo.setBizVersion(1L);
                        ccontractApprovalInfo.setUpdateTime(DateUtils.getNowDate());
                        ccontractApprovalInfo.setUpdateBy(SecurityUtils.getUsername());
                        contractApprovalInfoMapper.updateContractApprovalInfo(ccontractApprovalInfo);
                    }
                } else {
                    // 合同编号为空的场合
                    contractIdIsEmptyList.add(id);
                }
            }

            // 合同编号为空汇总
            System.out.println("Process COde：" + processCode);
            System.out.println("合同编号为空的审批实例总数：" + contractIdIsEmptyList.size());
            for (String instanceId : contractIdIsEmptyList) {
                System.out.println("合同编号为空的审批实例ID：" + instanceId);
            }
        }

        return 1; // 成功
    }

    /**
     * 从钉钉同步付款数据
     *
     * @return 结果
     */
    @Override
    public int syncFkContractInfo() throws Exception {
        // 获取当前企业钉钉访问令牌
        String accessToken = getDingTalkAccessToken();

        // 获取当前企业所有可管理的表单
        // getCurrentEnterpriseAllMgrForm(accessToken);

        // 获取付款合同审批实例ID列表(测试数据用)
        List<String> idsFk = getFkContractForDemo(accessToken);
        for (String idFk : idsFk) {
            System.out.println("审批实例ID：" + idFk);
            ZjzyFkInfo zjzyFkInfo = getFkContractData(accessToken, idFk);

            // 检查合同是否已经导入合同表
            ZjzyFkInfo selectZjzyFkInfo = zjzyFkInfoMapper
                    .selectZjzyFkInfoByFkBusinessId(zjzyFkInfo.getFkBusinessId());
            if (selectZjzyFkInfo == null) {
                zjzyFkInfo.setBizVersion(1L);
                zjzyFkInfo.setCreateTime(DateUtils.getNowDate());
                zjzyFkInfo.setUpdateTime(DateUtils.getNowDate());
                zjzyFkInfo.setCreateBy(SecurityUtils.getUsername());
                zjzyFkInfo.setUpdateBy(SecurityUtils.getUsername());
                zjzyFkInfoMapper.insertZjzyFkInfo(zjzyFkInfo);
            } else {
                zjzyFkInfo.setBizVersion(1L);
                zjzyFkInfo.setUpdateTime(DateUtils.getNowDate());
                zjzyFkInfo.setUpdateBy(SecurityUtils.getUsername());
                zjzyFkInfoMapper.updateZjzyFkInfo(zjzyFkInfo);
            }
        }

        return 1;
    }

    @Override
    public String importContract(List<ContractContentInfo> contractList, Boolean isUpdateSupport,
                                 String operName) {

        if (StringUtils.isNull(contractList) || contractList.size() == 0) {
            throw new ServiceException("导入合同数据不能为空！");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (ContractContentInfo contract : contractList) {
            try {
                ContractContentInfo selContract = contractContentInfoMapper
                        .selectContractContentInfoByContractId(contract.getContractId());
                if (StringUtils.isNull(selContract)) {
                    BeanValidators.validateWithException(validator, contract);
                    contract.setBizVersion(1L);
                    contract.setCreateBy(operName);
                    contract.setCreateTime(DateUtils.parseDate(DateUtils.getTime()));
                    contract.setUpdateBy(operName);
                    contract.setUpdateTime(DateUtils.parseDate(DateUtils.getTime()));
                    insertContractContentInfo(contract);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + contract.getContractName() + " 导入成功");
                } else if (isUpdateSupport) {
                    BeanValidators.validateWithException(validator, contract);
                    contract.setBizVersion(1L);
                    contract.setUpdateBy(operName);
                    contract.setUpdateTime(DateUtils.parseDate(DateUtils.getNowDate()));
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、合同 " + contract.getContractName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、合同 " + contract.getContractName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、合同 " + contract.getContractName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }

        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
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
    private List<String> getCurrentEnterpriseAllMgrForm(final String accessToken) throws Exception {

        com.aliyun.dingtalkworkflow_1_0.Client client = createClient();
        GetManageProcessByStaffIdHeaders getManageProcessByStaffIdHeaders = new GetManageProcessByStaffIdHeaders();
        getManageProcessByStaffIdHeaders.xAcsDingtalkAccessToken = accessToken;

//        GetManageProcessByStaffIdRequest getManageProcessByStaffIdRequest = new GetManageProcessByStaffIdRequest()
//                .setUserId("282350193529366375");

        String[] syncUserIds = new String[] {
//                "1054566453903766",
//                "1054582138808487376",
//                "1023291525-1101082476",
//                "173703126738684904",
//                "013123634833456429",
//                "621832300920030353",
//                "1054575040791042",
//                "1002693800788633",
//                "214729110429276521",
//                "1729613335655268",
//                "310864301924151987",
                "282350193529366375"
        };

        List<String> processCodeList = new ArrayList<>();

        for (int index = 0; index < syncUserIds.length; index++) {

            GetManageProcessByStaffIdRequest getManageProcessByStaffIdRequest = new GetManageProcessByStaffIdRequest()
                .setUserId(syncUserIds[index]);

            try {
                GetManageProcessByStaffIdResponse resp = client
                        .getManageProcessByStaffIdWithOptions(getManageProcessByStaffIdRequest,
                                getManageProcessByStaffIdHeaders, new RuntimeOptions());

                int size = resp.getBody().getResult().size();
                for (int i = 0; i < size; i++) {
                    GetManageProcessByStaffIdResponseBody.GetManageProcessByStaffIdResponseBodyResult responseBodyResult = null;
                    responseBodyResult = resp.getBody().getResult().get(i);
                    System.out.println(responseBodyResult.getFlowTitle() + "------" + responseBodyResult.getProcessCode());
                    if (StringUtils.contains(responseBodyResult.getFlowTitle(), "合同")) {
                        // 存储合同表单
                        processCodeList.add(responseBodyResult.getProcessCode());
                    }
                }
            } catch (TeaException err) {
                if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                    // err 中含有 code 和 message 属性，可帮助开发定位问题
                    System.out.println("TeaException-[index: " + index + "]-[userId: " + syncUserIds[index] + "]-[code: " + err.code + "]-[message: " + err.message + "]");
                }
            } catch (Exception _err) {
                TeaException err = new TeaException(_err.getMessage(), _err);
                if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                    // err 中含有 code 和 message 属性，可帮助开发定位问题
                    System.out.println("Exception-[index: " + index + "]-[userId: " + syncUserIds[index] + "]-[code: " + err.code + "]-[message: " + err.message + "]");
                }
            }
        }

        System.out.println("-------------------------合同开始-------------------------");
        for (String processCode : processCodeList) {
            System.out.println(processCode);
        }
        System.out.println("-------------------------合同结束-------------------------");

        return processCodeList;
    }

    /**
     * 获取审批实例ID列表(测试用)
     *
     * @param accessToken
     * @throws Exception
     */
    private List<String> getContractForDemo(final String accessToken, List <String> processCodeList) throws Exception {

        com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsHeaders listProcessInstanceIdsHeaders =
                new com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsHeaders();
        listProcessInstanceIdsHeaders.xAcsDingtalkAccessToken = accessToken;

        List<String> instanceList = new ArrayList<>();
        for (int i = 0; i < processCodeList.size(); i++) {
            String processCode = processCodeList.get(i);
            long curTime = System.currentTimeMillis();
            com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsRequest listProcessInstanceIdsRequest =
                    new com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsRequest()
//                    .setProcessCode("PROC-53AEE967-1CA5-43CF-9489-CAF178BC1E46") // 测试API
                    .setProcessCode(processCode)
//                    .setStartTime(1667232000000L)
                    .setStartTime(1667232000000L)
//                    .setEndTime(1675958400000L)
                    .setNextToken(0L).setMaxResults(10L);

            try {
                ListProcessInstanceIdsResponse resp = client.listProcessInstanceIdsWithOptions(listProcessInstanceIdsRequest,
                        listProcessInstanceIdsHeaders, new com.aliyun.teautil.models.RuntimeOptions());
//                System.out.println("审批实例ID列表：" + resp.getBody().getResult().getList());
                instanceList.addAll(resp.getBody().getResult().getList());
//                return resp.getBody().getResult().getList();
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

        System.out.println("-------------------------审批实例开始-------------------------");
        System.out.println("-------------------------获取审批实例ID列表总数："+ instanceList.size() + "-------------------------");
        for (String isntance : instanceList) {
            System.out.println(isntance);
        }
        System.out.println("-------------------------审批实例结束-------------------------");

        return instanceList;
    }

    /**
     * 获取审批实例ID列表
     *
     * @param accessToken
     * @throws Exception
     */
    private List<String> getContract(final String accessToken, final String processCode) throws Exception {

        com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsHeaders listProcessInstanceIdsHeaders =
                new com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsHeaders();
        listProcessInstanceIdsHeaders.xAcsDingtalkAccessToken = accessToken;

        // https://open.dingtalk.com/document/orgapp/obtain-an-approval-list-of-instance-ids
        List<String> instanceList = new ArrayList<>();
        Long nextToken = 0L;
        while (nextToken >= 0L) {
            com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsRequest listProcessInstanceIdsRequest =
                    new com.aliyun.dingtalkworkflow_1_0.models
                            .ListProcessInstanceIdsRequest()
                            .setProcessCode(processCode)
                            .setStartTime(1667232000000L) // 2022-11-01 00:00:00------至今
                            .setNextToken(nextToken)
                            .setMaxResults(20L);
            try {
                ListProcessInstanceIdsResponse resp = client.listProcessInstanceIdsWithOptions(listProcessInstanceIdsRequest,
                        listProcessInstanceIdsHeaders, new com.aliyun.teautil.models.RuntimeOptions());
                String tempNextToken = resp.getBody().getResult().getNextToken();
                if (StringUtils.isNotBlank(tempNextToken)) {
                    nextToken = Long.parseLong(tempNextToken);
                } else {
                    break;
                }
                instanceList.addAll(resp.getBody().getResult().getList());
            } catch (TeaException err) {
                if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                    // err 中含有 code 和 message 属性，可帮助开发定位问题
                    System.out.println("TeaException");
                    System.out.println(err.code);
                    System.out.println(err.message);
                    break;
                }
            } catch (Exception _err) {
                TeaException err = new TeaException(_err.getMessage(), _err);
                if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                    // err 中含有 code 和 message 属性，可帮助开发定位问题
                    System.out.println("Exception");
                    System.out.println(err.code);
                    System.out.println(err.message);
                    break;
                }
            }
        }

        System.out.println("-------------------------审批实例开始-------------------------");
        System.out.println("-------------------------获取审批实例ID列表总数："+ instanceList.size() + "-------------------------");
        for (String instance : instanceList) {
            System.out.println(instance);
        }
        System.out.println("-------------------------审批实例结束-------------------------");

        return instanceList;
    }

    /**
     * 获取付款合同审批实例ID列表(测试用)
     *
     * @param accessToken
     * @throws Exception
     */
    private List<String> getFkContractForDemo(final String accessToken) throws Exception {
        com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsHeaders listProcessInstanceIdsHeaders = new com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsHeaders();
        listProcessInstanceIdsHeaders.xAcsDingtalkAccessToken = accessToken;
        com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsRequest listProcessInstanceIdsRequest = new com.aliyun.dingtalkworkflow_1_0.models.ListProcessInstanceIdsRequest()
                .setProcessCode("PROC-14DEE72E-B6C8-4D18-B684-06A88E4CC714") // 测试API
                .setStartTime(1667232000000L)
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
                    contract.setGoodsId(String.valueOf(materialMap.get(item.getValue())));
                    contract.setGoodsName(item.getValue());
                }
                // 合同类型
                if (StringUtils.equals(item.getName(), "合同类型")) {
                    if (StringUtils.contains(item.getValue(), "收购合同") ||
                            StringUtils.contains(item.getValue(), "采购合同")) {
                        contract.setContractType("P");
                    } else if (StringUtils.contains(item.getValue(), "物流合同") ||
                            StringUtils.contains(item.getValue(), "销售合同")) {
                        contract.setContractType("S");
                    } else {
                        // 其他合同类型
                        contract.setContractType("Q");
                    }
                }
                // 合同名称
                if (StringUtils.equals(item.getName(), "合同名称")) {
                    contract.setContractName(item.getValue());
                }
                // 合同编号
                if (StringUtils.equals(item.getName(), "合同编号")) {
                    if (!StringUtils.contains(item.getValue(), "/")) {
                        contract.setContractId(item.getValue());
                    } else {
                        throw new Exception("同步的合同编号包含非法字符[/]");
                    }
                }
                // 签约日期
                if (StringUtils.equals(item.getName(), "签约日期")) {
                    if (StringUtils.isNotBlank(item.getValue())) {
                        contract.setSignDate(DateUtils.dateTime(DateUtils.YYYY_MM_DD, item.getValue()));
                    }
                }
                // 交货日期
                if (StringUtils.equals(item.getName(), "交货日期")) {
                    if (StringUtils.isNotBlank(item.getValue())) {
                        contract.setDeliveryDate(DateUtils.dateTime(DateUtils.YYYY_MM_DD, item.getValue()));
                    }
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
                    if (StringUtils.isNotBlank(item.getValue()) && isDigit(item.getValue())) {
                        contract.setContractPrice(new BigDecimal(getDigit(item.getValue())));
                    }
                }
                // 合同总价
                if (StringUtils.equals(item.getName(), "合同总价")) {
                    if (StringUtils.isNotBlank(item.getValue()) && isDigit(item.getValue())) {
                        contract.setContractTotal(new BigDecimal(getDigit(item.getValue())));
                    }
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
                    if (StringUtils.isNotBlank(item.getValue()) && isDigit(item.getValue())) {
                        contract.setPortToFactoryFare(new BigDecimal(getDigit(item.getValue())));
                    }
                }
                // 港口到港口运费
                if (StringUtils.equals(item.getName(), "港口到港口运费")) {
                    if (StringUtils.isNotBlank(item.getValue()) && isDigit(item.getValue())) {
                        contract.setPortToPortFare(new BigDecimal(getDigit(item.getValue())));
                    }
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
        // 经办人 -> 当前登录用户名称
        purchaseInfo.setHandledBy(SecurityUtils.getUsername());
        // 所属部门 -> 当前登录用户所属部门
        purchaseInfo.setBelongDept(String.valueOf(SecurityUtils.getDeptId()));
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
        if (StringUtils.contains(contractInfo.getOppositeCompanyName(), "KH")) {
            // 供应商名称为供应商编码（KHXXXXXX）的场合
            purchaseInfo.setSupplierName(contractInfo.getOppositeCompanyName());
        } else {
            // 供应商名称为汉字名称的场合
            MasterDataClientInfo param = new MasterDataClientInfo();
            param.setCompanyName(contractInfo.getOppositeCompanyName());
            List<MasterDataClientInfo> masterDataClientInfoList = masterDataClientInfoMapper
                    .selectMasterDataClientInfoList(param);
            purchaseInfo.setSupplierName(masterDataClientInfoList.get(0).getBaseId());
        }
        // 单价 -> 合同单价
        purchaseInfo.setUnitPrice(contractInfo.getContractPrice());
        // 计量单位 -> 平方米
        purchaseInfo.setMeteringUnit("3");
        // 预期到货日期 -> 交货日期
        purchaseInfo.setArrivalDate(contractInfo.getDeliveryDate());
        // 要求交货期 —> 交货日期
        purchaseInfo.setRequiredDeliveryDate(contractInfo.getDeliveryDate());
        // 账期 —> 账期期限中的数字部分
        if (StringUtils.isNotBlank(contractInfo.getAccountingPeriod())) {
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(contractInfo.getAccountingPeriod());
            String result = m.replaceAll("").trim();
            if (StringUtils.isNotBlank(result)) {
                purchaseInfo.setPurchaseQuantity(Long.parseLong(result));
            } else {
                purchaseInfo.setAccountPeriod(0L);
            }
        }
        // 到账条件 —>  账期期限
        purchaseInfo.setArrivalTerms(contractInfo.getAccountingPeriod());
        // 结算方式 -> 发货 = 发货数量结算
        if (StringUtils.contains(contractInfo.getAccountingPeriod(), "卸货")) {
            purchaseInfo.setSettlementMethod("1");
        } else if (StringUtils.contains(contractInfo.getAccountingPeriod(), "发货")) {
            purchaseInfo.setSettlementMethod("2");
        }

        purchaseInfo.setOrderStatus(contractInfo.getContractStatus());
    }

    /**
     * 根据实例ID，获取付款合同数据(测试用)
     *
     * @param accessToken
     * @param id
     */
    private ZjzyFkInfo getFkContractData(final String accessToken, final String id) {
        com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceHeaders getProcessInstanceHeaders = new com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceHeaders();
        getProcessInstanceHeaders.xAcsDingtalkAccessToken = accessToken;
        com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceRequest getProcessInstanceRequest = new com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceRequest()
                .setProcessInstanceId(id);

        ZjzyFkInfo fk = new ZjzyFkInfo();

        try {
            GetProcessInstanceResponse resp = client.getProcessInstanceWithOptions(getProcessInstanceRequest, getProcessInstanceHeaders, new com.aliyun.teautil.models.RuntimeOptions());
            System.out.println("付款合同状态------" + resp.getBody().getResult().status);
            fk.setFkspzt(resp.getBody().getResult().status);

            System.out.println("业务编号------" + resp.getBody().getResult().businessId);
            fk.setFkBusinessId(resp.getBody().getResult().businessId);

            List<GetProcessInstanceResponseBody.GetProcessInstanceResponseBodyResultFormComponentValues> list = resp.getBody().getResult().formComponentValues;
            System.out.println("------付款合同项总数------" + list.size());
            System.out.println("------以下为付款合同项内容------");
            for (int i = 0; i < list.size(); i++) {
                GetProcessInstanceResponseBody.GetProcessInstanceResponseBodyResultFormComponentValues item = list.get(i);
                System.out.println(item.getName() + "------" + item.getValue());
                // 付款事由
                if (StringUtils.equals(item.getName(), "付款事由")) {
                    fk.setFkSy(item.getValue());
                }
                // 贸易品种
                if (StringUtils.equals(item.getName(), "贸易品种")) {
                    //TODO 需要补充
                    fk.setFkWlbh("");
                    fk.setFkWlmc(item.getValue());
                }
                // 其他品种名称
                if (StringUtils.equals(item.getName(), "其他品种名称")) {
                    fk.setFkQtpzmc(item.getValue());
                }
                // 资金用途
                if (StringUtils.equals(item.getName(), "资金用途")) {
                    if (StringUtils.equals(item.getValue(), "运费")) {
                        fk.setFkZjyt("1");
                    } else {
                        //TODO 需要补充
                        fk.setFkZjyt("0");
                    }
                }
                // 单价
                if (StringUtils.equals(item.getName(), "单价")) {
                    if (StringUtils.isNotBlank(item.getValue())) {
                        fk.setFkDj(new BigDecimal(item.getValue()));
                    } else {
                        fk.setFkDj(new BigDecimal(0));
                    }
                }
                // 数量
                if (StringUtils.equals(item.getName(), "数量")) {
                    if (StringUtils.isNotBlank(item.getValue())) {
                        fk.setFkSl(Long.parseLong(item.getValue()));
                    } else {
                        fk.setFkSl(0L);
                    }
                }
                // 付款金额（元）
                if (StringUtils.equals(item.getName(), "付款金额（元）")) {
                    if (StringUtils.isNotBlank(item.getValue())) {
                        fk.setFkJe(new BigDecimal(item.getValue()));
                    } else {
                        fk.setFkJe(new BigDecimal(0L));
                    }
                }
                // 付款账户
                if (StringUtils.equals(item.getName(), "付款账户")) {
                    //TODO 需要补充
                    fk.setFkKhbh("");
                    fk.setFkKhmc(item.getValue());
                }
                // 收款账号
                if (StringUtils.equals(item.getName(), "收款账号")) {
                    fk.setFkZh(item.getValue());
                }
                // 开户银行
                if (StringUtils.equals(item.getName(), "开户银行")) {
                    fk.setFkKhyh(item.getValue());
                }
                // 银行行号
                if (StringUtils.equals(item.getName(), "银行行号")) {
                    fk.setFkYhhh(item.getValue());
                }
                // 运输方式
                if (StringUtils.equals(item.getName(), "运输方式")) {
                    if (StringUtils.equals(item.getValue(), "铁运")) {
                        fk.setFkYsfs("1");
                    } else {
                        //TODO 需要补充
                        fk.setFkYsfs("0");
                    }
                }
                // 装车、到货
                if (StringUtils.equals(item.getName(), "装车、到货")) {
                    fk.setFkZcdh(item.getValue());
                }
                // 备注
                if (StringUtils.equals(item.getName(), "账期期限")) {
                    fk.setFkBz(item.getValue());
                }
            }

            return fk;
        } catch (TeaException err) {
            // err 中含有 code 和 message 属性，可帮助开发定位问题
            System.out.println("TeaException");
            System.out.println(err.code);
            System.out.println(err.message);
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            // err 中含有 code 和 message 属性，可帮助开发定位问题
            System.out.println("Exception");
            System.out.println(err.code);
            System.out.println(err.message);
        }

        return null;
    }

    /**
     * 根据实例ID，获取合同审批数据(测试用)
     *
     * @param accessToken
     * @param id
     * @return
     */
    private ContractApprovalInfo getContractApprovaData(final String accessToken, final String id) {

        com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceHeaders getProcessInstanceHeaders =
                new com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceHeaders();
        getProcessInstanceHeaders.xAcsDingtalkAccessToken = accessToken;
        com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceRequest getProcessInstanceRequest =
                new com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceRequest()
                .setProcessInstanceId(id);

        ContractApprovalInfo contractApprovalInfo = new ContractApprovalInfo();

        try {
            com.aliyun.dingtalkworkflow_1_0.models.GetProcessInstanceResponse rsp = null;
            rsp = client.getProcessInstanceWithOptions(getProcessInstanceRequest,
                    getProcessInstanceHeaders, new com.aliyun.teautil.models.RuntimeOptions());

            List<GetProcessInstanceResponseBody.GetProcessInstanceResponseBodyResultFormComponentValues> valuesList = null;

            // 合同编号
            valuesList = rsp.getBody().getResult().formComponentValues;
            String contractId = valuesList.get(3).value;
            contractApprovalInfo.setContractId(contractId);

            // 审批编号
            String businessId = rsp.getBody().getResult().businessId;
            contractApprovalInfo.setApprovalId(businessId);

            // 审批合同标题
            String title = rsp.getBody().getResult().title;
            contractApprovalInfo.setApprovalTitle(title);

            // 审批状态
            String status = rsp.getBody().getResult().status;
            if (StringUtils.isBlank(status)) {
                contractApprovalInfo.setApprovalStatus(StringUtils.EMPTY);
            } else {
                contractApprovalInfo.setApprovalStatus(status);
            }

            // 审批结果
            String result = rsp.getBody().getResult().result;
            contractApprovalInfo.setApprovalResult(result);

            // 审批发起时间
            String fqsj = rsp.getBody().getResult().createTime;
            contractApprovalInfo.setLaunchTime(converTZDate2UTCDate(fqsj));

            // 审批完成时间
            String wcsj = rsp.getBody().getResult().finishTime;
            if (StringUtils.isBlank(wcsj)) {
                contractApprovalInfo.setCompleteTime(DateUtils.parseDate(DateUtils.getTime()));
                wcsj = DateUtils.getTime();
            } else {
                contractApprovalInfo.setCompleteTime(converTZDate2UTCDate(wcsj));
            }

            // 耗时
            String takeupTime = StringUtils.EMPTY;
            if (StringUtils.indexOf(wcsj, "T") == -1
                    && StringUtils.indexOf(wcsj, "Z") == -1) {
                takeupTime = DateUtils.getDatePoor(DateUtils.parseDate(wcsj), converTZDate2UTCDate(fqsj));
            } else {
                takeupTime = DateUtils.getDatePoor(converTZDate2UTCDate(wcsj), converTZDate2UTCDate(fqsj));
            }
            contractApprovalInfo.setTakeupTime(takeupTime);

            // 发起人工号
            String fqrgh = "";
            contractApprovalInfo.setLaunchJobId(fqrgh);

            // 发起人ID
            String fqrid = rsp.getBody().getResult().originatorUserId;
            contractApprovalInfo.setLaunchId(fqrid);

            // 发起人姓名
            String fqrxm = rsp.getBody().getResult().originatorUserId;
            contractApprovalInfo.setLaunchName(fqrxm);

            // 发起人部门
            String fqrbmId = rsp.getBody().getResult().originatorDeptId;
            String fqrbm = rsp.getBody().getResult().originatorDeptName;
            contractApprovalInfo.setLaunchDepartment(fqrbmId);

            // 审批人姓名
            List<GetProcessInstanceResponseBody.GetProcessInstanceResponseBodyResultOperationRecords> list = null;
            list = rsp.getBody().getResult().operationRecords;
            List<String> sprxmList = new ArrayList<>();
            List<ContractApprovalRecordsInfo> listApprovalRecords = new ArrayList<>();
            for(GetProcessInstanceResponseBody.GetProcessInstanceResponseBodyResultOperationRecords record: list) {

                sprxmList.add(record.getUserId());

                ContractApprovalRecordsInfo approvalRecords  = new ContractApprovalRecordsInfo();
                approvalRecords.setApprovalName(record.getUserId());
                approvalRecords.setCompleteTime(converTZDate2UTCDate(record.getDate()));
                if (StringUtils.endsWithIgnoreCase(record.getType(), "START_PROCESS_INSTANCE")) {
                    approvalRecords.setApprovalRecord("提交申请");
                    approvalRecords.setApprovalResult("提交申请");
                } else  {
                    approvalRecords.setApprovalRecord("审批");
                    approvalRecords.setApprovalResult(record.getResult());
                }

                approvalRecords.setApprovalId(contractApprovalInfo.getApprovalId());
                approvalRecords.setBizVersion(1L);
                approvalRecords.setCreateTime(DateUtils.getNowDate());
                approvalRecords.setUpdateTime(DateUtils.getNowDate());
                approvalRecords.setCreateBy(SecurityUtils.getUsername());
                approvalRecords.setUpdateBy(SecurityUtils.getUsername());

                listApprovalRecords.add(approvalRecords);
            }

            String sprxm = StringUtils.join(sprxmList, ",");
            contractApprovalInfo.setApprovalName(sprxm);

            // 当前处理人
            String dqclr = sprxmList.get(sprxmList.size() - 1);
            contractApprovalInfo.setProcessorName(dqclr);

            for (ContractApprovalRecordsInfo approvalRecord: listApprovalRecords) {

                ContractApprovalRecordsInfo selRecord = contractApprovalRecordsInfoMapper
                        .selectContractApprovalRecordsInfoByApprovalIdAndUserId(approvalRecord);

                if (selRecord == null) {
                    approvalRecord.setApprovalRecordId(UUID.randomUUID().toString().replace("-", ""));
                    approvalRecord.setBizVersion(1L);
                    approvalRecord.setCreateTime(DateUtils.getNowDate());
                    approvalRecord.setUpdateTime(DateUtils.getNowDate());
                    approvalRecord.setCreateBy(SecurityUtils.getUsername());
                    approvalRecord.setUpdateBy(SecurityUtils.getUsername());
                    contractApprovalRecordsInfoMapper.insertContractApprovalRecordsInfo(approvalRecord);
                } else {
                    approvalRecord.setUpdateTime(DateUtils.getNowDate());
                    approvalRecord.setUpdateBy(SecurityUtils.getUsername());
                    contractApprovalRecordsInfoMapper.updateContractApprovalRecordsInfo(approvalRecord);
                }
            }
        } catch (TeaException err) {
            System.out.println("TeaException");
            System.out.println(err.code);
            System.out.println(err.message);
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            // err 中含有 code 和 message 属性，可帮助开发定位问题
            System.out.println("Exception");
            System.out.println(err.code);
            System.out.println(err.message);
        }

        return contractApprovalInfo;
    }

    /**
     * 导入合同数据到采购表或者销售表
     *
     * @return 结果
     */
    public int importContractDataIntoPurchaseSaleTable(ContractContentInfo contract) {

        if (contract == null) {
            return 0;
        }

        contract.setBizVersion(1L);
        contract.setCreateTime(DateUtils.getNowDate());
        contract.setUpdateTime(DateUtils.getNowDate());
        contract.setCreateBy(SecurityUtils.getUsername());
        contract.setUpdateBy(SecurityUtils.getUsername());

        // 检查是否已经导入采购表和销售表
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

        // 采购订单或者销售订单，默认都需要开发票
        FpglMainInfo fpglMainInfo = new FpglMainInfo();

        // 取得主数据管理中所有物料列表
        List<MasterDataMaterialInfo> masterDataMaterialInfoList =  masterDataMaterialInfoMapper
                .selectMasterDataMaterialInfoList(new MasterDataMaterialInfo());
        // 取得物料名称 -> 物料编号的Map
        Map<String, Integer> maps = masterDataMaterialInfoList.stream()
                .collect(Collectors.toMap(MasterDataMaterialInfo::getMaterialName, MasterDataMaterialInfo::getMaterialId));
        fpglMainInfo.setFpglKpmx(String.valueOf(maps.get(purchaseInfo.getMaterialName())));
        fpglMainInfo.setFpglKpsl(0L);
        fpglMainInfo.setFpglKpdj(BigDecimal.ZERO);
        fpglMainInfo.setFpglKpje(BigDecimal.ZERO);
        fpglMainInfo.setFpglFpzt("3");
        fpglMainInfo.setFpglDdbh(purchaseInfo.getOrderId());
        fpglMainInfo.setFpglSqr(SecurityUtils.getUsername());

        FpglMainInfo selectFpglMainInfo = fpglMainInfoMapper
                .selectFpglMainInfoByFpglDdbh(purchaseInfo.getOrderId());
        if (selectFpglMainInfo == null) {
            fpglMainInfo.setBizVersion(1L);
            fpglMainInfo.setCreateTime(DateUtils.getNowDate());
            fpglMainInfo.setUpdateTime(DateUtils.getNowDate());
            fpglMainInfo.setCreateBy(SecurityUtils.getUsername());
            fpglMainInfo.setUpdateBy(SecurityUtils.getUsername());
            fpglMainInfoMapper.insertFpglMainInfo(fpglMainInfo);
        } else {
            fpglMainInfo.setUpdateTime(DateUtils.getNowDate());
            fpglMainInfo.setUpdateBy(SecurityUtils.getUsername());
            fpglMainInfoMapper.updateFpglMainInfo(fpglMainInfo);
        }

        return 1;
    }

    /**
     * 生成前端显示合同数据列表
     *
     * @param contractContentInfoList
     * @return
     */
    public void makeModelViewData(List<ContractContentInfo> contractContentInfoList) {

        // 取得所有采购销售订单
        List<PurchaseSaleOrderInfo> purchaseSaleOrderInfoList = purchaseSaleOrderInfoMapper
                .selectPurchaseSaleOrderInfoList(new PurchaseSaleOrderInfo());

        // 取得所有采购销售订单对应的合同列表
        List<String> contractIdList = purchaseSaleOrderInfoList
                .stream()
                .map(PurchaseSaleOrderInfo::getContractId)
                .collect(Collectors.toList());

        // 取得所有客户主数据
        List<MasterDataClientInfo> clientList =  masterDataClientInfoMapper
                .selectMasterDataClientInfoList(new MasterDataClientInfo());

        // 客户主数据列表转成Map（key：baseId, value：companyName）
        Map<String, String> clientMap = clientList
                .stream()
                .collect(Collectors.toMap(MasterDataClientInfo::getBaseId, MasterDataClientInfo::getCompanyName));

        // 钉钉同步过来的合同， 在采购或销售表是否存在
        contractContentInfoList.stream().forEach(element -> {
            String contractId = element.getContractId();
            if (contractIdList.contains(contractId)) {
                element.setConstractIsExist(1);
            } else {
                element.setConstractIsExist(0);
            }
        });

        // 钉钉同步过来的合同， 设置显示的公司名称
        contractContentInfoList.stream().forEach(element -> {
            // 设置显示的公司名称
            if (clientMap.containsKey(element.getOppositeCompanyName()) && element.getOppositeCompanyName().contains("KH")) {
                element.setBaseId(element.getOppositeCompanyName());
                element.setCompanyName(clientMap.get(element.getOppositeCompanyName()));
            } else {
                clientMap.forEach((k, v) -> {
                    if(v.indexOf(element.getOppositeCompanyName()) >= 0){
                        companyBaseId = k;
                        return;
                    }
                });
                element.setBaseId(companyBaseId);
                element.setCompanyName(element.getOppositeCompanyName());
            }
        });
    }

    /**
     * 根据合同编号，判断该编号合同是否存在
     *
     * @param contractId 合同管理主键
     * @return 合同管理
     */
    public boolean isExistContractByContractId(final String contractId) {
        ContractContentInfo contractContentInfo = contractContentInfoMapper.selectContractContentInfoByContractId(contractId);
        if (contractContentInfo == null) {
            return false;
        }

        return true;
    }

    /**
     * // 将日期字符串（包含T,Z）转化为Date类型.
     *
     * @param utDate
     * @return
     */
    private Date converTZDate2UTCDate(final String utDate) {

        SimpleDateFormat sdfTZ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        SimpleDateFormat sdfUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date tempDate = null;
        Date targetDate = null;
        try {
            // 解析到Date对象
            tempDate = sdfTZ.parse(utDate);

            // 输出UTC格式：2017-01-22 09:28:33字符串
            String utcDate  = sdfUTC.format(tempDate);
            targetDate = DateUtils.parseDate(utcDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return targetDate;
    }

    /**
     *
     *
     * @param value
     * @return
     */
    private boolean isDigit(final String value) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(value);
        String result = m.replaceAll("").trim();
        if (StringUtils.isNotBlank(result)) {
            return true;
        }

        return false;
    }

    /**
     *
     *
     * @param value
     * @return
     */
    private String getDigit(final String value) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(value);
        String result = m.replaceAll("").trim();
        if (StringUtils.isNotBlank(result)) {
            return result;
        }

        return "0";
    }
}
