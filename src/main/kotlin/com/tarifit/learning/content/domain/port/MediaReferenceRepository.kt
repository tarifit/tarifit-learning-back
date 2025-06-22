package com.tarifit.learning.content.domain.port

import com.tarifit.learning.content.domain.entity.MediaReference

interface MediaReferenceRepository {
    fun findByExerciseId(exerciseId: String): List<MediaReference>
    fun findByMediaType(mediaType: String): List<MediaReference>
    fun save(mediaReference: MediaReference): MediaReference
    fun deleteById(id: String)
}