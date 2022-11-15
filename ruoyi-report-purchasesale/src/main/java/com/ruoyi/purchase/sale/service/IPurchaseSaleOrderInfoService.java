package com.ruoyi.purchase.sale.service;

import java.util.List;
import com.ruoyi.purchase.sale.domain.PurchaseSaleOrderInfo;

/**
 * 采购收货销售发货管理Service接口
 * 
 * @author changgong0513
 * @date 2022-11-03
 */
public interface IPurchaseSaleOrderInfoService 
{
    /**
     * 查询采购收货销售发货管理
     * 
     * @param contractId 采购收货销售发货管理主键
     * @return 采购收货销售发货管理
     */
    public PurchaseSaleOrderInfo selectPurchaseSaleOrderInfoByContractId(String contractId);

    /**
     * 查询采购收货销售发货管理列表
     * 
     * @param purchaseSaleOrderInfo 采购收货销售发货管理
     * @return 采购收货销售发货管理集合
     */
    public List<PurchaseSaleOrderInfo> selectPurchaseSaleOrderInfoList(PurchaseSaleOrderInfo purchaseSaleOrderInfo);

    /**
     * 查询采购收货列表
     *
     * @param purchaseSaleOrderInfo 采购收货销售发货管理
     * @return 采购收货集合
     */
    public List<PurchaseSaleOrderInfo> selectPurchaseOrderInfoUnionList(PurchaseSaleOrderInfo purchaseSaleOrderInfo);

    /**
     * 查询销售发货管理列表
     *
     * @param purchaseSaleOrderInfo 采购收货销售发货管理
     * @return 销售发货集合
     */
    public List<PurchaseSaleOrderInfo> selectSaleOrderInfoUnionList(PurchaseSaleOrderInfo purchaseSaleOrderInfo);

    /**
     * 新增采购收货销售发货管理
     * 
     * @param purchaseSaleOrderInfo 采购收货销售发货管理
     * @return 结果
     */
    public int insertPurchaseSaleOrderInfo(PurchaseSaleOrderInfo purchaseSaleOrderInfo);

    /**
     * 修改采购收货销售发货管理
     * 
     * @param purchaseSaleOrderInfo 采购收货销售发货管理
     * @return 结果
     */
    public int updatePurchaseSaleOrderInfo(PurchaseSaleOrderInfo purchaseSaleOrderInfo);

    /**
     * 批量删除采购收货销售发货管理
     * 
     * @param orderIds 需要删除的采购收货销售发货管理主键集合
     * @return 结果
     */
    public int deletePurchaseSaleOrderInfoByOrderIds(String[] orderIds);

    /**
     * 删除采购收货销售发货管理信息
     * 
     * @param orderId 采购收货销售发货管理主键
     * @return 结果
     */
    public int deletePurchaseSaleOrderInfoByOrderId(String orderId);
}
