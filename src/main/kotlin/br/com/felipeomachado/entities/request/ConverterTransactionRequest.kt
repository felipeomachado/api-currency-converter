package br.com.felipeomachado.entities.request

import br.com.felipeomachado.entities.ConverterTransaction

class ConverterTransactionRequest (
        private val userId: Long,
        val sourceCurrency: String,
        val sourceValue: Double,
        val targetCurrency: String
    ) {



    fun toModel(targetValue: Double, conversionRate: Double): ConverterTransaction {
        return ConverterTransaction(id = null, userId = userId, sourceCurrency =  sourceCurrency, sourceValue =  sourceValue, targetCurrency =  targetCurrency, targetValue, conversionRate )
    }
}

