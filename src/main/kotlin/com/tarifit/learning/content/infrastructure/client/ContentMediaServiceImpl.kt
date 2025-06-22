package com.tarifit.learning.content.infrastructure.client

import com.tarifit.learning.content.domain.port.ContentMediaService
import com.tarifit.learning.content.domain.port.MediaMetadata
import org.springframework.stereotype.Service

@Service
class ContentMediaServiceImpl(
    private val contentServiceClient: ContentServiceClient
) : ContentMediaService {
    
    override fun getMediaUrl(mediaId: String): String? {
        return try {
            contentServiceClient.getMediaUrl(mediaId)
        } catch (e: Exception) {
            null
        }
    }
    
    override fun getMediaMetadata(mediaId: String): MediaMetadata? {
        return try {
            contentServiceClient.getMediaMetadata(mediaId)
        } catch (e: Exception) {
            null
        }
    }
    
    override fun isMediaAvailable(mediaId: String): Boolean {
        return try {
            contentServiceClient.isMediaAvailable(mediaId)
        } catch (e: Exception) {
            false
        }
    }
}