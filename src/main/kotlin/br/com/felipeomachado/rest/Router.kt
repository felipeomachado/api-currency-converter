package br.com.felipeomachado.rest


import br.com.felipeomachado.entities.request.ConverterTransactionRequest
import br.com.felipeomachado.repositories.ConverterTransactionRepository
import br.com.felipeomachado.services.ConverterTransactionService
import io.javalin.Javalin
import io.javalin.http.Context
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class Router(private val port: Int) {
    private val logger: Logger = LoggerFactory.getLogger(Router::class.java)

    val app = Javalin.create { cfg -> cfg.requestLogger(::logRequest).enableCorsForAllOrigins() }
        .post("/api/v1/converter", ::convertCurrency)

        .get("/api/v1/transaction/:user-id", ::listTransactionsByUser)

    private fun logRequest(ctx: Context, executionTimeMs: Float) =
        logger.info("${ctx.method()} ${ctx.fullUrl()} status=${ctx.status()} durationMs=$executionTimeMs")

    fun start(): Router {
        app.start(port)
        return this
    }

    private fun convertCurrency(ctx: Context) {
        val converterTransactionService =
            ConverterTransactionService(ConverterTransactionRepository())
        val result = converterTransactionService.convert(ctx.body<ConverterTransactionRequest>())

        result.onSuccess {
            result.getOrNull()?.let { it1 -> ctx.json(it1) }
        }
        result.onFailure {
            result.exceptionOrNull()?.message?.let { it1 -> ctx.json(it1) }
        }
    }

    private fun listTransactionsByUser(ctx: Context) {
        val converterTransactionService = ConverterTransactionService(ConverterTransactionRepository())
        ctx.json(converterTransactionService.getAllTransactionsByUser(ctx.pathParam("user-id").toLong()))
    }
}