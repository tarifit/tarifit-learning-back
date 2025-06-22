package com.tarifit.learning.content.infrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "exercise_types")
data class ExerciseTypeEntity(
    @Id
    val id: String? = null,
    
    @Indexed(unique = true)
    val typeName: String,
    
    val description: String,
    val validationRules: Map<String, Any>,
    
    val isActive: Boolean = true,
    val createdAt: String? = null
)