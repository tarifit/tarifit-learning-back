package com.tarifit.learning.content.domain.entity

data class SkillDependency(
    val id: String?,
    val skillId: String,
    val prerequisiteSkillId: String,
    val requiredCompletionPercentage: Int
)