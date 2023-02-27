package com.vivek.inventorymanagement.data.util

object DateTimeUtility {

    private const val ONE_DAY_IN_MILLISECONDS: Long = 86400000
    fun getCurrentTimeInMilliseconds(): Long {
        return System.currentTimeMillis()
    }

    /**
     * [hasOneDayPassed] checks if date is older by 24hours
     * */
    fun hasOneDayPassed(timeInMilliseconds: Long): Boolean {
        return (System.currentTimeMillis() - timeInMilliseconds) > ONE_DAY_IN_MILLISECONDS
    }
}