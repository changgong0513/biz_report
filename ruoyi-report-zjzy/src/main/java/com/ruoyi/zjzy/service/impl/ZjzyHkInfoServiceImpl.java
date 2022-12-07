package com.ruoyi.zjzy.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.report.masterdata.domain.MasterDataClientInfo;
import com.ruoyi.report.masterdata.service.IMasterDataClientInfoService;
import com.ruoyi.zjzy.domain.ZjzyHkInfo;
import com.ruoyi.zjzy.mapper.ZjzyHkInfoMapper;
import com.ruoyi.zjzy.service.IZjzyHkInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

/**
 * 回款认领Service业务层处理
 * 
 * @author changgong0513
 * @date 2022-12-04
 */
@Service
public class ZjzyHkInfoServiceImpl implements IZjzyHkInfoService {

    private static final Logger log = LoggerFactory.getLogger(ZjzyHkInfoServiceImpl.class);

    @Autowired
    private ZjzyHkInfoMapper zjzyHkInfoMapper;

    @Autowired
    private IMasterDataClientInfoService masterDataClientInfoService;

    @Autowired
    protected Validator validator;

    /**
     * 查询回款认领
     * 
     * @param hkId 回款认领主键
     * @return 回款认领
     */
    @Override
    public ZjzyHkInfo selectZjzyHkInfoByHkId(String hkId)
    {
        return zjzyHkInfoMapper.selectZjzyHkInfoByHkId(hkId);
    }

    /**
     * 查询回款认领列表
     * 
     * @param zjzyHkInfo 回款认领
     * @return 回款认领
     */
    @Override
    public List<ZjzyHkInfo> selectZjzyHkInfoList(ZjzyHkInfo zjzyHkInfo)
    {
        return zjzyHkInfoMapper.selectZjzyHkInfoList(zjzyHkInfo);
    }

    /**
     * 取得回款总金额
     *
     * @return 结果
     */
    public double getHkrlTotal() {
        return zjzyHkInfoMapper.getHkrlTotal();
    }

    /**
     * 新增回款认领
     * 
     * @param zjzyHkInfo 回款认领
     * @return 结果
     */
    @Override
    public int insertZjzyHkInfo(ZjzyHkInfo zjzyHkInfo)
    {
        zjzyHkInfo.setCreateTime(DateUtils.getNowDate());
        return zjzyHkInfoMapper.insertZjzyHkInfo(zjzyHkInfo);
    }

    /**
     * 修改回款认领
     * 
     * @param zjzyHkInfo 回款认领
     * @return 结果
     */
    @Override
    public int updateZjzyHkInfo(ZjzyHkInfo zjzyHkInfo)
    {
        zjzyHkInfo.setUpdateTime(DateUtils.getNowDate());
        return zjzyHkInfoMapper.updateZjzyHkInfo(zjzyHkInfo);
    }

    /**
     * 批量删除回款认领
     * 
     * @param hkIds 需要删除的回款认领主键
     * @return 结果
     */
    @Override
    public int deleteZjzyHkInfoByHkIds(String[] hkIds)
    {
        return zjzyHkInfoMapper.deleteZjzyHkInfoByHkIds(hkIds);
    }

    /**
     * 删除回款认领信息
     * 
     * @param hkId 回款认领主键
     * @return 结果
     */
    @Override
    public int deleteZjzyHkInfoByHkId(String hkId)
    {
        return zjzyHkInfoMapper.deleteZjzyHkInfoByHkId(hkId);
    }

    /**
     * 导入回款数据
     *
     * @param hkList 回款数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return
     */
    @Override
    public String importHkData(List<ZjzyHkInfo> hkList, Boolean isUpdateSupport, String operName) {

        if (StringUtils.isNull(hkList) || hkList.size() == 0) {
            throw new ServiceException("导入回款数据不能为空！");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        MasterDataClientInfo masterDataClientInfo = new MasterDataClientInfo();

        for (ZjzyHkInfo hkData : hkList) {

            try {
                BeanValidators.validateWithException(validator, hkData);
                masterDataClientInfo.setCompanyName(hkData.getHkKhmc());
                List<MasterDataClientInfo> khList = masterDataClientInfoService.selectMasterDataClientInfoList(masterDataClientInfo);
                hkData.setHkKhbh(khList.get(0).getBaseId());
                hkData.setBizVersion(1L);
                hkData.setCreateTime(DateUtils.getNowDate());
                hkData.setUpdateTime(DateUtils.getNowDate());
                hkData.setCreateBy(operName);
                hkData.setUpdateBy(operName);
                insertZjzyHkInfo(hkData);
                successNum++;
                successMsg.append("<br/>" + successNum + "、账号 " + hkData.getHkKhmc() + " 导入成功");

            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、合同 " + hkData.getHkKhmc() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }

        return successMsg.toString();
    }
}
