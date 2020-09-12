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
 * The ViewModel used in [ScrollingActivity].
 */
class AdventureTextViewModel(
    private val textSnippetRepository: TextSnippetRepository,
    private val textHistoryRepository: TextHistoryRepository,
) : ViewModel() {
    private val resetID = "T1"
    private val startID: String = getStartID()
    var adventureText = textHistoryRepository.getTextHistory()
    val adventureTextLiveData: MutableLiveData<String> = MutableLiveData<String>("Default text")

    val snippetIDLiveData = MutableLiveData<String>(snippetID)

    val textSnippetLiveData: LiveData<TextSnippet> = Transformations.switchMap(
        snippetIDLiveData, ::updateTextSnippet
    )

    val choicesLiveData: LiveData<Map<String, String>> = Transformations.map(
        textSnippetLiveData, ::updateAdventureText
    )

    private fun getStartID(): String {
        val lastID = textHistoryRepository.getLastID()
        val lastSnippet = textSnippetRepository.getTextSnippetByID(lastID)
        setNextSnippetIfKnown(snippet)
        // TODO this needs to initialise the choices as the user will
        // almost definitely have been on choices when they saved their
        // last session
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
            snippetIDLiveData.setValue(snippet.nextSnippets.get(0))
    }

    fun updateAdventureText(snippet: TextSnippet): Map<String, String> {
        val cleanDescription = cleanTextFromDB(snippet.description)
        adventureText = adventureText + cleanDescription
        if (snippet.type == SnippetType.TEXT) {
            adventureText = adventureText + "\n\n"
        }
        adventureTextLiveData.setValue(adventureText)
        setNextSnippetIfKnown(snippet)
        return getChoicesMap(snippet.choices, snippet.nextSnippets)
    }

    private fun resetTextHistory() {
        adventureText = ""
        adventureTextLiveData.setValue(adventureText)
        snippetIDLiveData.setValue("T1")
        textHistoryRepository.resetTextHistory()
    }

    fun makeChoice(choiceText: String, snippetID: String) {
        adventureText = adventureText + "\n\n" + choiceText.toUpperCase() + "\n\n"
        adventureTextLiveData.setValue(adventureText)
        snippetIDLiveData.setValue(snippetID)
        textHistoryRepository.addHistoryByValue(choiceText, snippetID)

        if (snippetID == resetID) {
            resetTextHistory()
        }
    }

    fun appendToAdventureText(addition: String) {
        adventureTextLiveData.setValue(adventureTextLiveData.value + addition)
    }
}

