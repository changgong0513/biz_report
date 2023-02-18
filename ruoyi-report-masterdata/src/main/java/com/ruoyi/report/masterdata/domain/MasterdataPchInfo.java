package com.ruoyi.report.masterdata.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.poi.hpsf.Decimal;

/**
 * 批次号管理对象 masterdata_pch_info
 * 
 * @author changgong
 * @date 2022-12-04
 */
public class MasterdataPchInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private String id;

    /** 批次号 */
    @Excel(name = "批次号")
    private String pch;

    /** 批次号名称 */
    @Excel(name = "批次号名称")
    private String pchmc;

    /** 所属部门编号 */
    private String belongDept;

    /** 所属部门名称 */
    @Excel(name = "所属部门")
    private String belongDeptName;

    /** 批次号所属年份 */
    @Excel(name = "批次号所属年份")
    private String ssnf;

    /** 批次号状态 */
    @Excel(name = "批次号状态", dictType = "masterdata_pch_status")
    private String pchzt;

    /** 版本号 */
    private Long bizVersion;

    /** 利率 */
    @Excel(name = "利率")
    private BigDecimal moneyRate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPch() {
        return pch;
    }

    public void setPch(String pch) {
        this.pch = pch;
    }

    public String getPchmc() {
        return pchmc;
    }

    public void setPchmc(String pchmc) {
        this.pchmc = pchmc;
    }

    public String getBelongDept() {
        return belongDept;
    }

    public void setBelongDept(String belongDept) {
        this.belongDept = belongDept;
    }

    public String getBelongDeptName() {
        return belongDeptName;
    }

    public void setBelongDeptName(String belongDeptName) {
        this.belongDeptName = belongDeptName;
    }

    public String getSsnf() {
        return ssnf;
    }

    public void setSsnf(String ssnf) {
        this.ssnf = ssnf;
    }

    public String getPchzt() {
        return pchzt;
    }

    public void setPchzt(String pchzt) {
        this.pchzt = pchzt;
    }

    public Long getBizVersion() {
        return bizVersion;
    }

    public void setBizVersion(Long bizVersion) {
        this.bizVersion = bizVersion;
    }

    public BigDecimal getMoneyRate() {
        return moneyRate;
    }

    public void setMoneyRate(BigDecimal moneyRate) {
        this.moneyRate = moneyRate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("pch", getPch())
            .append("pchmc", getPchmc())
            .append("ssnf", getSsnf())
            .append("pchzt", getPchzt())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
