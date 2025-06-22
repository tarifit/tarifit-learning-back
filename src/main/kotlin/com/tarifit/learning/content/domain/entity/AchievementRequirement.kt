package com.tarifit.learning.content.domain.entity

data class AchievementRequirement(
    val id: String?,
    val achievementId: String,
    val requirementType: String,
    val targetValue: Int,
    val skillId: String?
)