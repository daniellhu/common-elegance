package com.yonyou.cloud.common.beans;

import java.util.List;

public class PageDate<T> {
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

	@Override
	public String toString() {
		return "PageDate [total=" + total + ", rows=" + rows + "]";
	}
}
