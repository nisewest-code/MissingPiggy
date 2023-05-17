package com.mis.singp.iggy.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.mis.singp.iggy.R

class DialogMissingPiggyInfo(private val startCallback: ()->Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            val view = inflater.inflate(R.layout.dialog_missing_piggy_info, null)

            view.findViewById<AppCompatButton>(R.id.play_btn_MISSING).setOnClickListener {
                dialog?.cancel()
                startCallback()
            }
            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}