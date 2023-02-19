package com.ruoyi.zjzy.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ZjzyStatisticsInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 部门编号 */
    private String tjBmbh;

    /** 部门名称 */
    private String tjBmmc;

    /** 批次号 */
    private String tjPch;

    /** 付款金额 */
    private String tjFkje;

    /** 回款金额 */
    private String tjHkje;

    /** 占用金额 */
    private String tjZyje;

    /** 利息 */
    private String tjLx;

    public String getTjBmbh() {
        return tjBmbh;
    }

    public void setTjBmbh(String tjBmbh) {
        this.tjBmbh = tjBmbh;
    }

    public String getTjBmmc() {
        return tjBmmc;
    }

    public void setTjBmmc(String tjBmmc) {
        this.tjBmmc = tjBmmc;
    }

    public String getTjPch() {
        return tjPch;
    }

    public void setTjPch(String tjPch) {
        this.tjPch = tjPch;
    }

    public String getTjFkje() {
        return tjFkje;
    }

    public void setTjFkje(String tjFkje) {
        this.tjFkje = tjFkje;
    }

    public String getTjHkje() {
        return tjHkje;
    }

    public void setTjHkje(String tjHkje) {
        this.tjHkje = tjHkje;
    }

    public String getTjZyje() {
        return tjZyje;
    }

    public void setTjZyje(String tjZyje) {
        this.tjZyje = tjZyje;
    }

    public String getTjLx() {
        return tjLx;
    }

    public void setTjLx(String tjLx) {
        this.tjLx = tjLx;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tjBmbh", getTjBmbh())
                .append("tjBmbc", getTjBmmc())
                .append("tjPch", getTjPch())
                .append("tjFkje", getTjFkje())
                .append("tjHkje", getTjHkje())
                .append("tjZyje", getTjZyje())
                .append("tjLx", getTjLx())
                .toString();

    }
}
