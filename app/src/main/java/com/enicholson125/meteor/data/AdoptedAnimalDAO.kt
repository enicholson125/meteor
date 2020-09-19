package com.enicholson125.meteor.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

/**
 * The Data Access Object for the [AdoptedAnimal] class.
 */
@Dao
interface AdoptedAnimalDAO {
    @Query("SELECT * FROM adopted_animals WHERE id = :id")
    fun getAdoptedAnimalByID(id: String): LiveData<AdoptedAnimal>

    @Query("SELECT * FROM adopted_animals")
    fun getAllAdoptedAnimals(): LiveData<List<AdoptedAnimal>>

    @Insert
    suspend fun insertAdoptedAnimal(adoptedAnimal: AdoptedAnimal)
}
