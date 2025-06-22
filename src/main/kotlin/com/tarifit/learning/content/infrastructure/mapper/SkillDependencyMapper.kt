package com.tarifit.learning.content.infrastructure.mapper

import com.tarifit.learning.content.domain.entity.SkillDependency
import com.tarifit.learning.content.infrastructure.persistence.entity.SkillDependencyEntity
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class SkillDependencyMapper {
    
    fun toDomain(entity: SkillDependencyEntity): SkillDependency {
        return SkillDependency(
            id = entity.id,
            skillId = entity.skillId,
            prerequisiteSkillId = entity.prerequisiteSkillId,
            requiredCompletionPercentage = entity.requiredCompletionPercentage
        )
    }
    
    fun toEntity(domain: SkillDependency): SkillDependencyEntity {
        val now = Instant.now().toString()
        return SkillDependencyEntity(
            id = domain.id,
            skillId = domain.skillId,
            prerequisiteSkillId = domain.prerequisiteSkillId,
            requiredCompletionPercentage = domain.requiredCompletionPercentage,
            isActive = true,
            createdAt = if (domain.id == null) now else null
        )
    }
    
    fun toDomainList(entities: List<SkillDependencyEntity>): List<SkillDependency> {
        return entities.map { toDomain(it) }
    }
    
    fun toEntityList(domains: List<SkillDependency>): List<SkillDependencyEntity> {
        return domains.map { toEntity(it) }
    }
}