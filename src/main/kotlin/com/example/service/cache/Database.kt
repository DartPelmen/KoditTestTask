package com.example.service.cache

import org.jetbrains.exposed.sql.Database
/**
 * Одиночка для работы с БД
 * */
object Database {
    val database = Database.connect(
        url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        user = "root",
        driver = "org.h2.Driver",
        password = "",
    )
}