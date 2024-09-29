package com.example.exception


/**
 * Показывает, что переданы некорректные параметры запроса
 * */
class InvalidParametersException(message: String): Exception(message)