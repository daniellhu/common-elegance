package com.yonyou.cloud.common.jwt;

import org.joda.time.DateTime;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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
        String compactJws = Jwts.builder()
                .setSubject(jwtInfo.getUniqueName())
                .claim(JWT_KEY_USER_ID, jwtInfo.getId())
                .claim(JWT_KEY_NAME, jwtInfo.getName())
                .claim(JWT_KEY_REMARK, jwtInfo.getRemark())
                .claim(JWT_KEY_DEALERCODE, jwtInfo.getDealerCode())
                .claim(JWT_KEY_DEALERNAME, jwtInfo.getDealerName())
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
    public static IJwtInfo getInfoFromToken(String token, String pubKeyPath) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKeyPath);
        Claims body = claimsJws.getBody();
        return new JwtInfo(body.getSubject(), 
        		StringHelper.getObjectValue(body.get(JWT_KEY_USER_ID)), 
        		StringHelper.getObjectValue(body.get(JWT_KEY_NAME)),
        		StringHelper.getObjectValue(body.get(JWT_KEY_DEALERCODE)),
        		StringHelper.getObjectValue(body.get(JWT_KEY_DEALERNAME)),
        		StringHelper.getObjectValue(body.get(JWT_KEY_REMARK)));
    }

}
