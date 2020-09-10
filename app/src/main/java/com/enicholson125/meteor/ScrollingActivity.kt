package com.enicholson125.meteor

import android.util.Log
import android.os.Bundle
import android.content.Context
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Button
import com.enicholson125.meteor.utilities.InjectorUtils
import com.enicholson125.meteor.viewmodels.AdventureTextViewModel
import com.enicholson125.meteor.data.TextSnippet

class ScrollingActivity : AppCompatActivity() {

    private val model: AdventureTextViewModel by viewModels {
        InjectorUtils.provideAdventureTextViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))

        val textView = findViewById<TextView>(R.id.text_view)
        val buttonList = mutableListOf<Button>()
        buttonList.add(findViewById<Button>(R.id.button_choice_0))
        buttonList.add(findViewById<Button>(R.id.button_choice_1))

        val choicesObserver = Observer<Map<String, String>> { choicesMap ->
            textView.text = model.adventureTextLiveData.value
            if (choicesMap.size == 1) {
                buttonList.get(1).setVisibility(View.INVISIBLE)
            }
            var index = 0
            for ((choiceText, snippetID) in choicesMap) {
                buttonList.get(index).setVisibility(View.VISIBLE)
                buttonList.get(index).text = choiceText
                buttonList.get(index).setOnClickListener { _ ->
                    model.makeChoice(choiceText, snippetID)
                }
                index = index + 1
            }
        }

        val liveDescription = model.choicesLiveData
        liveDescription.observe(this, choicesObserver)

        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "I do nothing", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
