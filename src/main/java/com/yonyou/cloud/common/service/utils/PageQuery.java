package com.yonyou.cloud.common.service.utils;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 通用的分页查询条件
 * 
 * @author BENJAMIN
 *
 */
public class PageQuery extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
    /**
     * 默认页码1
     */
    private int page = 1;
    
    /**
     * 默认一页10条
     */
    private int limit = 10;
    
    /**
     * 页码参数名
     */
    private static final String PARAM_PAGE="page";
    
    /**
     * 显示条数参数名
     */
    private static final String PARAM_LIMIT="limit";
    		
    		

    public PageQuery(Map<String, Object> params){
        this.putAll(params);
        //分页参数
        if(params.get(PARAM_PAGE)!=null) {
            this.page = Integer.parseInt(params.get(PARAM_PAGE).toString());
        }
        if(params.get(PARAM_LIMIT)!=null) {
            this.limit = Integer.parseInt(params.get(PARAM_LIMIT).toString());
        }
        this.remove(PARAM_PAGE);
        this.remove(PARAM_LIMIT);
    }
    

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
}
