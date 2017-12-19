package com.yonyou.cloud.common.service.utils;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ES 翻页查询
 * 
 * @author BENJAMIN
 *
 */
public class EsPageQuery {
	private static final long serialVersionUID = 1L;
	
    /**
     * 默认页面
     */
    private int page = 1;
    
    /**
     * 默认每页数量
     */
    private int limit = 10;
    
    private String index;
    
    private String queryString;
    
    private String orderBy;
    
    private String orderType;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getQueryString() {
		return queryString;
	}


	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}


	public String getOrderBy() {
		return orderBy;
	}


	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}


	public String getOrderType() {
		return orderType;
	}


	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
    
    
}
