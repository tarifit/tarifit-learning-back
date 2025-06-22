package com.tarifit.learning.content.adapter.web.controller

import com.tarifit.learning.content.adapter.web.dto.SkillDto
import com.tarifit.learning.content.adapter.web.dto.ExerciseDto
import com.tarifit.learning.content.application.service.SkillService
import com.tarifit.learning.content.application.service.ExerciseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = ["*"])
class SkillController(
    private val skillService: SkillService,
    private val exerciseService: ExerciseService
) {
    
    @GetMapping
    fun getAllSkills(): ResponseEntity<List<SkillDto>> {
        val skills = skillService.getAllSkills().map { skill ->
            SkillDto(
                skillId = skill.skillId,
                name = skill.name,
                description = skill.description,
                icon = skill.icon,
                color = skill.color,
                unlockRequirement = skill.unlockRequirement,
                totalExercises = skill.totalExercises
            )
        }
        return ResponseEntity.ok(skills)
    }
    
    @GetMapping("/{skillId}")
    fun getSkillById(@PathVariable skillId: String): ResponseEntity<SkillDto> {
        return skillService.getSkillById(skillId)?.let { skill ->
            val dto = SkillDto(
                skillId = skill.skillId,
                name = skill.name,
                description = skill.description,
                icon = skill.icon,
                color = skill.color,
                unlockRequirement = skill.unlockRequirement,
                totalExercises = skill.totalExercises
            )
            ResponseEntity.ok(dto)
        } ?: ResponseEntity.notFound().build()
    }
    
    @GetMapping("/{skillId}/exercises")
    fun getSkillExercises(@PathVariable skillId: String): ResponseEntity<List<ExerciseDto>> {
        val exercises = exerciseService.getExercisesBySkillId(skillId).map { exercise ->
            ExerciseDto(
                exerciseId = exercise.exerciseId,
                skillId = exercise.skillId,
                type = exercise.type,
                title = exercise.title,
                question = exercise.question,
                options = exercise.options,
                meaning = exercise.meaning,
                context = exercise.context
            )
        }
        return ResponseEntity.ok(exercises)
    }
    
    @GetMapping("/user-progress")
    fun getSkillsWithUserProgress(
        @RequestHeader("X-User-Id") userId: String
    ): ResponseEntity<List<SkillDto>> {
        val skills = skillService.getAllSkills().map { skill ->
            val isUnlocked = skillService.isSkillUnlocked(userId, skill.skillId)
            val progress = skillService.calculateSkillProgress(userId, skill.skillId)
            
            SkillDto(
                skillId = skill.skillId,
                name = skill.name,
                description = skill.description,
                icon = skill.icon,
                color = skill.color,
                unlockRequirement = skill.unlockRequirement,
                totalExercises = skill.totalExercises,
                isUnlocked = isUnlocked,
                completionPercentage = progress
            )
        }
        return ResponseEntity.ok(skills)
    }
}