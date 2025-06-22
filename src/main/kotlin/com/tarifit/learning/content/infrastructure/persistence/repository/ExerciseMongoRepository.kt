package com.tarifit.learning.content.infrastructure.persistence.repository

import com.tarifit.learning.content.infrastructure.persistence.entity.ExerciseEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ExerciseMongoRepository : MongoRepository<ExerciseEntity, String> {
    
    fun findByExerciseId(exerciseId: String): ExerciseEntity?
    
    @Query("{'skillId': ?0, 'isActive': true}")
    fun findBySkillId(skillId: String): List<ExerciseEntity>
    
    @Query("{'type': ?0, 'isActive': true}")
    fun findByType(type: String): List<ExerciseEntity>
    
    @Query("{'skillId': ?0, 'type': ?1, 'isActive': true}")
    fun findBySkillIdAndType(skillId: String, type: String): List<ExerciseEntity>
    
    @Query("{'skillId': ?0, 'difficulty': ?1, 'isActive': true}")
    fun findBySkillIdAndDifficulty(skillId: String, difficulty: String): List<ExerciseEntity>
    
    @Query("{'exerciseId': {'$in': ?0}, 'isActive': true}")
    fun findByExerciseIdIn(exerciseIds: List<String>): List<ExerciseEntity>
    
    fun existsByExerciseId(exerciseId: String): Boolean
    
    @Query(value = "{'skillId': ?0, 'isActive': true}", count = true)
    fun countBySkillId(skillId: String): Long
}