package com.ruoyi.purchase.sale.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.purchase.sale.domain.PurchaseReceiptInfo;
import com.ruoyi.purchase.sale.mapper.PurchaseReceiptInfoMapper;
import com.ruoyi.purchase.sale.service.IPurchaseReceiptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 收货管理Service业务层处理
 * 
 * @author changgong0513
 * @date 2022-11-05
 */
@Service
public class PurchaseReceiptInfoServiceImpl implements IPurchaseReceiptInfoService
{
    @Autowired
    private PurchaseReceiptInfoMapper purchaseReceiptInfoMapper;

    /**
     * 查询收货管理
     * 
     * @param receiptId 收货管理主键
     * @return 收货管理
     */
    @Override
    public PurchaseReceiptInfo selectPurchaseReceiptInfoByReceiptId(String receiptId)
    {
        return purchaseReceiptInfoMapper.selectPurchaseReceiptInfoByReceiptId(receiptId);
    }

    /**
     * 查询收货管理列表
     * 
     * @param purchaseReceiptInfo 收货管理
     * @return 收货管理
     */
    @Override
    public List<PurchaseReceiptInfo> selectPurchaseReceiptInfoList(PurchaseReceiptInfo purchaseReceiptInfo)
    {
        return purchaseReceiptInfoMapper.selectPurchaseReceiptInfoList(purchaseReceiptInfo);
    }

    /**
     * 新增收货管理
     * 
     * @param purchaseReceiptInfo 收货管理
     * @return 结果
     */
    @Override
    public int insertPurchaseReceiptInfo(PurchaseReceiptInfo purchaseReceiptInfo)
    {
        purchaseReceiptInfo.setCreateTime(DateUtils.getNowDate());
        return purchaseReceiptInfoMapper.insertPurchaseReceiptInfo(purchaseReceiptInfo);
    }

    /**
     * 修改收货管理
     * 
     * @param purchaseReceiptInfo 收货管理
     * @return 结果
     */
    @Override
    public int updatePurchaseReceiptInfo(PurchaseReceiptInfo purchaseReceiptInfo)
    {
        purchaseReceiptInfo.setUpdateTime(DateUtils.getNowDate());
        return purchaseReceiptInfoMapper.updatePurchaseReceiptInfo(purchaseReceiptInfo);
    }

    /**
     * 批量删除收货管理
     * 
     * @param receiptIds 需要删除的收货管理主键
     * @return 结果
     */
    @Override
    public int deletePurchaseReceiptInfoByReceiptIds(String[] receiptIds)
    {
        return purchaseReceiptInfoMapper.deletePurchaseReceiptInfoByReceiptIds(receiptIds);
    }

    /**
     * 删除收货管理信息
     * 
     * @param receiptId 收货管理主键
     * @return 结果
     */
    @Override
    public int deletePurchaseReceiptInfoByReceiptId(String receiptId)
    {
        return purchaseReceiptInfoMapper.deletePurchaseReceiptInfoByReceiptId(receiptId);
    }
}
