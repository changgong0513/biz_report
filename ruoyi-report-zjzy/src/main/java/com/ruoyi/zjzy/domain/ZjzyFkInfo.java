package com.ruoyi.zjzy.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 付款对象 zjzy_fk_info
 * 
 * @author changgong0513
 * @date 2022-12-06
 */
public class ZjzyFkInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private String fkId;

    /** 客户编号 */
    @Excel(name = "客户编号")
    private String fkKhbh;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String fkKhmc;

    /** 付款账号 */
    @Excel(name = "付款账号")
    private String fkZh;

    /** 付款金额 */
    @Excel(name = "付款金额")
    private BigDecimal fkJe;

    /** 付款状态 */
    @Excel(name = "付款状态")
    private String fkZt;

    /** 付款事由 */
    @Excel(name = "付款事由")
    private String fkSy;

    /** 贸易品种编号 */
    @Excel(name = "贸易品种编号")
    private String fkWlbh;

    /** 贸易品种名称 */
    @Excel(name = "贸易品种名称")
    private String fkWlmc;

    /** 其他品种名称 */
    @Excel(name = "其他品种名称")
    private String fkQtpzmc;

    /** 资金用途 */
    @Excel(name = "资金用途")
    private String fkZjyt;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal fkDj;

    /** 数量 */
    @Excel(name = "数量")
    private Long fkSl;

    /** 收款账号 */
    @Excel(name = "收款账号")
    private String fkSkzh;

    /** 开户银行 */
    @Excel(name = "开户银行")
    private String fkKhyh;

    /** 银行行号 */
    @Excel(name = "银行行号")
    private String fkYhhh;

    /** 运输方式 */
    @Excel(name = "运输方式")
    private String fkYsfs;

    /** 装车、到货 */
    @Excel(name = "装车、到货")
    private String fkZcdh;

    /** 备注 */
    @Excel(name = "备注")
    private String fkBz;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long bizVersion;

    /** 付款合同审批状态 */
    private String fkSpzt;

    /** 业务编号 */
    private String fkBusinessId;

    /** 付款认领金额 */
    private BigDecimal fkrlJe;

    public void setFkId(String fkId) 
    {
        this.fkId = fkId;
    }

    public String getFkId() 
    {
        return fkId;
    }
    public void setFkKhbh(String fkKhbh) 
    {
        this.fkKhbh = fkKhbh;
    }

    public String getFkKhbh() 
    {
        return fkKhbh;
    }
    public void setFkKhmc(String fkKhmc) 
    {
        this.fkKhmc = fkKhmc;
    }

    public String getFkKhmc() 
    {
        return fkKhmc;
    }
    public void setFkZh(String fkZh) 
    {
        this.fkZh = fkZh;
    }

    public String getFkZh() 
    {
        return fkZh;
    }
    public void setFkJe(BigDecimal fkJe) 
    {
        this.fkJe = fkJe;
    }

    public BigDecimal getFkJe() 
    {
        return fkJe;
    }
    public void setFkZt(String fkZt) 
    {
        this.fkZt = fkZt;
    }

    public String getFkZt() 
    {
        return fkZt;
    }
    public void setFkSy(String fkSy) 
    {
        this.fkSy = fkSy;
    }

    public String getFkSy() 
    {
        return fkSy;
    }
    public void setFkWlbh(String fkWlbh) 
    {
        this.fkWlbh = fkWlbh;
    }

    public String getFkWlbh() 
    {
        return fkWlbh;
    }
    public void setFkWlmc(String fkWlmc) 
    {
        this.fkWlmc = fkWlmc;
    }

    public String getFkWlmc() 
    {
        return fkWlmc;
    }
    public void setFkQtpzmc(String fkQtpzmc) 
    {
        this.fkQtpzmc = fkQtpzmc;
    }

    public String getFkQtpzmc() 
    {
        return fkQtpzmc;
    }
    public void setFkZjyt(String fkZjyt) 
    {
        this.fkZjyt = fkZjyt;
    }

    public String getFkZjyt() 
    {
        return fkZjyt;
    }
    public void setFkDj(BigDecimal fkDj) 
    {
        this.fkDj = fkDj;
    }

    public BigDecimal getFkDj() 
    {
        return fkDj;
    }
    public void setFkSl(Long fkSl) 
    {
        this.fkSl = fkSl;
    }

    public Long getFkSl() 
    {
        return fkSl;
    }
    public void setFkSkzh(String fkSkzh) 
    {
        this.fkSkzh = fkSkzh;
    }

    public String getFkSkzh() 
    {
        return fkSkzh;
    }
    public void setFkKhyh(String fkKhyh) 
    {
        this.fkKhyh = fkKhyh;
    }

    public String getFkKhyh() 
    {
        return fkKhyh;
    }
    public void setFkYhhh(String fkYhhh) 
    {
        this.fkYhhh = fkYhhh;
    }

    public String getFkYhhh() 
    {
        return fkYhhh;
    }
    public void setFkYsfs(String fkYsfs) 
    {
        this.fkYsfs = fkYsfs;
    }

    public String getFkYsfs() 
    {
        return fkYsfs;
    }
    public void setFkZcdh(String fkZcdh) 
    {
        this.fkZcdh = fkZcdh;
    }

    public String getFkZcdh() 
    {
        return fkZcdh;
    }
    public void setFkBz(String fkBz) 
    {
        this.fkBz = fkBz;
    }

    public String getFkBz() 
    {
        return fkBz;
    }
    public void setBizVersion(Long bizVersion)
    {
        this.bizVersion = bizVersion;
    }

    public Long getBizVersion()
    {
        return bizVersion;
    }

    public void setFkspzt(String fkSpzt) {
        this.fkSpzt = fkSpzt;
    }

    public String getFkspzt() {
        return fkSpzt;
    }

    public void setFkBusinessId(String fkBusinessId) {
        this.fkBusinessId = fkBusinessId;
    }

    public String getFkBusinessId() {
        return fkBusinessId;
    }

    public BigDecimal getFkrlJe() {
        return fkrlJe;
    }

    public void setFkrlJe(BigDecimal fkrlJe) {
        this.fkrlJe = fkrlJe;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fkId", getFkId())
            .append("fkKhbh", getFkKhbh())
            .append("fkKhmc", getFkKhmc())
            .append("fkZh", getFkZh())
            .append("fkJe", getFkJe())
            .append("fkZt", getFkZt())
            .append("fkSy", getFkSy())
            .append("fkWlbh", getFkWlbh())
            .append("fkWlmc", getFkWlmc())
            .append("fkQtpzmc", getFkQtpzmc())
            .append("fkZjyt", getFkZjyt())
            .append("fkDj", getFkDj())
            .append("fkSl", getFkSl())
            .append("fkSkzh", getFkSkzh())
            .append("fkKhyh", getFkKhyh())
            .append("fkYhhh", getFkYhhh())
            .append("fkYsfs", getFkYsfs())
            .append("fkZcdh", getFkZcdh())
            .append("fkBz", getFkBz())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
