package com.vivek.inventorymanagement.features.inventory.view.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vivek.inventorymanagement.features.inventory.view.fragments.InventoryProductGridViewFragment
import com.vivek.inventorymanagement.features.inventory.view.fragments.InventoryProductListViewFragment
import com.vivek.inventorymanagement.features.inventory.view.fragments.UpcomingFeatureFragment

class ViewPagerStateAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InventoryProductListViewFragment.newInstance()
            1 -> InventoryProductGridViewFragment.newInstance()
            else -> UpcomingFeatureFragment.newInstance()
        }
    }

}