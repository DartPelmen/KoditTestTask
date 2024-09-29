package com.example.service.cache

/**
 * Описание таблицы для хранения ответов от API.
 *
 * @param api адрес API, куда было проведено обращение
 * @param parameterHash хэш комбинации параметров
 * @param answer ответ от api
 * */
data class CacheSchema(val api: String, val parameterHash: String, val answer: String)