package com.ruoyi.fpgl.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 发票管理对象 fpgl_main_info
 * 
 * @author changgong0513
 * @date 2022-11-06
 */
public class FpglMainInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private String fpglId;

    /** 开票日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开票日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fpglKprq;

    /** 开票明细 */
    @Excel(name = "开票明细")
    private String fpglKpmx;

    /** 开票数量 */
    @Excel(name = "开票数量")
    private Long fpglKpsl;

    /** 开票单价 */
    @Excel(name = "开票单价")
    private BigDecimal fpglKpdj;

    /** 开票金额 */
    @Excel(name = "开票金额")
    private BigDecimal fpglKpje;

    /** 申请人 */
    @Excel(name = "申请人")
    private String fpglSqr;

    /** 发票状态 */
    @Excel(name = "发票状态")
    private String fpglFpzt;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String fpglDdbh;

    /** 发票号 */
    @Excel(name = "发票号")
    private String fpglFpno;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long bizVersion;

    /** 动作标志（1：开具发票 2：申请发票） */
    private String actionFlag;

    public void setFpglId(String fpglId) 
    {
        this.fpglId = fpglId;
    }

    public String getFpglId() 
    {
        return fpglId;
    }
    public void setFpglKprq(Date fpglKprq) 
    {
        this.fpglKprq = fpglKprq;
    }

    public Date getFpglKprq() 
    {
        return fpglKprq;
    }
    public void setFpglKpmx(String fpglKpmx) 
    {
        this.fpglKpmx = fpglKpmx;
    }

    public String getFpglKpmx() 
    {
        return fpglKpmx;
    }
    public void setFpglKpsl(Long fpglKpsl) 
    {
        this.fpglKpsl = fpglKpsl;
    }

    public Long getFpglKpsl() 
    {
        return fpglKpsl;
    }
    public void setFpglKpdj(BigDecimal fpglKpdj) 
    {
        this.fpglKpdj = fpglKpdj;
    }

    public BigDecimal getFpglKpdj() 
    {
        return fpglKpdj;
    }
    public void setFpglKpje(BigDecimal fpglKpje) 
    {
        this.fpglKpje = fpglKpje;
    }

    public BigDecimal getFpglKpje() 
    {
        return fpglKpje;
    }
    public void setFpglSqr(String fpglSqr) 
    {
        this.fpglSqr = fpglSqr;
    }

    public String getFpglSqr() 
    {
        return fpglSqr;
    }
    public void setFpglFpzt(String fpglFpzt) 
    {
        this.fpglFpzt = fpglFpzt;
    }

    public String getFpglFpzt() 
    {
        return fpglFpzt;
    }
    public void setFpglDdbh(String fpglDdbh) 
    {
        this.fpglDdbh = fpglDdbh;
    }

    public String getFpglDdbh() 
    {
        return fpglDdbh;
    }

    public String getFpglFpno() {
        return fpglFpno;
    }

    public void setFpglFpno(String fpglFpno) {
        this.fpglFpno = fpglFpno;
    }

    public void setBizVersion(Long bizVersion)
    {
        this.bizVersion = bizVersion;
    }

    public Long getBizVersion() 
    {
        return bizVersion;
    }

    public String getActionFlag() {
        return actionFlag;
    }

    public void setActionFlag(String actionFlag) {
        this.actionFlag = actionFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fpglId", getFpglId())
            .append("fpglKprq", getFpglKprq())
            .append("fpglKpmx", getFpglKpmx())
            .append("fpglKpsl", getFpglKpsl())
            .append("fpglKpdj", getFpglKpdj())
            .append("fpglKpje", getFpglKpje())
            .append("fpglSqr", getFpglSqr())
            .append("fpglFpzt", getFpglFpzt())
            .append("fpglDdbh", getFpglDdbh())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("bizVersion", getBizVersion())
            .toString();
    }
}
