package com.example.plugins


import com.example.exception.InvalidParametersException
import com.example.model.ErrorMessage
import com.example.service.cache.CacheService
import com.example.service.proxy.HttpClientProxy
import com.example.service.proxy.ProxyService
import com.example.validation.BodyValidator
import com.example.validation.ParameterValidator
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val httpClientProxy = HttpClientProxy()
    httpClientProxy.apiKey("Token APIKEY")
    val proxyService = ProxyService(CacheService(),httpClientProxy)
    routing {
        post("/api/suggest/address") {
            val parameters = call.parameters
            val body = call.receiveText()
            println("body is $body")
             /*
            * Подробная алидация параметров пропущена, и отдается на откуп целевому API.
            * */
            if (ParameterValidator.validate(parameters) || BodyValidator.validateBody(body)){
                val suggestions = proxyService.getSuggestions("http://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address", parameters, body)
                if(suggestions != null) {
                    call.respondText(status = HttpStatusCode.OK, text = suggestions)
                } else {
                    call.respond(HttpStatusCode.OK, ErrorMessage("не найдено подсказок!"))
                }
            } else {
                log.error("no query parameter")
                call.respond(HttpStatusCode.BadRequest,ErrorMessage("no query parameter"))
            }

        }
    }
}
