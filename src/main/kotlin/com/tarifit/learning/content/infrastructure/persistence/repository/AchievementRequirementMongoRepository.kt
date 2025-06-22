package com.tarifit.learning.content.infrastructure.persistence.repository

import com.tarifit.learning.content.infrastructure.persistence.entity.AchievementRequirementEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AchievementRequirementMongoRepository : MongoRepository<AchievementRequirementEntity, String> {
    
    @Query("{'achievementId': ?0, 'isActive': true}")
    fun findByAchievementId(achievementId: String): List<AchievementRequirementEntity>
    
    @Query("{'skillId': ?0, 'isActive': true}")
    fun findBySkillId(skillId: String): List<AchievementRequirementEntity>
    
    @Query("{'requirementType': ?0, 'isActive': true}")
    fun findByRequirementType(requirementType: String): List<AchievementRequirementEntity>
}