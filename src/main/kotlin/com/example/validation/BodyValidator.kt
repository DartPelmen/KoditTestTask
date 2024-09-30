package com.example.validation

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

object BodyValidator {
    /**
     * Внимание! Подробная проверка тела опущена для упрощения понимания кода!
     * @param body тело запроса для проверки
     * @return наличие параметра query в теле запроса
     * */
    fun validateBody(body: String): Boolean{
        return Json.parseToJsonElement(body).jsonObject.contains("query")
    }
}