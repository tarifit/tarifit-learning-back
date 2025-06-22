package com.tarifit.learning.content.application.service

import com.tarifit.learning.content.domain.entity.Exercise
import com.tarifit.learning.content.domain.port.ExerciseRepository
import org.springframework.stereotype.Service
import kotlin.math.max
import kotlin.math.min

@Service
class ExerciseValidationService(
    private val exerciseRepository: ExerciseRepository
) {
    
    fun validateMultipleChoiceAnswer(exercise: Exercise, userAnswer: String): ValidationResult {
        val normalizedUserAnswer = userAnswer.trim().lowercase()
        val normalizedCorrectAnswer = exercise.correctAnswer.trim().lowercase()
        
        val isCorrect = normalizedUserAnswer == normalizedCorrectAnswer
        val score = if (isCorrect) 100.0 else 0.0
        
        return ValidationResult(
            isCorrect = isCorrect,
            score = score,
            feedback = if (isCorrect) "Correct!" else "The correct answer is: ${exercise.correctAnswer}",
            explanation = exercise.meaning
        )
    }
    
    fun validateTranslationAnswer(exercise: Exercise, userAnswer: String): ValidationResult {
        val userWords = normalizeText(userAnswer).split(Regex("\\s+"))
        val correctWords = normalizeText(exercise.correctAnswer).split(Regex("\\s+"))
        
        val accuracy = calculateWordAccuracy(userWords, correctWords)
        val fuzzyMatch = calculateFuzzyMatch(userAnswer, exercise.correctAnswer)
        
        val finalScore = max(accuracy, fuzzyMatch) * 100
        val isCorrect = finalScore >= 80.0
        
        val feedback = when {
            finalScore >= 95.0 -> "Perfect translation!"
            finalScore >= 80.0 -> "Good translation! Minor differences detected."
            finalScore >= 60.0 -> "Partial match. Check word order and spelling."
            else -> "Incorrect. The correct translation is: ${exercise.correctAnswer}"
        }
        
        return ValidationResult(
            isCorrect = isCorrect,
            score = finalScore,
            feedback = feedback,
            explanation = "Context: ${exercise.context}",
            suggestions = generateTranslationSuggestions(userWords, correctWords)
        )
    }
    
    fun validateSentenceBuildingAnswer(exercise: Exercise, userAnswer: String): ValidationResult {
        val userWords = userAnswer.trim().split(Regex("\\s+"))
        val correctWords = exercise.correctAnswer.trim().split(Regex("\\s+"))
        
        val positionAccuracy = calculatePositionAccuracy(userWords, correctWords)
        val wordSetAccuracy = calculateWordSetAccuracy(userWords, correctWords)
        
        val finalScore = (positionAccuracy * 0.7 + wordSetAccuracy * 0.3) * 100
        val isCorrect = finalScore >= 85.0
        
        val feedback = when {
            finalScore >= 95.0 -> "Perfect sentence construction!"
            finalScore >= 85.0 -> "Correct! Great sentence building."
            finalScore >= 70.0 -> "Close! Check the word order."
            else -> "Incorrect order. Try: ${exercise.correctAnswer}"
        }
        
        return ValidationResult(
            isCorrect = isCorrect,
            score = finalScore,
            feedback = feedback,
            explanation = exercise.meaning,
            suggestions = generateOrderSuggestions(userWords, correctWords)
        )
    }
    
    fun validatePictureMatchingAnswer(exercise: Exercise, userAnswer: String): ValidationResult {
        val normalizedUserAnswer = userAnswer.trim().lowercase()
        val normalizedCorrectAnswer = exercise.correctAnswer.trim().lowercase()
        
        val isCorrect = normalizedUserAnswer == normalizedCorrectAnswer
        val score = if (isCorrect) 100.0 else 0.0
        
        return ValidationResult(
            isCorrect = isCorrect,
            score = score,
            feedback = if (isCorrect) "Correct match!" else "The correct match is: ${exercise.correctAnswer}",
            explanation = exercise.meaning
        )
    }
    
    fun calculateAccuracyScore(exerciseType: String, userAnswer: String, correctAnswer: String): Double {
        return when (exerciseType.lowercase()) {
            "multiple_choice" -> if (normalizeText(userAnswer) == normalizeText(correctAnswer)) 100.0 else 0.0
            "translation" -> calculateTranslationAccuracy(userAnswer, correctAnswer)
            "sentence_building" -> calculateSentenceBuildingAccuracy(userAnswer, correctAnswer)
            "picture_matching" -> if (normalizeText(userAnswer) == normalizeText(correctAnswer)) 100.0 else 0.0
            else -> 0.0
        }
    }
    
    private fun normalizeText(text: String): String {
        return text.lowercase().trim().replace(Regex("[^\\w\\s]"), "").replace(Regex("\\s+"), " ")
    }
    
    private fun calculateWordAccuracy(userWords: List<String>, correctWords: List<String>): Double {
        if (correctWords.isEmpty()) return 0.0
        
        val matchingWords = userWords.intersect(correctWords.toSet()).size
        return matchingWords.toDouble() / correctWords.size
    }
    
    private fun calculateFuzzyMatch(userText: String, correctText: String): Double {
        val userNormalized = normalizeText(userText)
        val correctNormalized = normalizeText(correctText)
        
        return calculateLevenshteinSimilarity(userNormalized, correctNormalized)
    }
    
    private fun calculateLevenshteinSimilarity(s1: String, s2: String): Double {
        val longer = if (s1.length > s2.length) s1 else s2
        val shorter = if (s1.length > s2.length) s2 else s1
        
        if (longer.isEmpty()) return 1.0
        
        val distance = levenshteinDistance(longer, shorter)
        return (longer.length - distance).toDouble() / longer.length
    }
    
    private fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        
        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j
        
        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
                dp[i][j] = min(
                    min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                    dp[i - 1][j - 1] + cost
                )
            }
        }
        
        return dp[s1.length][s2.length]
    }
    
    private fun calculatePositionAccuracy(userWords: List<String>, correctWords: List<String>): Double {
        if (correctWords.isEmpty()) return 0.0
        
        val minLength = min(userWords.size, correctWords.size)
        val correctPositions = (0 until minLength).count { userWords[it] == correctWords[it] }
        
        return correctPositions.toDouble() / correctWords.size
    }
    
    private fun calculateWordSetAccuracy(userWords: List<String>, correctWords: List<String>): Double {
        if (correctWords.isEmpty()) return 0.0
        
        val userSet = userWords.toSet()
        val correctSet = correctWords.toSet()
        val intersection = userSet.intersect(correctSet)
        
        return intersection.size.toDouble() / correctSet.size
    }
    
    private fun calculateTranslationAccuracy(userAnswer: String, correctAnswer: String): Double {
        val userWords = normalizeText(userAnswer).split(Regex("\\s+"))
        val correctWords = normalizeText(correctAnswer).split(Regex("\\s+"))
        
        val wordAccuracy = calculateWordAccuracy(userWords, correctWords)
        val fuzzyMatch = calculateFuzzyMatch(userAnswer, correctAnswer)
        
        return max(wordAccuracy, fuzzyMatch) * 100
    }
    
    private fun calculateSentenceBuildingAccuracy(userAnswer: String, correctAnswer: String): Double {
        val userWords = userAnswer.trim().split(Regex("\\s+"))
        val correctWords = correctAnswer.trim().split(Regex("\\s+"))
        
        val positionAccuracy = calculatePositionAccuracy(userWords, correctWords)
        val wordSetAccuracy = calculateWordSetAccuracy(userWords, correctWords)
        
        return (positionAccuracy * 0.7 + wordSetAccuracy * 0.3) * 100
    }
    
    private fun generateTranslationSuggestions(userWords: List<String>, correctWords: List<String>): List<String> {
        val suggestions = mutableListOf<String>()
        val userSet = userWords.toSet()
        val correctSet = correctWords.toSet()
        
        val missing = correctSet - userSet
        val extra = userSet - correctSet
        
        if (missing.isNotEmpty()) {
            suggestions.add("Missing words: ${missing.joinToString(", ")}")
        }
        
        if (extra.isNotEmpty()) {
            suggestions.add("Extra words: ${extra.joinToString(", ")}")
        }
        
        return suggestions
    }
    
    private fun generateOrderSuggestions(userWords: List<String>, correctWords: List<String>): List<String> {
        val suggestions = mutableListOf<String>()
        
        if (userWords.size != correctWords.size) {
            suggestions.add("Check the number of words (expected: ${correctWords.size}, got: ${userWords.size})")
        }
        
        val wrongPositions = mutableListOf<String>()
        val minLength = min(userWords.size, correctWords.size)
        
        for (i in 0 until minLength) {
            if (userWords[i] != correctWords[i]) {
                wrongPositions.add("Position ${i + 1}: expected '${correctWords[i]}', got '${userWords[i]}'")
            }
        }
        
        if (wrongPositions.isNotEmpty()) {
            suggestions.addAll(wrongPositions.take(3))
        }
        
        return suggestions
    }
}

data class ValidationResult(
    val isCorrect: Boolean,
    val score: Double,
    val feedback: String,
    val explanation: String? = null,
    val suggestions: List<String> = emptyList()
)