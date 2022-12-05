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

    /** 客户名称（回款单位） */
    @Excel(name = "回款单位")
    private String hkKhmc;

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

    /** 版本号 */
    private Long bizVersion;

    public void setHkId(String hkId) 
    {
        this.hkId = hkId;
    }

    public String getHkId() 
    {
        return hkId;
    }
    public void setHkKhbh(String hkKhbh) 
    {
        this.hkKhbh = hkKhbh;
    }

    public String getHkKhbh() 
    {
        return hkKhbh;
    }
    public void setHkKhmc(String hkKhmc) 
    {
        this.hkKhmc = hkKhmc;
    }

    public String getHkKhmc() 
    {
        return hkKhmc;
    }
    public void setHkHkzh(String hkHkzh) 
    {
        this.hkHkzh = hkHkzh;
    }

    public String getHkHkzh() 
    {
        return hkHkzh;
    }
    public void setHkHkje(BigDecimal hkHkje) 
    {
        this.hkHkje = hkHkje;
    }

    public BigDecimal getHkHkje() 
    {
        return hkHkje;
    }
    public void setHkHkzt(String hkHkzt) 
    {
        this.hkHkzt = hkHkzt;
    }

    public BigDecimal getHkrlJe() {
        return hkrlJe;
    }

    public void setHkrlJe(BigDecimal hkrlJe) {
        this.hkrlJe = hkrlJe;
    }

    public String getHkHkzt()
    {
        return hkHkzt;
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
