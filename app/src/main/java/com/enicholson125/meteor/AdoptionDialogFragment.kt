package com.enicholson125.meteor

import android.app.AlertDialog
import android.app.Dialog
import androidx.fragment.app.DialogFragment
import android.os.Bundle
import android.content.DialogInterface

class AdoptionDialogFragment(
    private val animalTypeName: String,
): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_adoption, null))
                    .setTitle(getString(R.string.adopt_title, animalTypeName))
                    // Add action buttons
                    .setPositiveButton(R.string.adopt,
                            DialogInterface.OnClickListener { dialog, id ->
                                // sign in the user ...
                            })
                    .setNegativeButton(R.string.cancel,
                            DialogInterface.OnClickListener { dialog, id ->
                                getDialog()?.cancel()
                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
