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
import kotlinx.coroutines.launch

/**
 * The ViewModel used in [AdoptionDialogFragment].
 */
class AdoptionDialogViewModel(
    private val adoptedAnimalRepository: AdoptedAnimalRepository,
) : ViewModel() {
    fun addAdoptedAnimal(name: String, id: String) {
        viewModelScope.launch{
            val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
            val randomString = (1..10)
                .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
            adoptedAnimalRepository.addAdoptedAnimal(
                AdoptedAnimal(randomString, id, name)
            )
        }
    }
}

