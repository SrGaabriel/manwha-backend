package io.github.vibraplatform.database.service

import io.github.vibraplatform.common.snowflake.Snowflake
import io.github.vibraplatform.common.struct.UserRegistryData
import io.github.vibraplatform.database.dao.User
import io.github.vibraplatform.database.dao.Users
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserService {
    suspend fun createUser(id: Snowflake, data: UserRegistryData): User = newSuspendedTransaction {
        User.new(id) {
            username = data.username
            displayName = data.displayName
            email = data.email
            password = data.password
        }
    }

    suspend fun getUser(id: Snowflake): User? = newSuspendedTransaction {
        User.findById(id)
    }

    suspend fun getUserByEmail(email: String): User? = newSuspendedTransaction {
        User.find { Users.email eq email }.firstOrNull()
    }
    
    suspend fun getUserByUsername(username: String): User? = newSuspendedTransaction {
        User.find { Users.username eq username }.firstOrNull()
    }

    suspend fun deleteUser(id: Snowflake) = newSuspendedTransaction {
        Users.deleteWhere { Users.id eq id }
    }

    companion object {
        private val EMPTY_MAP = mapOf<String, Any>()
    }
}