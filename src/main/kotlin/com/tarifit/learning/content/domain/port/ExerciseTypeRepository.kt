package com.tarifit.learning.content.domain.port

import com.tarifit.learning.content.domain.entity.ExerciseType

interface ExerciseTypeRepository {
    fun findAll(): List<ExerciseType>
    fun findByTypeName(typeName: String): ExerciseType?
    fun save(exerciseType: ExerciseType): ExerciseType
    fun deleteById(id: String)
}