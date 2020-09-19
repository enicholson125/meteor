package com.enicholson125.meteor.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enicholson125.meteor.data.AdoptedAnimalRepository

/**
 * Factory for creating a [AdoptionDialogViewModel] with a constructor that
 * takes a [AdoptedAnimalRepository] and a [TextHistoryRepository]
 */
class AdoptionDialogViewModelFactory(
    private val adoptedAnimalRepository: AdoptedAnimalRepository,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AdoptionDialogViewModel(
            adoptedAnimalRepository,
        ) as T
    }
}
