package com.tarifit.learning.content.infrastructure.persistence.repository

import com.tarifit.learning.content.infrastructure.persistence.entity.ExerciseTypeEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ExerciseTypeMongoRepository : MongoRepository<ExerciseTypeEntity, String> {
    
    fun findByTypeName(typeName: String): ExerciseTypeEntity?
    
    @Query("{'isActive': true}")
    fun findAllActive(): List<ExerciseTypeEntity>
    
    fun existsByTypeName(typeName: String): Boolean
}