package com.tarifit.learning.content.application.service

import com.tarifit.learning.content.domain.entity.Skill
import com.tarifit.learning.content.domain.port.SkillRepository
import com.tarifit.learning.content.domain.port.SkillDependencyRepository
import com.tarifit.learning.content.domain.port.UserProgressService
import org.springframework.stereotype.Service

@Service
class SkillService(
    private val skillRepository: SkillRepository,
    private val skillDependencyRepository: SkillDependencyRepository,
    private val userProgressService: UserProgressService
) {
    
    fun getAllSkills(): List<Skill> {
        return skillRepository.findAll()
    }
    
    fun getSkillById(skillId: String): Skill? {
        return skillRepository.findById(skillId)
    }
    
    fun getSkillsByCategory(categoryName: String): List<Skill> {
        return skillRepository.findByCategory(categoryName)
    }
    
    fun isSkillUnlocked(userId: String, skillId: String): Boolean {
        val dependencies = skillDependencyRepository.findPrerequisites(skillId)
        
        if (dependencies.isEmpty()) {
            return true
        }
        
        return dependencies.all { dependency ->
            val progress = userProgressService.getUserSkillProgress(userId, dependency.prerequisiteSkillId)
            progress?.completionPercentage?.let { it >= dependency.requiredCompletionPercentage } ?: false
        }
    }
    
    fun calculateSkillProgress(userId: String, skillId: String): Double {
        val progress = userProgressService.getUserSkillProgress(userId, skillId)
        return progress?.completionPercentage ?: 0.0
    }
    
    fun getSkillDependencies(skillId: String): List<String> {
        return skillDependencyRepository.findPrerequisites(skillId)
            .map { it.prerequisiteSkillId }
    }
    
    fun determineNextAvailableSkills(userId: String): List<Skill> {
        val allSkills = skillRepository.findAll()
        
        return allSkills.filter { skill ->
            val isUnlocked = isSkillUnlocked(userId, skill.skillId)
            val progress = calculateSkillProgress(userId, skill.skillId)
            
            isUnlocked && progress < 100.0
        }.sortedBy { skill ->
            val dependencies = skillDependencyRepository.findPrerequisites(skill.skillId)
            dependencies.size
        }
    }
}