package com.tarifit.learning.content.infrastructure.persistence.repository

import com.tarifit.learning.content.domain.entity.Exercise
import com.tarifit.learning.content.domain.port.ExerciseRepository
import com.tarifit.learning.content.infrastructure.mapper.ExerciseMapper
import org.springframework.stereotype.Repository

@Repository
class ExerciseRepositoryImpl(
    private val mongoRepository: ExerciseMongoRepository,
    private val mapper: ExerciseMapper
) : ExerciseRepository {
    
    override fun findBySkillId(skillId: String): List<Exercise> {
        return mapper.toDomainList(mongoRepository.findBySkillId(skillId))
    }
    
    override fun findById(exerciseId: String): Exercise? {
        return mongoRepository.findByExerciseId(exerciseId)?.let { mapper.toDomain(it) }
    }
    
    override fun findByType(type: String): List<Exercise> {
        return mapper.toDomainList(mongoRepository.findByType(type))
    }
    
    override fun save(exercise: Exercise): Exercise {
        val entity = mapper.toEntity(exercise)
        val savedEntity = mongoRepository.save(entity)
        return mapper.toDomain(savedEntity)
    }
    
    override fun deleteById(exerciseId: String) {
        mongoRepository.findByExerciseId(exerciseId)?.let { entity ->
            val deactivatedEntity = entity.copy(isActive = false)
            mongoRepository.save(deactivatedEntity)
        }
    }
}