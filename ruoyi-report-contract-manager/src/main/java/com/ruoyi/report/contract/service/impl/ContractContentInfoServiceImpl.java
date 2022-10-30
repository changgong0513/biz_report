package com.ruoyi.report.contract.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dingtalkworkflow_1_0.models.GetManageProcessByStaffIdHeaders;
import com.aliyun.dingtalkworkflow_1_0.models.GetManageProcessByStaffIdRequest;
import com.aliyun.dingtalkworkflow_1_0.models.GetManageProcessByStaffIdResponse;
import com.aliyun.dingtalkworkflow_1_0.models.GetManageProcessByStaffIdResponseBody;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.Client;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.ruoyi.common.utils.DateUtils;
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

    /**
     * 查询合同管理
     * 
     * @param goodsId 合同管理主键
     * @return 合同管理
     */
    @Override
    public ContractContentInfo selectContractContentInfoByGoodsId(String goodsId)
    {
        return contractContentInfoMapper.selectContractContentInfoByGoodsId(goodsId);
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
     * @param goodsIds 需要删除的合同管理主键
     * @return 结果
     */
    @Override
    public int deleteContractContentInfoByGoodsIds(String[] goodsIds)
    {
        return contractContentInfoMapper.deleteContractContentInfoByGoodsIds(goodsIds);
    }

    /**
     * 删除合同管理信息
     * 
     * @param goodsId 合同管理主键
     * @return 结果
     */
    @Override
    public int deleteContractContentInfoByGoodsId(String goodsId)
    {
        return contractContentInfoMapper.deleteContractContentInfoByGoodsId(goodsId);
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
        getCurrentEnterpriseAllMgrForm(accessToken);

        return 0;
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
}
