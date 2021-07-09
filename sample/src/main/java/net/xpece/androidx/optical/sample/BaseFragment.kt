package net.xpece.androidx.optical.sample

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter<String>(
            view.context,
            R.layout.item_button_disabled,
            listOf("Optical Layout")
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        view.findViewById<Spinner>(R.id.spinnerButton).adapter = adapter
        view.findViewById<Spinner>(R.id.spinnerButtonClip).adapter = adapter
    }
}
