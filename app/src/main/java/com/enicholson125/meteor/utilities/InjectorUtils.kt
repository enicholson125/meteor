package com.enicholson125.meteor.utilities

import android.content.Context
import com.enicholson125.meteor.data.TextSnippetRepository
import com.enicholson125.meteor.data.TextHistoryRepository
import com.enicholson125.meteor.data.SpeciesRepository
import com.enicholson125.meteor.data.AdoptedAnimalRepository
import com.enicholson125.meteor.data.AppDatabase
import com.enicholson125.meteor.viewmodels.AdventureTextViewModelFactory
import com.enicholson125.meteor.viewmodels.AdoptionDialogViewModelFactory
import com.enicholson125.meteor.viewmodels.AdoptedAnimalsViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getTextSnippetRepository(context: Context): TextSnippetRepository {
        return TextSnippetRepository.getInstance(
            AppDatabase.getInstance(context).textSnippetDAO()
        )
    }

    private fun getTextHistoryRepository(context: Context): TextHistoryRepository {
        return TextHistoryRepository.getInstance(
            AppDatabase.getInstance(context).textHistoryDAO()
        )
    }

    private fun getSpeciesRepository(context: Context): SpeciesRepository {
        return SpeciesRepository.getInstance(
            AppDatabase.getInstance(context).speciesDAO()
        )
    }

    private fun getAdoptedAnimalRepository(context: Context): AdoptedAnimalRepository {
        return AdoptedAnimalRepository.getInstance(
            AppDatabase.getInstance(context).adoptedAnimalDAO()
        )
    }

    fun provideAdventureTextViewModelFactory(
        context: Context,
    ): AdventureTextViewModelFactory {
        return AdventureTextViewModelFactory(
            getTextSnippetRepository(context),
            getTextHistoryRepository(context),
            getSpeciesRepository(context),
        )
    }

    fun provideAdoptionDialogViewModelFactory(
        context: Context,
    ): AdoptionDialogViewModelFactory {
        return AdoptionDialogViewModelFactory(
            getAdoptedAnimalRepository(context),
        )
    }

    fun provideAdoptedAnimalsViewModelFactory(
        context: Context,
    ): AdoptedAnimalsViewModelFactory {
        return AdoptedAnimalsViewModelFactory(
            getAdoptedAnimalRepository(context),
        )
    }
}
