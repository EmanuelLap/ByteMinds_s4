package com.byteminds.utils;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import io.jsonwebtoken.security.Keys;

//Clase centralizada para gestionar la clave
public class KeyManager {
    private static final String keyString = "EstaEsUnaClaveSecretaDeAlMenos32Caracteres";
    private static final SecretKey key = Keys.hmacShaKeyFor(keyString.getBytes(StandardCharsets.UTF_8));

    public static SecretKey getKey() {
        return key;
    }
}

