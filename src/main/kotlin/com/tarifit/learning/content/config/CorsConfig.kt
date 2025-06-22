package com.tarifit.learning.content.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {
    
    @Value("\${cors.allowed-origins}")
    private lateinit var allowedOrigins: String
    
    @Value("\${cors.allowed-methods}")
    private lateinit var allowedMethods: String
    
    @Value("\${cors.allowed-headers}")
    private lateinit var allowedHeaders: String
    
    @Value("\${cors.allow-credentials}")
    private lateinit var allowCredentials: String
    
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins(*allowedOrigins.split(",").toTypedArray())
            .allowedMethods(*allowedMethods.split(",").toTypedArray())
            .allowedHeaders(*allowedHeaders.split(",").toTypedArray())
            .allowCredentials(allowCredentials.toBoolean())
            .maxAge(3600)
    }
}