package com.nexaria.public

import kotlinx.serialization.Serializable

@Serializable
data class ExecutionResponse(
    val status: String,
    val response: List<String>
)