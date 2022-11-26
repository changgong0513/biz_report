package com.ruoyi.purchase.sale.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.purchase.sale.mapper.SaleDeliverInfoMapper;
import com.ruoyi.purchase.sale.domain.SaleDeliverInfo;
import com.ruoyi.purchase.sale.service.ISaleDeliverInfoService;

/**
 * 发货管理Service业务层处理
 * 
 * @author chanagong0513
 * @date 2022-11-05
 */
@Service
public class SaleDeliverInfoServiceImpl implements ISaleDeliverInfoService 
{
    @Autowired
    private SaleDeliverInfoMapper saleDeliverInfoMapper;

    /**
     * 查询发货管理
     * 
     * @param deliverId 发货管理主键
     * @return 发货管理
     */
    @Override
    public SaleDeliverInfo selectSaleDeliverInfoByDeliverId(String deliverId)
    {
        return saleDeliverInfoMapper.selectSaleDeliverInfoByDeliverId(deliverId);
    }

    /**
     * 查询发货管理列表
     * 
     * @param saleDeliverInfo 发货管理
     * @return 发货管理
     */
    @Override
    public List<SaleDeliverInfo> selectSaleDeliverInfoList(SaleDeliverInfo saleDeliverInfo)
    {
        return saleDeliverInfoMapper.selectSaleDeliverInfoList(saleDeliverInfo);
    }

    /**
     * 取得最大的发货编号
     *
     * @return 发货管理
     */
    public SaleDeliverInfo selectMaxDeliverId() {
        return saleDeliverInfoMapper.selectMaxDeliverId();
    }

    /**
     * 新增发货管理
     * 
     * @param saleDeliverInfo 发货管理
     * @return 结果
     */
    @Override
    public int insertSaleDeliverInfo(SaleDeliverInfo saleDeliverInfo)
    {
        saleDeliverInfo.setCreateTime(DateUtils.getNowDate());
        return saleDeliverInfoMapper.insertSaleDeliverInfo(saleDeliverInfo);
    }

    /**
     * 修改发货管理
     * 
     * @param saleDeliverInfo 发货管理
     * @return 结果
     */
    @Override
    public int updateSaleDeliverInfo(SaleDeliverInfo saleDeliverInfo)
    {
        saleDeliverInfo.setUpdateTime(DateUtils.getNowDate());
        return saleDeliverInfoMapper.updateSaleDeliverInfo(saleDeliverInfo);
    }

    /**
     * 批量删除发货管理
     * 
     * @param deliverIds 需要删除的发货管理主键
     * @return 结果
     */
    @Override
    public int deleteSaleDeliverInfoByDeliverIds(String[] deliverIds)
    {
        return saleDeliverInfoMapper.deleteSaleDeliverInfoByDeliverIds(deliverIds);
    }

    /**
     * 删除发货管理信息
     * 
     * @param deliverId 发货管理主键
     * @return 结果
     */
    @Override
    public int deleteSaleDeliverInfoByDeliverId(String deliverId)
    {
        return saleDeliverInfoMapper.deleteSaleDeliverInfoByDeliverId(deliverId);
    }
}
