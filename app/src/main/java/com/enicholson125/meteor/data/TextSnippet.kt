package com.enicholson125.meteor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// TODO have snippet types be defined in code, not written in comments
enum class SnippetType {
    // Texts always have a description, a singular nextSnippet and no choices or animalIDs
    TEXT,
    // Decisions have no descriptions, one or two nextSnippets, the same number of choices as nextSnippets and no animalID
    DECISION,
    // Adoptions have no descriptions, a singular nextSnippet, no choices and a singular animalID
    ADOPTION,
    // Reset has no description, a singular nextSnippet and no choices or animalIDs
    RESET,
}

@Entity(
    tableName = "text_snippets",
    foreignKeys = [
        ForeignKey(entity = Species::class, parentColumns = ["id"], childColumns = ["animal_id"])
    ],
    indices = [Index("id"), Index("animal_id")]
)
data class TextSnippet(
    @PrimaryKey @ColumnInfo(name = "id") val snippetID: String,

    @ColumnInfo(name = "snippet_type") val type: SnippetType,

    @ColumnInfo(name = "snippet") val description: String,

    @ColumnInfo(name = "next_snippets") val nextSnippets: List<String>,

    @ColumnInfo(name = "choices") val choices: List<String>,

    @ColumnInfo(name = "animal_id") val animalID: String?,

    @ColumnInfo(name = "hidden_animal_text") val hiddenAnimalText: String?,

    @ColumnInfo(name = "additional_text") val additionalText: String?,
) {
}
