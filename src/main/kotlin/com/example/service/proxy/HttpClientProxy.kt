package com.example.service.proxy

import com.example.exception.InvalidStatusException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

/**
 * Реализация прокси-клиента на основе Ktor Client.
 * Примечание: поскольку целевой сервис и так выдает данные в JSON, можно опустить конвертацию данных в "свой" json =)
 * */
class HttpClientProxy : ProxyRequest<String> {
    private var apiUrl: String = ""

    /**
     * Карта параметров
     * */
    private val parameters = mutableMapOf<String, MutableList<String>>()
    /**
     * Добавляет API ключ к запросу
     * Важно! Ключ добавлен просто строкой для упрощения кода!
     * */

    /**
     * добавялет параметр в формате ключ-значение
     * @param key имя параметра
     * @param value значение параметра
     * */
    override fun addParameter(key: String, value: String) {
        if (parameters[key] != null) {
            parameters[key]!!.add(value)
        } else {
            parameters[key] = mutableListOf(value)
        }
    }

    /**
     * задает целевой url
     * @param url url для запроса
     * */
    override fun url(url: String) {
        apiUrl = url
    }

    /**
     * Выполнить запрос на основе поданных ранее параметров
     * @return подсказки с целевого url
     * @throws InvalidStatusException если запрос не будет успешен (HTTP 200), получим искключение
     * */
    override suspend fun get(): String {

        val response = client.get(apiUrl) {
            headers {
                append("Authorization", "Token c81b424020fd4fa62e38a495d38076d3d8475c7e")
            }
            url {
                this@HttpClientProxy.parameters.forEach { param ->
                    param.value.forEach {
                        parameters.append(param.key, it)
                    }
                }
            }
        }
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