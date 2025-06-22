# Tarifit Learning Content Service

A Spring Boot Kotlin microservice for managing learning content, skills, exercises, and achievements in the Tarifit language learning platform.

## Architecture

This service follows strict anemic domain principles with clear separation of concerns:

### Domain Layer (Pure Anemic Data)
- **Entities**: Completely anemic data classes with no business logic
- **Ports**: Repository interfaces defining data access contracts

### Application Layer (Business Logic)
- **Services**: All business logic and domain rules
- **Achievement System**: Achievement tracking and validation
- **Exercise Validation**: Exercise answer validation and scoring
- **Skill Progress**: Skill dependency and progression logic

### Infrastructure Layer (Persistence & External)
- **MongoDB Entities**: Separate persistence models with annotations
- **Repository Implementations**: Data access layer
- **Feign Clients**: External service communication (User Service, Content Service)
- **Mappers**: Entity-to-domain mapping

### Adapter Layer (Web Interface)
- **Controllers**: REST API endpoints
- **DTOs**: Request/response objects
- **Exception Handling**: Global error handling

## Features

### Skill Management
- Hierarchical skill structure with categories
- Skill dependencies and prerequisites
- Progress tracking and unlocking logic
- Dynamic skill availability based on user progress

### Exercise System
- Multiple exercise types (vocabulary, grammar, listening, etc.)
- Exercise validation and scoring
- Media reference support (audio, images)
- Difficulty progression within skills

### Achievement System
- Dynamic achievement requirements
- Progress calculation and tracking
- XP rewards for achievement completion
- Integration with user progress service

### Content Management
- Media asset references
- Exercise type definitions
- Skill categorization
- Content versioning support

## API Endpoints

### Skills
- `GET /api/skills` - Get all skills with user progress
- `GET /api/skills/{skillId}` - Get specific skill details
- `GET /api/skills/categories` - Get skill categories
- `GET /api/skills/{skillId}/exercises` - Get exercises for a skill

### Exercises
- `GET /api/exercises/{exerciseId}` - Get exercise details
- `POST /api/exercises/{exerciseId}/validate` - Validate exercise answer
- `GET /api/exercises/types` - Get available exercise types

### Achievements
- `GET /api/achievements` - Get all achievements
- `GET /api/achievements/{achievementId}` - Get achievement details
- `POST /api/achievements/{achievementId}/check-progress` - Check achievement progress

## Configuration

### Database
- MongoDB on port 27017
- Database: `tarifit_learning_content`
- Collections: Auto-created with proper indexes

### Security
- No authentication logic (handled by BFF gateway)
- Receives `X-User-Id` header from gateway
- CORS enabled for all origins

### External Services
- User Service: `http://localhost:8083` - User progress and statistics
- Content Service: `http://localhost:8081` - Media asset management
- Authorization headers forwarded automatically

## Running the Service

```bash
./mvnw spring-boot:run
```

The service will start on port 8082.

## Business Rules

### Skill Progression
- Skills have prerequisite dependencies
- Users must complete prerequisite skills before unlocking new ones
- Progress is calculated based on completed exercises within a skill
- Skill completion unlocks dependent skills automatically

### Exercise Validation
- Multiple choice: Exact answer matching
- Text input: Fuzzy matching with configurable tolerance
- Audio exercises: Speech-to-text validation
- Scoring based on accuracy and attempt count

### Achievement System
- Real-time progress calculation
- Automatic unlocking when criteria met
- XP rewards vary by achievement difficulty
- Support for various achievement types (streaks, completions, perfection, etc.)

### Content Organization
- Skills organized by categories (beginner, intermediate, advanced)
- Exercises within skills follow difficulty progression
- Media references managed externally but linked within exercises

## Data Models

### Domain Entities (Anemic)
- Skill
- SkillCategory
- SkillDependency
- Exercise
- ExerciseType
- Achievement
- AchievementRequirement
- MediaReference

### MongoDB Collections
- skills
- skill_categories
- skill_dependencies
- exercises
- exercise_types
- achievements
- achievement_requirements
- media_references

## Dependencies

- Spring Boot 3.3.3
- Spring Data MongoDB
- Spring Cloud OpenFeign
- Kotlin 1.9.24
- Jackson Kotlin Module
- MongoDB (runtime dependency)

## Development

### Prerequisites
- Java 17+
- MongoDB 4.4+
- Maven 3.6+

### Local Setup
1. Start MongoDB locally or via Docker
2. Configure application.yml with your MongoDB connection
3. Run the application: `./mvnw spring-boot:run`
4. Access health check: `http://localhost:8082/actuator/health`

### Testing
```bash
./mvnw test
```

Includes embedded MongoDB for integration tests.

## Integration

This service integrates with:
- **User Service**: For progress tracking and statistics updates
- **Content Service**: For media asset management and retrieval
- **BFF Gateway**: Receives user context and forwards requests

The service is designed to be fault-tolerant with fallback mechanisms for external service failures.
