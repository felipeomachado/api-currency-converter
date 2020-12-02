package br.com.felipeomachado.api

import org.junit.Test
import kotlin.test.assertTrue

class ForeignExchangeRatesApiTest {
    private val foreignExchangeRatesApi = ForeignExchangeRatesApi()

    @Test
    fun `should get success on call the external api to request the rates when is a valid currency`() {
        val result = foreignExchangeRatesApi.getRates("USD")
        assertTrue(result.isSuccess)
    }

    @Test
    fun `should get failure on call the external api to request the rates when is a invalid currency`() {
        val result = foreignExchangeRatesApi.getRates("ASD")
        assertTrue(result.isFailure)
    }
}