package com.enicholson125.meteor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

enum class SnippetType {
    TEXT,
    DECISION,
    ADOPTION,
}

@Entity(
    tableName = "text_snippets",
    foreignKeys = [
        ForeignKey(entity = AnimalType::class, parentColumns = ["id"], childColumns = ["animal_id"])
    ],
    indices = [Index("id"), Index("animal_id")]
)
data class TextSnippet(
    @PrimaryKey @ColumnInfo(name = "id") val snippetID: String,

    @ColumnInfo(name = "snippet_type") val snippetType: SnippetType,

    @ColumnInfo(name = "snippet") val snippet: String,

    @ColumnInfo(name = "next_snippets") val nextSnippet: String?,

    @ColumnInfo(name = "choices") val choices: String?,

    @ColumnInfo(name = "animal_id") val animalID: String?,
) {
}
