package io.github.vibraplatform.database.service

import io.github.vibraplatform.common.snowflake.Snowflake
import io.github.vibraplatform.common.struct.WorkRegistryData
import io.github.vibraplatform.database.dao.User
import io.github.vibraplatform.database.dao.Work
import io.github.vibraplatform.database.dao.WorkMapping
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class WorkService {
    suspend fun createWork(id: Snowflake, user: User, data: WorkRegistryData): Work = newSuspendedTransaction {
         Work.new(id) {
            name = data.name
            chapter = data.chapter
            status = data.status
            this.user = user
        }.also { createdWork ->
            data.initialMapping?.let { mapping ->
                 WorkMapping.new {
                     website = mapping.website
                     value = mapping.workId
                     work = createdWork
                     this.user = user
                 }
            }
         }
    }
}