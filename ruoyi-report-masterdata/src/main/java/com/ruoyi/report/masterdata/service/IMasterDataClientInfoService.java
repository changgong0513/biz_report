package com.ruoyi.report.masterdata.service;

import com.ruoyi.report.masterdata.domain.MasterDataClientInfo;

import java.util.List;

/**
 * 业务报表Service接口
 *
 * @author ruoyi
 * @date 2022-09-16
 */
public interface IMasterDataClientInfoService
{
    /**
     * 查询业务报表
     *
     * @param baseId 业务报表主键
     * @return 业务报表
     */
    public MasterDataClientInfo selectMasterDataClientInfoByBaseId(String baseId);

    /**
     * 查询业务报表列表
     *
     * @param masterDataClientInfo 业务报表
     * @return 业务报表集合
     */
    public List<MasterDataClientInfo> selectMasterDataClientInfoList(MasterDataClientInfo masterDataClientInfo);

    /**
     * 新增业务报表
     *
     * @param masterDataClientInfo 业务报表
     * @return 结果
     */
    public int insertMasterDataClientInfo(MasterDataClientInfo masterDataClientInfo);

    /**
     * 修改业务报表
     *
     * @param masterDataClientInfo 业务报表
     * @return 结果
     */
    public int updateMasterDataClientInfo(MasterDataClientInfo masterDataClientInfo);

    /**
     * 批量删除业务报表
     *
     * @param baseIds 需要删除的业务报表主键集合
     * @return 结果
     */
    public int deleteMasterDataClientInfoByBaseIds(String[] baseIds);

    /**
     * 删除业务报表信息
     *
     * @param baseId 业务报表主键
     * @return 结果
     */
    public int deleteMasterDataClientInfoByBaseId(String baseId);
}

