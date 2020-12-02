package br.com.felipeomachado.services

import br.com.felipeomachado.entities.ConverterTransaction
import br.com.felipeomachado.entities.request.ConverterTransactionRequest
import br.com.felipeomachado.entities.response.ConverterTransactionResponse
import br.com.felipeomachado.repositories.ConverterTransactionRepository

class ConverterTransactionService(converterTransactionRepository: ConverterTransactionRepository) {

    fun convert(converterTransactionRequest: ConverterTransactionRequest): Result<ConverterTransactionResponse> {
        TODO("to implement")
    }

    fun getAllTransactionsByUser(userId: Long): List<ConverterTransaction> {
        TODO("to implement")
    }

}
