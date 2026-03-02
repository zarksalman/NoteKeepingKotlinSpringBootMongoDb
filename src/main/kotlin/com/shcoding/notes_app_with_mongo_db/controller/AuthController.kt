package com.shcoding.notes_app_with_mongo_db.controller

import com.shcoding.notes_app_with_mongo_db.security.AuthService
import com.shcoding.notes_app_with_mongo_db.security.JwtService
import io.jsonwebtoken.security.Password
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    data class AuthRequest(
        val email: String,
        val password: String
    )

    data class RefreshRequest(
        val refreshToken: String
    )

    @PostMapping("/register")
    fun register(
        @RequestBody authBody: AuthRequest
    ) = authService.registerUser(authBody.email, authBody.password)

    @PostMapping("/login")
    fun login(
        @RequestBody authBody: AuthRequest
    ): AuthService.TokenPair = authService.loginUser(authBody.email, authBody.password)


    @PostMapping("/refresh")
    fun refresh(
        @RequestBody refreshBody: RefreshRequest
    ): AuthService.TokenPair = authService.refresh(refreshBody.refreshToken)


}