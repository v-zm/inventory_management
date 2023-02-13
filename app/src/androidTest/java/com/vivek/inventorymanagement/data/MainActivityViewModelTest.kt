package com.vivek.inventorymanagement.data

import android.os.Handler
import android.os.Looper
import org.junit.Test

internal class MainActivityViewModelTest {

    private val model: MainActivityViewModel = MainActivityViewModel()
    private var handler: Handler

    init {
        if (Looper.getMainLooper() == null)
            Looper.prepare()
        handler = Handler(Looper.getMainLooper())
    }

    @Test
    fun updateGreetingText() {
        handler.post {
            model.updateGreetingText("Hello Vivek!")
            assert(model.currentGreetingText.value == "Hello Vivek!")
        }
    }

    @Test
    fun updateButtonText() {
        handler.post {
            model.updateButtonText("Hello Vivek!")
            assert(model.buttonText.value == "Hello Vivek!")
        }
    }
}
