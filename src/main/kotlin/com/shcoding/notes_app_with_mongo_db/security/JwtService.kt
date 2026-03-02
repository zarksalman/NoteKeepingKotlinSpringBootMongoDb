package com.shcoding.notes_app_with_mongo_db.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {
    private val jwtSecretKey = "Arham10Musa07Zarrar07Salman01Sarwat01Shum09Nauman06"
    private val jwtSecretKeyEncoded = "QXJoYW0xME11c2EwN1phcnJhcjA3U2FsbWFuMDFTYXJ3YXQwMVNodW0wOU5hdW1hbjA2"
    private val secreteKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecretKeyEncoded))
    private val accessTokenValidityMs = 15 * 60 * 1000L
    val refreshTokenValidityMs = 30 * 24 * 60 * 60 * 1000L

    private fun generateToken(
        userId: String,
        type: String,
        expiry: Long
    ): String {
        val now = Date()
        val expiryDate = Date(now.time + expiry)

        return Jwts
            .builder()
            .subject(userId)
            .claim("type", type)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secreteKey, Jwts.SIG.HS256)
            .compact()
    }

    fun generateAccessToken(userId: String): String {
        return generateToken(
            userId = userId,
            type = "access",
            accessTokenValidityMs

        )
    }

    fun generateRefreshToken(userId: String): String {
        return generateToken(
            userId = userId,
            type = "refresh",
            refreshTokenValidityMs

        )
    }

    fun validateAccessToken(token: String): Boolean{
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "access"
    }

    fun validateRefreshToken(token: String): Boolean{
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "refresh"
    }

    // Authorization: Bearer <token>
    fun getUserIdFromToken(token: String): String{
        val claims = parseAllClaims(token) ?: throw IllegalArgumentException("Invalid Token.")

        // here subject is userId, we use it for token generation
        return claims.subject

    }
    fun parseAllClaims(token: String): Claims?{
        val rawToken = if (token.startsWith("Bearer ")){
            token.removePrefix("Bearer ")
        }else{
            token
        }
        return try {
            Jwts
                .parser()
                .verifyWith(secreteKey)
                .build()
                .parseSignedClaims(rawToken)
                .payload
        }catch (exception: Exception){
            null
        }
    }
}