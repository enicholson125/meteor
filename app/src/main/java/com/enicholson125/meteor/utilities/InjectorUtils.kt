package com.enicholson125.meteor.utilities

import android.content.Context
import com.enicholson125.meteor.data.TextSnippetRepository
import com.enicholson125.meteor.data.AppDatabase
import com.enicholson125.meteor.viewmodels.SnippetDetailViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getTextSnippetRepository(context: Context): TextSnippetRepository {
        return TextSnippetRepository.getInstance(
            AppDatabase.getInstance(context).textSnippetDAO()
        )
    }

    fun provideSnippetDetailViewModelFactory(
        context: Context,
        snippetID: String = "T1",
    ): SnippetDetailViewModelFactory {
        return SnippetDetailViewModelFactory(getTextSnippetRepository(context), snippetID)
    }
}
