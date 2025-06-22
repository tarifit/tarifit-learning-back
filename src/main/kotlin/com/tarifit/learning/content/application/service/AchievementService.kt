package com.tarifit.learning.content.application.service

import com.tarifit.learning.content.domain.entity.Achievement
import com.tarifit.learning.content.domain.port.AchievementRepository
import com.tarifit.learning.content.domain.port.AchievementRequirementRepository
import com.tarifit.learning.content.domain.port.UserProgressService
import org.springframework.stereotype.Service

@Service
class AchievementService(
    private val achievementRepository: AchievementRepository,
    private val achievementRequirementRepository: AchievementRequirementRepository,
    private val userProgressService: UserProgressService
) {
    
    fun getAllAchievements(): List<Achievement> {
        return achievementRepository.findAll()
    }
    
    fun getAchievementById(achievementId: String): Achievement? {
        return achievementRepository.findById(achievementId)
    }
    
    fun getAchievementsByType(achievementType: String): List<Achievement> {
        return achievementRepository.findByType(achievementType)
    }
    
    fun calculateAchievementProgress(userId: String, achievementId: String): Double {
        val achievement = achievementRepository.findById(achievementId) ?: return 0.0
        val requirements = achievementRequirementRepository.findByAchievementId(achievementId)
        
        if (requirements.isEmpty()) return 0.0
        
        val userProgress = userProgressService.getUserProgress(userId) ?: return 0.0
        
        return when (achievement.type) {
            "streak" -> calculateStreakProgress(userProgress.streakDays, requirements.first().targetValue)
            "completion" -> calculateCompletionProgress(userId, requirements)
            "perfect" -> calculatePerfectProgress(userId, requirements)
            "xp" -> calculateXpProgress(userProgress.totalXp, requirements.first().targetValue)
            "exercise_count" -> calculateExerciseCountProgress(userProgress.totalExercisesCompleted, requirements.first().targetValue)
            else -> 0.0
        }
    }
    
    fun checkAchievementEligibility(userId: String, achievementId: String): Boolean {
        val progress = calculateAchievementProgress(userId, achievementId)
        return progress >= 100.0
    }
    
    fun getAchievementRequirements(achievementId: String): List<String> {
        val achievement = achievementRepository.findById(achievementId) ?: return emptyList()
        val requirements = achievementRequirementRepository.findByAchievementId(achievementId)
        
        return requirements.map { requirement ->
            when (requirement.requirementType) {
                "streak" -> "Maintain a ${requirement.targetValue}-day learning streak"
                "skill_completion" -> "Complete skill: ${requirement.skillId}"
                "perfect_exercises" -> "Complete ${requirement.targetValue} exercises with perfect score"
                "total_xp" -> "Earn ${requirement.targetValue} total XP"
                "exercise_count" -> "Complete ${requirement.targetValue} exercises"
                else -> "Complete requirement: ${requirement.requirementType}"
            }
        }
    }
    
    private fun calculateStreakProgress(currentStreak: Int, targetStreak: Int): Double {
        return (currentStreak.toDouble() / targetStreak * 100).coerceAtMost(100.0)
    }
    
    private fun calculateCompletionProgress(userId: String, requirements: List<Any>): Double {
        // Implementation would check if specific skills are completed
        // This is a simplified version
        return 0.0
    }
    
    private fun calculatePerfectProgress(userId: String, requirements: List<Any>): Double {
        // Implementation would count perfect exercise scores
        // This is a simplified version
        return 0.0
    }
    
    private fun calculateXpProgress(currentXp: Int, targetXp: Int): Double {
        return (currentXp.toDouble() / targetXp * 100).coerceAtMost(100.0)
    }
    
    private fun calculateExerciseCountProgress(currentCount: Int, targetCount: Int): Double {
        return (currentCount.toDouble() / targetCount * 100).coerceAtMost(100.0)
    }
}