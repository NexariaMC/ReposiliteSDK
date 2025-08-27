package com.nexaria.public

import com.nexaria.public.session.SessionDetails
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.request
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.util.Base64

class ReposiliteClient(
    private val baseUrl: String,
    val credentials: String? = null,
    private val debug: Boolean = false,
) : AutoCloseable {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    init {
        println(credentials)
    }

    val wroteCredentials: Boolean
        get() = credentials != null


    constructor(
        baseUrl: String,
        username: String,
        password: String,
        debug: Boolean = false
    ): this(
        baseUrl,
        createBasicAuth(username, password),
        debug
    )

    suspend fun getTokenDetails(): SessionDetails = get("/api/auth/me")
    suspend fun getInstanceStatus(): InstanceStatusResponse = get("/api/status/instance")
    suspend fun getSettingsDomains(): List<String> = get("/api/settings/domains")
    suspend fun getFileDetails(repository: String, gav: String): FileDetails =
        get("/api/maven/details/$repository/$gav")

    suspend fun executeCommand(command: String): ExecutionResponse {
        return client.post("$baseUrl/api/console/execute") {
            setAuth()
            contentType(ContentType.Application.Json)
            setBody(CommandRequest(command))
        }.body()
    }

    suspend fun getLatestVersion(
        repository: String,
        gav: String,
        extension: String? = null,
        classifier: String? = null,
        filter: String? = null,
        raw: Boolean = false
    ): String {
        return client.get("$baseUrl/api/maven/latest/version/$repository/$gav") {
            setAuth()
            parameter("extension", extension)
            parameter("classifier", classifier)
            parameter("filter", filter)
            parameter("type", if (raw) "raw" else null)
        }.body()
    }

    private suspend inline fun <reified T> get(path: String): T {

        val amogus =
        client.get("$baseUrl$path") {
            setAuth()
            contentType(ContentType.Application.Json)
        }
        if (amogus.status.isSuccess()) {
            return amogus.body()
        }

        val errorBuilder = StringBuilder()
        errorBuilder.append("Failed to fetch data from $path: ${amogus.status.value} - ${amogus.body<String>()}")
        if (debug) {
            val req = amogus.request
            errorBuilder.append("\nRequest URL: ${req.url}")
            errorBuilder.append("\nRequest method: ${req.method.value}")
            errorBuilder.append("\nRequest headers: ${req.headers}")
        }
        throw RuntimeException(errorBuilder.toString())
    }

    private fun HttpRequestBuilder.setAuth() {
        credentials?.let {
            header("Authorization", it)
        }
    }

    override fun close() {
        client.close()
    }

    companion object {
        fun createBasicAuth(username: String, password: String): String {
            val auth = "$username:$password".encodeBase64()
            return "Basic $auth"
        }

        private fun String.encodeBase64(): String {
            return Base64.getEncoder().encodeToString(toByteArray())
        }
    }
}
