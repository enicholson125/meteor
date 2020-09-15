package com.enicholson125.meteor.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

/**
 * The Data Access Object for the [TextHistory] class.
 */
@Dao
interface TextHistoryDAO {
    @Query("SELECT group_concat(text_description, '\n\n') FROM text_history ORDER BY text_index DESC")
    fun getTextHistory(): String

    @Query("SELECT snippet_id FROM text_history WHERE text_index = :index")
    fun getSnippetIDByIndex(index: Int): String

    @Query("SELECT MAX(text_index) FROM text_history")
    fun getMostRecentIndex(): Int

    @Insert
    suspend fun insertTextHistory(textHistory: TextHistory)

    @Query("DELETE FROM text_history")
    suspend fun resetTextHistory()
}
