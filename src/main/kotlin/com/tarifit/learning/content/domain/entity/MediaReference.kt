package com.tarifit.learning.content.domain.entity

data class MediaReference(
    val id: String?,
    val exerciseId: String,
    val mediaType: String,
    val mediaUrl: String,
    val altText: String,
    val duration: Int?
)