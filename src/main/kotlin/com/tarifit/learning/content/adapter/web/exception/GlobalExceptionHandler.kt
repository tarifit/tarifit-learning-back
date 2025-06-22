package com.tarifit.learning.content.adapter.web.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {
    
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    
    @ExceptionHandler(SkillNotFoundException::class)
    fun handleSkillNotFoundException(
        ex: SkillNotFoundException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        logger.error("Skill not found: ${ex.message}", ex)
        val error = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = "Skill Not Found",
            message = ex.message ?: "The requested skill was not found",
            path = request.getDescription(false)
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }
    
    @ExceptionHandler(ExerciseNotFoundException::class)
    fun handleExerciseNotFoundException(
        ex: ExerciseNotFoundException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        logger.error("Exercise not found: ${ex.message}", ex)
        val error = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = "Exercise Not Found",
            message = ex.message ?: "The requested exercise was not found",
            path = request.getDescription(false)
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error)
    }
    
    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(
        ex: ValidationException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        logger.error("Validation error: ${ex.message}", ex)
        val error = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Validation Error",
            message = ex.message ?: "Request validation failed",
            path = request.getDescription(false)
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }
    
    @ExceptionHandler(ExternalServiceException::class)
    fun handleExternalServiceException(
        ex: ExternalServiceException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        logger.error("External service error: ${ex.message}", ex)
        val error = ErrorResponse(
            status = HttpStatus.SERVICE_UNAVAILABLE.value(),
            error = "External Service Unavailable",
            message = "External service is temporarily unavailable",
            path = request.getDescription(false)
        )
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error)
    }
    
    @ExceptionHandler(Exception::class)
    fun handleGenericException(
        ex: Exception,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        logger.error("Unexpected error: ${ex.message}", ex)
        val error = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Internal Server Error",
            message = "An unexpected error occurred",
            path = request.getDescription(false)
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
    }
}

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String,
    val path: String,
    val timestamp: String = java.time.Instant.now().toString()
)

class SkillNotFoundException(message: String) : RuntimeException(message)
class ExerciseNotFoundException(message: String) : RuntimeException(message)
class ValidationException(message: String) : RuntimeException(message)
class ExternalServiceException(message: String) : RuntimeException(message)