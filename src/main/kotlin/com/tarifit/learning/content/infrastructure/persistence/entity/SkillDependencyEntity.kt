package com.tarifit.learning.content.infrastructure.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "skill_dependencies")
@CompoundIndex(def = "{'skillId': 1, 'prerequisiteSkillId': 1}", unique = true)
data class SkillDependencyEntity(
    @Id
    val id: String? = null,
    
    @Indexed
    val skillId: String,
    
    @Indexed
    val prerequisiteSkillId: String,
    
    val requiredCompletionPercentage: Int,
    
    val isActive: Boolean = true,
    val createdAt: String? = null
)