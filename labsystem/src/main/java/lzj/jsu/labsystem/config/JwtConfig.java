package lzj.jsu.labsystem.config;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

//在config.jwt文件中取出，对应着secret，expire，header的信息
@ConfigurationProperties(prefix = "config.jwt",ignoreInvalidFields = true)
@Component
@Data
public class JwtConfig {
    private String secret;
    private Long expire;
    private String header;

    //生成token
    public String createToken(String subject){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime()+expire*1000);
        return Jwts.builder()
                //令牌类型
                .setHeaderParam("typ","JWT")
                //放在负载中的
                .setSubject(subject)
                //当前的时间
                .setIssuedAt(nowDate)
                //过期的时间
                .setExpiration(expireDate)
                //加密算法，secret是自己设置的
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    //获取token
    public Claims getTokenClaim(String token){
        try {
            //进行解析
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException(e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    //获取有效信息
    public String getSubject(String token){
        return getTokenClaim(token).getSubject();
    }
}
