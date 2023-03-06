package com.vivek.inventorymanagement.data.api.clients

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.vivek.inventorymanagement.data.api.ConnectivityAwareClient
import com.vivek.inventorymanagement.data.api.dtos.ItemsDto
import com.vivek.inventorymanagement.data.api.services.InventoryApiService
import com.vivek.inventorymanagement.data.util.MockResponseFileReader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import java.net.HttpURLConnection

@RunWith(RobolectricTestRunner::class)
class InventoryHttpClientTest {

    @get:Rule
    val taskInstantExecutor: TestRule = InstantTaskExecutorRule()

    private lateinit var apiService: InventoryApiService
    private lateinit var mockWebserver: MockWebServer
    private val connectivityAwareClient: ConnectivityAwareClient =
        ConnectivityAwareClient(ApplicationProvider.getApplicationContext())
    private val client: IHttpClient = InventoryHttpClient(connectivityAwareClient)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockWebserver = MockWebServer()
        mockWebserver.start()
        apiService = client.getHttpClient().create(InventoryApiService::class.java)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchApiCall() = runTest {
//        val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(
//                "{\n" + "\t\"status\": \"success\",\n" + "\t\"error\": null,\n" + "\t\"data\": {\n" + "\t\t\"items\": [{\n" + "\t\t\t\"name\": \"Item 1\",\n" + "\t\t\t\"price\": \"₹ 100\",\n" + "                        \"image\": \"https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png\",\n" + "\t\t\t\"extra\": \"Same day shipping\"\n" + "\t\t}, {\n" + "\t\t\t\"name\": \"Item 2\",\n" + "            \t\t\"price\": \"₹ 400\",\n" + "            \t\t\"image\": \"https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png\",\n" + "\t\t\t\"extra\": \"Same day shipping\"\n" + "\t\t}, {\n" + "\t\t\t\"name\": \"Item 3\",\n" + "\t\t\t\"price\": \"₹ 100\",\n" + "            \t\t\"image\": \"https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png\",\n" + "\t\t\t\"extra\": \"Same day shipping\"\n" + "\t\t}, {\n" + "\t\t\t\"name\": \"Item 4\",\n" + "\t\t\t\"price\": \"₹ 80\",\n" + "            \t\t\"image\": \"https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png\",\n" + "\t\t\t\"extra\": null\n" + "\t\t}, {\n" + "\t\t\t\"name\": \"Item 5\",\n" + "\t\t\t\"price\": \"₹ 190\",\n" + "            \t\t\"image\": \"https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png\",\n" + "\t\t\t\"extra\": null\n" + "\t\t}, {\n" + "\t\t\t\"name\": \"Item 6\",\n" + "\t\t\t\"price\": \"₹ 70\",\n" + "            \t\t\"image\": \"https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png\",\n" + "\t\t\t\"extra\": null\n" + "\t\t}, {\n" + "\t\t\t\"name\": \"Item 7\",\n" + "\t\t\t\"price\": \"₹ 190\",\n" + "            \t\t\"image\": \"https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png\",\n" + "\t\t\t\"extra\": null\n" + "\t\t}, {\n" + "\t\t\t\"name\": \"Item 8\",\n" + "\t\t\t\"price\": \"₹ 190\",\n" + "            \t\t\"image\": \"https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png\",\n" + "\t\t\t\"extra\": null\n" + "\t\t}, {\n" + "\t\t\t\"name\": \"Item 9\",\n" + "\t\t\t\"price\": \"₹ 190\",\n" + "            \t\t\"image\": \"https://imgstatic.phonepe.com/images/dark/app-icons-ia-1/transfers/80/80/ic_check_balance.png\",\n" + "\t\t\t\"extra\": null\n" + "\t\t}]\n" + "\t}\n" + "}"
//            )

        val response = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("items.json").content)
        mockWebserver.enqueue(response)

        val mockResponse = response.getBody()?.readUtf8()

        val actualResponse = apiService.getInventoryList()
        assert(actualResponse.body().toString().isNotEmpty())
        assertEquals(
            mockResponse?.let {
                val a = parseMockedJson(it)
                val mapper: ObjectMapper = ObjectMapper()
                val item: ItemsDto = mapper.readValue(a, ItemsDto::class.java)
                item
            }, actualResponse.body()?.data
        )
    }

    private fun parseMockedJson(mockResponse: String): String {
        val reader = JSONObject(mockResponse)
        return reader.getString("data")
    }
}