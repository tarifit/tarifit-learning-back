package com.tarifit.learning.content.domain.port

import com.tarifit.learning.content.domain.entity.Exercise

interface ExerciseRepository {
    fun findBySkillId(skillId: String): List<Exercise>
    fun findById(exerciseId: String): Exercise?
    fun findByType(type: String): List<Exercise>
    fun save(exercise: Exercise): Exercise
    fun deleteById(exerciseId: String)
}