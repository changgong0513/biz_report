package com.ruoyi.purchase.sale.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UploadDataPurchaseSale {

    private static final long serialVersionUID = 1L;

    private String uploadOrderId;

    public void setUploadOrderId(String uploadOrderId)
    {
        this.uploadOrderId = uploadOrderId;
    }
    public String getUploadOrderId()
    {
        return uploadOrderId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("uploadContractId", getUploadOrderId())
                .toString();
    }
}
