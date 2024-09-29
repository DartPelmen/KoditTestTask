package com.example.service.proxy

/**
 * Описание функционала прокси-клиента
 * */
interface ProxyRequest<T> {
    /**
     * выполнить запрос
     * @return результат выполнения запроса
     * */
    suspend fun get():T
    fun url(url: String)
    fun addParameter(key: String, value: String)
}