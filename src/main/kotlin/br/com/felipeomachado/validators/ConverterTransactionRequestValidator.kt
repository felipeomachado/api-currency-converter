package br.com.felipeomachado.validators

import br.com.felipeomachado.entities.request.ConverterTransactionRequest
import javax.xml.bind.ValidationException

class ConverterTransactionRequestValidator {
    companion object {
        fun validate(converterTransactionRequest: ConverterTransactionRequest) {
            if(converterTransactionRequest.userId <= 0) {
                throw ValidationException("Invalid User Id")
            }

            if(converterTransactionRequest.sourceCurrency.trim().isEmpty()) {
                throw ValidationException("Invalid Source Currency")
            }

            if(converterTransactionRequest.targetCurrency.trim().isEmpty()) {
                throw ValidationException("Invalid Target Currency")
            }
        }
    }
}