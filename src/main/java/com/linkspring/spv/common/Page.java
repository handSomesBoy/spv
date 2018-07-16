package com.linkspring.spv.common;

import java.util.Map;

/**
 * 分页工具类
 * 暂时只作为分页查询 输入参数
 */
public class Page  {
    private int currentPage; //当前页
    private int pageSize; //每页多少条
    private Map<String, Object> condition;
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pagesize) {
        this.pageSize = pagesize;
    }

    public Map<String, Object> getCondition() {
        return condition;
    }
    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }
}
