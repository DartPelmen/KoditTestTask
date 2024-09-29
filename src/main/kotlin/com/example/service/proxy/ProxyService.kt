package com.example.service.proxy

import com.example.exception.InvalidStatusException
 import com.example.service.cache.CacheSchema
import com.example.service.cache.CacheService
import com.example.service.cache.HashByParametersGenerator
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import io.ktor.util.logging.*

class ProxyService(
    private val cacheService: CacheService,
    private val proxyRequest: ProxyRequest<String>
) {
    companion object{
        private val LOGGER = KtorSimpleLogger(ProxyService::class.java.simpleName)
    }
    /**
     * Выдает подсказки для заданных параметров запроса с заданного API. Найденные подсказки кэшируются в h2.
     * @param apiUrl адрес API
     * @param parameters параметры запроса
     * @return подсказки с API
     * */
    suspend fun getSuggestions(apiUrl: String, parameters: Parameters): String? {
        val parametersHash = HashByParametersGenerator.md5hash(parameters)
        cacheService.read(parametersHash)?.let {
            LOGGER.info("found in cache")
            return it.answer
        }
        LOGGER.info("not found in cache")
        proxyRequest.url(apiUrl)
        parameters.forEach { key, values ->
            values.forEach {
                proxyRequest.addParameter(key, it)
            }
        }
        var answer: String? = null
        try {
            answer = proxyRequest.get()
            cacheService.create(CacheSchema(apiUrl,parametersHash, Json.encodeToString(answer)))
        } catch (e: InvalidStatusException){
            LOGGER.error(e)
        }
        return answer
    }
}