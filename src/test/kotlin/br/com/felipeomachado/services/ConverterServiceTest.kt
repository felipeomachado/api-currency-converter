package br.com.felipeomachado.services


import br.com.felipeomachado.entities.ConverterTransaction
import br.com.felipeomachado.entities.request.ConverterTransactionRequest
import br.com.felipeomachado.entities.response.ConverterTransactionResponse
import br.com.felipeomachado.repositories.ConverterTransactionRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


class ConverterServiceTest {

    private val converterTransactionRepository = ConverterTransactionRepository()
    private val converterTransactionService = ConverterTransactionService(converterTransactionRepository)

    @Test
    fun `should convert currency`() {
        val userId = 1L
        val sourceCurrency = "USD"
        val sourceValue = 1.00
        val targetCurrency = "BRL"

        val result: Result<ConverterTransactionResponse> =  converterTransactionService.convert(ConverterTransactionRequest(userId, sourceCurrency, sourceValue, targetCurrency))

        assertTrue(result.isSuccess)
        assertNotNull(result.getOrNull()?.transactionId)
        assertEquals("Target Value was not correct", sourceValue * result.getOrNull()?.conversionRate!!,
            result.getOrNull()?.targetValue!!, 0.0)
    }

    @Test
    fun `should return error when trying to convert an source currency that does not supported`() {
        val userId = 1L
        val sourceCurrency = "ASD"
        val sourceValue = 1.00
        val targetCurrency = "BRL"

        val result: Result<ConverterTransactionResponse> =  converterTransactionService.convert(ConverterTransactionRequest(userId, sourceCurrency, sourceValue, targetCurrency))

        assertTrue(result.isFailure)
    }

    @Test
    fun `should return error when trying to convert an target currency that does not supported`() {
        val userId = 1L
        val sourceCurrency = "USD"
        val sourceValue = 1.00
        val targetCurrency = "ASD"

        val result: Result<ConverterTransactionResponse> =  converterTransactionService.convert(ConverterTransactionRequest(userId, sourceCurrency, sourceValue, targetCurrency))

        assertTrue(result.isFailure)
    }

    @Test
    fun `should get all transaction by user`() {
        val userId = 5L

        val transactionList: List<ConverterTransaction> =  converterTransactionService.getAllTransactionsByUser(userId)

        assertTrue(transactionList.isNotEmpty())
    }

}