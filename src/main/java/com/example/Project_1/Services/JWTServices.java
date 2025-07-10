package com.example.Project_1.Services;

import com.example.Project_1.Model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTServices {

    public String secretKey= "";

    public JWTServices(){
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        }catch (NoSuchAlgorithmException no){
            throw new RuntimeException();
        }
    }

    public SecretKey getKey(){
        byte [] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(String username , Role role){
        Map<String ,Object> obj = new HashMap<>();
                obj.put("role", role.getName());

        return Jwts.builder()
                .claims(obj)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getKey())
                .compact();
    }

    public String First(String token){ //username
        return Second(token, Claims::getSubject);
    }

    public List<String> extraRole(String token) {

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            System.out.println("JWT Claims: " + claims);

            Object roleObject = claims.get("role");

            if (roleObject == null) {
                throw new IllegalArgumentException("Role not found in JWT token");
            }
            return List.of(roleObject.toString());

        }catch (Exception e) {
            System.err.println("Error extracting role: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    private <T> T Second(String token, Function<Claims,T> function){//Claims
        final Claims claims = Three(token);
        return function.apply(claims);
    }

    private Claims Three (String token){//Return Claims
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username = First(token);
        return (username.equals(userDetails.getUsername()) && !isFour(token));
    }

    private boolean isFour(String token){ //ExpirationToken
        return Five(token).before(new Date());
    }

    private Date Five(String token){ //DateExpiry
        return Second(token,Claims::getExpiration);
    }

}

