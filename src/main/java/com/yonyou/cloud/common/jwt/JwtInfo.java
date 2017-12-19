package com.yonyou.cloud.common.jwt;

import java.io.Serializable;

/**
 * JWT
 * 
 * @author BENJAMIN
 *
 */
public class JwtInfo implements Serializable,IJwtHelper {
    private String username;
    private String userId;
    private String name;
    private String remark;

    public JwtInfo(String username, String userId, String name) {
        this.username = username;
        this.userId = userId;
        this.name = name;
    }

    public JwtInfo(String username, String userId, String name,String remark) {
        this.username = username;
        this.userId = userId;
        this.name = name;
        this.remark = remark;
    }

    @Override
    public String getUniqueName() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getName() {
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
}
