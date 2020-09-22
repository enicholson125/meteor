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
    // init {
    //     currentAnimal.value = AdoptedAnimal("unset", "unset", "unset")
    // }
    // currentAnimal.addSource(allAdoptedAnimals, updateCurrentAdoptedAnimal::)
    // currentAnimal.addSource(currentAnimalIndex, Observer<Int>() { value ->
    //     currentAnimal.setValue = getAdoptedAnimalFromList(allAdoptedAnimals, currentAnimalIndex)
    // })

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

    // fun updateCurrentAdoptedAnimal(animal: AdoptedAnimal?) {
    //     val adoptedList = allAdoptedAnimals.value
    //     val index = currentAnimalIndex.value

    //     // Don't send a success until we have both results
    //     if (adoptedList == null || index == null || adoptedList.size == 0) {
    //         // TODO return better default adopted animal
    //         currentAnimal.setValue(AdoptedAnimal("unset", "unset", "unset"))
    //     }
    //     currentAnimal.setValue(adoptedList.get(index % adoptedList.size))
    // }


    // val left = MutableLiveData<String>()
    // val right = MutableLiveData<String>()
    // val result = MediatorLiveData<String>().apply {

    //     val plus: (String?) -> Unit = plus@{
    //         val leftNum = left.value?.toIntOrNull()
    //         if (leftNum == null) {
    //             visibleResult.value = false
    //             return@plus
    //         }

    //         val rightNum = right.value?.toIntOrNull()
    //         if (rightNum == null) {
    //             visibleResult.value = false
    //             return@plus
    //         }

    //         value = (leftNum + rightNum).toString()
    //         visibleResult.value = true
    //     }

    //     addSource(left, plus)
    //     addSource(right, plus)
    // }

    fun incrementCurrentAnimalIndex() {
        currentAnimalIndex.setValue(currentAnimalIndex.value!! + 1)
    }

    fun decrementCurrentAnimalIndex() {
        currentAnimalIndex.setValue(currentAnimalIndex.value!! - 1)
    }

    val currentAnimalSpecies: LiveData<Species> = Transformations.switchMap(
        currentAnimal, ::getAnimalSpecies,
    )

    private fun getAnimalSpecies(animal: AdoptedAnimal): LiveData<Species> {
        return speciesRepository.getSpeciesByID(animal.speciesID)
    }
}

