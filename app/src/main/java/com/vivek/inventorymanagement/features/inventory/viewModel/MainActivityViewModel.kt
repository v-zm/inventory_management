package com.vivek.inventorymanagement.features.inventory.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel(){

    val currentGreetingText:MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val buttonText:MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun updateGreetingText(text:String){
        currentGreetingText.value=text
    }
    fun updateButtonText(text:String){
        buttonText.value=text
    }

    fun doNetworkCall(){

    }
}