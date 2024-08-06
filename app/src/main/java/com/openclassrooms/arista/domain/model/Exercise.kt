package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.ExerciseDto
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.TimeZone

data class Exercise(
    val id: Long? = null,
    var startTime: LocalDateTime,
    var duration: Int,
    var category: ExerciseCategory,
    var intensity: Int
) {
    companion object {
        fun fromDto(dto: ExerciseDto): Exercise {
            return Exercise(
                id = dto.id,
                startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dto.startTime),TimeZone.getDefault().toZoneId()),
                duration = dto.duration,
                category = ExerciseCategory.valueOf(dto.category),
                intensity = dto.intensity
            )
        }
    }

    fun toDto() = ExerciseDto(
        id = id ?: throw IllegalArgumentException("Id should not be null"),
        startTime = startTime.toInstant(ZoneOffset.UTC).toEpochMilli(),
        duration = duration,
        category = category.toString(),
        intensity = intensity
    )


}