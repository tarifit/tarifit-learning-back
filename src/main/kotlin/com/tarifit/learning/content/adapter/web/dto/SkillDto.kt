package com.tarifit.learning.content.adapter.web.dto

data class SkillDto(
    val skillId: String,
    val name: String,
    val description: String,
    val icon: String,
    val color: String,
    val unlockRequirement: String,
    val totalExercises: Int,
    val isUnlocked: Boolean? = null,
    val completionPercentage: Double? = null
)

data class ExerciseDto(
    val exerciseId: String,
    val skillId: String,
    val type: String,
    val title: String,
    val question: String,
    val options: List<String>,
    val meaning: String? = null,
    val context: String? = null
)

data class AchievementDto(
    val achievementId: String,
    val name: String,
    val description: String,
    val type: String,
    val xpReward: Int,
    val iconUrl: String,
    val progress: Double? = null,
    val isEarned: Boolean? = null
)

data class ExerciseValidationRequest(
    val exerciseId: String,
    val userAnswer: String,
    val timeSpent: Long? = null
)

data class ExerciseValidationResult(
    val isCorrect: Boolean,
    val score: Double,
    val feedback: String,
    val explanation: String? = null,
    val suggestions: List<String> = emptyList()
)

data class SkillProgressSummary(
    val userId: String,
    val totalSkills: Int,
    val completedSkills: Int,
    val overallPercentage: Double,
    val currentLevel: Int,
    val totalXp: Int,
    val streakDays: Int
)