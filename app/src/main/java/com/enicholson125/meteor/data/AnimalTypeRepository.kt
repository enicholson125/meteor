package com.enicholson125.meteor.data

class AnimalTypeRepository private constructor(
    private val animalTypeDAO: AnimalTypeDAO
) {
    fun getAnimalTypeByID(id: String) = animalTypeDAO.getTypeByID(id)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AnimalTypeRepository? = null

        fun getInstance(animalTypeDAO: AnimalTypeDAO) =
            instance ?: synchronized(this) {
                instance ?: AnimalTypeRepository(animalTypeDAO).also { instance = it }
            }
    }
}
