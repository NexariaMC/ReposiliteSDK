package com.nexaria.public

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReposiliteSdkTest {
   private val client = BlockingReposiliteClient(
        ReposiliteClient(
            "https://repo.nexariamc.net",
            debug = true
        )
    )



    @Test
    fun testGetFileDetails() {
        val fileDetails = client.getFileDetails(
            repository = "snapshots",
            gav = "me/tofaa/entitylib/api/+1f4aeef-SNAPSHOT"
        )
        assertEquals(FileType.DIRECTORY, fileDetails.type)
        println(fileDetails)
    }

    @Test
    fun testGetLatestVersion() {

        val latestVersion = client.getLatestVersion(
            repository = "snapshots",
            gav = "me/tofaa/entitylib/api"
        )
        println(latestVersion)
    }

    @Test
    fun testInstanceStatus() {
        val status = client.getInstanceStatus()
        println(status)
    }

    @Test
    fun testTokenDetails() {
        val d = client.getTokenDetails()
        println(d)
    }

}