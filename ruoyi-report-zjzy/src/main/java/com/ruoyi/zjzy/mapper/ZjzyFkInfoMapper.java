package com.ruoyi.zjzy.mapper;

import java.util.List;
import com.ruoyi.zjzy.domain.ZjzyFkInfo;

/**
 * 付款Mapper接口
 * 
 * @author changgong0513
 * @date 2022-12-06
 */
public interface ZjzyFkInfoMapper 
{
    /**
     * 查询付款
     * 
     * @param fkId 付款主键
     * @return 付款
     */
    public ZjzyFkInfo selectZjzyFkInfoByFkId(String fkId);

    /**
     * 根据业务编号，查询付款数据
     *
     * @param fkBusinessId 付款主键
     * @return 付款
     */
    public ZjzyFkInfo selectZjzyFkInfoByFkBusinessId(String fkBusinessId);

    /**
     * 查询付款列表
     * 
     * @param zjzyFkInfo 付款
     * @return 付款集合
     */
    public List<ZjzyFkInfo> selectZjzyFkInfoList(ZjzyFkInfo zjzyFkInfo);

    /**
     * 取得付款总金额
     *
     * @return 结果
     */
    public double getFkrlTotal();

    /**
     * 新增付款
     * 
     * @param zjzyFkInfo 付款
     * @return 结果
     */
    public int insertZjzyFkInfo(ZjzyFkInfo zjzyFkInfo);

    /**
     * 修改付款
     * 
     * @param zjzyFkInfo 付款
     * @return 结果
     */
    public int updateZjzyFkInfo(ZjzyFkInfo zjzyFkInfo);

    /**
     * 删除付款
     * 
     * @param fkId 付款主键
     * @return 结果
     */
    public int deleteZjzyFkInfoByFkId(String fkId);

    /**
     * 批量删除付款
     * 
     * @param fkIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZjzyFkInfoByFkIds(String[] fkIds);
}
