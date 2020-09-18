package com.enicholson125.meteor

import android.app.AlertDialog
import android.app.Dialog
import androidx.fragment.app.DialogFragment
import android.os.Bundle
import android.content.DialogInterface
import android.widget.ImageView
import android.widget.EditText
import android.content.Context

class AdoptionDialogFragment(
    private val speciesName: String,
    private val speciesImageResource: Int,
    private val nextSnippetID: String,
): DialogFragment() {
    // Use this instance of the interface to deliver action events
    internal lateinit var listener: AdoptionDialogListener
    private var congratulationsText: String = ""

    // These are used by the calling activity to set the next steps
    // after adoption is successfully called
    fun getChoiceText(): String {
        return congratulationsText
    }

    fun getNextSnippetID(): String {
        return nextSnippetID
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface AdoptionDialogListener {
        fun onDialogAdoptionClick(dialog: AdoptionDialogFragment)
    }

    // Override the Fragment.onAttach() method to instantiate the AdoptionDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the AdoptionDialogListener so we can send events to the host
            listener = context as AdoptionDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement AdoptionDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.dialog_adoption, null)
            view.findViewById<ImageView>(R.id.adoption_image)?.setImageResource(speciesImageResource)
            val nameEntry = view.findViewById<EditText>(R.id.adoption_name)
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                    .setTitle(getString(R.string.adopt_title, speciesName))
                    // Add action buttons
                    .setPositiveButton(R.string.adopt,
                            DialogInterface.OnClickListener { dialog, id ->
                                var name: String = nameEntry.getText().toString()
                                if (name == "") {
                                    name = getString(R.string.adopt_hint)
                                }
                                congratulationsText = getString(R.string.adopt_congrats, name, speciesName)
                                // Send the positive button event back to the host activity
                                listener.onDialogAdoptionClick(this)
                            })
                    .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { dialog, id ->
                                getDialog()?.cancel()
                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
