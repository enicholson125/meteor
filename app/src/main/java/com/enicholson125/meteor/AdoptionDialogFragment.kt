package com.enicholson125.meteor

import android.app.AlertDialog
import android.app.Dialog
import androidx.fragment.app.DialogFragment
import android.os.Bundle
import android.content.DialogInterface
import android.widget.ImageView

class AdoptionDialogFragment(
    private val speciesName: String,
    private val speciesImageResource: Int,
): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.dialog_adoption, null)
            view.findViewById<ImageView>(R.id.adoption_image)?.setImageResource(speciesImageResource)

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                    .setTitle(getString(R.string.adopt_title, speciesName))
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
