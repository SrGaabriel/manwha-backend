package io.github.vibraplatform.common.struct

import io.github.vibraplatform.common.snowflake.Snowflake
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
data class RawWork(
    val id: Snowflake,
    val name: String,
    val chapter: Int,
    val status: WorkStatus
)

@Serializable
enum class WorkStatus {
    READING,
    PAUSED,
    DROPPED,
    COMPLETED,
    PLANNED,
    TRACKING
}

@Serializable
data class RawWorkMapping(
    val website: String,
    val workId: String
)

@Serializable
data class WorkRegistryData(
    val name: String,
    val chapter: Int,
    val status: WorkStatus,
    val initialMapping: RawWorkMapping? = null
)