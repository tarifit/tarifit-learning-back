package com.tarifit.learning.content.application.service

import com.tarifit.learning.content.domain.entity.Exercise
import com.tarifit.learning.content.domain.port.ExerciseRepository
import com.tarifit.learning.content.domain.port.UserProgressService
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class ExerciseService(
    private val exerciseRepository: ExerciseRepository,
    private val userProgressService: UserProgressService
) {
    
    fun getExercisesBySkillId(skillId: String): List<Exercise> {
        return exerciseRepository.findBySkillId(skillId)
    }
    
    fun getExerciseById(exerciseId: String): Exercise? {
        return exerciseRepository.findById(exerciseId)
    }
    
    fun getRandomExercise(skillId: String, userId: String): Exercise? {
        val exercises = exerciseRepository.findBySkillId(skillId)
        
        val uncompletedExercises = exercises.filter { exercise ->
            val stats = userProgressService.getUserExerciseStats(userId, exercise.exerciseId)
            stats?.isCompleted != true
        }
        
        return if (uncompletedExercises.isNotEmpty()) {
            uncompletedExercises[Random.nextInt(uncompletedExercises.size)]
        } else {
            exercises.randomOrNull()
        }
    }
    
    fun validateExerciseAnswer(exerciseId: String, userAnswer: String): Boolean {
        val exercise = exerciseRepository.findById(exerciseId) ?: return false
        return normalizeAnswer(userAnswer) == normalizeAnswer(exercise.correctAnswer)
    }
    
    fun calculateExerciseScore(exerciseType: String, userAnswer: String, correctAnswer: String, timeSpent: Long): Double {
        val baseScore = if (normalizeAnswer(userAnswer) == normalizeAnswer(correctAnswer)) 100.0 else 0.0
        
        if (baseScore == 0.0) return 0.0
        
        val timeBonus = when {
            timeSpent < 5000 -> 1.2
            timeSpent < 10000 -> 1.1
            timeSpent < 20000 -> 1.0
            else -> 0.9
        }
        
        val accuracyBonus = when (exerciseType) {
            "translation" -> calculateTranslationAccuracy(userAnswer, correctAnswer)
            "multiple_choice" -> 1.0
            "sentence_building" -> calculateSentenceAccuracy(userAnswer, correctAnswer)
            else -> 1.0
        }
        
        return (baseScore * timeBonus * accuracyBonus).coerceAtMost(100.0)
    }
    
    fun getExerciseHints(exerciseId: String, attemptNumber: Int): List<String> {
        val exercise = exerciseRepository.findById(exerciseId) ?: return emptyList()
        
        return when (attemptNumber) {
            1 -> listOf("Think about the context: ${exercise.context}")
            2 -> listOf("The meaning is: ${exercise.meaning}", "Consider the word structure")
            3 -> listOf("First letter: ${exercise.correctAnswer.first()}", "Length: ${exercise.correctAnswer.length} characters")
            else -> listOf("Answer: ${exercise.correctAnswer}")
        }
    }
    
    fun filterExercisesByDifficulty(exercises: List<Exercise>, difficultyLevel: String): List<Exercise> {
        return when (difficultyLevel.lowercase()) {
            "easy" -> exercises.filter { it.options.size <= 3 }
            "medium" -> exercises.filter { it.options.size in 4..6 }
            "hard" -> exercises.filter { it.options.size > 6 }
            else -> exercises
        }
    }
    
    private fun normalizeAnswer(answer: String): String {
        return answer.lowercase().trim().replace(Regex("\\s+"), " ")
    }
    
    private fun calculateTranslationAccuracy(userAnswer: String, correctAnswer: String): Double {
        val userWords = userAnswer.lowercase().split(Regex("\\s+"))
        val correctWords = correctAnswer.lowercase().split(Regex("\\s+"))
        
        val matchingWords = userWords.intersect(correctWords.toSet()).size
        val totalWords = correctWords.size
        
        return if (totalWords > 0) matchingWords.toDouble() / totalWords else 0.0
    }
    
    private fun calculateSentenceAccuracy(userAnswer: String, correctAnswer: String): Double {
        val userOrder = userAnswer.split(" ")
        val correctOrder = correctAnswer.split(" ")
        
        if (userOrder.size != correctOrder.size) return 0.0
        
        val correctPositions = userOrder.zip(correctOrder).count { it.first == it.second }
        return correctPositions.toDouble() / correctOrder.size
    }
}