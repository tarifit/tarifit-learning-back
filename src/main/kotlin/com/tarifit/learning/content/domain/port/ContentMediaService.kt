package com.tarifit.learning.content.domain.port

interface ContentMediaService {
    fun getMediaUrl(mediaId: String): String?
    fun getMediaMetadata(mediaId: String): MediaMetadata?
    fun isMediaAvailable(mediaId: String): Boolean
}

data class MediaMetadata(
    val mediaId: String,
    val originalUrl: String,
    val cdnUrl: String,
    val fileSize: Long,
    val mimeType: String,
    val duration: Int?,
    val thumbnailUrl: String?
)