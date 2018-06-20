package com.yonyou.cloud.common.jwt;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT
 * 
 * @author BENJAMIN
 *
 */
public class JwtInfo implements Serializable,IJwtInfo {
	private static final long serialVersionUID = 3561940848549383659L;
	private String username;
    private String userId;
    private String id;
    private String name;
    private String remark;
    private String dealerName;
    private String dealerCode;
    private String telPhone;
    private String uniqueName;
    
    public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	private boolean kickout=true;
    private Map<String,String> param;

//    public JwtInfo(String username, String userId, String name,String dealerCode,
//    		String dealerName,String telPhone) {
//        this.username = username;
//        this.userId = userId;
//        this.name = name;
//        this.dealerCode=dealerCode;
//        this.dealerName=dealerName;
//        this.telPhone=telPhone;
//    }
    public JwtInfo(){
    	
    }

    public JwtInfo(String username, String userId, String name,String dealerCode,
    		String dealerName,String telPhone,boolean kickOut,Map<String,String> params, String remark) {
        this.username = username;
        //同时设置uniqueName=username
        this.uniqueName=username;
        this.userId = userId;
        //同时设置id = userId
        this.id=userId;
        this.name = name;
        this.dealerCode=dealerCode;
        this.dealerName=dealerName;
        this.telPhone=telPhone;
        this.kickout=kickOut;
        this.param=params;
        this.remark = remark;
    }

    @Override
    public String getUniqueName() {
        return username;
    }
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
    	 	this.uniqueName=username;
        this.username = username;
    }

    @Override
    public String getId() {
        return userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getName() {
    	if(name==null){
    		name="";
    	}
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JwtInfo jwtInfo = (JwtInfo) o;

        if (username != null ? !username.equals(jwtInfo.username) : jwtInfo.username != null) {
            return false;
        }
        return userId != null ? userId.equals(jwtInfo.userId) : jwtInfo.userId == null;

    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

	@Override
	public String getRemark() {
		return this.remark;
	}

	@Override
	public String getDealerName() {
		// TODO Auto-generated method stub
		return dealerName;
	}

	@Override
	public String getDealerCode() {
		// TODO Auto-generated method stub
		return dealerCode;
	}

	@Override
	public String getTelPhone() {
		// TODO Auto-generated method stub
		return telPhone;
	}

	@Override
	public boolean getKickout() {
		return kickout;
	}

	@Override
	public Map<String, String> getParam() {
		if(param==null){
			param=new HashMap();
		}
		return param;
	}

}
