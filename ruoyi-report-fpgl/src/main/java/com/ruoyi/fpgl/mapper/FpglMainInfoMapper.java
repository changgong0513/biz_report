package com.ruoyi.fpgl.mapper;

import java.util.HashMap;
import java.util.List;

import com.ruoyi.fpgl.domain.FpglListInfo;
import com.ruoyi.fpgl.domain.FpglMainInfo;

/**
 * 发票管理Mapper接口
 * 
 * @author changgong0513
 * @date 2022-11-06
 */
public interface FpglMainInfoMapper 
{
    /**
     * 查询发票管理
     * 
     * @param fpglDdbh 发票管理主键
     * @return 发票管理
     */
    public FpglMainInfo selectFpglMainInfoByFpglDdbh(String fpglDdbh);

    /**
     * 查询发票管理列表
     * 
     * @param fpglMainInfo 发票管理
     * @return 发票管理集合
     */
    public List<FpglMainInfo> selectFpglMainInfoList(FpglMainInfo fpglMainInfo);

    /**
     *
     *
     * @return
     */
    public List<FpglListInfo> selectFpglList(FpglListInfo fpglListInfo);

    /**
     * 登录用户角色为财务的场合，取得发票明细列表.
     *
     * @param fpglListInfo 查询发票明细列表条件值
     * @return
     */
    public List<FpglListInfo> selectFpglListForCw(FpglListInfo fpglListInfo);

    /**
     * 新增发票管理
     * 
     * @param fpglMainInfo 发票管理
     * @return 结果
     */
    public int insertFpglMainInfo(FpglMainInfo fpglMainInfo);

    /**
     * 修改发票管理
     * 
     * @param fpglMainInfo 发票管理
     * @return 结果
     */
    public int updateFpglMainInfo(FpglMainInfo fpglMainInfo);

    /**
     * 删除发票管理
     * 
     * @param fpglId 发票管理主键
     * @return 结果
     */
    public int deleteFpglMainInfoByFpglId(String fpglId);

    /**
     * 批量删除发票管理
     * 
     * @param fpglIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFpglMainInfoByFpglIds(String[] fpglIds);
}
