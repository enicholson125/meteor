package com.enicholson125.meteor

import android.util.Log
import android.os.Bundle
import android.content.Context
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
import android.widget.ImageView
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.enicholson125.meteor.utilities.InjectorUtils
import com.enicholson125.meteor.viewmodels.AdventureTextViewModel
import com.enicholson125.meteor.viewmodels.AdoptedAnimalsViewModel
import com.enicholson125.meteor.data.TextSnippet
import com.enicholson125.meteor.data.Species
import com.enicholson125.meteor.data.AdoptedAnimal
import com.enicholson125.meteor.AdoptionDialogFragment

class AdoptedAnimalsActivity : AppCompatActivity() {
    private val model: AdoptedAnimalsViewModel by viewModels {
        InjectorUtils.provideAdoptedAnimalsViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_adopted_animals)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        val imageView = findViewById<ImageView>(R.id.animal_image)
        val animalNameTextView = findViewById<TextView>(R.id.animal_name)

        val adoptedAnimalObserver = Observer<AdoptedAnimal> { adoptedAnimal ->
            animalNameTextView.text = adoptedAnimal.animalName
        }
        model.currentAnimal.observe(this, adoptedAnimalObserver)

        val currentAnimalSpeciesObserver = Observer<Species> { species ->
            val imageID = getResources().getIdentifier(species.image, "drawable", getPackageName())
            imageView.setImageResource(imageID)
        }
        model.currentAnimalSpecies.observe(this, currentAnimalSpeciesObserver)

        val backButton = findViewById<ImageView>(R.id.back_arrow).setOnClickListener { _ ->
            model.decrementCurrentAnimalIndex()
        }
    }
}
