package com.yonyou.cloud.common.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 接口通用的返回bean
 * 
 * @author BENJAMIN
 *
 */
public class ResultBean implements Serializable {

	public ResultBean() {
		super();
	}

	private static final long serialVersionUID = -1092646848721671618L;

	/**
	 * API 调用成功
	 */
	public final static Integer SUCCESS = 200;
	
	/**
	 * 验证失败
	 */
	public final static Integer VALID_FAILD = 400;
	
	/**
	 * 未知错误
	 */
	public final static Integer ERROR_UNKNOWN = 900;

	public final static Integer ERROR_DB = 901;
	
	private Integer resultCode;
	
	private String errMsg;

	private long elapsedMilliseconds;
	
	public ResultBean(Integer resultCode,String errMsg) {
		this.resultCode=resultCode;
		this.errMsg=errMsg;
	}
	

	public long getElapsedMilliseconds() {
		return elapsedMilliseconds;
	}

	public void setElapsedMilliseconds(long elapsedMilliseconds) {
		elapsedMilliseconds = elapsedMilliseconds;
	}


	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}