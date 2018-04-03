package com.yonyou.cloud.common.jwt;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT工具类
 * 
 * @author BENJAMIN
 *
 */
public class JwtHelper {
    public static final String JWT_KEY_USER_ID = "userId";
    public static final String JWT_KEY_NAME = "name";
    public static final String JWT_KEY_REMARK = "remark";
    public static final String JWT_KEY_DEALERCODE = "dealerCode";
    public static final String JWT_KEY_DEALERNAME = "dealerName";
    public static final String JWT_KEY_TELPHONE = "telPhone";
    public static final String JWT_KEY_KICKOUT = "kickOut";
    private static RsaKeyHelper rsaKeyHelper = new RsaKeyHelper();
    /**
     * 密钥加密token
     *
     * @param jwtInfo
     * @param priKeyPath
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(IJwtInfo jwtInfo, String priKeyPath, int expire) throws Exception {
    	JwtBuilder jwtBuilder=Jwts.builder()
                .setSubject(jwtInfo.getUniqueName())//jwt的主题为登录账号
                .claim(JWT_KEY_USER_ID, jwtInfo.getId())
                .claim(JWT_KEY_NAME, jwtInfo.getName())
                .claim(JWT_KEY_REMARK, jwtInfo.getRemark())
                .claim(JWT_KEY_DEALERCODE, jwtInfo.getDealerCode())
                .claim(JWT_KEY_DEALERNAME, jwtInfo.getDealerName())
                .claim(JWT_KEY_KICKOUT, jwtInfo.getKickout())
                .claim(JWT_KEY_TELPHONE, jwtInfo.getTelPhone());
    	for(Entry<String, String> entry: jwtInfo.getParam().entrySet()){
    		jwtBuilder.claim(entry.getKey(), entry.getValue());
    	}
        String compactJws = jwtBuilder
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.RS256, rsaKeyHelper.getPrivateKey(priKeyPath))
                .compact();
        return compactJws;
    }

    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token, String pubKeyPath) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(rsaKeyHelper.getPublicKey(pubKeyPath)).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 获取token中的用户信息
     *
     * @param token
     * @param pubKeyPath
     * @return
     * @throws Exception
     */
    public static IJwtInfo getInfoFromToken(String token, String pubKeyPath,String[] paramNames) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKeyPath);
        Claims body = claimsJws.getBody();
        String userName=body.getSubject();//jwt的主题为登录账号
        //从jwt提取动态参数值
        Map<String,String> map=new HashMap();
        if(paramNames!=null){
    		for(int i=0;i<paramNames.length;i++){
    			String value = StringHelper.getObjectValue(body.get(paramNames[i]));
    			map.put(paramNames[i], value);
    		}
        }
        Object tmp=body.get(JWT_KEY_KICKOUT);
        boolean kickOut=true;
        if(tmp!=null){
        	kickOut=(boolean) tmp;
        }
        return new JwtInfo(userName, 
        		StringHelper.getObjectValue(body.get(JWT_KEY_USER_ID)), 
        		StringHelper.getObjectValue(body.get(JWT_KEY_NAME)),
        		StringHelper.getObjectValue(body.get(JWT_KEY_DEALERCODE)),
        		StringHelper.getObjectValue(body.get(JWT_KEY_DEALERNAME)),
        		StringHelper.getObjectValue(body.get(JWT_KEY_TELPHONE)),
        		kickOut,
        		map,
        		StringHelper.getObjectValue(body.get(JWT_KEY_REMARK)));
    }

}
