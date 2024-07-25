package com.example.blog.security.jwt;

import com.example.blog.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
    private final UserDetailsServiceImpl userDetailsService;
    private Key secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String createAccessToken(String user_email) {
        /* Claims 객체 생성, sub(subject) 클레임을 설정하는 메서드
            JWT는 Header, Payload, Signature 로 설정되며 Payload는 Claims로 구성됨
            Claims에는 등록된 클레임, 공개 클레임, 비공개 클레임이 있고 등록된 클레임에는 iss(발급자), exp(만료일), sub(subject),
            aud(audience)등이 있음
            */
        Claims claims = Jwts.claims().setSubject(user_email);
        // 공개 클레임에 클레임 추가
        claims.put("type", "access");
        Date now = new Date();
        long tokenValidMillisecond = 1000L * 60 * 30;
        return Jwts.builder()
                .signWith(secretKey)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByEmail(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public String getAuthorizationToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        try{
            if(!token.substring(0,"BEARER ".length()).equalsIgnoreCase("Bearer ")){
                throw new IllegalStateException("Token 정보가 존재하지 않습니다.");
            }
            token = token.split(" ")[1].trim();
        }catch (Exception e){
            return null;
        }
        return token;
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
