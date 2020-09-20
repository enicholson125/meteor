package com.enicholson125.meteor.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enicholson125.meteor.data.AdoptedAnimalRepository
import com.enicholson125.meteor.data.SpeciesRepository

/**
 * Factory for creating a [AdoptedAnimalsViewModel] with a constructor that
 * takes a [AdoptedAnimalRepository]
 */
class AdoptedAnimalsViewModelFactory(
    private val adoptedAnimalRepository: AdoptedAnimalRepository,
    private val speciesRepository: SpeciesRepository,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AdoptedAnimalsViewModel(
            adoptedAnimalRepository,
            speciesRepository
        ) as T
    }
}
