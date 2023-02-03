package com.abs.news.utilities;

import com.abs.news.exception.JwtException;
import com.abs.news.exception.SystemException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtProvider {
    @Value("${app.jwt-exp:}")
    private long jwtExpirationTime;

    @Value("${app.private-key:}")
    private String privateKey;
    @Value("${app.public-key:}")
    private String publicKey;

    public String generateAccessToken(String data, Map<String, Object> claims) {
        log.info("---- JWT Provider generateToken method for {} ----", data);
        PrivateKey privateKey = getPrivateKey();

        String accessToken = Jwts.builder().setClaims(claims)
                .setSubject(data)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (jwtExpirationTime * 1000)))
                .signWith(SignatureAlgorithm.RS256, privateKey).compact();
        log.info("---- JWT Provider generateToken result {} ----", accessToken);
        return accessToken;
    }

    private Boolean isTokenExpired(String accessToken) {
        final Date expiration = getClaimFromToken(accessToken, Claims::getExpiration);
        boolean expired = expiration.before(new Date());
        return expired;
    }

    public boolean validateToken(String token) {
        PublicKey publicKey = getPublicKey();
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
            if (!claims.isEmpty() && !isTokenExpired(token)) {
                return true;
            }
            return false;
        } catch (ExpiredJwtException e) {
            throw new JwtException("Access token expired", e);
        } catch (Exception e) {
            throw new JwtException("Unable to validate the access token", e);
        }
    }

    public <T> T getClaimFromToken(String accessToken, Function<Claims, T> claimsResolver) {
        try {
            Claims allClaims = getAllClaimsFromToken(accessToken);
            return claimsResolver.apply(allClaims);
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new JwtException("Access token expired", e);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new JwtException("Unable to validate the access token", e);
        }
    }

    public Claims getAllClaimsFromToken(String accessToken) {
        try {
            PublicKey publicKey = getPublicKey();
            Claims allClaims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(accessToken).getBody();
            if (allClaims.getSubject().isEmpty()) {
                log.error("No subject available in token");
                throw new JwtException("Unable to validate the access token");
            }
            return allClaims;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new JwtException("Access token expired", e);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new JwtException("Unable to validate the access token", e);
        }
    }

    public String getUsernameFromToken(String accessToken) {
        if (isTokenExpired(accessToken)) {
            log.error("Access token expired");
            throw new JwtException("Access token expired");
        }

        return getClaimFromToken(accessToken, Claims::getSubject);
    }

    private PublicKey getPublicKey() {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Base64.Decoder decoder = Base64.getDecoder();

            PublicKey decodedPublicKey = keyFactory.generatePublic(new X509EncodedKeySpec(decoder.decode(publicKey)));
            return decodedPublicKey;
        } catch (Exception e) {
            throw new SystemException("Error decoding public key", e);
        }
    }

    private PrivateKey getPrivateKey() {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Base64.Decoder decoder = Base64.getDecoder();

            PrivateKey decodedPrivateKey =
                    keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoder.decode(privateKey)));

            return decodedPrivateKey;
        } catch (Exception e) {
            throw new SystemException("Error decoding private key", e);
        }
    }
}