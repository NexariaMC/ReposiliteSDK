package com.nexaria.public

import kotlinx.serialization.Serializable

@Serializable
data class FileDetails(
    val type: FileType,
    val name: String
)

enum class FileType {
    FILE,
    DIRECTORY,
}
