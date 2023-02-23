package com.vivek.inventorymanagement.data.util

import org.junit.Assert.assertEquals
import org.junit.Test

class DateTimeUtilityTest {
    companion object {
        const val ONE_HOUR_IN_MILLIS: Long = 3600000
        const val TWELVE_HOUR_IN_MILLIS: Long = 43200000
        const val TWO_DAYS_IN_MILLIS: Long = 172800000
        const val TWENTY_FIVE_HOURS_IN_MILLIS: Long = 90000000
    }

    @Test
    fun getCurrentTimeInMilliseconds_execute_getCurrentDateAndTime() {
        val a = DateTimeUtility.getCurrentTimeInMilliseconds()
        val b = System.currentTimeMillis()
        assertEquals(a, b)
    }

    @Test
    fun isOneDayPassed_Pass_1Hour_diff_milis_get_False() {
        assertEquals(
            false,
            DateTimeUtility.isOneDayPassed(System.currentTimeMillis() - ONE_HOUR_IN_MILLIS)
        )
    }

    @Test
    fun isOneDayPassed_Pass_12Hours_diff_milis_get_False() {
        assertEquals(
            false,
            DateTimeUtility.isOneDayPassed(System.currentTimeMillis() - TWELVE_HOUR_IN_MILLIS)
        )
    }

    @Test
    fun isOneDayPassed_Pass_2Day_diff_milis_get_True() {
        assertEquals(
            true,
            DateTimeUtility.isOneDayPassed(System.currentTimeMillis() - TWO_DAYS_IN_MILLIS)
        )
    }

    @Test
    fun isOneDayPassed_Pass_25Hours_diff_milis_get_True() {
        assertEquals(
            true,
            DateTimeUtility.isOneDayPassed(System.currentTimeMillis() - TWENTY_FIVE_HOURS_IN_MILLIS)
        )
    }

}