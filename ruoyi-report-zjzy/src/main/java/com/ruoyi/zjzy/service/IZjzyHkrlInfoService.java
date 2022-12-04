package com.ruoyi.zjzy.service;

import com.ruoyi.zjzy.domain.ZjzyHkrlInfo;

import java.util.List;

/**
 * 回款认领Service接口
 * 
 * @author changgong0513
 * @date 2022-12-04
 */
public interface IZjzyHkrlInfoService 
{
    /**
     * 查询回款认领
     * 
     * @param hkrlId 回款认领主键
     * @return 回款认领
     */
    public ZjzyHkrlInfo selectZjzyHkrlInfoByHkrlId(Long hkrlId);

    /**
     * 查询回款认领列表
     * 
     * @param zjzyHkrlInfo 回款认领
     * @return 回款认领集合
     */
    public List<ZjzyHkrlInfo> selectZjzyHkrlInfoList(ZjzyHkrlInfo zjzyHkrlInfo);

    /**
     * 新增回款认领
     * 
     * @param zjzyHkrlInfo 回款认领
     * @return 结果
     */
    public int insertZjzyHkrlInfo(ZjzyHkrlInfo zjzyHkrlInfo);

    /**
     * 修改回款认领
     * 
     * @param zjzyHkrlInfo 回款认领
     * @return 结果
     */
    public int updateZjzyHkrlInfo(ZjzyHkrlInfo zjzyHkrlInfo);

    /**
     * 批量删除回款认领
     * 
     * @param hkrlIds 需要删除的回款认领主键集合
     * @return 结果
     */
    public int deleteZjzyHkrlInfoByHkrlIds(Long[] hkrlIds);

    /**
     * 删除回款认领信息
     * 
     * @param hkrlId 回款认领主键
     * @return 结果
     */
    public int deleteZjzyHkrlInfoByHkrlId(Long hkrlId);
}
