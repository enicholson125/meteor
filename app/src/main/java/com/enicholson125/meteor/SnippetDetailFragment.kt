package com.enicholson125.meteor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.widget.TextView
import androidx.lifecycle.Observer
import com.enicholson125.meteor.utilities.InjectorUtils
import com.enicholson125.meteor.viewmodels.SnippetDetailViewModel
import com.enicholson125.meteor.data.TextSnippet

class SnippetDetailFragment : Fragment() {
    private val model: SnippetDetailViewModel by viewModels {
        InjectorUtils.provideSnippetDetailViewModelFactory(requireActivity(), "D1")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_scrolling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val textView = view.findViewById<TextView>(R.id.text_view)

        // Create the observer which updates the UI.
        val snippetObserver = Observer<TextSnippet> { snippet ->
            // Update the UI, in this case, a TextView.
            if (snippet.choices != null && snippet.choices.size > 0) {
                textView.text = snippet.choices.get(0)
            }
        }

        var liveDataText = model.textSnippet
        liveDataText.observe(this, snippetObserver)

        model.updateSnippetID("D2")

        var otherText = model.textSnippet
        otherText.observe(this, snippetObserver)

    }

}
