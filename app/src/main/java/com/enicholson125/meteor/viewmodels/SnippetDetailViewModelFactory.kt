package com.enicholson125.meteor.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enicholson125.meteor.data.TextSnippetRepository

/**
 * Factory for creating a [PlantDetailViewModel] with a constructor that takes a [PlantRepository]
 * and an ID for the current [Plant].
 */
class SnippetDetailViewModelFactory(
    private val textSnippetRepository: TextSnippetRepository,
    private val snippetID: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SnippetDetailViewModel(textSnippetRepository, snippetID) as T
    }
}
