package io.github.vibraplatform.database.dao

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class WorkMapping(id: EntityID<Int>): IntEntity(id) {
    companion object: EntityClass<Int, WorkMapping>(WorkMappings)

    var work by Work referencedOn WorkMappings.work
    var user by User referencedOn WorkMappings.user

    var website by WorkMappings.website
    var value by WorkMappings.value
}

object WorkMappings: IntIdTable("work_mappings") {
    val website = varchar("website", 64)
    val value = varchar("value", 32)

    val work = reference("work", Works)
    val user = reference("user", Users)
}