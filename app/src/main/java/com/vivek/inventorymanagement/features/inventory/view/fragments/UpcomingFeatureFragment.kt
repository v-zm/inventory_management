package com.vivek.inventorymanagement.features.inventory.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vivek.inventorymanagement.R

/**
 * A simple [Fragment] subclass.
 * Use the [UpcomingFeatureFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpcomingFeatureFragment : Fragment(R.layout.fragment_upcoming_feature) {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_feature, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = UpcomingFeatureFragment()
    }
}