package com.tarifit.learning.content.domain.port

import com.tarifit.learning.content.domain.entity.Skill

interface SkillRepository {
    fun findAll(): List<Skill>
    fun findById(skillId: String): Skill?
    fun findByCategory(categoryName: String): List<Skill>
    fun save(skill: Skill): Skill
    fun deleteById(skillId: String)
}