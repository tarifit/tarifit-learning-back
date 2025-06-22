package com.tarifit.learning.content.infrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "media_references")
data class MediaReferenceEntity(
    @Id
    val id: String? = null,
    
    @Indexed
    val exerciseId: String,
    
    @Indexed
    val mediaType: String,
    
    val mediaUrl: String,
    val altText: String,
    val duration: Int? = null,
    
    val isActive: Boolean = true,
    val createdAt: String? = null
)