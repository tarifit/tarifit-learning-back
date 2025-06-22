package com.tarifit.learning.content.infrastructure.persistence.repository

import com.tarifit.learning.content.infrastructure.persistence.entity.SkillEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SkillMongoRepository : MongoRepository<SkillEntity, String> {
    
    fun findBySkillId(skillId: String): SkillEntity?
    
    @Query("{'category': ?0, 'isActive': true}")
    fun findByCategory(category: String): List<SkillEntity>
    
    @Query("{'isActive': true}")
    fun findAllActive(): List<SkillEntity>
    
    @Query("{'skillId': {'$in': ?0}, 'isActive': true}")
    fun findBySkillIdIn(skillIds: List<String>): List<SkillEntity>
    
    fun existsBySkillId(skillId: String): Boolean
}