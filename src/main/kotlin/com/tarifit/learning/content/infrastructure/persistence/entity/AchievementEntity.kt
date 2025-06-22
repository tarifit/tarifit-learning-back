package com.tarifit.learning.content.infrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "achievements")
data class AchievementEntity(
    @Id
    val id: String? = null,
    
    @Indexed(unique = true)
    val achievementId: String,
    
    val name: String,
    val description: String,
    
    @Indexed
    val type: String,
    
    val requirement: String,
    val xpReward: Int,
    val iconUrl: String,
    
    val category: String? = null,
    val tier: String? = null,
    val orderIndex: Int? = null,
    val isActive: Boolean = true,
    val createdAt: String? = null,
    val updatedAt: String? = null
)