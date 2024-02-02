package com.grup.pokerdaw.api_rest_pokerdaw.jwt;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.grup.pokerdaw.api_rest_pokerdaw.entity.UsuarioPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    @Value("${security.jwt.secret-key}")
    private String secret;
    @Value("${security.jwt.expiration-time}")
    private int expiration;

    public String generateToken(Authentication authentication){

    UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
    
    return Jwts.builder().setSubject(usuarioPrincipal.getUsername())
    .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + expiration * 1000))
    .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    public String getNicknameUsuarioFromToken(String token){
     return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply (claims);
    }

    private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = getNicknameUsuarioFromToken(token) ;
    return (username.equals (userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
    }   

    private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys .hmacShaKeyFor(keyBytes);
    }
}