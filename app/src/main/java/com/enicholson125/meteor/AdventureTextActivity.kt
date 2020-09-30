package com.enicholson125.meteor

import android.util.Log
import android.os.Bundle
import android.content.Context
import android.content.Intent
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.enicholson125.meteor.utilities.InjectorUtils
import com.enicholson125.meteor.viewmodels.AdventureTextViewModel
import com.enicholson125.meteor.viewmodels.AdoptionDialogViewModel
import com.enicholson125.meteor.data.TextSnippet
import com.enicholson125.meteor.data.Species
import com.enicholson125.meteor.AdoptionDialogFragment

class AdventureTextActivity : AppCompatActivity(),
        AdoptionDialogFragment.AdoptionDialogListener {
    var adoptionSpecies = Species("unset", "unset", "unset", "ic_launcher_background")

    private val model: AdventureTextViewModel by viewModels {
        InjectorUtils.provideAdventureTextViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_adventure_text)

        val textView = findViewById<TextView>(R.id.text_view)
        val buttonList = mutableListOf<Button>()
        buttonList.add(findViewById<Button>(R.id.button_choice_0))
        buttonList.add(findViewById<Button>(R.id.button_choice_1))

        val choicesObserver = Observer<Map<String, String>> { choicesMap ->
            if (choicesMap.size == 1) {
                buttonList.get(1).setVisibility(View.INVISIBLE)
            }
            var index = 0
            for ((choiceText, snippetID) in choicesMap) {
                buttonList.get(index).setVisibility(View.VISIBLE)
                buttonList.get(index).text = choiceText
                buttonList.get(index).setOnClickListener { _ ->
                    makeChoice(choiceText, snippetID)
                }
                index = index + 1
            }
        }
        val liveChoices = model.choicesLiveData
        liveChoices.observe(this, choicesObserver)

        val adventureTextObserver = Observer<String> { adventureText ->
            textView.text = adventureText
        }
        val liveAdventureText = model.adventureTextLiveData
        liveAdventureText.observe(this, adventureTextObserver)

        val speciesObserver = Observer<Species> { species ->
            adoptionSpecies = species
        }
        val liveSpecies = model.adoptionSpeciesLiveData
        liveSpecies.observe(this, speciesObserver)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ ->
            startActivity(Intent(this, AdoptedAnimalsActivity::class.java))
        }
    }

    fun makeChoice(choiceText: String, snippetID: String) {
        if (model.isAdoptionID(snippetID)) {
            // TODO probably the dialog doesn't actually need to
            // know the snippetID and those are
            // things that should be held onto here
            showAdoptionDialog(snippetID)
            return
        }
        model.makeChoice(choiceText.toUpperCase(), snippetID)
    }

    fun showAdoptionDialog(snippetID: String) {
        val adoptionFragment = AdoptionDialogFragment(adoptionSpecies, snippetID)
        adoptionFragment.show(supportFragmentManager, "adopt")
    }

    override fun onDialogAdoptionClick(dialog: AdoptionDialogFragment) {
        // It feels kinda bad that we have to get these values
        // from the dialog when they originated in the activity
        model.makeChoice(dialog.getChoiceText(), dialog.getNextSnippetID())
    }
}
