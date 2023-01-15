package com.ruoyi.zjzy.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 回款认领对象 zjzy_hk_info
 * 
 * @author changgong0513
 * @date 2022-12-04
 */
public class ZjzyHkInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private String hkId;

    /** 客户编号 */
    private String hkKhbh;

    /** 客户编号（回款单位） */
    private String hkKhmc;

    /** 客户名称（回款单位） */
    @Excel(name = "回款单位")
    private String hkRealKhmc;

    /** 回款账号 */
    @Excel(name = "回款账号")
    private String hkHkzh;

    /** 回款金额 */
    @Excel(name = "回款金额（单位：元）")
    private BigDecimal hkHkje;

    /** 回款状态 */
    private String hkHkzt;

    /** 回款认领金额 */
    private BigDecimal hkrlJe;

    /** 回款年份月份 */
    private String hkYearMonth;

    /** 按年份月份回款总额 */
    private BigDecimal hkTotalJe;

    /** 版本号 */
    private Long bizVersion;

    public String getHkId() {
        return hkId;
    }

    public void setHkId(String hkId) {
        this.hkId = hkId;
    }

    public String getHkKhbh() {
        return hkKhbh;
    }

    public void setHkKhbh(String hkKhbh) {
        this.hkKhbh = hkKhbh;
    }

    public String getHkKhmc() {
        return hkKhmc;
    }

    public void setHkKhmc(String hkKhmc) {
        this.hkKhmc = hkKhmc;
    }

    public String getHkRealKhmc() {
        return hkRealKhmc;
    }

    public void setHkRealKhmc(String hkRealKhmc) {
        this.hkRealKhmc = hkRealKhmc;
    }

    public String getHkHkzh() {
        return hkHkzh;
    }

    public void setHkHkzh(String hkHkzh) {
        this.hkHkzh = hkHkzh;
    }

    public BigDecimal getHkHkje() {
        return hkHkje;
    }

    public void setHkHkje(BigDecimal hkHkje) {
        this.hkHkje = hkHkje;
    }

    public String getHkHkzt() {
        return hkHkzt;
    }

    public void setHkHkzt(String hkHkzt) {
        this.hkHkzt = hkHkzt;
    }

    public BigDecimal getHkrlJe() {
        return hkrlJe;
    }

    public void setHkrlJe(BigDecimal hkrlJe) {
        this.hkrlJe = hkrlJe;
    }

    public String getHkYearMonth() {
        return hkYearMonth;
    }

    public void setHkYearMonth(String hkYearMonth) {
        this.hkYearMonth = hkYearMonth;
    }

    public BigDecimal getHkTotalJe() {
        return hkTotalJe;
    }

    public void setHkTotalJe(BigDecimal hkTotalJe) {
        this.hkTotalJe = hkTotalJe;
    }

    public Long getBizVersion() {
        return bizVersion;
    }

    public void setBizVersion(Long bizVersion) {
        this.bizVersion = bizVersion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("hkId", getHkId())
            .append("hkKhbh", getHkKhbh())
            .append("hkKhmc", getHkKhmc())
            .append("hkHkzh", getHkHkzh())
            .append("hkHkje", getHkHkje())
            .append("hkHkzt", getHkHkzt())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
