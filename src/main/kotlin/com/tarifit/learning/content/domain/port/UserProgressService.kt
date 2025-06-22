package com.tarifit.learning.content.domain.port

interface UserProgressService {
    fun getUserProgress(userId: String): UserProgress?
    fun getUserSkillProgress(userId: String, skillId: String): SkillProgress?
    fun getUserExerciseStats(userId: String, exerciseId: String): ExerciseStats?
    fun getUserAchievements(userId: String): List<UserAchievement>
}

data class UserProgress(
    val userId: String,
    val totalXp: Int,
    val level: Int,
    val streakDays: Int,
    val totalExercisesCompleted: Int,
    val skillsCompleted: Int
)

data class SkillProgress(
    val userId: String,
    val skillId: String,
    val exercisesCompleted: Int,
    val totalExercises: Int,
    val completionPercentage: Double,
    val isUnlocked: Boolean,
    val xpEarned: Int
)

data class ExerciseStats(
    val userId: String,
    val exerciseId: String,
    val attempts: Int,
    val bestScore: Double,
    val averageScore: Double,
    val timeSpent: Long,
    val isCompleted: Boolean
)

data class UserAchievement(
    val userId: String,
    val achievementId: String,
    val earnedAt: String,
    val progress: Double
)