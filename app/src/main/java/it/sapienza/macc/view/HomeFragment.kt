package it.sapienza.macc.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import it.sapienza.macc.R
import it.sapienza.macc.control.HomeControl
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (loginBtn === null) {
            Log.e("error", "BUTTON NOT DEFINED!")
        }

        initActions()
    }

    fun initActions() {
        val hc = HomeControl(requireActivity())
        if (loginBtn === null) {
            Log.e("error", "BUTTON NOT DEFINED!")
        }
        loginBtn?.setOnClickListener(hc)
    }

}