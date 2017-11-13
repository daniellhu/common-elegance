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

	public final static Integer success = 200;//API 调用成功
	
	public final static Integer validFaild = 400;// 验证失败
	
	public final static Integer errorUnknown = 900;//未知错误

	public final static Integer errorDB = 901;
	
	private Integer resultCode;
	
	private String errMsg;

	private long ElapsedMilliseconds;
	
	public ResultBean(Integer resultCode,String errMsg) {
		this.resultCode=resultCode;
		this.errMsg=errMsg;
	}
	

	public long getElapsedMilliseconds() {
		return ElapsedMilliseconds;
	}

	public void setElapsedMilliseconds(long elapsedMilliseconds) {
		ElapsedMilliseconds = elapsedMilliseconds;
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