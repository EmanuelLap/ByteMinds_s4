package com.byteminds.utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
    	 String path = requestContext.getUriInfo().getPath();
    	 System.out.println("requestContext.getUriInfo().getPath(): "+path);
         // Permitir el acceso a la ruta de login sin autenticación
         if (path.startsWith("/login")|| path.startsWith("/layout")) {
             return;
         }
    	String authHeader = requestContext.getHeaderString("Authorization");
//        System.out.println("FILTRO DE AUTORIZACION...");
//    	System.out.println(authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }
        
        String token = authHeader.substring("Bearer".length()).trim();
        
//        System.out.println("AuthFilter: Token: "+token);
        try {
//            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        	 SecretKey key = KeyManager.getKey(); // Usa la clave centralizada para validar el token
             Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (Exception e) {
        	System.out.println("Se genero una exeption "+ e);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            System.out.println("Abortando solicitud!");
        }
    }
}
