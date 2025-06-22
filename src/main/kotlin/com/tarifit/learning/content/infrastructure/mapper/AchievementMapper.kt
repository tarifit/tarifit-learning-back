package com.tarifit.learning.content.infrastructure.mapper

import com.tarifit.learning.content.domain.entity.Achievement
import com.tarifit.learning.content.infrastructure.persistence.entity.AchievementEntity
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class AchievementMapper {
    
    fun toDomain(entity: AchievementEntity): Achievement {
        return Achievement(
            id = entity.id,
            achievementId = entity.achievementId,
            name = entity.name,
            description = entity.description,
            type = entity.type,
            requirement = entity.requirement,
            xpReward = entity.xpReward,
            iconUrl = entity.iconUrl
        )
    }
    
    fun toEntity(domain: Achievement): AchievementEntity {
        val now = Instant.now().toString()
        return AchievementEntity(
            id = domain.id,
            achievementId = domain.achievementId,
            name = domain.name,
            description = domain.description,
            type = domain.type,
            requirement = domain.requirement,
            xpReward = domain.xpReward,
            iconUrl = domain.iconUrl,
            category = determineCategory(domain.type),
            tier = determineTier(domain.xpReward),
            orderIndex = null,
            isActive = true,
            createdAt = if (domain.id == null) now else null,
            updatedAt = now
        )
    }
    
    fun toDomainList(entities: List<AchievementEntity>): List<Achievement> {
        return entities.map { toDomain(it) }
    }
    
    fun toEntityList(domains: List<Achievement>): List<AchievementEntity> {
        return domains.map { toEntity(it) }
    }
    
    private fun determineCategory(type: String): String {
        return when (type) {
            "streak" -> "CONSISTENCY"
            "completion" -> "PROGRESS"
            "perfect" -> "MASTERY"
            "xp" -> "EXPERIENCE"
            else -> "GENERAL"
        }
    }
    
    private fun determineTier(xpReward: Int): String {
        return when {
            xpReward < 50 -> "BRONZE"
            xpReward < 100 -> "SILVER"
            xpReward < 200 -> "GOLD"
            else -> "PLATINUM"
        }
    }
}