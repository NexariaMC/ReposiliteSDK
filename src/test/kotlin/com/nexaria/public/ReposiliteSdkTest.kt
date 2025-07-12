package com.nexaria.public

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ReposiliteSdkTest {

    private val client = BlockingReposiliteClient(
        ReposiliteClient(
            "https://maven.evokegames.gg"
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

}