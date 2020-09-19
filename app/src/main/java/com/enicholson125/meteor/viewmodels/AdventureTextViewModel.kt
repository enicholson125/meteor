package com.enicholson125.meteor.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.enicholson125.meteor.data.TextSnippetRepository
import com.enicholson125.meteor.data.TextHistoryRepository
import com.enicholson125.meteor.data.SpeciesRepository
import com.enicholson125.meteor.data.TextSnippet
import com.enicholson125.meteor.data.SnippetType
import com.enicholson125.meteor.data.Species
import kotlinx.coroutines.launch

/**
 * The ViewModel used in [AdventureTextActivity].
 */
class AdventureTextViewModel(
    private val textSnippetRepository: TextSnippetRepository,
    private val textHistoryRepository: TextHistoryRepository,
    private val speciesRepository: SpeciesRepository,
) : ViewModel() {
    private val resetID = "R1"
    private val startID: String = getStartID()
    var adventureText = cleanTextFromDB(textHistoryRepository.getTextHistory())
    val adventureTextLiveData: MutableLiveData<String> = MutableLiveData<String>("Default text")

    val snippetIDLiveData = MutableLiveData<String>(startID)

    val textSnippetLiveData: LiveData<TextSnippet> = Transformations.switchMap(
        snippetIDLiveData, ::updateTextSnippet,
    )

    val choicesLiveData: LiveData<Map<String, String>> = Transformations.map(
        textSnippetLiveData, ::updateAdventureText,
    )

    private fun getStartID(): String {
        return textHistoryRepository.getLastID()
        // TODO this needs to initialise the choices as the user will
        // almost definitely have been on choices when they saved their
        // last session. Currently it's going to give duplicates
    }

    private fun updateTextSnippet(snippetID: String): LiveData<TextSnippet> {
        return textSnippetRepository.getLiveTextSnippetByID(snippetID)
    }

    private fun cleanTextFromDB(text: String): String {
        val escapedNewLine = """\n"""
        val doubleNewLine = "\n\n"
        return text.replace(escapedNewLine, doubleNewLine)
    }

    private fun getChoicesMap(choices: List<String>, nextSnippets: List<String>): Map<String, String> {
        // TODO do this with a lambda function and map
        if (choices.size == 0) {
            return mapOf<String, String>()
        } else if (nextSnippets.size == 1) {
            return mapOf<String, String>(
                choices.get(0) to nextSnippets.get(0)
            )
        } else {
            return mapOf<String, String>(
                choices.get(0) to nextSnippets.get(0),
                choices.get(1) to nextSnippets.get(1)
            )
        }
    }

    private fun setNextSnippetIfKnown(snippet: TextSnippet) {
        if (snippet.choices.size == 0) {
            // Add the history just before loading a new snippet, so
            // that we are adding to the history at the last possible
            // moment, as otherwise we will get duplicates
            addHistory(snippet.description, snippet.snippetID)
            snippetIDLiveData.setValue(snippet.nextSnippets.get(0))
        }
    }

    fun updateAdventureText(snippet: TextSnippet): Map<String, String> {
        val cleanDescription = cleanTextFromDB(snippet.description)
        adventureText = adventureText + cleanDescription
        adventureTextLiveData.setValue(adventureText)
        addHistory(snippet.description, snippet.snippetID)
        if (snippet.type == SnippetType.DECISION) {
            return getChoicesMap(snippet.choices, snippet.nextSnippets)
        } else {
            snippetIDLiveData.setValue(snippet.nextSnippets.get(0))
            return mapOf<String, String>()
        }
    }

    val adoptionSpeciesLiveData: LiveData<Species> = Transformations.switchMap(
        textSnippetLiveData, ::updateSpecies,
    )

    private fun updateSpecies(snippet: TextSnippet): LiveData<Species> {
        if (snippet.animalID != null && snippet.animalID != "") {
            return speciesRepository.getSpeciesByID(snippet.animalID)
        } else {
            // TODO make it so there's some sort of sensible default
            return speciesRepository.getSpeciesByID("ForestGriffin")
        }
    }

    fun resetTextHistory() {
        adventureText = ""
        adventureTextLiveData.setValue(adventureText)
        snippetIDLiveData.setValue("T1")
        viewModelScope.launch {
            textHistoryRepository.resetTextHistory()
        }
    }

    fun addHistory(text: String, id: String) {
        viewModelScope.launch{
            textHistoryRepository.addHistory(text, id)
        }
    }

    fun makeChoice(choiceText: String, snippetID: String) {
        val formattedChoiceText = "\n\n" + choiceText + "\n\n"
        adventureText = adventureText + formattedChoiceText
        adventureTextLiveData.setValue(adventureText)

        snippetIDLiveData.setValue(snippetID)

        // Add the choice text into history, so that the user can see
        // what choice they selected.
        addHistory(formattedChoiceText, snippetID)

        // TODO make this check the type, not be hardcoded by ID
        if (snippetID == resetID) {
            resetTextHistory()
        }
    }

    fun appendToAdventureText(addition: String) {
        adventureTextLiveData.setValue(adventureTextLiveData.value + addition)
    }

    fun isAdoptionID(id: String): Boolean {
        return id.get(0).toLowerCase() == 'a'
    }
}

