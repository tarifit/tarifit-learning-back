package com.tarifit.learning.content.infrastructure.persistence.repository

import com.tarifit.learning.content.infrastructure.persistence.entity.AchievementEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AchievementMongoRepository : MongoRepository<AchievementEntity, String> {
    
    fun findByAchievementId(achievementId: String): AchievementEntity?
    
    @Query("{'type': ?0, 'isActive': true}")
    fun findByType(type: String): List<AchievementEntity>
    
    @Query("{'category': ?0, 'isActive': true}")
    fun findByCategory(category: String): List<AchievementEntity>
    
    @Query("{'isActive': true}")
    fun findAllActive(): List<AchievementEntity>
    
    @Query("{'tier': ?0, 'isActive': true}")
    fun findByTier(tier: String): List<AchievementEntity>
    
    fun existsByAchievementId(achievementId: String): Boolean
}