package io.github.vibraplatform.webserver.route

import io.github.vibraplatform.common.snowflake.SnowflakeService
import io.github.vibraplatform.common.struct.RawWorkMapping
import io.github.vibraplatform.common.struct.WorkRegistryData
import io.github.vibraplatform.database.service.WorkService
import io.github.vibraplatform.database.util.toDTO
import io.github.vibraplatform.webserver.auth.authenticate
import io.github.vibraplatform.webserver.util.receiveOrBadRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.resources.post
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.koin.ktor.ext.inject

fun Route.workRoute() {
    val workService by inject<WorkService>()
    val snowflakeService by inject<SnowflakeService>()
    post<UserRoute.Me.Works> {
        authenticate { user ->
            val work = call.receiveOrBadRequest<WorkRegistryData>() ?: return@post
            val createdWork = workService.createWork(
                snowflakeService.nextNode().generate(),
                user,
                work
            )
            call.respond(HttpStatusCode.Created, createdWork.toDTO())
        }
    }
    get<UserRoute.Me.Works> {
        authenticate { user ->
            call.respond(HttpStatusCode.OK, user.getWorks().map { it.toDTO() })
        }
    }
    get<UserRoute.Me.Works.Id> { (id) ->
        authenticate { user ->
            val work = user.works.find { it.snowflake == id }
            when (work) {
                null -> call.respond(HttpStatusCode.NotFound)
                else -> call.respond(HttpStatusCode.OK, work.toDTO())
            }
        }
    }
    delete<UserRoute.Me.Works.Id> { (id) ->
        authenticate { user ->
            val work = user.works.find { it.snowflake == id }
            when (work) {
                null -> call.respond(HttpStatusCode.NotFound)
                else -> {
                    work.delete()
                    call.respond(HttpStatusCode.NoContent)
                }
            }
        }
    }
    get<UserRoute.Me.Works.Mappings.Website.Value> { value ->
        authenticate { user ->
            val website = value.website.website
            val value = value.value
            println(user.getMappings().map {
                "${it.id.value} ${it.value}"
            })

            val (mapping, workDto) = newSuspendedTransaction {
                val mapping = user.mappings.find { it.website == website && it.value == value }
                mapping to mapping?.work?.toDTO()
            }
            when (mapping) {
                null -> call.respond(HttpStatusCode.NotFound)
                else -> call.respond(HttpStatusCode.OK, workDto!!)
            }
        }
    }
}
