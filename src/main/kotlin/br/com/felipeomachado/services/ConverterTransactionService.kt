package br.com.felipeomachado.services

import br.com.felipeomachado.api.ForeignExchangeRatesApi
import br.com.felipeomachado.entities.request.ConverterTransactionRequest
import br.com.felipeomachado.entities.response.ConverterTransactionResponse
import br.com.felipeomachado.repositories.ConverterTransactionRepository
import java.time.format.DateTimeFormatter

class ConverterTransactionService(private val converterTransactionRepository: ConverterTransactionRepository) {

    private val foreignExchangeRatesApi = ForeignExchangeRatesApi()

    fun convert(converterTransactionRequest: ConverterTransactionRequest): Result<ConverterTransactionResponse> {
        val ratesResult = foreignExchangeRatesApi.getRates(converterTransactionRequest.sourceCurrency)

        ratesResult.onSuccess { value ->
            if (value != null) {
                val conversionRate = value.rates[converterTransactionRequest.targetCurrency]
                        ?: return Result.failure(Exception("Target currency '${converterTransactionRequest.targetCurrency}' was not supported"))

                val targetValue = conversionRate!! * converterTransactionRequest.sourceValue

                converterTransactionRequest.conversionRate = conversionRate;
                converterTransactionRequest.targetValue = targetValue

                val id: Long = converterTransactionRepository.save(converterTransactionRequest)

                return Result.success(
                    ConverterTransactionResponse(
                    id,
                    converterTransactionRequest.userId,
                    converterTransactionRequest.sourceCurrency,
                    converterTransactionRequest.sourceValue,
                    converterTransactionRequest.targetCurrency,
                    converterTransactionRequest.targetValue,
                    converterTransactionRequest.conversionRate,
                    converterTransactionRequest.dateTime.format(DateTimeFormatter.ISO_DATE_TIME)
                ))
            }
        }

        return Result.failure(Exception("Source currency '${converterTransactionRequest.sourceCurrency}' was not supported"))
    }

    fun getAllTransactionsByUser(userId: Long): List<ConverterTransactionResponse> {
        return converterTransactionRepository.findAllByUser((userId))
    }

}
