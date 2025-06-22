package com.tarifit.learning.content.infrastructure.persistence.repository

import com.tarifit.learning.content.domain.entity.Achievement
import com.tarifit.learning.content.domain.port.AchievementRepository
import com.tarifit.learning.content.infrastructure.mapper.AchievementMapper
import org.springframework.stereotype.Repository

@Repository
class AchievementRepositoryImpl(
    private val mongoRepository: AchievementMongoRepository,
    private val mapper: AchievementMapper
) : AchievementRepository {
    
    override fun findAll(): List<Achievement> {
        return mapper.toDomainList(mongoRepository.findAllActive())
    }
    
    override fun findById(achievementId: String): Achievement? {
        return mongoRepository.findByAchievementId(achievementId)?.let { mapper.toDomain(it) }
    }
    
    override fun findByType(type: String): List<Achievement> {
        return mapper.toDomainList(mongoRepository.findByType(type))
    }
    
    override fun save(achievement: Achievement): Achievement {
        val entity = mapper.toEntity(achievement)
        val savedEntity = mongoRepository.save(entity)
        return mapper.toDomain(savedEntity)
    }
    
    override fun deleteById(achievementId: String) {
        mongoRepository.findByAchievementId(achievementId)?.let { entity ->
            val deactivatedEntity = entity.copy(isActive = false)
            mongoRepository.save(deactivatedEntity)
        }
    }
}