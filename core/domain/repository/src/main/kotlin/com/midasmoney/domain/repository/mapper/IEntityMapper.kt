package com.midasmoney.domain.repository.mapper

interface IEntityMapper<E, D> {
    fun toDomain(entity: E): D
    fun toEntity(domain: D): E
}
