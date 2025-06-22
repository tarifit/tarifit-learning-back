package com.tarifit.learning.content.infrastructure.persistence.repository

import com.tarifit.learning.content.domain.entity.Skill
import com.tarifit.learning.content.domain.port.SkillRepository
import com.tarifit.learning.content.infrastructure.mapper.SkillMapper
import org.springframework.stereotype.Repository

@Repository
class SkillRepositoryImpl(
    private val mongoRepository: SkillMongoRepository,
    private val mapper: SkillMapper
) : SkillRepository {
    
    override fun findAll(): List<Skill> {
        return mapper.toDomainList(mongoRepository.findAllActive())
    }
    
    override fun findById(skillId: String): Skill? {
        return mongoRepository.findBySkillId(skillId)?.let { mapper.toDomain(it) }
    }
    
    override fun findByCategory(categoryName: String): List<Skill> {
        return mapper.toDomainList(mongoRepository.findByCategory(categoryName))
    }
    
    override fun save(skill: Skill): Skill {
        val entity = mapper.toEntity(skill)
        val savedEntity = mongoRepository.save(entity)
        return mapper.toDomain(savedEntity)
    }
    
    override fun deleteById(skillId: String) {
        mongoRepository.findBySkillId(skillId)?.let { entity ->
            val deactivatedEntity = entity.copy(isActive = false)
            mongoRepository.save(deactivatedEntity)
        }
    }
}