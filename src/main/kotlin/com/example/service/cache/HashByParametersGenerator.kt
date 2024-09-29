package com.example.service.cache

import io.ktor.http.*
import java.security.MessageDigest

/**
 * Генератор хэшей на основе параметров запроса.
 * Внимание! Реализация не проработана, требуется более эффективный алгоритм!
 * */
object HashByParametersGenerator {
    /**
     * Генерирует hash на основе md5.
     * Внимиание! Реализация не проработана, требуется более эффективный алгоритм!
     * @param parameters набор параметров для генерации hash
     * @return hash md5
     * */
    @OptIn(ExperimentalStdlibApi::class)
    fun md5hash(parameters: Parameters): String {
        return MessageDigest.getInstance("MD5")
            .digest(parameters.entries().toTypedArray().contentDeepToString().toByteArray())
            .toHexString()
    }
}