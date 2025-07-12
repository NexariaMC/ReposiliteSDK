package com.nexaria.public

import kotlinx.serialization.Serializable

@Serializable
/**
    Base request object.
 */
data class CommandRequest(
    val command: String
)