package com.tarifit.learning.content.infrastructure.client

import com.tarifit.learning.content.domain.port.UserProgressService
import com.tarifit.learning.content.domain.port.UserProgress
import com.tarifit.learning.content.domain.port.SkillProgress
import com.tarifit.learning.content.domain.port.ExerciseStats
import com.tarifit.learning.content.domain.port.UserAchievement
import org.springframework.stereotype.Service

@Service
class UserProgressServiceImpl(
    private val userServiceClient: UserServiceClient
) : UserProgressService {
    
    override fun getUserProgress(userId: String): UserProgress? {
        return try {
            userServiceClient.getUserProgress(userId)
        } catch (e: Exception) {
            null
        }
    }
    
    override fun getUserSkillProgress(userId: String, skillId: String): SkillProgress? {
        return try {
            userServiceClient.getUserSkillProgress(userId, skillId)
        } catch (e: Exception) {
            null
        }
    }
    
    override fun getUserExerciseStats(userId: String, exerciseId: String): ExerciseStats? {
        return try {
            userServiceClient.getUserExerciseStats(userId, exerciseId)
        } catch (e: Exception) {
            null
        }
    }
    
    override fun getUserAchievements(userId: String): List<UserAchievement> {
        return try {
            userServiceClient.getUserAchievements(userId)
        } catch (e: Exception) {
            emptyList()
        }
    }
}