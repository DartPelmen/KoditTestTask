package com.example.model

import kotlinx.serialization.Serializable

/**
 * Используентся для выдачи сообщений об ошибках
 * */
@Serializable
data class ErrorMessage(val message: String)