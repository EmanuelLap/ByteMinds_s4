package com.byteminds.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class AuthService {
  
//    public String login(String username, String password) {
//        // Aquí debes implementar la lógica de autenticación. Este es solo un ejemplo simple.
//        if ("user".equals(username) && "password".equals(password)) {
//            return createJWT("1", "ByteMindsApp", username, 3600000); // genera un token JWT con 1 hora de vida
//        } else {
//            throw new RuntimeException("Autenticación fallida");
//        }
//    }

    public String createJWT(String id, String issuer, String subject, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        if (ttlMillis <= 0) {
            throw new IllegalArgumentException("El tiempo de vida (ttlMillis) debe ser positivo.");
        }

        long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);

		// Crea el JWT!
        String jwt = new String();
        jwt= Jwts.builder()
                .setId(id)
                .setIssuer(issuer)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(KeyManager.getKey()) // usa la Key para firmar el JWT
                .compact();

		System.out.println("TOKEN: "+jwt);
        
		return jwt; 
    }

    public void parseJWT(String jwt) {
        Jws<Claims> jws;
        
        try {
            jws = Jwts.parserBuilder()  // Recibes un JwtParserBuilder
                    .setSigningKey(KeyManager.getKey()) // La clave de firma
                    .build()            // Llamas a build para obtener un JwtParser
                    .parseClaimsJws(jwt); // Finalmente puedes llamar a parseClaimsJws
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear JWT", e);
        }

        // En este punto, puedes confiar en que el JWT es válido
        assert jws.getBody().getSubject().equals("Joe");
    }
}

