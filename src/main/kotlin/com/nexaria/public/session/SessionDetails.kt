package com.nexaria.public.session

import com.nexaria.public.session.Route
import kotlinx.serialization.Serializable

@Serializable
data class SessionDetails(
    val accessToken: AccessTokenDto,
    val permissions: List<String>,
    val routes: List<Route>
)