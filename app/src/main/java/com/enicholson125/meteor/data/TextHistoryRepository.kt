package com.enicholson125.meteor.data

class TextHistoryRepository private constructor(
    private val textHistoryDAO: TextHistoryDAO
) {
    private var currentIndex = getMostRecentIndex()

    // TODO check this is empty string when there's nothing in the DB
    fun getTextHistory(): String {
        val history = textHistoryDAO.getTextHistory()
        if (history == "null") {
            return ""
        }
        return history
    }

    fun getLastID(): String {
        val lastID = textHistoryDAO.getSnippetIDByIndex(currentIndex)
        if (lastID == null) {
            return "T1"
        }
        return lastID
    }

    // I'm assuming this will return 0 when the table is empty. This seems
    // like a pretty big assumption
    private fun getMostRecentIndex(): Int =
        textHistoryDAO.getMostRecentIndex()

    fun addHistoryByValue(description: String, id: String) {
        // Increment index even if transaction fails - the important
        // thing is the ordering by index, not that they are strictly
        // one incremented from each other
        currentIndex = currentIndex + 1
        val textHistory = TextHistory(
            textIndex = currentIndex,
            textDescription = description,
            snippetID = id,
        )
        textHistoryDAO.insertTextHistory(textHistory)
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
