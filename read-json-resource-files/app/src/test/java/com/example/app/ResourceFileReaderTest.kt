package com.example.app

import com.example.app.models.Data
import com.example.app.models.OrderItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test


class ResourceFileReaderTest {
    private val reader = ResourceFileReader

    @Test
    fun readFile_canReadJsonFile() {
        val jsonString = reader.readFile("/objects/data.json")
        val expectedResult = """
            {
              "message": "hello world"
            }
            """.trimIndent()
        assertEquals(expectedResult, jsonString)
    }

    @Test
    fun fromFile_canReadDataObject() {
        val data = reader.fromFile<Data>("/objects/data.json")
        val expectedResult = Data(message = "hello world")
        assertEquals(expectedResult, data)
    }

    @Test
    fun fromFile_returnNullIfInvalidPath() {
        val data = reader.fromFile<Data>("/data-test.json")
        assertNull(data)
    }

    @Test
    fun get_canGetDataObject() {
        val data = reader.get<Data>()
        val expectedResult = Data(message = "hello world")
        assertEquals(expectedResult, data)
    }

    @Test
    fun get_canGetOrderItemObject() {
        val data = reader.get<OrderItem>()
        val expectedResult = OrderItem(id = 1, productId = 1, quantity = 2)
        assertEquals(expectedResult, data)
    }
}
