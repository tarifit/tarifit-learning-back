package com.tarifit.learning.content.application.service

import com.tarifit.learning.content.domain.entity.Skill
import com.tarifit.learning.content.domain.port.SkillRepository
import com.tarifit.learning.content.domain.port.SkillDependencyRepository
import com.tarifit.learning.content.domain.port.UserProgressService
import org.springframework.stereotype.Service

@Service
class SkillProgressService(
    private val skillRepository: SkillRepository,
    private val skillDependencyRepository: SkillDependencyRepository,
    private val userProgressService: UserProgressService
) {
    
    fun calculateOverallProgress(userId: String): OverallProgress {
        val allSkills = skillRepository.findAll()
        val userProgress = userProgressService.getUserProgress(userId)
        
        val totalSkills = allSkills.size
        val completedSkills = allSkills.count { skill ->
            val progress = userProgressService.getUserSkillProgress(userId, skill.skillId)
            progress?.completionPercentage == 100.0
        }
        
        val totalExercises = allSkills.sumOf { it.totalExercises }
        val completedExercises = userProgress?.totalExercisesCompleted ?: 0
        
        val overallPercentage = if (totalExercises > 0) {
            (completedExercises.toDouble() / totalExercises * 100).coerceAtMost(100.0)
        } else 0.0
        
        return OverallProgress(
            userId = userId,
            totalSkills = totalSkills,
            completedSkills = completedSkills,
            totalExercises = totalExercises,
            completedExercises = completedExercises,
            overallPercentage = overallPercentage,
            currentLevel = userProgress?.level ?: 1,
            totalXp = userProgress?.totalXp ?: 0,
            streakDays = userProgress?.streakDays ?: 0
        )
    }
    
    fun determineSkillUnlockOrder(): List<SkillRecommendation> {
        val allSkills = skillRepository.findAll()
        val skillDependencyMap = mutableMapOf<String, List<String>>()
        
        allSkills.forEach { skill ->
            val dependencies = skillDependencyRepository.findPrerequisites(skill.skillId)
            skillDependencyMap[skill.skillId] = dependencies.map { it.prerequisiteSkillId }
        }
        
        val sortedSkills = topologicalSort(allSkills, skillDependencyMap)
        
        return sortedSkills.mapIndexed { index, skill ->
            SkillRecommendation(
                skillId = skill.skillId,
                name = skill.name,
                description = skill.description,
                recommendedOrder = index + 1,
                prerequisites = skillDependencyMap[skill.skillId] ?: emptyList(),
                estimatedDifficulty = calculateDifficulty(skill, skillDependencyMap[skill.skillId] ?: emptyList())
            )
        }
    }
    
    fun validateSkillPrerequisites(userId: String, skillId: String): SkillValidationResult {
        val dependencies = skillDependencyRepository.findPrerequisites(skillId)
        
        if (dependencies.isEmpty()) {
            return SkillValidationResult(
                skillId = skillId,
                canAccess = true,
                missingPrerequisites = emptyList(),
                completionRequirements = emptyList()
            )
        }
        
        val missingPrerequisites = mutableListOf<String>()
        val completionRequirements = mutableListOf<String>()
        
        dependencies.forEach { dependency ->
            val progress = userProgressService.getUserSkillProgress(userId, dependency.prerequisiteSkillId)
            val currentCompletion = progress?.completionPercentage ?: 0.0
            
            if (currentCompletion < dependency.requiredCompletionPercentage) {
                missingPrerequisites.add(dependency.prerequisiteSkillId)
                completionRequirements.add(
                    "Complete ${dependency.requiredCompletionPercentage}% of ${dependency.prerequisiteSkillId} " +
                    "(currently ${currentCompletion.toInt()}%)"
                )
            }
        }
        
        return SkillValidationResult(
            skillId = skillId,
            canAccess = missingPrerequisites.isEmpty(),
            missingPrerequisites = missingPrerequisites,
            completionRequirements = completionRequirements
        )
    }
    
    fun getPersonalizedSkillRecommendations(userId: String): List<PersonalizedRecommendation> {
        val allSkills = skillRepository.findAll()
        val userProgress = userProgressService.getUserProgress(userId)
        
        return allSkills.mapNotNull { skill ->
            val skillProgress = userProgressService.getUserSkillProgress(userId, skill.skillId)
            val canAccess = validateSkillPrerequisites(userId, skill.skillId).canAccess
            
            if (!canAccess) return@mapNotNull null
            
            val recommendation = when {
                skillProgress?.completionPercentage == 100.0 -> "COMPLETED"
                skillProgress?.completionPercentage ?: 0.0 > 0.0 -> "IN_PROGRESS"
                canAccess -> "AVAILABLE"
                else -> "LOCKED"
            }
            
            val priority = calculateRecommendationPriority(skill, skillProgress, userProgress)
            
            PersonalizedRecommendation(
                skillId = skill.skillId,
                name = skill.name,
                currentProgress = skillProgress?.completionPercentage ?: 0.0,
                recommendation = recommendation,
                priority = priority,
                reasonForRecommendation = generateRecommendationReason(skill, skillProgress, userProgress)
            )
        }.sortedByDescending { it.priority }
    }
    
    private fun topologicalSort(skills: List<Skill>, dependencies: Map<String, List<String>>): List<Skill> {
        val visited = mutableSetOf<String>()
        val result = mutableListOf<Skill>()
        
        fun dfs(skillId: String) {
            if (skillId in visited) return
            visited.add(skillId)
            
            dependencies[skillId]?.forEach { prerequisite ->
                dfs(prerequisite)
            }
            
            skills.find { it.skillId == skillId }?.let { result.add(it) }
        }
        
        skills.forEach { skill ->
            if (skill.skillId !in visited) {
                dfs(skill.skillId)
            }
        }
        
        return result
    }
    
    private fun calculateDifficulty(skill: Skill, prerequisites: List<String>): String {
        return when {
            prerequisites.isEmpty() -> "BEGINNER"
            prerequisites.size <= 2 -> "INTERMEDIATE"
            else -> "ADVANCED"
        }
    }
    
    private fun calculateRecommendationPriority(skill: Skill, skillProgress: Any?, userProgress: Any?): Int {
        return when {
            skillProgress != null -> 100
            skill.unlockRequirement == "none" -> 90
            else -> 50
        }
    }
    
    private fun generateRecommendationReason(skill: Skill, skillProgress: Any?, userProgress: Any?): String {
        return when {
            skillProgress != null -> "Continue your progress in this skill"
            skill.unlockRequirement == "none" -> "Perfect for beginners"
            else -> "Builds on your existing knowledge"
        }
    }
}

data class OverallProgress(
    val userId: String,
    val totalSkills: Int,
    val completedSkills: Int,
    val totalExercises: Int,
    val completedExercises: Int,
    val overallPercentage: Double,
    val currentLevel: Int,
    val totalXp: Int,
    val streakDays: Int
)

data class SkillRecommendation(
    val skillId: String,
    val name: String,
    val description: String,
    val recommendedOrder: Int,
    val prerequisites: List<String>,
    val estimatedDifficulty: String
)

data class SkillValidationResult(
    val skillId: String,
    val canAccess: Boolean,
    val missingPrerequisites: List<String>,
    val completionRequirements: List<String>
)

data class PersonalizedRecommendation(
    val skillId: String,
    val name: String,
    val currentProgress: Double,
    val recommendation: String,
    val priority: Int,
    val reasonForRecommendation: String
)