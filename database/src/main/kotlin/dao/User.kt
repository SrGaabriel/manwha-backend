package io.github.vibraplatform.database.dao

import io.github.vibraplatform.database.util.SnowflakeEID
import io.github.vibraplatform.database.util.SnowflakeEntity
import io.github.vibraplatform.database.util.SnowflakeEntityClass
import io.github.vibraplatform.database.util.SnowflakeIdTable
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class User(id: SnowflakeEID): SnowflakeEntity(id) {
    companion object: SnowflakeEntityClass<User>(Users)

    var username by Users.username
    var email by Users.email
    var displayName by Users.displayName
    var password by Users.password
    val works by Work referrersOn Works.user
    val mappings by WorkMapping referrersOn WorkMappings.user

    suspend fun getWorks() = newSuspendedTransaction { works.toList() }
    suspend fun getMappings() = newSuspendedTransaction { mappings.toList() }
}

object Users: SnowflakeIdTable("users") {
    val email = varchar("email", 64).uniqueIndex()
    val username = varchar("username", 32).uniqueIndex()
    val displayName = varchar("display_name", 32)
    val password = varchar("password", 72)
}