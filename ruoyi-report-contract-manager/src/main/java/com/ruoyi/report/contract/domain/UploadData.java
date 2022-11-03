package com.ruoyi.report.contract.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UploadData {

    private static final long serialVersionUID = 1L;

    private String uploadContractId;

    public void setUploadContractId(String uploadContractId)
    {
        this.uploadContractId = uploadContractId;
    }
    public String getUploadContractId()
    {
        return uploadContractId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("uploadContractId", getUploadContractId())
                .toString();
    }
}
