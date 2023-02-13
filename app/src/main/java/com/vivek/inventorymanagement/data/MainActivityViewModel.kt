package com.vivek.inventorymanagement.data

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
}


/// which of the following is not related to variable declaration
/// A. dynamic B. var C. super d. final