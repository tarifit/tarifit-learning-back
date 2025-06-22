package com.tarifit.learning.content.infrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "skill_categories")
data class SkillCategoryEntity(
    @Id
    val id: String? = null,
    
    @Indexed(unique = true)
    val categoryName: String,
    
    val description: String,
    val orderIndex: Int,
    
    val isActive: Boolean = true,
    val createdAt: String? = null
)