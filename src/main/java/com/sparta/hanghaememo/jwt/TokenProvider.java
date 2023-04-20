//package com.sparta.hanghaememo.security;
//
//import com.sparta.hanghaememo.entity.UserRoleEnum;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Date;
//
//@Slf4j
//@Service
//public class TokenProvider {
//    @Value("${jwt.secret.key}")
//    private String SECURITY_KEY;
//    private static final String BEARER_PREFIX = "Bearer ";
//    public static final String AUTHORIZATION_KEY = "auth";
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//    // JWT 생성하는 메서드
//    public String create (String username, UserRoleEnum role) {
//        Date date = new Date(System.currentTimeMillis());
//        Date exprTime = (Date) Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
//
//        // JWT를 생성(Bearer Prefix를 넣어야 하는지?)
//        return BEARER_PREFIX +
//                Jwts.builder()
//                        // 암호화에 사용될 알고리즘, 키
//                        .signWith(SignatureAlgorithm.HS512, SECURITY_KEY)
//                        .claim(AUTHORIZATION_KEY, role) //auth 키에 사용자 권한 value 담기
//                        .setSubject(username)
//                        .setIssuedAt(date)
//                        .setExpiration(exprTime)
//                        .compact();
//    }
//
//    // header 토큰을 가져오기
//    public String resolveToken(HttpServletRequest request) {
//        //Authorization 이라는 헤더 값(토큰)을 가져옴
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        //토큰 값이 있는지, 토큰 값이 Bearer 로 시작하는지 판단
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//            //Bearer를 자른 값을 전달
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//    // JWT 검증
//    public String validate (String token) {
//        // 매개변수로 받은 token을 키를 사용해서 복호화 (디코딩)
//        Claims claims = Jwts.parser().setSigningKey(SECURITY_KEY).parseClaimsJws(token).getBody();
//        // 복호화된 토큰의 payload에서 제목을 가져옴
//        return claims.getSubject();
//    }
//}
