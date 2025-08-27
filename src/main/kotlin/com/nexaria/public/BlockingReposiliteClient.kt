package com.nexaria.public

import com.nexaria.public.session.SessionDetails
import kotlinx.coroutines.runBlocking

class BlockingReposiliteClient(
    private val client: ReposiliteClient
) {


    val wroteCredentials: Boolean get() = client.wroteCredentials
    val credentials: String? get() = client.credentials

    fun getTokenDetails(): SessionDetails = runBlocking { client.getTokenDetails() }
    fun getInstanceStatus(): InstanceStatusResponse = runBlocking { client.getInstanceStatus() }
    fun getSettingsDomains(): List<String> = runBlocking { client.getSettingsDomains() }
    fun getFileDetails(repository: String, gav: String): FileDetails =
        runBlocking { client.getFileDetails(repository, gav) }

    fun executeCommand(command: String): ExecutionResponse =
        runBlocking { client.executeCommand(command) }

    fun getLatestVersion(
        repository: String,
        gav: String,
        extension: String? = null,
        classifier: String? = null,
        filter: String? = null,
        raw: Boolean = false
    ): String = runBlocking {
        client.getLatestVersion(repository, gav, extension, classifier, filter, raw)
    }

    fun close() = client.close()
}