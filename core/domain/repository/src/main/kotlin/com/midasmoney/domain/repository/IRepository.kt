package com.midasmoney.domain.repository

import kotlinx.coroutines.flow.Flow

interface IRepository <T> {
    suspend fun insert(item: T): Result<Unit>
    suspend fun insert(items: List<T>): Result<Unit>
    suspend fun update(item: T): Result<Unit>
    suspend fun delete(item: T): Result<Unit>
    suspend fun getById(id: String): T?
    suspend fun getAll(): Flow<List<T>>
}
