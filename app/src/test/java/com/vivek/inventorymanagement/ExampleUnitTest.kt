package com.vivek.inventorymanagement

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun addition_isFailed(){
        assertNotEquals(4,1+2)
    }
    @Test
    fun subtract_NotPassed(){
        assertEquals(1,10-8)
    }
}