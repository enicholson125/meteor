package com.enicholson125.meteor.data

import androidx.lifecycle.LiveData

class SpeciesRepository private constructor(
    private val speciesDAO: SpeciesDAO
) {
    fun getSpeciesByID(id: String): LiveData<Species> = speciesDAO.getTypeByID(id)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: SpeciesRepository? = null

        fun getInstance(speciesDAO: SpeciesDAO) =
            instance ?: synchronized(this) {
                instance ?: SpeciesRepository(speciesDAO).also { instance = it }
            }
    }
}
