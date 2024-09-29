package com.example.plugins


import com.example.exception.InvalidParametersException
import com.example.model.ErrorMessage
import com.example.service.cache.CacheService
import com.example.service.proxy.HttpClientProxy
import com.example.service.proxy.ProxyService
import com.example.validation.ParameterValidator
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val proxyService = ProxyService(CacheService(), HttpClientProxy())
    routing {
        get("/api/suggest/address") {
            val parameters = call.parameters
             /*
            * Подробная алидация параметров пропущена, и отдается на откуп целевому API.
            * */
            try{
                ParameterValidator.validate(parameters)
                val suggestions = proxyService.getSuggestions("http://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address", parameters)
                if(suggestions != null) {
                    call.respondText(status = HttpStatusCode.OK, text = suggestions)
                } else {
                    call.respond(HttpStatusCode.OK, ErrorMessage("не найдено подсказок!"))
                }
            } catch (e: InvalidParametersException){
                log.error(e.stackTraceToString())
                call.respond(HttpStatusCode.BadRequest,ErrorMessage(e.message.toString()))
            }

        }
    }
}
