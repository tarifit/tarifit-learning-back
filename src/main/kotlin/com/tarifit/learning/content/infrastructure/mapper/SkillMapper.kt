package com.tarifit.learning.content.infrastructure.mapper

import com.tarifit.learning.content.domain.entity.Skill
import com.tarifit.learning.content.infrastructure.persistence.entity.SkillEntity
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class SkillMapper {
    
    fun toDomain(entity: SkillEntity): Skill {
        return Skill(
            id = entity.id,
            skillId = entity.skillId,
            name = entity.name,
            description = entity.description,
            icon = entity.icon,
            color = entity.color,
            unlockRequirement = entity.unlockRequirement,
            totalExercises = entity.totalExercises
        )
    }
    
    fun toEntity(domain: Skill): SkillEntity {
        val now = Instant.now().toString()
        return SkillEntity(
            id = domain.id,
            skillId = domain.skillId,
            name = domain.name,
            description = domain.description,
            icon = domain.icon,
            color = domain.color,
            unlockRequirement = domain.unlockRequirement,
            totalExercises = domain.totalExercises,
            category = null,
            orderIndex = null,
            isActive = true,
            createdAt = if (domain.id == null) now else null,
            updatedAt = now
        )
    }
    
    fun toDomainList(entities: List<SkillEntity>): List<Skill> {
        return entities.map { toDomain(it) }
    }
    
    fun toEntityList(domains: List<Skill>): List<SkillEntity> {
        return domains.map { toEntity(it) }
    }
}