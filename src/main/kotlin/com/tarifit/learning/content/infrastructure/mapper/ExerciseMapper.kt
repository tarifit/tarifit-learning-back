package com.tarifit.learning.content.infrastructure.mapper

import com.tarifit.learning.content.domain.entity.Exercise
import com.tarifit.learning.content.infrastructure.persistence.entity.ExerciseEntity
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class ExerciseMapper {
    
    fun toDomain(entity: ExerciseEntity): Exercise {
        return Exercise(
            id = entity.id,
            exerciseId = entity.exerciseId,
            skillId = entity.skillId,
            type = entity.type,
            title = entity.title,
            question = entity.question,
            options = entity.options,
            correctAnswer = entity.correctAnswer,
            meaning = entity.meaning,
            context = entity.context
        )
    }
    
    fun toEntity(domain: Exercise): ExerciseEntity {
        val now = Instant.now().toString()
        return ExerciseEntity(
            id = domain.id,
            exerciseId = domain.exerciseId,
            skillId = domain.skillId,
            type = domain.type,
            title = domain.title,
            question = domain.question,
            options = domain.options,
            correctAnswer = domain.correctAnswer,
            meaning = domain.meaning,
            context = domain.context,
            difficulty = determineDifficulty(domain),
            orderIndex = null,
            mediaReferences = emptyList(),
            isActive = true,
            createdAt = if (domain.id == null) now else null,
            updatedAt = now
        )
    }
    
    fun toDomainList(entities: List<ExerciseEntity>): List<Exercise> {
        return entities.map { toDomain(it) }
    }
    
    fun toEntityList(domains: List<Exercise>): List<ExerciseEntity> {
        return domains.map { toEntity(it) }
    }
    
    private fun determineDifficulty(exercise: Exercise): String {
        return when {
            exercise.options.size <= 2 -> "EASY"
            exercise.options.size <= 4 -> "MEDIUM"
            else -> "HARD"
        }
    }
}