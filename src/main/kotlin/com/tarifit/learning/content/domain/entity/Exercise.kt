package com.tarifit.learning.content.domain.entity

data class Exercise(
    val id: String?,
    val exerciseId: String,
    val skillId: String,
    val type: String,
    val title: String,
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val meaning: String,
    val context: String
)