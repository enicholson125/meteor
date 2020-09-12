package com.enicholson125.meteor.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

/**
 * The Data Access Object for the [TextSnippet] class.
 */
@Dao
interface TextSnippetDAO {
    @Query("SELECT * FROM text_snippets WHERE id = :id")
    fun getLiveTextSnippetByID(id: String): LiveData<TextSnippet>

    @Query("SELECT * FROM text_snippets WHERE id = :id")
    fun getTextSnippetByID(id: String): TextSnippet
}
