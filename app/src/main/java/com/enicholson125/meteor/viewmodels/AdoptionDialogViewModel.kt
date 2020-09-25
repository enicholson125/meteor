package com.enicholson125.meteor.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.enicholson125.meteor.data.AdoptedAnimalRepository
import com.enicholson125.meteor.data.AdoptedAnimal
import com.enicholson125.meteor.data.Species
import com.enicholson125.meteor.utilities.GeneratorUtils
import kotlinx.coroutines.launch

/**
 * The ViewModel used in [AdoptionDialogFragment].
 */
class AdoptionDialogViewModel(
    private val adoptedAnimalRepository: AdoptedAnimalRepository,
) : ViewModel() {
    private val idLength = 10

    fun addAdoptedAnimal(name: String, id: String) {
        viewModelScope.launch{
            adoptedAnimalRepository.addAdoptedAnimal(
                AdoptedAnimal(GeneratorUtils.getRandomID(idLength), id, name)
            )
        }
    }
}

