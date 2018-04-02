package com.yonyou.cloud.common.jwt;

import java.util.Map;

/**
 * 共用JWT用户对象
 * 
 * @author BENJAMIN
 *
 */
public interface IJwtInfo {
    /**
     * 获取用户名
     * @return
     */
    String getUniqueName();

    /**
     * 获取用户ID
     * @return
     */
    String getId();

    /**
     * 获取名称
     * @return
     */
    String getName();

    /**
     * 获取经销商名称
     * @return
     */
    String getDealerName();

    /**
     * 获取经销商编号
     * @return
     */
    String getDealerCode();
    /**
     * 获取phone
     * @return
     */
    String getTelPhone();
    /**
     * 获取账号是否互踢参数
     * @return
     */
    boolean getKickout();
    /**
     * 获取参数值,key/value
     * @return
     */
    Map<String, String> getParam();

    /**
     * 获取备注
     * @return
     */
    String getRemark();
}
