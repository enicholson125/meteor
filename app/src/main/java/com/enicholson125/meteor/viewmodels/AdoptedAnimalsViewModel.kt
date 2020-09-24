package com.enicholson125.meteor.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.Observer
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

    private val allAdoptedAnimals = adoptedAnimalRepository.getAllAdoptedAnimals()
    private val currentAnimalIndex = MutableLiveData<Int>(1)

    var currentAnimal = MediatorLiveData<AdoptedAnimal>().apply {
        addSource(allAdoptedAnimals, ::updateCurrentAdoptedAnimal)
        addSource(currentAnimalIndex, ::updateCurrentAdoptedAnimal)
    }

    fun updateCurrentAdoptedAnimal(animalList: List<AdoptedAnimal>) {
        if (animalList.size > 0) {
            var animalIndex = currentAnimalIndex.value!!
            if (animalIndex < 0) {
                animalIndex = animalIndex * -1
            }
            currentAnimal.setValue(animalList.get(animalIndex % animalList.size))
        }
    }

    fun updateCurrentAdoptedAnimal(index: Int) {
        val animalList = allAdoptedAnimals.value
        if (animalList != null) {
            updateCurrentAdoptedAnimal(animalList)
        }
    }

    val currentAnimalSpecies: LiveData<Species> = Transformations.switchMap(
        currentAnimal, ::getAnimalSpecies,
    )

    val animalListSize: LiveData<Int> = Transformations.map(
        allAdoptedAnimals, { animalList -> animalList.size })

    fun incrementCurrentAnimalIndex() {
        currentAnimalIndex.setValue(currentAnimalIndex.value!! + 1)
    }

    fun decrementCurrentAnimalIndex() {
        currentAnimalIndex.setValue(currentAnimalIndex.value!! - 1)
    }

    private fun getAnimalSpecies(animal: AdoptedAnimal): LiveData<Species> {
        return speciesRepository.getSpeciesByID(animal.speciesID)
    }
}

