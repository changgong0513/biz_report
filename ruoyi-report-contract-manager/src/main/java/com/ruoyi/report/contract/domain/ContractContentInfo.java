package com.ruoyi.report.contract.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 合同管理对象 contract_content_info
 * 
 * @author changgong
 * @date 2022-10-30
 */
public class ContractContentInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 货物编号 */
    private String goodsId;

    /** 货物名称 */
    private String goodsName;

    /** 合同类型 */
    private String contractType;

    /** 合同名称 */
    @Excel(name = "合同名称")
    private String contractName;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractId;

    /** 签约日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签约日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signDate;

    /** 交货日期 */
    private Date deliveryDate;

    /** 我方单位名称 */
    private String ourCompanyName;

    /** 我方负责人 */
    private String ourPrincipal;

    /** 对方单位名称 */
    @Excel(name = "对方单位名称")
    private String oppositeCompanyName;

    /** 对方负责人 */
    private String oppositePrincipal;

    /** 合同数量 */
    private String contractQuantity;

    /** 合同单价 */
    private BigDecimal contractPrice;

    /** 合同总价 */
    @Excel(name = "合同总价")
    private BigDecimal contractTotal;

    /** 账期 */
    private String accountingPeriod;

    /** 交货方式 */
    private String deliveryMethod;

    /** 港口到厂运费 */
    private BigDecimal portToFactoryFare;

    /** 港口到港口运费 */
    private BigDecimal portToPortFare;

    /** 其他 */
    private String contractOther;

    /** 代理或合作方 */
    private String contractAgent;

    /** 合同备注 */
    private String contractRemark;

    /** 合同状态 */
    private String contractStatus;

    /** 版本号 */
    private Long bizVersion;

    public void setGoodsId(String goodsId) 
    {
        this.goodsId = goodsId;
    }

    public String getGoodsId() 
    {
        return goodsId;
    }
    public void setGoodsName(String goodsName) 
    {
        this.goodsName = goodsName;
    }

    public String getGoodsName() 
    {
        return goodsName;
    }
    public void setContractType(String contractType) 
    {
        this.contractType = contractType;
    }

    public String getContractType() 
    {
        return contractType;
    }
    public void setContractName(String contractName) 
    {
        this.contractName = contractName;
    }

    public String getContractName() 
    {
        return contractName;
    }
    public void setContractId(String contractId) 
    {
        this.contractId = contractId;
    }

    public String getContractId() 
    {
        return contractId;
    }
    public void setSignDate(Date signDate) 
    {
        this.signDate = signDate;
    }

    public Date getSignDate() 
    {
        return signDate;
    }
    public void setDeliveryDate(Date deliveryDate) 
    {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() 
    {
        return deliveryDate;
    }
    public void setOurCompanyName(String ourCompanyName) 
    {
        this.ourCompanyName = ourCompanyName;
    }

    public String getOurCompanyName() 
    {
        return ourCompanyName;
    }
    public void setOurPrincipal(String ourPrincipal) 
    {
        this.ourPrincipal = ourPrincipal;
    }

    public String getOurPrincipal() 
    {
        return ourPrincipal;
    }
    public void setOppositeCompanyName(String oppositeCompanyName) 
    {
        this.oppositeCompanyName = oppositeCompanyName;
    }

    public String getOppositeCompanyName() 
    {
        return oppositeCompanyName;
    }
    public void setOppositePrincipal(String oppositePrincipal) 
    {
        this.oppositePrincipal = oppositePrincipal;
    }

    public String getOppositePrincipal() 
    {
        return oppositePrincipal;
    }
    public void setContractQuantity(String contractQuantity) 
    {
        this.contractQuantity = contractQuantity;
    }

    public String getContractQuantity() 
    {
        return contractQuantity;
    }
    public void setContractPrice(BigDecimal contractPrice) 
    {
        this.contractPrice = contractPrice;
    }

    public BigDecimal getContractPrice() 
    {
        return contractPrice;
    }
    public void setContractTotal(BigDecimal contractTotal) 
    {
        this.contractTotal = contractTotal;
    }

    public BigDecimal getContractTotal() 
    {
        return contractTotal;
    }
    public void setAccountingPeriod(String accountingPeriod) 
    {
        this.accountingPeriod = accountingPeriod;
    }

    public String getAccountingPeriod() 
    {
        return accountingPeriod;
    }
    public void setDeliveryMethod(String deliveryMethod) 
    {
        this.deliveryMethod = deliveryMethod;
    }

    public String getDeliveryMethod() 
    {
        return deliveryMethod;
    }
    public void setPortToFactoryFare(BigDecimal portToFactoryFare) 
    {
        this.portToFactoryFare = portToFactoryFare;
    }

    public BigDecimal getPortToFactoryFare() 
    {
        return portToFactoryFare;
    }
    public void setPortToPortFare(BigDecimal portToPortFare) 
    {
        this.portToPortFare = portToPortFare;
    }

    public BigDecimal getPortToPortFare() 
    {
        return portToPortFare;
    }
    public void setContractOther(String contractOther) 
    {
        this.contractOther = contractOther;
    }

    public String getContractOther() 
    {
        return contractOther;
    }
    public void setContractAgent(String contractAgent) 
    {
        this.contractAgent = contractAgent;
    }

    public String getContractAgent() 
    {
        return contractAgent;
    }
    public void setContractRemark(String contractRemark) 
    {
        this.contractRemark = contractRemark;
    }

    public String getContractRemark() 
    {
        return contractRemark;
    }
    public void setContractStatus(String contractStatus) 
    {
        this.contractStatus = contractStatus;
    }

    public String getContractStatus() 
    {
        return contractStatus;
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
            .append("goodsId", getGoodsId())
            .append("goodsName", getGoodsName())
            .append("contractType", getContractType())
            .append("contractName", getContractName())
            .append("contractId", getContractId())
            .append("signDate", getSignDate())
            .append("deliveryDate", getDeliveryDate())
            .append("ourCompanyName", getOurCompanyName())
            .append("ourPrincipal", getOurPrincipal())
            .append("oppositeCompanyName", getOppositeCompanyName())
            .append("oppositePrincipal", getOppositePrincipal())
            .append("contractQuantity", getContractQuantity())
            .append("contractPrice", getContractPrice())
            .append("contractTotal", getContractTotal())
            .append("accountingPeriod", getAccountingPeriod())
            .append("deliveryMethod", getDeliveryMethod())
            .append("portToFactoryFare", getPortToFactoryFare())
            .append("portToPortFare", getPortToPortFare())
            .append("contractOther", getContractOther())
            .append("contractAgent", getContractAgent())
            .append("contractRemark", getContractRemark())
            .append("contractStatus", getContractStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
