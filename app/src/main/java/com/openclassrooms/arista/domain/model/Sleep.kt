package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.SleepDto

data class Sleep(
    val id: Long?,
    val startTime: Long,
    val endTime: Long,
    val duration: Int,
    val quality: Int
) {
    companion object {
        fun fromDto(dto: SleepDto): Sleep {
            return Sleep(
                id = dto.id,
                startTime = dto.startTime,
                endTime = dto.endTime,
                duration = dto.duration,
                quality = dto.quality
            )
        }
    }

    fun toDto(): SleepDto {
        return SleepDto(
            id = id ?: throw IllegalArgumentException("Sleep Id should not be null"),
            startTime = startTime,
            endTime = endTime,
            duration = duration,
            quality = quality
        )
    }
}
