package com.tarifit.learning.content.infrastructure.persistence.repository

import com.tarifit.learning.content.infrastructure.persistence.entity.MediaReferenceEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MediaReferenceMongoRepository : MongoRepository<MediaReferenceEntity, String> {
    
    @Query("{'exerciseId': ?0, 'isActive': true}")
    fun findByExerciseId(exerciseId: String): List<MediaReferenceEntity>
    
    @Query("{'mediaType': ?0, 'isActive': true}")
    fun findByMediaType(mediaType: String): List<MediaReferenceEntity>
    
    @Query("{'exerciseId': ?0, 'mediaType': ?1, 'isActive': true}")
    fun findByExerciseIdAndMediaType(exerciseId: String, mediaType: String): List<MediaReferenceEntity>
}