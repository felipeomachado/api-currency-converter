package br.com.felipeomachado.services

import br.com.felipeomachado.db.DatabaseInitializer
import br.com.felipeomachado.entities.request.ConverterTransactionRequest
import br.com.felipeomachado.entities.response.ConverterTransactionResponse
import br.com.felipeomachado.repositories.converterTransactionRepositoryModule
import org.jetbrains.exposed.sql.Database
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.KoinTestRule
import kotlin.test.assertTrue


class ConverterServiceTest : KoinTest {


    private val converterTransactionService by inject<ConverterTransactionService>()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger()
        modules(converterTransactionRepositoryModule, converterTransactionServiceModule)
    }

    @Test
    fun `should convert currency`() {
        Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
        DatabaseInitializer.createSchemaAndTestData()

        val userId = 1L
        val sourceCurrency = "USD"
        val sourceValue = 1.00
        val targetCurrency = "BRL"

        val result: Result<ConverterTransactionResponse> =  converterTransactionService.convert(ConverterTransactionRequest(userId, sourceCurrency, sourceValue, targetCurrency))



        assertTrue(result.isSuccess)
        assertEquals("Target Value was not correct", sourceValue * result.getOrNull()?.conversionRate!!,
            result.getOrNull()?.targetValue!!, 0.0)
    }

    @Test
    fun `should return error when trying to convert an source currency that does not supported`() {
        Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
        DatabaseInitializer.createSchemaAndTestData()

        val userId = 1L
        val sourceCurrency = "ASD"
        val sourceValue = 1.00
        val targetCurrency = "BRL"



        val result: Result<ConverterTransactionResponse> =  converterTransactionService.convert(ConverterTransactionRequest(userId, sourceCurrency, sourceValue, targetCurrency))

        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()?.message, "Source currency '${sourceCurrency}' was not supported")
    }

    @Test
    fun `should return error when trying to convert an target currency that does not supported`() {
        val userId = 1L
        val sourceCurrency = "USD"
        val sourceValue = 1.00
        val targetCurrency = "ASD"

        val result: Result<ConverterTransactionResponse> =  converterTransactionService.convert(ConverterTransactionRequest(userId, sourceCurrency, sourceValue, targetCurrency))

        assertTrue(result.isFailure)
        assertEquals(result.exceptionOrNull()?.message, "Target currency '${targetCurrency}' was not supported")
    }

    @Test
    fun `should get all transaction by user`() {
        Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
        DatabaseInitializer.createSchemaAndTestData()

        val userId = 5L

        val transactionList: List<ConverterTransactionResponse> =  converterTransactionService.getAllTransactionsByUser(userId)

        assertTrue(transactionList.isNotEmpty())
    }

}