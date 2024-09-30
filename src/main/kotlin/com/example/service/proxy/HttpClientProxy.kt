package com.example.service.proxy

import com.example.exception.InvalidStatusException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.reflect.*

/**
 * Реализация прокси-клиента на основе Ktor Client.
 * Примечание: поскольку целевой сервис и так выдает данные в JSON, можно опустить конвертацию данных в "свой" json =)
 * */
class HttpClientProxy : ProxyRequest<String> {
    private val requestBuilder = HttpRequestBuilder()
    init {
        requestBuilder.headers{
            append("Content-Type", "Application/Json")
        }
    }

    /**
     * Добавляет API ключ к запросу
     * Важно! Ключ добавлен просто строкой для упрощения кода!
     * */
    override fun apiKey(key: String){
        requestBuilder.headers {
            append("Authorization", "Token c81b424020fd4fa62e38a495d38076d3d8475c7e")
        }
    }
    /**
     * добавялет параметр в формате ключ-значение
     * @param key имя параметра
     * @param value значение параметра
     * */
    override fun addParameter(key: String, value: String) {
        requestBuilder.parameter(key, value)
    }
    /**
     * добавялет тело в формате json
     * @param key имя параметра
     * @param value значение параметра
     * */
    override fun body(value: String) {
        requestBuilder.setBody(value)
    }

    /**
     * задает целевой url
     * @param url url для запроса
     * */
    override fun url(url: String) {
        requestBuilder.url(url)
    }

    /**
     * Выполнить запрос на основе поданных ранее параметров
     * @return подсказки с целевого url
     * @throws InvalidStatusException если запрос не будет успешен (HTTP 200), получим искключение
     * */
    override suspend fun post(): String {
        val response = client.post(requestBuilder)
        println(response.bodyAsText())

        if (response.status != HttpStatusCode.OK) {
            throw InvalidStatusException("HTTP CODE IS NOT 200")
        }
        return response.body()
    }

    companion object {
        /**
         * Клиент, на основе которого выполняются запросы.
         * Создается в единственном экземпляре
         * */
        private val client = HttpClient()
    }
}