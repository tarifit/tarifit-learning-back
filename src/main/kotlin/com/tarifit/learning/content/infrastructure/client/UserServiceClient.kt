package com.tarifit.learning.content.infrastructure.client

import com.tarifit.learning.content.domain.port.UserProgress
import com.tarifit.learning.content.domain.port.SkillProgress
import com.tarifit.learning.content.domain.port.ExerciseStats
import com.tarifit.learning.content.domain.port.UserAchievement
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(
    name = "user-service",
    url = "\${services.user.url}",
    fallback = UserServiceClientFallback::class
)
interface UserServiceClient {
    
    @GetMapping("/api/users/{userId}/progress")
    fun getUserProgress(
        @PathVariable userId: String,
        @RequestHeader("Authorization") authorization: String? = null
    ): UserProgress?
    
    @GetMapping("/api/users/{userId}/skills/{skillId}/progress")
    fun getUserSkillProgress(
        @PathVariable userId: String,
        @PathVariable skillId: String,
        @RequestHeader("Authorization") authorization: String? = null
    ): SkillProgress?
    
    @GetMapping("/api/users/{userId}/exercises/{exerciseId}/stats")
    fun getUserExerciseStats(
        @PathVariable userId: String,
        @PathVariable exerciseId: String,
        @RequestHeader("Authorization") authorization: String? = null
    ): ExerciseStats?
    
    @GetMapping("/api/users/{userId}/achievements")
    fun getUserAchievements(
        @PathVariable userId: String,
        @RequestHeader("Authorization") authorization: String? = null
    ): List<UserAchievement>
}