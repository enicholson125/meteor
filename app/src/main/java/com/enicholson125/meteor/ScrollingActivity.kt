package com.enicholson125.meteor

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
import android.widget.TextView
import com.enicholson125.meteor.data.AppDatabase
import com.enicholson125.meteor.data.TextSnippet
import com.enicholson125.meteor.data.SnippetType
import com.enicholson125.meteor.viewmodels.SnippetDetailViewModel

class ScrollingActivity : AppCompatActivity() {
    private val model: SnippetDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))

        val textView = findViewById<TextView>(R.id.text_view)

        // Create the observer which updates the UI.
        val snippetObserver = Observer<TextSnippet> { newSnippet ->
            // Update the UI, in this case, a TextView.
            if (newSnippet.choices != null && newSnippet.choices.size > 0) {
                textView.text = newSnippet.choices.get(0)
            }
        }

        // TODO this should move to the repository/ViewModel
        val database = AppDatabase.getInstance(getApplicationContext())
        var dao = database.textSnippetDAO()
        var liveDataText = dao.getTextSnippetByID("D1")
        liveDataText.observe(this, snippetObserver)

        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "no longer a thing", Snackbar.LENGTH_LONG)
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
