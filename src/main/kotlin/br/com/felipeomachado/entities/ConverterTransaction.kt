package br.com.felipeomachado.entities

import java.time.LocalDateTime

data class ConverterTransaction (
        val id: Long?,
        val userId: Long,
        val sourceCurrency: String,
        val sourceValue: Double,
        val targetCurrency: String,
        val targetValue: Double,
        val conversionRate: Double,
        val dateTime: LocalDateTime = LocalDateTime.now(),
)

