package com.nexaria.public.session

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenDto(
    val identifier: AccessTokenIdentifier,
    val name: String,
    val createdAt: String,
    val description: String
)
