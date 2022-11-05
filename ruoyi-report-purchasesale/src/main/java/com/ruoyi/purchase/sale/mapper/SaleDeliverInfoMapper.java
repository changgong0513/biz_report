package com.ruoyi.purchase.sale.mapper;

import java.util.List;
import com.ruoyi.purchase.sale.domain.SaleDeliverInfo;

/**
 * 发货管理Mapper接口
 * 
 * @author chanagong0513
 * @date 2022-11-05
 */
public interface SaleDeliverInfoMapper 
{
    /**
     * 查询发货管理
     * 
     * @param deliverId 发货管理主键
     * @return 发货管理
     */
    public SaleDeliverInfo selectSaleDeliverInfoByDeliverId(String deliverId);

    /**
     * 查询发货管理列表
     * 
     * @param saleDeliverInfo 发货管理
     * @return 发货管理集合
     */
    public List<SaleDeliverInfo> selectSaleDeliverInfoList(SaleDeliverInfo saleDeliverInfo);

    /**
     * 新增发货管理
     * 
     * @param saleDeliverInfo 发货管理
     * @return 结果
     */
    public int insertSaleDeliverInfo(SaleDeliverInfo saleDeliverInfo);

    /**
     * 修改发货管理
     * 
     * @param saleDeliverInfo 发货管理
     * @return 结果
     */
    public int updateSaleDeliverInfo(SaleDeliverInfo saleDeliverInfo);

    /**
     * 删除发货管理
     * 
     * @param deliverId 发货管理主键
     * @return 结果
     */
    public int deleteSaleDeliverInfoByDeliverId(String deliverId);

    /**
     * 批量删除发货管理
     * 
     * @param deliverIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSaleDeliverInfoByDeliverIds(String[] deliverIds);
}
