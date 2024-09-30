package com.example.service.proxy

/**
 * Описание функционала прокси-клиента
 * */
interface ProxyRequest<T> {
    /**
     * выполнить запрос
     * @return результат выполнения запроса
     * */
    suspend fun post():T
    fun url(url: String)
    fun addParameter(key: String, value: String)
    fun body(value: String)
    fun apiKey(key: String)
}