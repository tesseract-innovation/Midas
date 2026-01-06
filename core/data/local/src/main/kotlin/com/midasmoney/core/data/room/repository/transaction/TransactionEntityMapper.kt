package com.midasmoney.core.data.room.repository.transaction

import com.midasmoney.core.data.room.entity.TransactionEntity
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.core.domain.model.IconType
import com.midasmoney.core.domain.model.Transaction
import com.midasmoney.core.domain.model.TransactionStatus
import com.midasmoney.core.domain.model.TransactionType
import com.midasmoney.core.domain.model.extension.formatAmountValue
import com.midasmoney.core.util.UUID
import com.midasmoney.domain.repository.mapper.ITransactionEntityMapper
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
object TransactionEntityMapper : ITransactionEntityMapper<TransactionEntity> {
    override fun toDomain(entity: TransactionEntity): Transaction {
        return entity.run {
            Transaction(
                accountId = UUID(accountId),
                icon = IconModel(IconType.valueOf(icon)),
                color = color,
                title = title,
                description = description,
                amount = amount,
                type = TransactionType.getByName(type),
                status = TransactionStatus.getByName(status),
                date = date,
                time = time,
                createAt = Instant.fromEpochSeconds(createdAt.toLong()),
                id = UUID(id),
            )
        }
    }

    override fun toEntity(domain: Transaction): TransactionEntity {
        return domain.run {
            TransactionEntity(
                accountId = accountId.toString(),
                icon = icon.iconType.name,
                color = color,
                title = title,
                description = description,
                type = type.name,
                status = status.name,
                amount = formatAmountValue(),
                date = date,
                time = time,
                createdAt = createAt.epochSeconds.toString(),
                id = id.toString()
            )
        }
    }
}
