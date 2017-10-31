package com.yonyou.cloud.common.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 接口通用的返回bean
 * 
 * @author BENJAMIN
 *
 * @param <T>
 */
public class ResultBean<T> implements Serializable {

	/**
	 *
	 * @author Administrator TODO description
	 * @date 2017年4月27日
	 */

	public ResultBean() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1092646848721671618L;

	/**
	 * 通用API调用成功
	 */
	public final static Integer success = 200;

	/**
	 * 通用的验证参数失败
	 */
	public final static Integer validFaild = 400;

	private long ElapsedMilliseconds;

	public long getElapsedMilliseconds() {
		return ElapsedMilliseconds;
	}

	public void setElapsedMilliseconds(long elapsedMilliseconds) {
		ElapsedMilliseconds = elapsedMilliseconds;
	}

	public final static Integer errorUnknown = 900;

	public final static Integer errorDB = 901;

	private Integer resultCode;

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	private T data;

	private String errMsg;

	/**
	 * 失败
	 * 
	 * @param resultCode
	 * @param errMsg
	 */
	public ResultBean(Integer resultCode, String errMsg) {
		super();
		// 创建日期对象
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(d);
		this.resultCode = resultCode;
		this.errMsg = errMsg;
		this.isSuccess = false;
		this.time = date;
	}

	/**
	 * 成功
	 * 
	 * @param resultCode
	 * @param data
	 * @param errMsg
	 */
	public ResultBean(Integer resultCode, T data, String errMsg) {
		super();
		// 创建日期对象
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(d);
		this.resultCode = resultCode;
		this.data = data;
		this.errMsg = errMsg;
		this.isSuccess = true;
		this.time = date;
	}

	private boolean isSuccess;

	private String time;

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
}