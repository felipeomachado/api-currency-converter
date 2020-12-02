package br.com.felipeomachado.entities.response

import br.com.felipeomachado.entities.ConverterTransaction
import java.time.LocalDateTime

class ConverterTransactionResponse(converterTransaction: ConverterTransaction) {
    val transactionId: Long? = converterTransaction.id
    val userId: Long = converterTransaction.userId
    val sourceCurrency: String = converterTransaction.sourceCurrency
    val sourceValue: Double = converterTransaction.sourceValue
    val targetCurrency: String = converterTransaction.targetCurrency
    val targetValue: Double = converterTransaction.targetValue
    val conversionRate: Double = converterTransaction.conversionRate
    val dateTime: LocalDateTime = converterTransaction.dateTime
}