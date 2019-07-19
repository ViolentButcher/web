package edu.njupt.feng.web.entity.common;

import java.io.Serializable;

/**
 * 返回分页信息
 */
public class PageInfo implements Serializable {
    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 信息实体
     */
    private Object infoList;

    /**
     * 总页数
     */
    private Integer sum;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Object getInfoList() {
        return infoList;
    }

    public void setInfoList(Object infoList) {
        this.infoList = infoList;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }
}
