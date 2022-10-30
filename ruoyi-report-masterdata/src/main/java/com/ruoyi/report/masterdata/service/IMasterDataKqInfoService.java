package com.ruoyi.report.masterdata.service;

import java.util.List;
import com.ruoyi.report.masterdata.domain.MasterDataKqInfo;

/**
 * WarehouseService接口
 * 
 * @author changgong0513
 * @date 2022-10-30
 */
public interface IMasterDataKqInfoService 
{
    /**
     * 查询Warehouse
     * 
     * @param kqCode Warehouse主键
     * @return Warehouse
     */
    public MasterDataKqInfo selectMasterDataKqInfoByKqCode(String kqCode);

    /**
     * 查询Warehouse列表
     * 
     * @param masterDataKqInfo Warehouse
     * @return Warehouse集合
     */
    public List<MasterDataKqInfo> selectMasterDataKqInfoList(MasterDataKqInfo masterDataKqInfo);

    /**
     * 新增Warehouse
     * 
     * @param masterDataKqInfo Warehouse
     * @return 结果
     */
    public int insertMasterDataKqInfo(MasterDataKqInfo masterDataKqInfo);

    /**
     * 修改Warehouse
     * 
     * @param masterDataKqInfo Warehouse
     * @return 结果
     */
    public int updateMasterDataKqInfo(MasterDataKqInfo masterDataKqInfo);

    /**
     * 批量删除Warehouse
     * 
     * @param kqCodes 需要删除的Warehouse主键集合
     * @return 结果
     */
    public int deleteMasterDataKqInfoByKqCodes(String[] kqCodes);

    /**
     * 删除Warehouse信息
     * 
     * @param kqCode Warehouse主键
     * @return 结果
     */
    public int deleteMasterDataKqInfoByKqCode(String kqCode);
}
