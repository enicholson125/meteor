package com.enicholson125.meteor.data

class AdoptedAnimalRepository private constructor(
    private val adoptedAnimalDAO: AdoptedAnimalDAO
) {
    fun getAdoptedAnimalByID(id: String) = adoptedAnimalDAO.getAdoptedAnimalByID(id)

    fun getAllAdoptedAnimals() = adoptedAnimalDAO.getAllAdoptedAnimals()

    suspend fun addAdoptedAnimal(adoptedAnimal: AdoptedAnimal) = adoptedAnimalDAO.insertAdoptedAnimal(adoptedAnimal)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AdoptedAnimalRepository? = null

        fun getInstance(adoptedAnimalDAO: AdoptedAnimalDAO) =
            instance ?: synchronized(this) {
                instance ?: AdoptedAnimalRepository(adoptedAnimalDAO).also { instance = it }
            }
    }
}
