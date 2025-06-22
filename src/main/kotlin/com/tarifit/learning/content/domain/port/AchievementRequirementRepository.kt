package com.tarifit.learning.content.domain.port

import com.tarifit.learning.content.domain.entity.AchievementRequirement

interface AchievementRequirementRepository {
    fun findByAchievementId(achievementId: String): List<AchievementRequirement>
    fun save(achievementRequirement: AchievementRequirement): AchievementRequirement
    fun deleteById(id: String)
}