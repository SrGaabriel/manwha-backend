package io.github.vibraplatform.database.util

import io.github.vibraplatform.common.struct.RawUser
import io.github.vibraplatform.common.struct.RawWork
import io.github.vibraplatform.database.dao.User
import io.github.vibraplatform.database.dao.Work

fun User.toDTO() = RawUser(
    id = snowflake,
    username = username,
    displayName = displayName
)

fun Work.toDTO() = RawWork(
    id = snowflake,
    name = name,
    chapter = chapter,
    status = status
)