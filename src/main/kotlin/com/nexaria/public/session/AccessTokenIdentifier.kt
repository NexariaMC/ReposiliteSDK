package com.nexaria.public.session

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenIdentifier(
    val type: String,
    val value: Int
)
