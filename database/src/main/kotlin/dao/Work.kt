package io.github.vibraplatform.database.dao

import io.github.vibraplatform.common.struct.WorkStatus
import io.github.vibraplatform.database.util.SnowflakeEID
import io.github.vibraplatform.database.util.SnowflakeEntity
import io.github.vibraplatform.database.util.SnowflakeEntityClass
import io.github.vibraplatform.database.util.SnowflakeIdTable

class Work(id: SnowflakeEID): SnowflakeEntity(id) {
    companion object: SnowflakeEntityClass<Work>(Works)

    var name by Works.name
    var chapter by Works.chapter
    var status by Works.status
    var user by User referencedOn Works.user
    val mappings by WorkMapping referrersOn WorkMappings.work
}

object Works: SnowflakeIdTable("works") {
    val name = varchar("name", 64)
    val chapter = integer("chapter")
    val status = enumeration<WorkStatus>("status")
    val user = reference("user", Users)
}