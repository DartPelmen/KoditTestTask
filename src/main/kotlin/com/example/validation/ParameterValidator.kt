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
     * @throws InvalidParametersException
     * */
    fun validate(parameters: Parameters) {
        if (!parameters.contains("query")) {
            throw InvalidParametersException("нет обязательного параметра query")
        }
    }
}