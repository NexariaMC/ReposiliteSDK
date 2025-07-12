package com.nexaria.public.session

import kotlinx.serialization.Serializable

@Serializable
data class Route(
    val path: String,
    val permission: RoutePermission
)

enum class RoutePermission {
    READ,
    WRITE,
}