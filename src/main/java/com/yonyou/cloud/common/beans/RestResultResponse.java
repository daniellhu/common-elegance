package com.yonyou.cloud.common.beans;

/**
 * rest接口返回的bean
 * 
 * @author BENJAMIN
 *
 * @param <T>
 */
public class RestResultResponse<T> extends ResultBean {

	private static final long serialVersionUID = -3630734380336964442L;
	
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
    	this.setResultCode(200);
        this.setData(data);
        return this;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


	@Override
	public String toString() {
		return "RestResultResponse [data=" + data + ", success=" + success + "]";
	}

    
    

}
