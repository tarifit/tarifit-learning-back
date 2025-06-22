package com.tarifit.learning.content.infrastructure.persistence.repository

import com.tarifit.learning.content.infrastructure.persistence.entity.SkillCategoryEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SkillCategoryMongoRepository : MongoRepository<SkillCategoryEntity, String> {
    
    fun findByCategoryName(categoryName: String): SkillCategoryEntity?
    
    @Query("{'isActive': true}")
    fun findAllActiveOrderByOrderIndex(): List<SkillCategoryEntity>
    
    fun existsByCategoryName(categoryName: String): Boolean
}