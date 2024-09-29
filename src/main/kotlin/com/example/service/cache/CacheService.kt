package com.example.service.cache

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class CacheService {
    object Caches : Table() {
        val api = text(name = "api")
        val hash = text("hash")
        val answer = text("answer")

        override val primaryKey = PrimaryKey(hash)
    }

    init {
        transaction(Database.database) {
            SchemaUtils.create(Caches)
        }
    }

    suspend fun create(cache: CacheSchema) = dbQuery {
        Caches.insert {
            it[api] = cache.api
            it[hash] = cache.parameterHash
            it[answer] = cache.answer
        }[Caches.hash]
    }

    suspend fun read(parameterHash: String): CacheSchema? {
        return dbQuery {
            Caches.selectAll().where { Caches.hash eq parameterHash }
                .map { CacheSchema(it[Caches.api], it[Caches.hash], it[Caches.answer]) }
                .singleOrNull()
        }
    }

    suspend fun update(parameterHash: String, user: CacheSchema) {
        dbQuery {
            Caches.update({ Caches.hash eq parameterHash }) {
                it[api] = user.api
                it[answer] = user.answer
            }
        }
    }

    suspend fun delete(parameterHash: String) {
        dbQuery {
            Caches.deleteWhere { hash.eq(parameterHash) }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}