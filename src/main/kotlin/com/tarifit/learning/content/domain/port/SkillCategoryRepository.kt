package com.tarifit.learning.content.domain.port

import com.tarifit.learning.content.domain.entity.SkillCategory

interface SkillCategoryRepository {
    fun findAll(): List<SkillCategory>
    fun findByCategoryName(categoryName: String): SkillCategory?
    fun save(skillCategory: SkillCategory): SkillCategory
    fun deleteById(id: String)
}