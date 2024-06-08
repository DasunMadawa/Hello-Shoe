package lk.ijse.helloshoe.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lk.ijse.helloshoe.service.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Transactional
@Slf4j
public class JWTServiceImpl implements JWTService {
    @Value("${token.key}")
    private String jwtKey;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token , Claims::getSubject);

    }

    @Override
    public String generateToken(UserDetails userDetails) {
        log.info("token generated");
        return generateToken(new HashMap<>() , userDetails);

    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String userName = extractUserName(token);

        return ( userName.equals(userDetails.getUsername()) &&  !isTokenExpired(token) );

    }

    private <T>T extractClaim(String token , Function<Claims, T> claimResolve) {
        Claims  allClaims = getAllClaims(token);

        return claimResolve.apply(allClaims);

    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();

    }

    private Key getSignKey() {
        byte[] decode = Decoders.BASE64.decode(jwtKey);
        return Keys.hmacShaKeyFor(decode);

    }

    private String generateToken(Map<String , Object> extractClaims , UserDetails userDetails) {
        extractClaims.put("role" , userDetails.getAuthorities());

        Date now = new Date();

        Date expire = new Date(now.getTime() + (1000 * 60 * 60 * 8));

        String accessToken = Jwts.builder().setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(getSignKey() , SignatureAlgorithm.HS256)
                .compact();

        return accessToken;

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());

    }

    private Date extractExpiration(String token) {
        return extractClaim(token , Claims::getExpiration);

    }

}
