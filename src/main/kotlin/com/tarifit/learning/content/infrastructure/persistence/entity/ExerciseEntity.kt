package com.tarifit.learning.content.infrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "exercises")
@CompoundIndex(def = "{'skillId': 1, 'type': 1}")
data class ExerciseEntity(
    @Id
    val id: String? = null,
    
    @Indexed(unique = true)
    val exerciseId: String,
    
    @Indexed
    val skillId: String,
    
    @Indexed
    val type: String,
    
    val title: String,
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val meaning: String,
    val context: String,
    
    val difficulty: String? = null,
    val orderIndex: Int? = null,
    val mediaReferences: List<String> = emptyList(),
    val isActive: Boolean = true,
    val createdAt: String? = null,
    val updatedAt: String? = null
)