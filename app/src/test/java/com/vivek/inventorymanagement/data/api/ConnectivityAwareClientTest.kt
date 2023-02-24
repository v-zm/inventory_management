package com.vivek.inventorymanagement.data.api

import androidx.test.core.app.ApplicationProvider
import org.junit.Before

class ConnectivityAwareClientTest {
    lateinit var connectivityAwareClient: ConnectivityAwareClient

    @Before
    fun setup() {
        connectivityAwareClient =
            ConnectivityAwareClient(ApplicationProvider.getApplicationContext())
    }


}