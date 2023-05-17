package com.mis.singp.iggy.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.mis.singp.iggy.R

class DialogMissingPiggyGameOver(private val score: Int, private val restartCallback: ()->Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            val view = inflater.inflate(R.layout.dialog_missing_piggy_game_over, null)

            val str = activity?.getString(R.string.game_over_desccription_missing, score)
            view.findViewById<TextView>(R.id.score_label_missing_piggy).text = str
            view.findViewById<AppCompatButton>(R.id.exit_btn_missing).setOnClickListener {
                activity?.finish()
            }

            view.findViewById<AppCompatButton>(R.id.restart_btn_missing).setOnClickListener {
                dialog?.cancel()
                restartCallback()
            }
            builder.setView(view)
            builder.setCancelable(false)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}