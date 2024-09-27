package com.sid.iauro.jwt.ProductManagementJWT.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

	//Our own sceret you can take any bit , here it is 256-bit hexa format secret
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    //4th extracting the token 
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    //5th 
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    //6th extract all the claims 
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //7th
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    //8th
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    //9th
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //1st
    public String generateToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userName); //calls below fun with the data
    }
    
    //2nd
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder() // for using jwt builder you should have 3 dependency in your pom
                .setClaims(claims)
                //claims means header+payload+signature all the 3 together 
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                //Token will expire after 30 mins
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
        		//add the certificate or signed key specify the key and what algorithm you want to use 1st component +3rd comp what secret you want to use
        		// HS256 IS ALGORITHM
    }
    
    //3rd
    private Key getSignKey() {
    	//with our own seceret do base64 
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes); // will give you signed key based on your own secret
    }
}