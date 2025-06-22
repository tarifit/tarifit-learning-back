package com.tarifit.learning.content.infrastructure.client

import com.tarifit.learning.content.domain.port.MediaMetadata
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "content-service",
    url = "\${services.content.url}",
    fallback = ContentServiceClientFallback::class
)
interface ContentServiceClient {
    
    @GetMapping("/api/media/{mediaId}/url")
    fun getMediaUrl(
        @PathVariable mediaId: String,
        @RequestHeader("Authorization") authorization: String? = null
    ): String?
    
    @GetMapping("/api/media/{mediaId}/metadata")
    fun getMediaMetadata(
        @PathVariable mediaId: String,
        @RequestHeader("Authorization") authorization: String? = null
    ): MediaMetadata?
    
    @GetMapping("/api/media/{mediaId}/available")
    fun isMediaAvailable(
        @PathVariable mediaId: String,
        @RequestHeader("Authorization") authorization: String? = null
    ): Boolean
}