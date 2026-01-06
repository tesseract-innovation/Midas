package com.midasmoney.core.data.room.repository

import com.midasmoney.core.data.room.dao.IDao
import com.midasmoney.core.data.room.entity.IEntity
import com.midasmoney.domain.repository.IRepository
import com.midasmoney.domain.repository.mapper.IEntityMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository<T, U : IEntity> : IRepository<T> {
    abstract val dao: IDao<U>
    abstract val entityMapper: IEntityMapper<U, T>

    override suspend fun insert(item: T): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val entity = entityMapper.toEntity(item)
                dao.insert(entity)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun insert(items: List<T>): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val entities = mutableListOf<U>()
                items.forEach { item ->
                    val entity = entityMapper.toEntity(item)
                    entities.add(entity)
                }
                dao.insert(entities)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun update(item: T): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val entity = entityMapper.toEntity(item)
                dao.update(entity)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun delete(item: T): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val entity = entityMapper.toEntity(item)
                dao.delete(entity)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
