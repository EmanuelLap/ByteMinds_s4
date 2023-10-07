package com.byteminds.utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

//@Provider //TODO:DESCOMENTAR PARA APLICAR FILTROS LUEGO DE VERIFICAR ERROR CON EL TOKEN
public class AuthFilter implements ContainerRequestFilter {

//    private String key = "EstaEsUnaClaveSecretaDeAlMenos32Caracteres"; // asegúrate de mantener esta clave secreta en un lugar seguro
	SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    EstaEsUnaClaveSecretaDeAlMenos32Caracteres
    @Override
    public void filter(ContainerRequestContext requestContext) {
    	String authHeader = requestContext.getHeaderString("Authorization");
        System.out.println("FILTRO DE AUTORIZACION...");
    	System.out.println(authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }
        
        String token = authHeader.substring("Bearer".length()).trim();
        System.out.println("AuthFilter: mostrando token: "+token);
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        } catch (Exception e) {
        	System.out.println("Se genero una exeption "+ e);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            System.out.println("Abortando solicitud!");
        }
    }
}
