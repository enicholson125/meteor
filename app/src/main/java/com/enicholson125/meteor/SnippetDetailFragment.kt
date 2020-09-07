package com.enicholson125.meteor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.navArgs
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.enicholson125.meteor.utilities.InjectorUtils
import com.enicholson125.meteor.viewmodels.SnippetDetailViewModel
import com.enicholson125.meteor.viewmodels.NextSnippetViewModel
import com.enicholson125.meteor.data.TextSnippet

class SnippetDetailFragment : Fragment() {
    // private val args: SnippetDetailFragmentArgs by navArgs()

    private lateinit var model: SnippetDetailViewModel

    // private val model: SnippetDetailViewModel by viewModels {
    //     InjectorUtils.provideSnippetDetailViewModelFactory(requireActivity(), args.snippetID)
    // }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.content_scrolling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedModel = ViewModelProvider(requireActivity()).get(NextSnippetViewModel::class.java)
        var id = "unset"
        if (sharedModel.nextSnippetID.getValue() != null) {
            id = sharedModel.nextSnippetID.getValue().toString()
        } else {
            // TODO handle snippetID being null better as if the unset
            // value makes it into the app it'll crash it
            val idObserver = Observer<String> { snippetID ->
                id = snippetID
            }
            var liveDataSnippetID = sharedModel.nextSnippetID
            liveDataSnippetID.observe(this, idObserver)
        }

        model = InjectorUtils
                .provideSnippetDetailViewModelFactory(requireActivity(), id)
                .create(SnippetDetailViewModel::class.java)
        val textView = view.findViewById<TextView>(R.id.text_view)
        textView.text = id

        // Create the observer which updates the UI.
        val snippetObserver = Observer<TextSnippet> { snippet ->
            // Update the UI, in this case, a TextView.
            if (snippet.nextSnippet != null && snippet.nextSnippet.size > 0) {
                sharedModel.updateSnippetID(snippet.nextSnippet.get(0))
            }
            textView.text = snippet.description
        }

        var liveDataText = model.textSnippet
        liveDataText.observe(this, snippetObserver)


        var otherText = model.textSnippet
        otherText.observe(this, snippetObserver)

    }

}
