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
    fun `getCurrentTimeInMilliseconds()  execute and get currentDateAndTime in millis`() {
        val a = DateTimeUtility.getCurrentTimeInMilliseconds()
        val b = System.currentTimeMillis()
        assertEquals(a, b)
    }

    @Test
    fun `hasOneDayPassed() pass 1Hour diff milis and get False`() {
        assertEquals(
            false,
            DateTimeUtility.hasOneDayPassed(System.currentTimeMillis() - ONE_HOUR_IN_MILLIS)
        )
    }

    @Test
    fun `hasOneDayPassed() pass 12Hours diff milis and get False`() {
        assertEquals(
            false,
            DateTimeUtility.hasOneDayPassed(System.currentTimeMillis() - TWELVE_HOUR_IN_MILLIS)
        )
    }

    @Test
    fun `hasOneDayPassed() pass 2Day diff milis and get True`() {
        assertEquals(
            true,
            DateTimeUtility.hasOneDayPassed(System.currentTimeMillis() - TWO_DAYS_IN_MILLIS)
        )
    }

    @Test
    fun `hasOneDayPassed() pass 25Hours diff milis and get True`() {
        assertEquals(
            true,
            DateTimeUtility.hasOneDayPassed(System.currentTimeMillis() - TWENTY_FIVE_HOURS_IN_MILLIS)
        )
    }

}