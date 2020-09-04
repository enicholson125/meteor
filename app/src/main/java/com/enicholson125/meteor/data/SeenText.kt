package com.enicholson125.meteor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "seen_text",
    foreignKeys = [
        ForeignKey(entity = TextSnippet::class, parentColumns = ["id"], childColumns = ["snippet_id"])
    ],
    indices = [Index("snippet_id")],
)
data class SeenText(
    @PrimaryKey @ColumnInfo(name = "text_index") val textIndex: Long,

    @ColumnInfo(name = "snippet_id") val snippetID: String,
) {
}
