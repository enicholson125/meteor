package com.enicholson125.meteor.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.enicholson125.meteor.data.AdoptedAnimalRepository
import com.enicholson125.meteor.data.SpeciesRepository
import com.enicholson125.meteor.data.AdoptedAnimal
import com.enicholson125.meteor.data.Species
import kotlinx.coroutines.launch

/**
 * The ViewModel used in [AdoptedAnimalsActivity].
 */
class AdoptedAnimalsViewModel(
    private val adoptedAnimalRepository: AdoptedAnimalRepository,
    private val speciesRepository: SpeciesRepository,
) : ViewModel() {

    val allAdoptedAnimals = adoptedAnimalRepository.getAllAdoptedAnimals()

    val currentAnimalID = MutableLiveData<String>("Set")
}

