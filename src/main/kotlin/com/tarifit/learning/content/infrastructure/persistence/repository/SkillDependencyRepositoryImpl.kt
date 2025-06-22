package com.tarifit.learning.content.infrastructure.persistence.repository

import com.tarifit.learning.content.domain.entity.SkillDependency
import com.tarifit.learning.content.domain.port.SkillDependencyRepository
import com.tarifit.learning.content.infrastructure.mapper.SkillDependencyMapper
import org.springframework.stereotype.Repository

@Repository
class SkillDependencyRepositoryImpl(
    private val mongoRepository: SkillDependencyMongoRepository,
    private val mapper: SkillDependencyMapper
) : SkillDependencyRepository {
    
    override fun findBySkillId(skillId: String): List<SkillDependency> {
        return mapper.toDomainList(mongoRepository.findBySkillId(skillId))
    }
    
    override fun findPrerequisites(skillId: String): List<SkillDependency> {
        return mapper.toDomainList(mongoRepository.findPrerequisitesBySkillId(skillId))
    }
    
    override fun save(skillDependency: SkillDependency): SkillDependency {
        val entity = mapper.toEntity(skillDependency)
        val savedEntity = mongoRepository.save(entity)
        return mapper.toDomain(savedEntity)
    }
    
    override fun deleteById(id: String) {
        mongoRepository.findById(id).ifPresent { entity ->
            val deactivatedEntity = entity.copy(isActive = false)
            mongoRepository.save(deactivatedEntity)
        }
    }
}