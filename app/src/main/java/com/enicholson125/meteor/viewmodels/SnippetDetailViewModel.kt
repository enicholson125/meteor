package com.enicholson125.meteor.viewmodels

import androidx.lifecycle.ViewModel
import com.enicholson125.meteor.data.TextSnippetRepository
import com.enicholson125.meteor.data.SnippetType

/**
 * The ViewModel used in [SnippetDetailFragment].
 */
class SnippetDetailViewModel(
    private val textSnippetRepository: TextSnippetRepository,
    private var snippetID: String
) : ViewModel() {

    var textSnippet = textSnippetRepository.getTextSnippetByID(snippetID)

    fun updateSnippetID(id: String) {
        snippetID = id
        textSnippet = textSnippetRepository.getTextSnippetByID(snippetID)
    }

    //fun getNextSnippetID() String
}
