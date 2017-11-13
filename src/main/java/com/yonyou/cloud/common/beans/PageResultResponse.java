package com.yonyou.cloud.common.beans;

import java.util.List;

/**
 * 翻页的通用返回
 * 
 * @author BENJAMIN
 *
 * @param <T>
 */
public class PageResultResponse<T> extends ResultBean<T> {

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

    class PageDate<T> {
        long total;
        List<T> rows;

        public PageDate(long total, List<T> rows) {
            this.total = total;
            this.rows = rows;
        }

        public PageDate() {
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<T> getRows() {
            return rows;
        }

        public void setRows(List<T> rows) {
            this.rows = rows;
        }
    }
}
