package com.tarifit.learning.content.infrastructure.client

import com.tarifit.learning.content.domain.port.MediaMetadata
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ContentServiceClientFallback : ContentServiceClient {
    
    private val logger = LoggerFactory.getLogger(ContentServiceClientFallback::class.java)
    
    override fun getMediaUrl(mediaId: String, authorization: String?): String? {
        logger.warn("Content service is unavailable, returning null for media URL: $mediaId")
        return null
    }
    
    override fun getMediaMetadata(mediaId: String, authorization: String?): MediaMetadata? {
        logger.warn("Content service is unavailable, returning null for media metadata: $mediaId")
        return null
    }
    
    override fun isMediaAvailable(mediaId: String, authorization: String?): Boolean {
        logger.warn("Content service is unavailable, returning false for media availability: $mediaId")
        return false
    }
}