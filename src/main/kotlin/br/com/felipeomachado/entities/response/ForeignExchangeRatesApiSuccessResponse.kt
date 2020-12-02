package br.com.felipeomachado.entities.response

import java.time.LocalDate

data class ForeignExchangeRatesApiSuccessResponse (
    val base : String,
    val date: String,
    val rates : HashMap<String, Double>
    )