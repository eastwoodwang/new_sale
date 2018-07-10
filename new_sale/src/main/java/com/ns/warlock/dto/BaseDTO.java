package com.ns.warlock.dto;

import java.io.Serializable;
import java.util.Date;

public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 6884950681960230859L;

    private long id;

    private Date createDate;

    private Date updateDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
