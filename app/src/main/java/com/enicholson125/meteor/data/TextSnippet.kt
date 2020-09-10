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

    @ColumnInfo(name = "snippet_type") val type: SnippetType,

    @ColumnInfo(name = "snippet") val description: String,

    @ColumnInfo(name = "next_snippets") val nextSnippets: List<String>,

    @ColumnInfo(name = "choices") val choices: List<String>?,

    @ColumnInfo(name = "animal_id") val animalID: String?,
) {
}
