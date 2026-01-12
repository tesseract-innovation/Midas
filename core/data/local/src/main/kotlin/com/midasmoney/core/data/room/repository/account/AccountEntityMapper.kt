package com.midasmoney.core.data.room.repository.account

import com.midasmoney.core.data.room.entity.AccountEntity
import com.midasmoney.core.domain.model.Account
import com.midasmoney.core.domain.model.Balance
import com.midasmoney.core.domain.model.IconModel
import com.midasmoney.core.domain.model.IconType
import com.midasmoney.core.util.UUID
import com.midasmoney.domain.repository.mapper.IAccountEntityMapper

object AccountEntityMapper : IAccountEntityMapper<AccountEntity> {
    override fun toDomain(entity: AccountEntity): Account {
        return entity.run {
            Account(
                id = UUID(id),
                name = name,
                icon = IconModel(IconType.valueOf(icon)),
                color = color,
                balance = Balance(
                    id = UUID.randomUUID(),
                    initialBalance = initialBalance,
                    currentBalance = balance,
                    income = income,
                    expense = expense
                ),
                transactions = emptyList()
            )
        }
    }

    override fun toEntity(domain: Account): AccountEntity {
        return domain.run {
            AccountEntity(
                id = id.toString(),
                name = name,
                icon = icon.iconType.name,
                color = color,
                initialBalance = balance.initialBalance,
                balance = balance.currentBalance,
                income = balance.income,
                expense = balance.expense
            )
        }
    }
}
