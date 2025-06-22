package com.tarifit.learning.content.infrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "achievement_requirements")
data class AchievementRequirementEntity(
    @Id
    val id: String? = null,
    
    @Indexed
    val achievementId: String,
    
    val requirementType: String,
    val targetValue: Int,
    val skillId: String? = null,
    
    val isActive: Boolean = true,
    val createdAt: String? = null
)