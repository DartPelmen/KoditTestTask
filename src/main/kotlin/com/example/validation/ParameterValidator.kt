package com.example.validation

import com.example.exception.InvalidParametersException
import io.ktor.http.*

/**
 * Валидатор параметров запроса.
 * */
object ParameterValidator {
    /**
     * Внимание! Подробная проверка параметров опущена для упрощения понимания кода!
     * @param parameters параметры запроса для проверки
     * @return наличие параметра query среди параметров запроса
     * */
    fun validate(parameters: Parameters):Boolean {
        return parameters.contains("query")
    }
}