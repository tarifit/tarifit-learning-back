package com.tarifit.learning.content.domain.port

import com.tarifit.learning.content.domain.entity.SkillDependency

interface SkillDependencyRepository {
    fun findBySkillId(skillId: String): List<SkillDependency>
    fun findPrerequisites(skillId: String): List<SkillDependency>
    fun save(skillDependency: SkillDependency): SkillDependency
    fun deleteById(id: String)
}