package com.tarifit.learning.content.domain.entity

data class Achievement(
    val id: String?,
    val achievementId: String,
    val name: String,
    val description: String,
    val type: String,
    val requirement: String,
    val xpReward: Int,
    val iconUrl: String
)