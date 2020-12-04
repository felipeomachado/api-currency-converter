package br.com.felipeomachado.entities.response

import br.com.felipeomachado.entities.ConverterTransaction
import java.time.LocalDateTime

class ConverterTransactionResponse(
    val transactionId: Long,
    val userId: Long,
    val sourceCurrency : String,
    val sourceValue: Double,
    val targetCurrency: String,
    val targetValue: Double,
    val conversionRate: Double,
    val dateTime: String
)

/*
class ConverterTransactionResponse(
    transactionId: Long, userId: Long,
    sourceCurrency : String ) {
    var transactionId: Long = transactionId
    var userId: Long = 0
    lateinit var sourceCurrency: String
    var sourceValue: Double = 0.0
    lateinit var targetCurrency: String
    var targetValue: Double = 0.0
    var conversionRate: Double = 0.0
    lateinit var dateTime: LocalDateTime
}*/
