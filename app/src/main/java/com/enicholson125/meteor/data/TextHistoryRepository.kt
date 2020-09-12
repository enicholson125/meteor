package com.enicholson125.meteor.data

class TextHistoryRepository private constructor(
    private val textHistoryDAO: TextHistoryDAO
) {
    private var currentIndex = getMostRecentIndex()

    // TODO check this is empty string when there's nothing in the DB
    fun getTextHistory(): String =
        textHistoryDAO.getTextHistory()

    // TODO I'm also expecting this to return empty string when there's nothing
    // in the DB
    fun getLastID(): String = textHistoryDAO.getSnippetIDByIndex(currentIndex)

    // I'm assuming this will return 0 when the table is empty. This seems
    // like a pretty big assumption
    private fun getMostRecentIndex(): Int =
        textHistoryDAO.getMostRecentIndex()

    fun addHistoryByValue(description: String, id: String) {
        // Increment index even if transaction fails - the important
        // thing is the ordering by index, not that they are strictly
        // one incremented from each other
        index = index + 1
        textHistoryDAO.insertTextHistory(index, description, id)
    }

    fun addHistoryBySnippet(snippet: TextSnippet) =
        addHistoryByValue(snippet.description, snippet.snippetID)

    fun resetTextHistory() = textHistoryDAO.resetTextHistory()

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TextHistoryRepository? = null

        fun getInstance(textHistoryDAO: TextHistoryDAO) =
            instance ?: synchronized(this) {
                instance ?: TextHistoryRepository(textHistoryDAO).also { instance = it }
            }
    }
}
