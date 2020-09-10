package com.enicholson125.meteor.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.enicholson125.meteor.data.TextSnippetRepository
import com.enicholson125.meteor.data.TextSnippet
import com.enicholson125.meteor.data.SnippetType

/**
 * The ViewModel used in [SnippetDetailFragment].
 */
class AdventureTextViewModel(
    private val textSnippetRepository: TextSnippetRepository,
    private var snippetID: String = "T1",
    //val adventureText: MutableLiveData<String> = MutableLiveData<String>()
) : ViewModel() {
    var adventureText = ""
    val adventureTextLiveData: MutableLiveData<String> = MutableLiveData<String>("Default text")

    val snippetIDLiveData = MutableLiveData<String>(snippetID)

    val textSnippetLiveData: LiveData<TextSnippet> = Transformations.switchMap(
        snippetIDLiveData, ::updateTextSnippet
    )

    // This is a bit of a hack, as I don't actually want a liveData
    // output from this transformation, I just want to trigger the
    // mutable data updates
    val choicesLiveData: LiveData<Map<String, String>> = Transformations.map(
        textSnippetLiveData, ::updateAdventureText
    )

    private fun updateTextSnippet(snippetID: String): LiveData<TextSnippet> {
        return textSnippetRepository.getTextSnippetByID(snippetID)
    }

    fun updateAdventureText(snippet: TextSnippet): Map<String, String> {
        adventureText = adventureText + snippet.description
        adventureTextLiveData.setValue(adventureText)
        if (snippet.nextSnippets.size == 1) {
            snippetIDLiveData.setValue(snippet.nextSnippets.get(0))
            return mapOf<String, String>()
        } else {
            // TODO do this with a lambda function and map
            return mapOf<String, String>(
                snippet.choices.get(0) to snippet.nextSnippets.get(0),
                snippet.choices.get(1) to snippet.nextSnippets.get(1)
            )
        }
    }

    fun updateSnippetID(id: String) {
        snippetIDLiveData.setValue(id)
    }

    fun appendToAdventureText(addition: String) {
        adventureTextLiveData.setValue(adventureTextLiveData.value + addition)
    }
}

