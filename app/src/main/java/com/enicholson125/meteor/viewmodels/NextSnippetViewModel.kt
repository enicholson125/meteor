package com.enicholson125.meteor.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

/**
 * The ViewModel which shares information between SnippetDetailFragments
 * and their calling Activity.
 */
class NextSnippetViewModel(
    val nextSnippetID: MutableLiveData<String> = MutableLiveData<String>("T1")
) : ViewModel() {

    fun updateSnippetID(id: String) {
        nextSnippetID.setValue(id)
    }
}
