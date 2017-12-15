package com.yonyou.cloud.common.beans;

import java.util.List;

/**
 * 翻页的通用返回
 * 
 * @author BENJAMIN
 *
 * @param <T>
 */
public class PageResultResponse<T> extends ResultBean {

	private static final long serialVersionUID = 6232459878517227047L;
	
	PageDate<T> data;

    public PageResultResponse(long total, List<T> rows) {
        this.data = new PageDate<T>(total, rows);
    }

    public PageResultResponse() {
        this.data = new PageDate<T>();
    }

    PageResultResponse<T> total(int total) {
        this.data.setTotal(total);
        return this;
    }

    PageResultResponse<T> total(List<T> rows) {
        this.data.setRows(rows);
        return this;
    }

    public PageDate<T> getData() {
        return data;
    }

    public void setData(PageDate<T> data) {
        this.data = data;
    }


	@Override
	public String toString() {
		return "PageResultResponse [data=" + data + "]";
	}
    
    
}
