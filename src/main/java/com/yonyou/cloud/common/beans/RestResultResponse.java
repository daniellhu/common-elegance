package com.yonyou.cloud.common.beans;

/**
 * rest接口返回的bean
 * 
 * @author BENJAMIN
 *
 * @param <T>
 */
public class RestResultResponse<T> extends ResultBean<T> {

    T data;
    boolean success;

    public boolean isSuccess() {
		return success;
	}


	public void setSuccess(boolean success) {
		this.success = success;
	}


	public RestResultResponse success(boolean success) {
        this.setSuccess(success);
        return this;
    }


    public RestResultResponse data(T data) {
        this.setData(data);
        return this;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
