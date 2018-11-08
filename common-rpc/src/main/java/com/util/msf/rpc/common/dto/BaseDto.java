package com.util.msf.rpc.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wencheng on 2018/11/8.
 */
public abstract class BaseDto implements Serializable {

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

}
