package com.tarifit.learning.content.domain.entity

data class ExerciseType(
    val id: String?,
    val typeName: String,
    val description: String,
    val validationRules: Map<String, Any>
)