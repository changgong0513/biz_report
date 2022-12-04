package com.ruoyi.report.masterdata.service;

import java.util.List;
import com.ruoyi.report.masterdata.domain.MasterdataPchInfo;

/**
 * 批次号管理Service接口
 * 
 * @author changgong
 * @date 2022-12-04
 */
public interface IMasterdataPchInfoService 
{
    /**
     * 查询批次号管理
     * 
     * @param id 批次号管理主键
     * @return 批次号管理
     */
    public MasterdataPchInfo selectMasterdataPchInfoById(String id);

    /**
     * 查询批次号管理列表
     * 
     * @param masterdataPchInfo 批次号管理
     * @return 批次号管理集合
     */
    public List<MasterdataPchInfo> selectMasterdataPchInfoList(MasterdataPchInfo masterdataPchInfo);

    /**
     * 新增批次号管理
     * 
     * @param masterdataPchInfo 批次号管理
     * @return 结果
     */
    public int insertMasterdataPchInfo(MasterdataPchInfo masterdataPchInfo);

    /**
     * 修改批次号管理
     * 
     * @param masterdataPchInfo 批次号管理
     * @return 结果
     */
    public int updateMasterdataPchInfo(MasterdataPchInfo masterdataPchInfo);

    /**
     * 批量删除批次号管理
     * 
     * @param ids 需要删除的批次号管理主键集合
     * @return 结果
     */
    public int deleteMasterdataPchInfoByIds(String[] ids);

    /**
     * 删除批次号管理信息
     * 
     * @param id 批次号管理主键
     * @return 结果
     */
    public int deleteMasterdataPchInfoById(String id);
}
