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
    @Query("SELECT group_concat(description) FROM text_history ORDER BY index DESC")
    fun getTextHistory(): String

    @Query("SELECT snippet_id FROM text_history WHERE index = :index")
    fun getSnippetIDByIndex(index: Int): String

    @Query("SELECT MAX(index) FROM text_history")
    fun getMostRecentIndex(): Int

    fun insertTextHistory(index: Int, description: String, id: String)

    fun resetTextHistory()
}
