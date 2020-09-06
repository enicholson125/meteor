package com.enicholson125.meteor.viewmodels

import androidx.lifecycle.ViewModel
import com.enicholson125.meteor.data.TextSnippetRepository
import com.enicholson125.meteor.data.SnippetType

/**
 * The ViewModel used in [SnippetDetailFragment].
 */
class SnippetDetailViewModel(
    private val textSnippetRepository: TextSnippetRepository,
    private val snippetID: String
) : ViewModel() {

    val textSnippet = textSnippetRepository.getTextSnippetByID(snippetID)

    //fun getNextSnippetID() String
}
