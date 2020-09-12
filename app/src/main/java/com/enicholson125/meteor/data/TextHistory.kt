package com.enicholson125.meteor.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "text_history",
    foreignKeys = [
        ForeignKey(entity = TextSnippet::class, parentColumns = ["id"], childColumns = ["snippet_id"])
    ],
    indices = [Index("snippet_id")],
)
data class TextHistory(
    @PrimaryKey @ColumnInfo(name = "text_index") val textIndex: Int,

    @ColumnInfo(name = "text_description") val textDescription: String,

    @ColumnInfo(name = "snippet_id") val snippetID: String,
) {
}
