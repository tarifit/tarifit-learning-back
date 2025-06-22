package com.tarifit.learning.content.infrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "skills")
data class SkillEntity(
    @Id
    val id: String? = null,
    
    @Indexed(unique = true)
    val skillId: String,
    
    val name: String,
    val description: String,
    val icon: String,
    val color: String,
    val unlockRequirement: String,
    val totalExercises: Int,
    
    @Indexed
    val category: String? = null,
    
    val orderIndex: Int? = null,
    val isActive: Boolean = true,
    val createdAt: String? = null,
    val updatedAt: String? = null
)