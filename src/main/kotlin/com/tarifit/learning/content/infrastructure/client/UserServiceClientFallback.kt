package com.tarifit.learning.content.infrastructure.client

import com.tarifit.learning.content.domain.port.UserProgress
import com.tarifit.learning.content.domain.port.SkillProgress
import com.tarifit.learning.content.domain.port.ExerciseStats
import com.tarifit.learning.content.domain.port.UserAchievement
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserServiceClientFallback : UserServiceClient {
    
    private val logger = LoggerFactory.getLogger(UserServiceClientFallback::class.java)
    
    override fun getUserProgress(userId: String, authorization: String?): UserProgress? {
        logger.warn("User service is unavailable, returning default progress for user: $userId")
        return UserProgress(
            userId = userId,
            totalXp = 0,
            level = 1,
            streakDays = 0,
            totalExercisesCompleted = 0,
            skillsCompleted = 0
        )
    }
    
    override fun getUserSkillProgress(userId: String, skillId: String, authorization: String?): SkillProgress? {
        logger.warn("User service is unavailable, returning default skill progress for user: $userId, skill: $skillId")
        return SkillProgress(
            userId = userId,
            skillId = skillId,
            exercisesCompleted = 0,
            totalExercises = 0,
            completionPercentage = 0.0,
            isUnlocked = skillId == "greetings", // Default to unlocking only greetings
            xpEarned = 0
        )
    }
    
    override fun getUserExerciseStats(userId: String, exerciseId: String, authorization: String?): ExerciseStats? {
        logger.warn("User service is unavailable, returning default exercise stats for user: $userId, exercise: $exerciseId")
        return ExerciseStats(
            userId = userId,
            exerciseId = exerciseId,
            attempts = 0,
            bestScore = 0.0,
            averageScore = 0.0,
            timeSpent = 0L,
            isCompleted = false
        )
    }
    
    override fun getUserAchievements(userId: String, authorization: String?): List<UserAchievement> {
        logger.warn("User service is unavailable, returning empty achievements for user: $userId")
        return emptyList()
    }
}