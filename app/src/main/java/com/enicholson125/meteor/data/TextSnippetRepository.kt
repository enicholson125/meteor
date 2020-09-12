package com.enicholson125.meteor.data

class TextSnippetRepository private constructor(
    private val textSnippetDAO: TextSnippetDAO
) {

    fun getTextSnippetByID(snippetID: String) = textSnippetDAO.getTextSnippetByID(snippetID)

    fun getLiveTextSnippetByID(snippetID: String) = textSnippetDAO.getLiveTextSnippetByID(snippetID)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TextSnippetRepository? = null

        fun getInstance(textSnippetDAO: TextSnippetDAO) =
            instance ?: synchronized(this) {
                instance ?: TextSnippetRepository(textSnippetDAO).also { instance = it }
            }
    }
}
