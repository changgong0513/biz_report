package com.ruoyi.purchase.sale.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 发货管理对象 sale_deliver_info
 * 
 * @author chanagong0513
 * @date 2022-11-05
 */
public class SaleDeliverInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 发货编号 */
    @Excel(name = "发货编号")
    private String deliverId;

    /** 销售订单编号 */
    @Excel(name = "销售订单编号")
    private String saleOrderId;

    /** 销售合同编号 */
    private String saleContractId;

    /** 经办人 */
    @Excel(name = "经办人")
    private String handledBy;

    /** 发货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliverDate;

    /** 客户编号 */
    private String clientId;

    /** 客户名称 */
    private String clientName;

    /** 物料编号 */
    private Long materialId;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 合同单价 */
    private Long contractPrice;

    /** 计量单位 */
    private String measurementUnit;

    /** 发货方式 */
    private String deliverMode;

    /** 仓库编码 */
    private String warehouseCode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /** 发货数量 */
    private Long deliverQuantity;

    /** 核算数量 */
    private Long checkQuantity;

    /** 核算单价 */
    private Long checkPrice;

    /** 核算金额 */
    private Long checkMoney;

    /** 货损数量 */
    private Long cargoDamageQuantity;

    /** 货损金额 */
    private Long cargoDamageMoney;

    /** 运输方式 */
    private String transportMode;

    /** 运输单号 */
    private String transportNumber;

    /** 运费金额 */
    private Long transportMoney;

    /** 其他金额 */
    private Long otherMoney;

    /** 预期到货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expectArrivalDate;

    /** 要求到货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date requireArrivalDate;

    /** 账期 */
    private int accountPeriod;

    /** 备注 */
    private String deliverRemark;

    /** 版本号 */
    private Long bizVersion;

    public void setDeliverId(String deliverId) 
    {
        this.deliverId = deliverId;
    }

    public String getDeliverId() 
    {
        return deliverId;
    }
    public void setSaleOrderId(String saleOrderId) 
    {
        this.saleOrderId = saleOrderId;
    }

    public String getSaleOrderId() 
    {
        return saleOrderId;
    }
    public void setSaleContractId(String saleContractId) 
    {
        this.saleContractId = saleContractId;
    }

    public String getSaleContractId() 
    {
        return saleContractId;
    }
    public void setHandledBy(String handledBy) 
    {
        this.handledBy = handledBy;
    }

    public String getHandledBy() 
    {
        return handledBy;
    }
    public void setDeliverDate(Date deliverDate) 
    {
        this.deliverDate = deliverDate;
    }

    public Date getDeliverDate() 
    {
        return deliverDate;
    }
    public void setClientId(String clientId) 
    {
        this.clientId = clientId;
    }

    public String getClientId() 
    {
        return clientId;
    }
    public void setClientName(String clientName) 
    {
        this.clientName = clientName;
    }

    public String getClientName() 
    {
        return clientName;
    }
    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }
    public void setMaterialName(String materialName) 
    {
        this.materialName = materialName;
    }

    public String getMaterialName() 
    {
        return materialName;
    }
    public void setContractPrice(Long contractPrice) 
    {
        this.contractPrice = contractPrice;
    }

    public Long getContractPrice() 
    {
        return contractPrice;
    }
    public void setMeasurementUnit(String measurementUnit) 
    {
        this.measurementUnit = measurementUnit;
    }

    public String getMeasurementUnit() 
    {
        return measurementUnit;
    }
    public void setDeliverMode(String deliverMode) 
    {
        this.deliverMode = deliverMode;
    }

    public String getDeliverMode() 
    {
        return deliverMode;
    }
    public void setWarehouseCode(String warehouseCode) 
    {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCode() 
    {
        return warehouseCode;
    }
    public void setWarehouseName(String warehouseName) 
    {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName() 
    {
        return warehouseName;
    }
    public void setDeliverQuantity(Long deliverQuantity) 
    {
        this.deliverQuantity = deliverQuantity;
    }

    public Long getDeliverQuantity() 
    {
        return deliverQuantity;
    }
    public void setCheckQuantity(Long checkQuantity) 
    {
        this.checkQuantity = checkQuantity;
    }

    public Long getCheckQuantity() 
    {
        return checkQuantity;
    }
    public void setCheckPrice(Long checkPrice) 
    {
        this.checkPrice = checkPrice;
    }

    public Long getCheckPrice() 
    {
        return checkPrice;
    }
    public void setCheckMoney(Long checkMoney) 
    {
        this.checkMoney = checkMoney;
    }

    public Long getCheckMoney() 
    {
        return checkMoney;
    }
    public void setCargoDamageQuantity(Long cargoDamageQuantity) 
    {
        this.cargoDamageQuantity = cargoDamageQuantity;
    }

    public Long getCargoDamageQuantity() 
    {
        return cargoDamageQuantity;
    }
    public void setCargoDamageMoney(Long cargoDamageMoney) 
    {
        this.cargoDamageMoney = cargoDamageMoney;
    }

    public Long getCargoDamageMoney() 
    {
        return cargoDamageMoney;
    }
    public void setTransportMode(String transportMode) 
    {
        this.transportMode = transportMode;
    }

    public String getTransportMode() 
    {
        return transportMode;
    }
    public void setTransportNumber(String transportNumber) 
    {
        this.transportNumber = transportNumber;
    }

    public String getTransportNumber() 
    {
        return transportNumber;
    }
    public void setTransportMoney(Long transportMoney) 
    {
        this.transportMoney = transportMoney;
    }

    public Long getTransportMoney() 
    {
        return transportMoney;
    }
    public void setOtherMoney(Long otherMoney) 
    {
        this.otherMoney = otherMoney;
    }

    public Long getOtherMoney() 
    {
        return otherMoney;
    }
    public void setExpectArrivalDate(Date expectArrivalDate) 
    {
        this.expectArrivalDate = expectArrivalDate;
    }

    public Date getExpectArrivalDate() 
    {
        return expectArrivalDate;
    }
    public void setRequireArrivalDate(Date requireArrivalDate) 
    {
        this.requireArrivalDate = requireArrivalDate;
    }

    public Date getRequireArrivalDate() 
    {
        return requireArrivalDate;
    }

    public void setAccountPeriod(int accountPeriod) { this.accountPeriod = accountPeriod; }

    public int getAccountPeriod()
    {
        return accountPeriod;
    }

    public void setDeliverRemark(String deliverRemark)
    {
        this.deliverRemark = deliverRemark;
    }

    public String getDeliverRemark()
    {
        return deliverRemark;
    }
    public void setBizVersion(Long bizVersion) 
    {
        this.bizVersion = bizVersion;
    }

    public Long getBizVersion() 
    {
        return bizVersion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deliverId", getDeliverId())
            .append("saleOrderId", getSaleOrderId())
            .append("saleContractId", getSaleContractId())
            .append("handledBy", getHandledBy())
            .append("deliverDate", getDeliverDate())
            .append("clientId", getClientId())
            .append("clientName", getClientName())
            .append("materialId", getMaterialId())
            .append("materialName", getMaterialName())
            .append("contractPrice", getContractPrice())
            .append("measurementUnit", getMeasurementUnit())
            .append("deliverMode", getDeliverMode())
            .append("warehouseCode", getWarehouseCode())
            .append("warehouseName", getWarehouseName())
            .append("deliverQuantity", getDeliverQuantity())
            .append("checkQuantity", getCheckQuantity())
            .append("checkPrice", getCheckPrice())
            .append("checkMoney", getCheckMoney())
            .append("cargoDamageQuantity", getCargoDamageQuantity())
            .append("cargoDamageMoney", getCargoDamageMoney())
            .append("transportMode", getTransportMode())
            .append("transportNumber", getTransportNumber())
            .append("transportMoney", getTransportMoney())
            .append("otherMoney", getOtherMoney())
            .append("expectArrivalDate", getExpectArrivalDate())
            .append("requireArrivalDate", getRequireArrivalDate())
                .append("accountPeriod", getAccountPeriod())
            .append("receiptRemark", getDeliverRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
