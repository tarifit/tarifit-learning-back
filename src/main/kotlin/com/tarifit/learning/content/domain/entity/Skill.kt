package com.tarifit.learning.content.domain.entity

data class Skill(
    val id: String?,
    val skillId: String,
    val name: String,
    val description: String,
    val icon: String,
    val color: String,
    val unlockRequirement: String,
    val totalExercises: Int
)