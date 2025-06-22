package com.tarifit.learning.content.adapter.web.controller

import com.tarifit.learning.content.adapter.web.dto.ExerciseDto
import com.tarifit.learning.content.adapter.web.dto.ExerciseValidationRequest
import com.tarifit.learning.content.adapter.web.dto.ExerciseValidationResult
import com.tarifit.learning.content.application.service.ExerciseService
import com.tarifit.learning.content.application.service.ExerciseValidationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = ["*"])
class ExerciseController(
    private val exerciseService: ExerciseService,
    private val validationService: ExerciseValidationService
) {
    
    @GetMapping("/{exerciseId}")
    fun getExerciseById(@PathVariable exerciseId: String): ResponseEntity<ExerciseDto> {
        return exerciseService.getExerciseById(exerciseId)?.let { exercise ->
            val dto = ExerciseDto(
                exerciseId = exercise.exerciseId,
                skillId = exercise.skillId,
                type = exercise.type,
                title = exercise.title,
                question = exercise.question,
                options = exercise.options,
                meaning = exercise.meaning,
                context = exercise.context
            )
            ResponseEntity.ok(dto)
        } ?: ResponseEntity.notFound().build()
    }
    
    @PostMapping("/{exerciseId}/validate")
    fun validateExercise(
        @PathVariable exerciseId: String,
        @RequestBody request: ExerciseValidationRequest
    ): ResponseEntity<ExerciseValidationResult> {
        val exercise = exerciseService.getExerciseById(exerciseId)
            ?: return ResponseEntity.notFound().build()
        
        val result = when (exercise.type) {
            "multiple_choice" -> validationService.validateMultipleChoiceAnswer(exercise, request.userAnswer)
            "translation" -> validationService.validateTranslationAnswer(exercise, request.userAnswer)
            "sentence_building" -> validationService.validateSentenceBuildingAnswer(exercise, request.userAnswer)
            "picture_matching" -> validationService.validatePictureMatchingAnswer(exercise, request.userAnswer)
            else -> validationService.validateMultipleChoiceAnswer(exercise, request.userAnswer)
        }
        
        val dto = ExerciseValidationResult(
            isCorrect = result.isCorrect,
            score = result.score,
            feedback = result.feedback,
            explanation = result.explanation,
            suggestions = result.suggestions
        )
        
        return ResponseEntity.ok(dto)
    }
    
    @GetMapping("/random/{skillId}")
    fun getRandomExercise(
        @PathVariable skillId: String,
        @RequestParam(required = false) userId: String?
    ): ResponseEntity<ExerciseDto> {
        val exercise = if (userId != null) {
            exerciseService.getRandomExercise(skillId, userId)
        } else {
            exerciseService.getExercisesBySkillId(skillId).randomOrNull()
        }
        
        return exercise?.let { ex ->
            val dto = ExerciseDto(
                exerciseId = ex.exerciseId,
                skillId = ex.skillId,
                type = ex.type,
                title = ex.title,
                question = ex.question,
                options = ex.options,
                meaning = ex.meaning,
                context = ex.context
            )
            ResponseEntity.ok(dto)
        } ?: ResponseEntity.notFound().build()
    }
    
    @GetMapping("/{exerciseId}/hints")
    fun getExerciseHints(
        @PathVariable exerciseId: String,
        @RequestParam(defaultValue = "1") attemptNumber: Int
    ): ResponseEntity<List<String>> {
        val hints = exerciseService.getExerciseHints(exerciseId, attemptNumber)
        return ResponseEntity.ok(hints)
    }
}