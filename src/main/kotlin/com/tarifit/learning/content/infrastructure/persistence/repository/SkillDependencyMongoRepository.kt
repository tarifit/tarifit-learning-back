package com.tarifit.learning.content.infrastructure.persistence.repository

import com.tarifit.learning.content.infrastructure.persistence.entity.SkillDependencyEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SkillDependencyMongoRepository : MongoRepository<SkillDependencyEntity, String> {
    
    @Query("{'skillId': ?0, 'isActive': true}")
    fun findBySkillId(skillId: String): List<SkillDependencyEntity>
    
    @Query("{'prerequisiteSkillId': ?0, 'isActive': true}")
    fun findByPrerequisiteSkillId(prerequisiteSkillId: String): List<SkillDependencyEntity>
    
    @Query("{'skillId': ?0, 'isActive': true}")
    fun findPrerequisitesBySkillId(skillId: String): List<SkillDependencyEntity>
    
    @Query("{'skillId': ?0, 'prerequisiteSkillId': ?1, 'isActive': true}")
    fun findBySkillIdAndPrerequisiteSkillId(skillId: String, prerequisiteSkillId: String): SkillDependencyEntity?
    
    @Query("{'isActive': true}")
    fun findAllActive(): List<SkillDependencyEntity>
}