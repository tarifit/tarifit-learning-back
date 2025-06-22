package com.tarifit.learning.content.domain.port

import com.tarifit.learning.content.domain.entity.Achievement

interface AchievementRepository {
    fun findAll(): List<Achievement>
    fun findById(achievementId: String): Achievement?
    fun findByType(type: String): List<Achievement>
    fun save(achievement: Achievement): Achievement
    fun deleteById(achievementId: String)
}