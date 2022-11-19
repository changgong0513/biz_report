package com.ruoyi.purchase.sale.mapper;

import java.util.List;
import com.ruoyi.purchase.sale.domain.PurchaseReceiptInfo;

/**
 * 收货管理Mapper接口
 * 
 * @author changgong0513
 * @date 2022-11-05
 */
public interface PurchaseReceiptInfoMapper 
{
    /**
     * 查询收货管理
     * 
     * @param receiptId 收货管理主键
     * @return 收货管理
     */
    public PurchaseReceiptInfo selectPurchaseReceiptInfoByReceiptId(String receiptId);

    /**
     * 取得最大的收货编号
     *
     * @return 收货管理
     */
    public PurchaseReceiptInfo selectMaxReceiptId();

    /**
     * 查询收货管理列表
     * 
     * @param purchaseReceiptInfo 收货管理
     * @return 收货管理集合
     */
    public List<PurchaseReceiptInfo> selectPurchaseReceiptInfoList(PurchaseReceiptInfo purchaseReceiptInfo);

    /**
     * 新增收货管理
     * 
     * @param purchaseReceiptInfo 收货管理
     * @return 结果
     */
    public int insertPurchaseReceiptInfo(PurchaseReceiptInfo purchaseReceiptInfo);

    /**
     * 修改收货管理
     * 
     * @param purchaseReceiptInfo 收货管理
     * @return 结果
     */
    public int updatePurchaseReceiptInfo(PurchaseReceiptInfo purchaseReceiptInfo);

    /**
     * 删除收货管理
     * 
     * @param receiptId 收货管理主键
     * @return 结果
     */
    public int deletePurchaseReceiptInfoByReceiptId(String receiptId);

    /**
     * 批量删除收货管理
     * 
     * @param receiptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseReceiptInfoByReceiptIds(String[] receiptIds);
}
