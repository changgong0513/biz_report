package com.ruoyi.zjzy.service;

import java.util.List;
import com.ruoyi.zjzy.domain.ZjzyHkInfo;

/**
 * 回款认领Service接口
 * 
 * @author changgong0513
 * @date 2022-12-04
 */
public interface IZjzyHkInfoService 
{
    /**
     * 查询回款认领
     * 
     * @param hkId 回款认领主键
     * @return 回款认领
     */
    public ZjzyHkInfo selectZjzyHkInfoByHkId(String hkId);

    /**
     * 查询回款认领列表
     * 
     * @param zjzyHkInfo 回款认领
     * @return 回款认领集合
     */
    public List<ZjzyHkInfo> selectZjzyHkInfoList(ZjzyHkInfo zjzyHkInfo);

    /**
     * 新增回款认领
     * 
     * @param zjzyHkInfo 回款认领
     * @return 结果
     */
    public int insertZjzyHkInfo(ZjzyHkInfo zjzyHkInfo);

    /**
     * 修改回款认领
     * 
     * @param zjzyHkInfo 回款认领
     * @return 结果
     */
    public int updateZjzyHkInfo(ZjzyHkInfo zjzyHkInfo);

    /**
     * 批量删除回款认领
     * 
     * @param hkIds 需要删除的回款认领主键集合
     * @return 结果
     */
    public int deleteZjzyHkInfoByHkIds(String[] hkIds);

    /**
     * 删除回款认领信息
     * 
     * @param hkId 回款认领主键
     * @return 结果
     */
    public int deleteZjzyHkInfoByHkId(String hkId);

    /**
     * 导入回款数据
     *
     * @param hkList 回款数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importHkData(List<ZjzyHkInfo> hkList, Boolean isUpdateSupport, String operName);
}
